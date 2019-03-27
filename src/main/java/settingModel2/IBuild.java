package settingModel2;

/**
 * 产品类中的各个小模块是不一样的，由他们建造组成产品。
 */
public interface IBuild {
    public void createEntity1();
    public void createEntity2();
    public void createEntity3();
    public Product composite();     // 复合的；合成的
    public Product create();
}

/**
 * 根据具体场景要求，定义n个生成器类：
 */
class BuildProduct implements IBuild{

    Product p = new Product();

    public void createEntity1() {
        p.entity1 = new Entity1();
        System.out.println("en1");
    }

    public void createEntity2() {
        p.entity2 = new Entity2();
        System.out.println("en2");
    }

    public void createEntity3() {
        p.entity3 = new Entity3();
        System.out.println("en3");
    }

    public Product composite() {

        return p;
    }

    public Product create() {
        return composite();
    }
}


class BuildProduct1 implements IBuild{

    Product p = new Product();

    public void createEntity1() {
        p.entity1 = new Entity1();
        System.out.println("en1");
    }

    public void createEntity2() {
        p.entity2 = new Entity2();
        System.out.println("en2");
    }

    public void createEntity3() {
        p.entity3 = new Entity3();
        System.out.println("en3");
    }

    public Product composite() {

        return p;
    }

    public Product create() {
        return composite();
    }
}

/**
 * 定义一个指挥者类，统一调度project：
 */
class Director {

    private IBuild build;

    public Director(IBuild build){
        this.build = build;
    }

    public Product buildpro(){
        return build.create();
    }

    public static void main(String[] args) {

        IBuild build = new BuildProduct();
        Director director = new Director(build);
        Product p = director.buildpro();
    }
}