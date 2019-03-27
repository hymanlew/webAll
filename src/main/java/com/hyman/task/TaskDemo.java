package com.hyman.task;

import com.hyman.dao.TestDao;
import com.hyman.dbmanager.DataSourceContextHolder;
import com.hyman.service.DemoService;
import com.hyman.util.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class TaskDemo {

    /**
     *  定时任务，秒，分，时，日，月，星期，如果不加斜杠则代表每一个上级单位才触发，而加了斜杠才是本单位的间隔时间
     *
     *  0 0 10,14,16 * * ?          每天10点,14点,16点
     *  0 0/30 9-17 * * ?           朝九晚五工作时间内每半小时
     *  0 0 12 ? * WED              每个星期三中午12点
     *  0 0 12 * * ?                每天中午12点
     *  0 15 10 ? * *               每天上午10:15触发
     *  0 15 10 * * ?               每天上午10:15触发
     *  0 15 10 * * ? *             每天上午10:15触发
     *  0 15 10 * * ? 2005          2005年的每天上午10:15触发 
     *  0 * 14 * * ?                在每天下午2点到下午2:59期间的每1分钟触发
     *  0 0/5 14 * * ?              在每天下午2点到下午2:55期间的每5分钟触发
     *  0 0/5 14,18 * * ?           在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发 
     *  0 0-5 14 * * ?              在每天下午2点到下午2:05期间的每1分钟触发
     *  0 10,44 14 ? 3 WED          每年三月的星期三的下午2:10和2:44触发
     *  0 15 10 ? * MON-FRI         周一至周五的上午10:15触发
     *  0 15 10 15 * ?              每月15日上午10:15触发 
     *  0 15 10 L * ?               每月最后一日的上午10:15触发 
     *  0 15 10 ? * 6L              每月的最后一个星期五上午10:15触发
     *  0 15 10 ? * 6L 2002-2005    2002年至2005年的每月的最后一个星期五上午10:15触发 
     *  0 15 10 ? * 6#3             每月的第三个星期五上午10:15触发
     *
     */

    //@Resource
    //private ControllerDemo controllerDemo;
    @Resource(name="service")
    private DemoService demoService;
    @Resource
    private TestDao testDao;

    @Scheduled(cron = "0/5 * * * * ?")
    public void te1(){
        demoService.checkXML();
        testDao.checkXML();
        //controllerDemo.checkXML();
        System.out.println("=== 定时任务 ===");
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    //@Scheduled(cron = "0/5 * * ? * *")
    public void test(){

        String date = DateUtil.fmPattern(new Date(),"mm:ss");
        System.out.println(date);
        System.out.println("--------------test task----------------");
    }


    @Scheduled(cron = "0 0/10 * ? * *")
    public void test1(){
        try{
            /**
             * 先设定为源数据库，拿到数据然后再放到目标数据库：
             *
             * 在该业务方法内如果操作只是执行一遍，则不必指定源数据库，因为在 xml 和数据库切换类中已经定义好了。
             *
             * 但如果是循环遍历的执行，则必须指定源数据库，否则在指定目标数据库之后，就会一直连接在目标数据库，而拿不到源
             * 数据库的数据了。
             */

            // 从原测试数据库中拿到数据
            DataSourceContextHolder.setDataSource(DataSourceContextHolder.DATA_SOURCE_TEST);
            System.out.println("======= 1 ======="+DataSourceContextHolder.getDataSource());
            //Data data = DataService.get(num);

            // 然后切换数据库之后，把拿到的数据放到真实数据库中。
            DataSourceContextHolder.setDataSource(DataSourceContextHolder.DATA_SOURCE_REAL);
            //DataService.update(type);

            System.out.println("======= 2 ========"+DataSourceContextHolder.getDataSource());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
