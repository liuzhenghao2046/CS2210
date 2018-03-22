package Assignment5;

/**
 * Created by Administrator on 2016/12/3.
 */
public class Edge {
    private int type;
    private Node firstEndpoint;
    private Node secondEndpoint;
    private String label;
    //type value:0 for free road  1 for toll road  -1 for compensation road
    public Edge(Node u, Node v, int type){
        this.type=type;
        this.firstEndpoint=u;
        this.secondEndpoint=v;
        this.label="";
    }
    public Node firstEndpoint(){return this.firstEndpoint;}
    public Node secondEndpoint(){return this.secondEndpoint;}
    public int  getType(){return  this.type;}
    public void setLabel(String label){this.label=label;}
    public String getLabel(){return this.label;}
}
