package settingModel6;

// 创建一个表达式接口
public interface Expression1 {
    public boolean interpret(String context);
}

// 创建实现了上述接口的实体类
class Terminal implements Expression1{
    private String data;

    public Terminal(String data){
        this.data = data;
    }

    public boolean interpret(String context) {
        if(context.contains(data)){
            return true;
        }
        return false;
    }
}

class OrExpression implements Expression1{
    private Expression1 left;   //指向 Terminal 构造器
    private Expression1 right;

    public OrExpression(Expression1 left,Expression1 right){
        this.left = left;
        this.right = right;
    }

    public boolean interpret(String context) {
        return left.interpret(context) || right.interpret(context);
    }
}

class AndExpression implements Expression1{
    private Expression1 left;   //指向 Terminal 构造器
    private Expression1 right;

    public AndExpression(Expression1 left,Expression1 right){
        this.left = left;
        this.right = right;
    }

    public boolean interpret(String context) {
        return left.interpret(context) && right.interpret(context);
    }
}

class Demo{
    //规则：Robert 和 John 是男性
    public static Expression1 getMaleExpression(){
        Expression1 rober = new Terminal("robert");
        Expression1 john = new Terminal("john");
        return new OrExpression(rober,john);
    }

    //规则：Julie 是一个已婚的女性
    public static Expression1 getMarried(){
        Expression1 julie = new Terminal("julie");
        Expression1 married = new Terminal("married");
        return new AndExpression(julie,married);
    }

    public static void main(String[] args) {
        Expression1 ismale = getMaleExpression();
        Expression1 ismarried = getMarried();

        System.out.println("John is male? " + ismale.interpret("john"));
        System.out.println("Julie is a married? " + ismarried.interpret("julie married"));
    }

}