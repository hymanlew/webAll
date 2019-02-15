package settingModel2;

import java.util.ArrayList;

/**
 * 原型模式（Prototype Pattern）： 通过复制现有的对象实例来创建新的对象实例。
 *
 * 优点：
     1，使用原型模型创建一个对象比直接new一个对象更有效率，因为它直接操作内存中的二进制流，特别是复制大对象时，性能的差别非常明显。
     2，隐藏了制造新实例的复杂性，使得创建对象就像我们在编辑文档时的复制粘贴一样简单。

    缺点：
     1，由于使用原型模式复制对象时不会调用类的构造方法，所以原型模式无法和单例模式组合使用，因为原型类需要将clone方法的作用域修改为public类型，那么单例模式的条件就无法满足了。
     2，使用原型模式时不能有final对象。
     3，Object类的clone方法只会拷贝对象中的基本数据类型，对于数组，引用对象等只能另行拷贝。这里涉及到深拷贝和浅拷贝的概念。


     浅拷贝：将一个对象复制后，基本数据类型的变量都会重新创建，而引用类型，指向的还是原对象所指向的（这样不安全）。
     深拷贝：将一个对象复制后，不论是基本数据类型还有引用类型，都是重新创建的。
     那么深拷贝如何具体实现呢？继续 demo 的例子，增加了一个ArrayList属性。


 */
public class Mail implements Cloneable {

    //收件人
    private String receiver;
    //主题
    private String subject;
    //内容
    private String content;
    //尾语
    private String tail;
    //深拷贝，用于拷贝数组及引用类型
    private ArrayList<String> ars;

    public Mail(EventTemplate et){
        this.tail = et.geteventContent();
        this.subject = et.geteventsubject();
    }

    @Override
    public Mail clone(){
        Mail mail = null;
        try {
            mail = (Mail) super.clone();
            mail.ars = (ArrayList<String>)this.ars.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return mail;
    }

    //get、set .....
    public String getContent() {
        return content;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "receiver='" + receiver + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", tail='" + tail + '\'' +
                '}';
    }

    //测试方法
    public static void sendMail(Mail mail){
        System.out.println(mail);
    }

    public static void main(String[] args) {
        int i = 0;
        int MAX_COUNT = 10;
        EventTemplate et = new EventTemplate("邀请函","允儿啦啦");

        Mail mail = new Mail(et);
        while (i<MAX_COUNT){
            Mail cloneMail = mail.clone();
            cloneMail.setContent("XXX先生"+mail.getTail());
            cloneMail.setReceiver("hello@163.com");
            //发送邮件的方法
            sendMail(cloneMail);
            i++;
        }
    }
}
