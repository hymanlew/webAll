package settingModel;

//定义抽象的工厂接口
public interface ICarFactory {
    Car getcar();
}

//具体的工厂子类
class BikeFactory implements ICarFactory{
    public Car getcar() {
        return new Bike();
    }
}

class BusFacory implements ICarFactory{
    public Car getcar() {
        return new Bus();
    }
}


//工厂方法模式，简单的测试类
class TestFactory {

    public static void test(){
        ICarFactory factory = null;

        //bike
        factory = new BikeFactory();
        Car bike = factory.getcar();
        bike.gotoworck();

        //bus
        factory = new BusFacory();
        Car bus = factory.getcar();
        bus.gotoworck();
    }

    public static void main(String[] args) {
        test();
    }
}
