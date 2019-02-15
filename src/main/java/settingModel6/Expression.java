package settingModel6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Stack;

/**
 * 解释器模式（interpreter）：它提供了评估语言的语法或表达式的方式，它属于行为型模式。这种模式实现了一个表达式接口，该接口解释一个特定的
 * 上下文。这种模式被用在 SQL 解析、符号处理引擎等。比较少用。
 *
 * 意图：给定一个语言，定义它的文法表示，并定义一个解释器，这个解释器使用该标识来解释语言中的句子。
 *
 * 主要解决：对于一些固定文法构建一个解释句子的解释器。
 *
 * 何时使用：如果一种特定类型的问题发生的频率足够高，那么可能就值得将该问题的各个实例表述为一个简单语言中的句子。这样就可以构建一个解释器，
 * 该解释器通过解释这些句子来解决该问题。
 *
 * 如何解决：构件语法树，定义终结符与非终结符。
 *
 * 关键代码：构件环境类，包含解释器之外的一些全局信息，一般是 HashMap。
 *
 * 应用实例：编译器、运算表达式计算。
 *
 * 优点： 1、可扩展性比较好，灵活。 2、增加了新的解释表达式的方式。 3、易于实现简单文法。
 *
 * 缺点： 1、可利用场景比较少。 2、对于复杂的文法比较难维护。 3、解释器模式会引起类膨胀。 4、解释器模式采用递归调用方法。
 *
 * 使用场景： 1、可以将一个需要解释执行的语言中的句子表示为一个抽象语法树。 2、一些重复出现的问题可以用一种简单的语言来进行表达。 3、一个
 * 简单语法需要解释的场景。
 *
 * 注意事项：可利用场景比较少，JAVA 中如果碰到可以用 expression4J 代替。
 */

/**
 * 抽象表达式（抽象解释器）：它是生产语法集合(也叫语法树)的关键，每个语法集合完成指定语法解析任务，它是通过递归调用的方式，最终由最小的语法
 * 单元进行解析完成。
 */
public abstract class Expression{
    //每个表达式必须有一个解析任务
    public abstract int interpreter(HashMap<String,Integer> map);
}

// 终结符表达式，主要是处理场景元素和数据的转换 如：a+b+c中的"a""b""c"
class TerminalExpression extends Expression{

    private String data;

    public TerminalExpression(String data){
        this.data = data;
    }

    //通常终结符表达式只有一个，但是有多个对象
    @Override
    public int interpreter(HashMap<String, Integer> map) {
        return map.get(data);
    }
}

/**
 * 非终结符表达式，每个非终结符表达式都代表一个文法规则，并且每个文法规则都只关心自己周边的文法规则结果(注意是结果),因此这就产生了每个终结符
 * 表达式调用自己周边的非终结符表达式，然后最终，最小的文法规则就是终结符表达式，终结符表达式的概念就是如此，不能在参与比自己更小的文法运算了
 */
abstract class NonterminalExpression extends Expression{

    protected Expression left;      //指向 TerminalExpression (string)
    protected Expression right;     //指向 TerminalExpression (string)

    //每个非终结符表达式都会对其他表达式产生依赖
    public NonterminalExpression(Expression left,Expression right){
        this.left = left;
        this.right = right;
    }

}

class AddExpression extends NonterminalExpression{

    public AddExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int interpreter(HashMap<String, Integer> map) {
        return super.left.interpreter(map) + super.right.interpreter(map);  //指向 Terminal interpreter(map.getValue)
    }
}

class SubExpression extends NonterminalExpression{

    public SubExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int interpreter(HashMap<String, Integer> map) {
        return super.left.interpreter(map) - super.right.interpreter(map);
    }
}

/**
 * java.util.Stack 是一个后进先出（last in first out，LIFO）的堆栈，在Vector类的基础上扩展5个方法而来。
 *
 * E push(E item)：  把项压入堆栈顶部（后进）。
 *
 * E pop()：         移除堆栈顶部的对象（先出），并作为此函数的值返回该对象。
 *
 * E peek()：        查看堆栈顶部的对象，但不从堆栈中移除它。
 *
 * boolean empty()： 测试堆栈是否为空。
 *
 * int search(Object o)： 返回对象在堆栈中的位置，以 1 为基数。

 * Stack本身通过扩展Vector而来，而Vector本身是一个可增长的对象数组，那么这个数组通过 peek()方法发现数组（Vector）的最后一位即为 Stack
 * 的栈顶。
 *
 * pop、peek以及search方法本身进行了同步
 * push方法调用了父类的addElement方法
 * empty方法调用了父类的size方法
 * Vector类为线程安全类
 *
 * 综上，Stack类为线程安全类(多个方法调用而产生的数据不一致问题属于原子性问题的范畴)
 *
 * Stack并不要求其中保存数据的唯一性，当Stack中有多个相同的item时，调用search方法，只返回与查找对象equal并且离栈顶最近的item与栈顶间距离
 * （见源码中search方法说明）
 *
 * Deque（双端队列）比起Stack具有更好的完整性和一致性，应该被优先使用
 */
// Calcuator（计算器）的作用是封装，根据迪米特原则，Client只与直接的朋友Calcuator交流，与其他类没关系。
class Calculator{
    //表达式
    private Expression expression;

    public Calculator(String expstr){
        //通常一个语法容器，容纳一个具体的表达式，通常为ListArray，LinkedList，Stack等类型
        Stack<Expression> stack = new Stack<Expression>();
        //表达式拆分为字符数组
        char[] chars = expstr.toCharArray();

        Expression left = null;
        Expression right = null;
        for(int i=0;i<chars.length;i++) {
            //进行语法判断，递归调用，stack 中数据队列的读取都是以表达式的形式（即a，b，c的形式）来进行的，而数据也只有表达式而已
            switch (chars[i]){
                case '+':
                    left = stack.pop();
                    right = new TerminalExpression(String.valueOf(chars[++i]));
                    stack.push(new AddExpression(left,right));
                    break;
                case '-':
                    left = stack.pop();
                    right = new TerminalExpression(String.valueOf(chars[++i]));
                    stack.push(new SubExpression(left,right));
                    break;
                default:
                    stack.push(new TerminalExpression(String.valueOf(chars[i])));
            }
        }
        //产生一个完整的语法树，由各个具体的语法分析进行解析
        this.expression = stack.pop();
    }

    public int run(HashMap<String,Integer> map){
        /**
         * 具体执行的子类顺序时，JVM会逐个尝试运行所有的继承类，然后在匹配 left，right 的值时，进行准确的判断。因为只有当为 add 时，left
         * 的值才会单独的值，而为 sub 时，left 的值里面还有 left 值。
         */
        return this.expression.interpreter(map);
    }
}

//场景类
class Client1{

    public static String getExpstr() throws IOException{
        System.out.println("输入表达式");
        return (new BufferedReader(new InputStreamReader(System.in))).readLine();
    }

    public static HashMap<String,Integer> getValue(String expstr) throws IOException {
        HashMap<String,Integer> map = new HashMap<String, Integer>();
        for(char c:expstr.toCharArray()){
            if(c!= '+' && c!='-'){
                if(!map.containsKey(String.valueOf(c))){
                    System.out.println("请输入"+c+"的值:");
                    String in = (new BufferedReader(new InputStreamReader(System.in))).readLine();
                    map.put(String.valueOf(c),Integer.valueOf(in));
                }
            }
        }
        return map;
    }
    public static void main(String[] args) throws IOException{

        // 获得运算表达式
        String expstr = getExpstr();
        // 获得具体运算的数据
        HashMap<String,Integer> map = getValue(expstr);
        // 运算
        Calculator calculator = new Calculator(expstr);
        //具体元素进入场景，将表达式与数据结合进行计算
        System.out.println("运算结果为："+expstr+" = "+ calculator.run(map));
    }
}