package demo;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;

import javax.annotation.Resource;
import java.util.List;

public class ReadWriteDB {

    /**
     * 由于要项目准备上线，所以要准备大量测试数据测试新项目的性能，但是又不太会用存储过程和数据库事务进行数据插入。
     * 最开始使用最简单的java的for循环语句不停的add对象，但是发现这个效率太低，要插入几百万数据估计得一天，于是在网上找了下，找到一篇直接
     * 使用jdbc直接插入数据的，拿下来试了下发现性能虽然比new对象好，但是效率也很低。
     *
     * 只能再找，终于找到一个很高的，也就是使用批次提交方式的sql执行，这个100万数据大概也就30秒左右。
     */


    // 需要 JDBC jar包
    //public static void insertRelease() {
    //    Long begin = new Date().getTime();
    //    String sql = "INSERT INTO tb_big_data (count, create_time, random) VALUES (?, SYSDATE(), ?)";
    //    try {
    //        conn.setAutoCommit(false);
    //        PreparedStatement pst = conn.prepareStatement(sql);
    //        for (int i = 1; i <= 100; i++) {
    //            for (int k = 1; k <= 10000; k++) {
    //                pst.setLong(1, k * i);
    //                pst.setLong(2, k * i);
    //                pst.addBatch();
    //            }
    //            pst.executeBatch();
    //            conn.commit();
    //        }
    //        pst.close();
    //        conn.close();
    //    } catch (SQLException e) {
    //        e.printStackTrace();
    //    }
    //    Long end = new Date().getTime();
    //    System.out.println("cast : " + (end - begin) / 1000 + " s");
    //}

    // 是由 sping—mybatis包提供
    @Resource(name="sqlSessionTemplate")
    protected transient SqlSessionTemplate sqlSessionTemplate;

    public SqlSessionTemplate getSqlSessionTemplate(){
        return sqlSessionTemplate;
    }

    public boolean insertCrossEvaluation(List<Apple> members)
            throws Exception {
        // TODO Auto-generated method stub
        int result = 1;
        SqlSession batchSqlSession = null;

        /**
         * 源码：
         *  public SqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
         *     this(sqlSessionFactory, sqlSessionFactory.getConfiguration().getDefaultExecutorType());
         *  }
         *
         *  public ExecutorType getDefaultExecutorType() {
         *     return defaultExecutorType;
         *  }
         *
         *  protected ExecutorType defaultExecutorType = ExecutorType.SIMPLE;
         *
         *  public enum ExecutorType {
         *     SIMPLE, REUSE, BATCH
         *  }
         *
         *  ExecutorType.SIMPLE: 这个执行器类型不做特殊的事情。它为每个语句的执行创建一个新的预处理语句。就是创建一个PreparedStatement对象，
         *  即便我们使用druid连接池进行处理，依然是每次都会向池中put一次并加入druid的cache中。这个效率可想而知，所以那个异常也有可能是insert
         *  timeout导致等待时间超过数据库驱动的最大等待值。
         *
         *  ExecutorType.REUSE: 这个执行器类型会复用预处理语句。
         *  ExecutorType.BATCH:这个执行器会批量执行所有更新语句,如果 SELECT 在它们中间执行还会标定它们是 必须的,来保证一个简单并易于理解的行为。
         *
         *  SqlSessionFactory 有六个方法可以用来创建 SqlSession 实例。通常来说,如何决定是你 选择下面这些方法时:
         *      Transaction (事务): 你想为 session 使用事务或者使用自动提交(通常意味着很多 数据库和/或 JDBC 驱动没有事务)?
         *      Connection (连接): 你想 MyBatis 获得来自配置的数据源的连接还是提供你自己
         *      Execution (执行): 你想 MyBatis 复用预处理语句和/或批量更新语句(包括插入和 删除)?
         */

        try {
            //batchSqlSession = this.getSqlSessionTemplate()
            batchSqlSession = sqlSessionTemplate
                    .getSqlSessionFactory()
                    .openSession(ExecutorType.BATCH, false);// 获取批量方式的sqlsession

            /**
             * 依旧报相同的错误，看来不仅仅是ExecutorType的问题，那会不会是一次commit的数据量过大导致响应时间过长呢？上面我也提到了这种可能
             * 性，那么就再分批次处理试试，也就是说，在同一事务范围内，分批commit insert batch。
             */
            int batchCount = 1000;// 每批commit的个数
            int batchLastIndex = batchCount;// 每批最后一个的下标

            for (int index = 0; index < members.size();) {
                if (batchLastIndex >= members.size()) {
                    batchLastIndex = members.size();
                    result = result * batchSqlSession.insert("MutualEvaluationMapper.insertCrossEvaluation",members.subList(index, batchLastIndex));
                    batchSqlSession.commit();
                    System.out.println("index:" + index+ " batchLastIndex:" + batchLastIndex);
                    break;// 数据插入完毕，退出循环
                } else {
                    result = result * batchSqlSession.insert("MutualEvaluationMapper.insertCrossEvaluation",members.subList(index, batchLastIndex));
                    batchSqlSession.commit();
                    System.out.println("index:" + index+ " batchLastIndex:" + batchLastIndex);
                    index = batchLastIndex;// 设置下一批下标
                    batchLastIndex = index + (batchCount - 1);
                }
            }
            batchSqlSession.commit();
        }
        finally {
            batchSqlSession.close();
        }
        return result>0;
    }
}
