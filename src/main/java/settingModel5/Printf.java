package settingModel5;

/**
 *  System.out.printf（"%n"，n）
 *      是用于定义一些变量，用来格式化输出。  "%"表示进行格式化输出，"%"之后的内容为格式的定义。
 *
 *  %f，表示输出小数
 *  %d，表示输出十进制数
 *  %f，表示输出字符串
 */
public class Printf {
    public static void main(String[] args) {

        double d = 123.123;
        String s = "hello!";
        int i = 123;

        System.out.printf("%f",d);
        System.out.println();

        //如果数据的位数不够，会以空格来补充（默认右对齐时），如果加上 - 号则默认左对齐，不会补充空格
        System.out.printf("%9.2f",d);
        System.out.println();

        System.out.printf("%+9.4f",d);
        System.out.println();

        System.out.printf("%-9.3f",d);
        System.out.println();

        System.out.printf("%+-9.3f",d);
        System.out.println();


        System.out.printf("%d",i);
        System.out.println();

        System.out.printf("%o",i);
        System.out.println();

        System.out.printf("%x",i);
        System.out.println();

        // 输出带有十六进制标志的整数（0x7b）
        System.out.printf("%#x",i);
        System.out.println();

        System.out.printf("%s",s);
        System.out.println();

        // 输出一个浮点数：123.123000，一个十进制整数：123，一个字符串：hello!
        System.out.printf("输出一个浮点数：%f，一个十进制整数：%d，一个字符串：%s",d,i,s);
        System.out.println();

        // 并且可以输出多个变量，注意顺序，（"X$"表示第几个变量）
        // 字符串：hello!，123的十六进制数是：0x7b
        System.out.printf("字符串：%2$s，%1$d的十六进制数是：%1$#x",i,s);
        System.out.println();

        // 将数据转换为字符串输出，3表示占用的位数，如果数据位数不够，则在前面以空格补充。
        System.out.printf("%3s",1);

    }
}
