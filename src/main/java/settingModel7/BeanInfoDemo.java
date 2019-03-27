package settingModel7;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaAPI之BeanInfo接口:
 *
 * 该接口提供了有关当前 bean 的所有显式信息的，并且实现了此接口的 bean 的某个 BeanInfo 类，该类实现 BeanInfo 接口并提供有关其 bean 的
 * 方法、属性、事件等显式信息。
 *
 * bean 实现类不必提供一组完整的显式信息。可以挑选出希望提供的信息，其余部分将通过使用 bean 类方法的低级别反射和应用标准设计模式的自动分析
 * 来获得。
 * 其底层使用的就是 java 的反射机制。
 *
 * 以下案例就是java反射的应用：用反射获取bean的get方法，set方法，所有的私有公有属性。
 */
public class BeanInfoDemo {

    /**
     * 将一个bean对象通过get方法插入到表中，本方法可以动态的生成sql语句
     * 将对象的sql语句生成
     * suffixs长度为空，则对所有的field进行插入
     * table为插入的表名
     */
    public static String getInsertSql(Class classname,List<String> suffixs,String table,String beanname){

        if("".equals(table)){
            System.out.println("表名为空");
            return null;
        }
        String sqlparam = "";
        String sql = "insert into "+table+" (sqlparam) values(";
        String bean = beanname;
        if("".equals(bean)){
            //获得类名并且将第一个字母转为小写
            int index = classname.getName().lastIndexOf(".")+1;
            bean = classname.getName().substring(index);
            bean = bean.substring(0,1).toLowerCase()+bean.substring(1);
        }
        //获得这个类的所有方法
        Method[] methods = classname.getMethods();

        //是否取所有的fields
        if(suffixs.size()==0){

            //获得私有属性，field的意思是“字段”,这里可以理解成属性变量
            Field[] fields = classname.getDeclaredFields();
            for(Field field:fields){
                suffixs.add(field.getName());
            }

            //获得共有属性
            fields = classname.getFields();
            for(Field field:fields){
                suffixs.add(field.getName());
            }
        }
        System.out.println("fields : "+suffixs);
        System.out.println();

        for(String sufix:suffixs){
            for(Method method:methods){
                //判断是否有 get方法
                if(method.getName().toLowerCase().equals("get"+sufix)){
                    try {
                        //Field field = className.getDeclaredField(suffix);
                        // 该方法返回一个 class类型数据，所以可作为判断依据
                        if(method.getReturnType()==java.lang.String.class){

                            sqlparam += sufix+",";
                            sql += "\""+bean +"."+method.getName()+"()"+"\",";
                        }else{
                            if(method.getReturnType()==int.class){
                                sqlparam += sufix+",";
                                sql += "\""+bean+"."+method.getName()+"()"+"\",";
                            }
                        }
                    }catch (SecurityException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        sql = sql.replace("(sqlparam)", "("+sqlparam.substring(0, sqlparam.length()-1)+")");
        sql = sql.substring(0,sql.length()-1)+")";
        return sql;
    }

    private static void getSql(Class classname) throws IntrospectionException {
        /**
         * Java内省：是指Java语言对Bean类属性、事件的一种缺省处理方法。
         * 而 Introspector类就是 Java中的内省类。
         *
         * Java内省的作用：一般在开发框架时，当需要操作一个JavaBean时，如果一直用反射来操作，显得很麻烦；所以sun公司开发一套API专门
         * 来用来操作JavaBean。即 Introspector类。
         */
        try{
            //在 Java Bean上进行内省，获得bean类，并了解其所有属性、公开的方法和事件。
            //如果 Java Bean的 BeanInfo类以前已经被内省，则从 BeanInfo 缓存中检索 BeanInfo 类。
            BeanInfo beanInfo = Introspector.getBeanInfo(classname);

            //PropertyDescriptor 类（属性描述），是指 Java Bean通过一对存储器的方法导出的一个属性，包括读写属性的值。
            //获得所有的属性描述
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for(PropertyDescriptor pd:propertyDescriptors){

                //获得读写方法，pd.getName() 默认都是输出小写的字段名，所以 javaBean中属性名不能大写
                System.out.println("property name :"+pd.getName());
                System.out.println("read method :"+pd.getReadMethod());   //读
                System.out.println("write method :"+pd.getWriteMethod());  //写
                System.out.println("---------------------------------------------");
                System.out.println();
            }
        }catch (IntrospectionException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IntrospectionException {

        ContentWordBean content = new ContentWordBean();
        List<String> fields = new ArrayList<String>();

        String table = "table";
        String beanname = content.getClass().getName();

        String insertSql = getInsertSql(ContentWordBean.class,fields,table,beanname);
        //getSql(ContentWordBean.class);
        getSql(content.getClass());

        System.out.println("sql :"+insertSql);
    }
}

/**
 * 1，JavaBean是一种特殊的Java类，主要用于传递数据信息，这种java类中的方法主要用于访问私有的字段，且方法名符合某种命名规则。
 * 如果要在两个模块之间传递多个信息，可以将这些信息封装到一个JavaBean中，这种JavaBean的实例对象通常称之为值对象（Value Object，简称VO）。
 *
 * 这些信息在类中用私有字段来存储，如果读取或设置这些字段的值，则需要通过一些相应的方法来访问。即 JavaBean的属性是根据其中的 setter 和
 * getter方法来确定的，而不是根据其中的成员变量。如果方法名为setId，中文意思即为设置id，至于你把它存到哪个变量上，用管吗？如果方法名为
 * getId，中文意思即为获取id，至于你从哪个变量上取，用管吗？去掉set前缀，剩余部分就是属性名，如果剩余部分的第二个字母是小写的，则把剩余部分
 * 的首字母改成小的。
 *
 * 总之，一个类被当作javaBean使用时，JavaBean的属性是根据方法名推断出来的，它根本看不到java类内部的成员变量。
 *
 *
 * 2.一个符合JavaBean特点的类可以当作普通类一样进行使用，但把它当JavaBean用肯定需要带来一些额外的好处，我们才会去了解和应用JavaBean！好处如下：
 *
 * 在Java EE开发中，经常要使用到JavaBean。很多环境就要求按JavaBean方式进行操作，别人都这么用和要求这么做，那你就没什么挑选的余地！
 * JDK中提供了对JavaBean进行操作的一些API，这套API就称为内省。如果要你自己去通过getX方法来访问私有的x，怎么做，有一定难度吧？用内省这套api
 * 操作JavaBean比用普通类的方式更方便。
 *
 */
class ContentWordBean{
    //题目类型
    private String itemType;
    //序号
    private String serial;
    //题目内容
    private String title;
    //答案
    private List<String> answerList;
    //标准答案
    private String lastAnswer;
    //答案解析
    private String analysis;
    //难度系数
    private String koc;
    //分数
    private String score;
    //章名称
    private String chapterName;
    //节名称
    private String sectionName;
    //知识点
    private String knowledge;

    public String getKnowledge() {
        return knowledge;
    }
    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }
    public String getItemType() {
        return itemType;
    }
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    public String getSerial() {
        return serial;
    }
    public void setSerial(String serial) {
        this.serial = serial;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<String> getAnswerList() {
        return answerList;
    }
    public void setAnswerList(List<String> answerList) {
        this.answerList = answerList;
    }
    public String getLastAnswer() {
        return lastAnswer;
    }
    public void setLastAnswer(String lastAnswer) {
        this.lastAnswer = lastAnswer;
    }
    public String getAnalysis() {
        return analysis;
    }
    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }
    public String getKoc() {
        return koc;
    }
    public void setKoc(String koc) {
        this.koc = koc;
    }
    public String getScore() {
        return score;
    }
    public void setScore(String score) {
        this.score = score;
    }
    public String getChapterName() {
        return chapterName;
    }
    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
    public String getSectionName() {
        return sectionName;
    }
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}