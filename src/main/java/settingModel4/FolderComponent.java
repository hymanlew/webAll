package settingModel4;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合模式（Composite）：又叫做部分-整体模式，它使我们树型结构的问题中，模糊了简单元素和复杂元素的概念，客户程序可以向处理简单元素一样来处
 * 理复杂元素,从而使得客户程序与复杂元素的内部结构解藕。
 *
 * 组合模式可以优化处理递归或分级数据结构。有许多关于分级数据结构的例子,使得组合模式非常有用武之地.
 *
 * 文件夹组件：
 */
public abstract class FolderComponent {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FolderComponent(){}

    public FolderComponent(final String name){
        this.name = name;
    }

    public abstract void add(FolderComponent component);

    public abstract void remove(FolderComponent component);

    public abstract void display();
}

//分支文件（叶子）
class FileLeaf extends FolderComponent{

    public FileLeaf(final String name){
        super(name);
    }

    @Override
    public void add(FolderComponent component) {
        System.out.println("add");
    }
    @Override
    public void remove(FolderComponent component) {
        System.out.println("remove");
    }
    @Override
    public void display() {
        System.out.println("FileLeaf:"+this.getName());
    }
}

//文件夹合成
class FolderComposite extends FolderComponent{

    private final List<FolderComponent> components;

    public FolderComposite(){
        this.components = new ArrayList<FolderComponent>();
    }

    public FolderComposite(final String name){
        super(name);
        this.components = new ArrayList<FolderComponent>();
    }

    @Override
    public void add(final FolderComponent component) {
        this.components.add(component);
    }
    @Override
    public void remove(final FolderComponent component) {
        this.components.remove(component);
    }
    @Override
    public void display() {
        System.out.println("FolderComposite-name:"+this.getName());
        for(final FolderComponent component:components){
            System.out.println("FolderComposite-component-name:"+component.getName());
        }
    }
}

//Client
class Client{
    public static void main(String[] args) {

        final FolderComponent leaf = new FileLeaf("leaf file");
        leaf.display();

        final FolderComponent folder = new FolderComposite("new folder");
        folder.add(new FileLeaf("content1 in new folder"));
        folder.add(new FileLeaf("content2 in new folder"));
        folder.display();
    }
}