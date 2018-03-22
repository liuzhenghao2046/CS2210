package Assignment5;

/**
 * Created by Administrator on 2016/12/3.
 */
public class Node {
    private int name;
    private boolean value;
    public Node(int name){this.name=name;}
    public void setMark(boolean mark){this.value=mark;}
    public boolean getMark(){return this.value;}
    public int getName(){return this.name;}
}
