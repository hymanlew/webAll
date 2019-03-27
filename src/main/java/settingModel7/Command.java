package settingModel7;

/**
 * 命令模式（Command pattern）：将一个请求封装成一个对象，从而让你使用不同的请求把客户端参数化，对请求排队或者记录请求日志，可以提供命令的
 * 撤销和恢复功能，行为类模式。
 *
 * 顾名思义命令模式就是对命令的封装。
 */
public abstract class Command {
    public abstract void execute();
}

// 调用者
class Invoker{
    private Command command;

    public void setCommand(Command command){
        this.command = command;
    }

    public void action(){
        this.command.execute();
    }
}

// 接受者
class Receiver{
    public void doSomething(){
        System.out.println("接受者-业务逻辑处理");
    }
}

class ConcreteCommand extends Command{
    private Receiver receiver;

    public ConcreteCommand(Receiver receiver){
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.doSomething();
    }
}

class Client3{
    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        Command command = new ConcreteCommand(receiver);
        //客户端直接执行具体命令方式（此方式与类图相符）
        command.execute();

        //客户端通过调用者来执行命令
        Invoker invoker = new Invoker();
        invoker.setCommand(command);
        invoker.action();
    }
}

/**
 * 通过代码我们可以看到，当我们调用时，执行的时序首先是调用者类，然后是命令类，最后是接收者类。也就是说一条命令的执行被分成了三步，它的耦合度
 * 要比把所有的操作都封装到一个类中要低的多，而这也正是命令模式的精髓所在：把命令的调用者与执行者分开，使双方不必关心对方是如何操作的。
 */
