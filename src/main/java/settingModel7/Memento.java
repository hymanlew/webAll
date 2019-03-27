package settingModel7;

/**
 * 备忘录模式（Memento）：在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态。这样就可以将该对象恢复到原先保存的状态，
 * 行为类模式。
 *
 * 代码演示了一个单状态单备份的例子，逻辑非常简单：Originator类中的state变量需要备份，以便在需要的时候恢复；Memento类中，也有一个state变
 * 量，用来存储Originator类中state变量的临时状态；而Caretaker类就是用来管理备忘录类的，用来向备忘录对象中写入状态或者取回状态。
 */
public class Memento {
    private String state = "";

    public Memento(String state){
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

// 发起人
class Originator{
    private String State = "";

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public Memento createMemento(){
        return new Memento(this.State);
    }

    public void restoreMemento(Memento memento){
        this.setState(memento.getState());
    }
}

// 看管者
class Caretaker{
    private Memento memento;

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }
}

class Client4{
    public static void main(String[] args) {

        Originator originator = new Originator();
        originator.setState("状态1");
        System.out.println("初始状态:"+originator.getState());

        Caretaker caretaker = new Caretaker();
        caretaker.setMemento(originator.createMemento());

        originator.setState("状态2");
        System.out.println("改变后状态:"+originator.getState());
        // 如果提交之后，就不能再恢复之前的状态了
        //caretaker.setMemento(originator.createMemento());

        originator.restoreMemento(caretaker.getMemento());
        System.out.println("恢复后状态:"+originator.getState());
    }
}