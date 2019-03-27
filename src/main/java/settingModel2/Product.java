package settingModel2;

/**
 * 生成器模式（builder pattern）：
 *      是将一个复杂对象的构建与它的表示（对象的结构方式）分离，使得同样的构建过程可以创建不同的表示。生成器模式利用一个导演者对象和具体建造者对象一个一个地建造出所有的零件，从而建造出完整的对象。
 *
 * 四个要素：
 *        Builder：生成器接口，定义创建一个Product对象所需要的各个部件的操作。
 *       ConcreteBuilder：具体的生成器实现，实现各个部件的创建，并负责组装Product对象的各个部件，同时还提供一个让用户获取组装完成后的产品对象的方法。
 *       Director：指导者，也被称导向者，主要用来使用Builder接口，以一个统一的过程来构建所需要的Product对象。
 *       Product：产品，表示被生成器构建的复杂对象，包含多个部件。
 *
 *
 * 定义一个产品类 ：
 */
public class Product {
    Entity1 entity1;
    Entity2 entity2;
    Entity3 entity3;
}

class Entity1{}
class Entity2{}
class Entity3{}


//实体类
class EventTemplate {

    private String tail = "send by Hyman";
    private String subject = "hi man";

    public EventTemplate(){}

    public EventTemplate(String tail,String subject){
        this.tail = tail;
        this.subject = subject;
    }

    public String geteventContent(){
        return tail;
    }

    public String geteventsubject(){
        return subject;
    }
}
