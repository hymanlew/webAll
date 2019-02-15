package settingModel;

//工厂方法模式（factory）：
//抽象的产品类：定义 car 交通工具类
public interface Car {
    void gotoworck();
}

//定义实际的产品类
class Bike implements Car {
    public void gotoworck() {
        System.out.println("骑自行车去上班！");
    }
}

class Bus implements Car{
    public void gotoworck() {
        System.out.println("坐公交车去上班！");
    }
}