package settingModel9;

/**
 * 迪米特法则又叫最少知道原则，通俗的来讲，就是一个类对自己依赖的类知道的越少越好。也就是说对于被依赖的类来说，无论逻辑多么复杂，都尽量地的
 * 将逻辑封装在类的内部，对外除了提供的public方法，不对外泄漏任何信息。
 *
 * 自从我们接触编程开始，就知道了软件编程的总的原则：低耦合，高内聚。无论是面向过程编程还是面向对象编程，只有使各个模块之间的耦合尽量的低，
 * 才能提高代码的复用率。低耦合的优点不言而喻，但是怎么样编程才能做到低耦合呢？那正是迪米特法则要去完成的。
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 有一个集团公司，下属单位有分公司和直属部门，现在要求打印出所有下属单位的员工ID。先来看一下违反迪米特法则的设计。
 *
 * 总公司员工：
 */
public class Employee {
    private String id;

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }
}

//分公司员工
class SubEmployee{
    private String id;
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return id;
    }
}

//分公司
class SubCompanyManager{
    public List<SubEmployee> getAllEmployee(){
        List<SubEmployee> list = new ArrayList<SubEmployee>();
        for(int i=0; i<100; i++){
            SubEmployee emp = new SubEmployee();
            //为分公司人员按顺序分配一个ID
            emp.setId("分公司"+i);
            list.add(emp);
        }
        return list;
    }

    // 修改后的代码：
    public void printEmployee(){
        List<SubEmployee> list = this.getAllEmployee();
        for(SubEmployee e:list){
            System.out.println(e.getId());
        }
    }
}

//总公司
class CompanyManager{
    public List<Employee> getAllEmployee(){
        List<Employee> list = new ArrayList<Employee>();
        for(int i=0; i<30; i++){
            Employee emp = new Employee();
            //为总公司人员按顺序分配一个ID
            emp.setId("总公司"+i);
            list.add(emp);
        }
        return list;
    }

    //public void printAllEmployee(SubCompanyManager sub){
    //    List<SubEmployee> list1 = sub.getAllEmployee();
    //    for(SubEmployee e:list1){
    //        System.out.println(e.getId());
    //    }
    //
    //    List<Employee> list2 = this.getAllEmployee();
    //    for(Employee e:list2){
    //        System.out.println(e.getId());
    //    }
    //}


    // 修改后的代码：
    public void printAllEmployee(SubCompanyManager sub){
        sub.printEmployee();
        List<Employee> list2 = this.getAllEmployee();
        for(Employee e:list2){
            System.out.println(e.getId());
        }
    }
}

class Client{
    public static void main(String[] args){
        CompanyManager e = new CompanyManager();
        e.printAllEmployee(new SubCompanyManager());
    }
}


/**
 * 根据迪米特法则，只与直接的朋友发生通信，而SubEmployee类并不是CompanyManager类的直接朋友（以局部变量出现的耦合不属于直接朋友），从逻
 * 辑上讲总公司只与他的分公司耦合就行了，与分公司的员工并没有任何联系，这样设计显然是增加了不必要的耦合。按照迪米特法则，应该避免类中出现这样
 * 非直接朋友关系的耦合。
 *
 * 直接的朋友：就是指每个对象都会与其他对象有耦合关系，只要两个对象之间有耦合关系，我们就说这两个对象之间是朋友关系。耦合的方式很多，依赖、
 * 关联、组合、聚合等。其中我们称出现成员变量、方法参数、方法返回值中的类为直接的朋友，而出现在局部变量中的类则不是直接的朋友（以局部变量出现
 * 的耦合不属于直接朋友）。也就是说陌生的类最好不要作为局部变量的形式出现在类的内部。
 *
 * 修改后的代码如下：标注修改后的代码。
 *
 * 修改后，为分公司增加了打印人员ID的方法，总公司直接调用来打印，从而避免了与分公司的员工发生耦合。
 */
