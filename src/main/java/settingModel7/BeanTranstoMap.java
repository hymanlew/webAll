package settingModel7;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 可以使用 BeanInfo实现 bean实体与 map之间的互相转换。
 */
public class BeanTranstoMap {

    // 实体转 map
    private Map<String,Object> beanToMap(User user) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Map<String,Object> map = new HashMap<String, Object>();

        /**
         * getClass()是一个对象实例的方法（继承自 object，是获得一个实例的类型类），只有对象实例才有这个方法，具体的类是没有的。
         * 而类的 Class类实例是通过 .class获得的，类没有 .getClass()方法。
         *
         * 类型类（.class）指的是代表一个类型的类，因为一切皆是对象，类型也不例外，在Java使用类型类来表示一个类型。所有的类型类都是Class类的实例。
         *
         * 而两者都是用于表示一个具体类的类实例，所以在 java反射机制中，类对象和类实例对象表示的都是同一个对象。
         * 因此如果你知道一个实例，那么你可以通过实例的 “getClass()”方法获得该对象的类型类，如果你知道一个类型，那么你可以使用
         * “.class” 的方法获得该类型的类型类。
         */
        BeanInfo beanInfo = Introspector.getBeanInfo(user.getClass());
        //BeanInfo beanInfo = Introspector.getBeanInfo(User.class);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        for(PropertyDescriptor pd:propertyDescriptors){

            // pd.getName()该方法会取到对象中的每个成员变量，并且默认都自动会转为小写，但使用 getter/setter 方法时大小写不影响结果
            String key = pd.getName();
            // 过滤class属性，因为 beanInfo在读取到所有的属性信息时，会先自动生成加载一个实例类对象 class。
            if(!key.equals("class")){

                // 得到property对应的getter方法
                Method getter = pd.getReadMethod();

                /**
                 *  invoke(class)，是动态地调用并执行类对象中的方法，即把方法参数化（执行无参方法）
                 *  invoke(class, object[])，是动态地调用并执行类中的方法，并把参数数组传入（执行有参方法）
                 *
                 *  而当前的程序执行的是 getter 方法，并且对象中已经包含有参数了，所以直接放入 class对象即可
                 */
                Object value = getter.invoke(user);
                map.put(key,value);
            }
        }
        return map;
    }

    // map 转实体
    private User mapToBean(Map<String, Object> map) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        User user = new User();
        BeanInfo beanInfo = Introspector.getBeanInfo(user.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        for(PropertyDescriptor pd : propertyDescriptors){

            // pd.getName()该方法会取到对象中的每个成员变量，并且默认都自动会转为小写，并且 getter/setter 方法时大小写不影响结果
            // 但是需要注意因为 key默认是小写，所以在 map中的 key也必须是小写，否则不会进入 setter 方法中。
            String key = pd.getName();

            // 自动过滤class属性，因为 beanInfo在读取到所有的属性信息时，会自动先生成加载一个实例类对象 class。
            if(map.containsKey(key)){
                Object value = map.get(key);
                //得到property对应的setter方法
                Method setter = pd.getWriteMethod();
                // invoke(class, object[]{})，是动态地调用并执行类中的方法，并把参数数组传入（执行有参方法）
                // 而当前程序是依据 classc对象中的属性来执行 setter 方法的，所以参数只能是指定的类型，而不能使用数组
                setter.invoke(user,value);
            }
        }
        return user;
    }

    public BeanTranstoMap() {
        try{
            Map<String,Object> usermap = beanToMap(new User(1,"lili","111"));

            /**
             *  Map().keySet();
             *  Map().values();
             *  entrySet() = key + value = set< entry<> >
             *
             *  获取迭代器 Iterator< Object> iter = list/set.iterator()
             */
            Iterator<Map.Entry<String,Object>> iterator = usermap.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String,Object> next = iterator.next();
                System.out.println(next.getKey()+" , "+next.getValue());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        BeanTranstoMap b1 = new BeanTranstoMap();

        System.out.println();
        Map<String,Object> map = new HashMap<String, Object>();

        //因为当前程序是依据 classc对象中的属性来执行 setter 方法的，所以参数只能是指定的类型，而不能使用数组
        //int[] id = {2};
        //map.put("id",id);

        map.put("id",2);
        map.put("name","lusi");
        map.put("password","222");
        try {
           User user = b1.mapToBean(map);
           System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class User{

    private String name;
    private String password;
    private int Id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", Id=" + Id +
                '}';
    }

    public User(){}

    public User(int index, String s,String password){
        this.Id = index;
        this.name = s;
        this.password = password;
    }
}