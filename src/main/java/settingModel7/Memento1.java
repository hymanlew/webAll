package settingModel7;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *  在上面的例子中，Originator类只有一个state变量需要备份，而通常情况下，发起人角色通常是一个javaBean，对象中需要备份的变量不止
 *  一个，需要备份的状态也不止一个，这就是多状态多备份备忘录。备份的信息则需要一个 map进行存储即可。
 *
 *  实现备忘录的方法很多，备忘录模式有很多变形和处理方式，像通用代码那样的方式一般不会用到，多数情况下的备忘录模式，是多状态多备份的。其实
 *  实现多状态多备份也很简单，最常用的方法是，我们在Memento中增加一个Map容器来存储所有的状态，在Caretaker类中同样使用一个Map容器才存储
 *  所有的备份。下面我们给出一个多状态多备份的例子：
 */
// 备忘录模板
public class Memento1 {
    private Map<String,Object> stateMap;

    public Memento1(Map<String,Object> map){
        this.stateMap = map;
    }

    public Map<String, Object> getStateMap() {
        return stateMap;
    }

    public void setStateMap(Map<String, Object> stateMap) {
        this.stateMap = stateMap;
    }
}

// 备忘录细节，备份，恢复
class BeanUtils{
    public static Map<String,Object> backup(Object bean){
        Map<String,Object> result = new HashMap<String, Object>();
        try{
            // BeanInfo接口提供了有关当前 bean 的所有显式信息的，并且实现了此接口的 bean 的某个 BeanInfo 类，该类实现 BeanInfo 接口
            // 并提供有关其 bean 的方法、属性、事件等显式信息。
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            for(PropertyDescriptor des : descriptors){
                String fieldName = des.getName();

                // 该方法可以执行忽略大小写的比较，当比较两个字符串时，它会认为A-Z和a-z是一样的。
                if(!fieldName.equalsIgnoreCase("class")){
                    Method getter = des.getReadMethod();
                    Object fieldValue = getter.invoke(bean);
                    result.put(fieldName,fieldValue);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static void restoreProp(Object bean,Map<String,Object> propMap){
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            for(PropertyDescriptor des : descriptors){
                String fieldName = des.getName();
                if(propMap.containsKey(fieldName)){
                    Method setter = des.getWriteMethod();
                    setter.invoke(bean,new Object[]{propMap.get(fieldName)});
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

// 发起人
class Originator1{
    private String state1 = "";
    private String state2 = "";
    private String state3 = "";

    public String getState1() {
        return state1;
    }

    public void setState1(String state1) {
        this.state1 = state1;
    }

    public String getState2() {
        return state2;
    }

    public void setState2(String state2) {
        this.state2 = state2;
    }

    public String getState3() {
        return state3;
    }

    public void setState3(String state3) {
        this.state3 = state3;
    }

    @Override
    public String toString() {
        return "Originator1{" +
                "state1='" + state1 + '\'' +
                ", state2='" + state2 + '\'' +
                ", state3='" + state3 + '\'' +
                '}';
    }

    public Memento1 createMemento(){
        return new Memento1(BeanUtils.backup(this));
    }

    public void restoreMemento(Memento1 memento1){
        BeanUtils.restoreProp(this,memento1.getStateMap());
    }
}

// 看管者
class Caretaker1{
    private Map<String,Memento1> memento1Map = new HashMap<String, Memento1>();

    public Memento1 getMemento1(String s) {
        return memento1Map.get(s);
    }

    public void setMemento1(String s, Memento1 memento1) {
        this.memento1Map.put(s,memento1);
    }
}

class Client{
    public static void main(String[] args) {
        Originator1 ori = new Originator1();
        Caretaker1 caret = new Caretaker1();

        ori.setState1("中国");
        ori.setState2("强盛");
        ori.setState3("繁荣");
        System.out.println("===初始化状态===\n"+ori);
        caret.setMemento1("001",ori.createMemento());

        ori.setState1("软件");
        ori.setState2("架构");
        ori.setState3("优秀");
        System.out.println("===修改后状态===\n"+ori);

        ori.restoreMemento(caret.getMemento1("001"));
        System.out.println("===恢复后状态===\n"+ori);
    }
}