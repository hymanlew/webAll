package settingModel8;

/**
 * 责任链模式（Chain of Responsibility）：使多个对象都有机会处理请求，从而避免了请求的发送者和接收者之间的耦合关系。将这些对象连成一条链，
 * 并沿着这条链传递该请求，直到有对象处理它为止，是行为类模式。
 */
class Level{
    private int lenum = 0;

    public Level(int level){
        this.lenum = level;
    }

    public boolean above(Level level){
        if(this.lenum >= level.lenum){
            return true;
        }
        return false;
    }
}

public class Request {
    Level level;

    public Request(Level level){
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }
}

class Response{}

/**
 * 抽象处理类：抽象处理类中主要包含一个指向下一处理类的成员变量nextHandler和一个处理请求的方法handRequest，handRequest方法的主要主要
 * 思想是，如果满足处理的条件，则有本处理类来进行处理，否则由nextHandler来处理。
 */
abstract class Handler{
    private Handler nextHandler;

    public void setNextHandler(Handler handler){
        this.nextHandler = handler;
    }

    protected abstract Level getHandlerLevel();

    public abstract Response response(Request request);

    public final Response handlerRequest(Request request){
        Response response = null;

        if(this.getHandlerLevel().above(request.getLevel())){
            response = this.response(request);
        }else{
            if(this.nextHandler!=null){
                this.nextHandler.handlerRequest(request);
            }else{
                System.out.println("-----没有合适的处理器-----");
            }
        }
        return response;
    }
}

class ConcreteHandler1 extends Handler{
    @Override
    protected Level getHandlerLevel() {
        return new Level(1);
    }

    @Override
    public Response response(Request request) {
        System.out.println("-----请求由处理器1进行处理-----");
        return null;
    }
}

class ConcreteHandler2 extends Handler{
    @Override
    protected Level getHandlerLevel() {
        return new Level(3);
    }

    @Override
    public Response response(Request request) {
        System.out.println("-----请求由处理器2进行处理-----");
        return null;
    }
}

class ConcreteHandler3 extends Handler{
    @Override
    protected Level getHandlerLevel() {
        return new Level(5);
    }

    @Override
    public Response response(Request request) {
        System.out.println("-----请求由处理器3进行处理-----");
        return null;
    }
}

class Client{
    public static void main(String[] args) {
        Handler handler1 = new ConcreteHandler1();
        Handler handler2 = new ConcreteHandler2();
        Handler handler3 = new ConcreteHandler3();

        handler1.setNextHandler(handler2);
        handler2.setNextHandler(handler3);

        // 执行方法时，是遍历所有的子类然后逐一判断，直到 boolean 为 true，然后执行 response
        Response response = handler1.handlerRequest(new Request(new Level(4)));
    }
}

/**
 * 代码中Level类是模拟判定条件；Request，Response分别对应请求和响应；抽象类Handler中主要进行条件的判断，这里模拟一个处理等级，只有处理
 * 类的处理等级高于Request的等级才能处理，否则交给下一个处理者处理。
 *
 * 在Client类中设置好链的前后执行关系，执行时将请求交给第一个处理类，这就是责任链模式，它完成的功能与前文中的if…else…语句是一样的。
 */
