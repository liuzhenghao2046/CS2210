package Assignment5;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * Created by Administrator on 2016/12/3.
 */
public class Graph implements GraphADT {
    static Vector<Node> nodeVec = new Vector<Node>();
    static Vector<Edge> edgeVec = new Vector<Edge>();

    public Graph(int n) {
        nodeVec.clear();
        for (int i = 0; i < n; i++) {
            nodeVec.add(new Node(i));
        }
    }

    public Node getNode(int name) throws GraphException {
        if (name >= 0 && name < nodeVec.size())
            return this.nodeVec.get(name);
        else
            throw new GraphException(" no node with this name exists");
    }

    public void insertEdge(Node u, Node v, int edgeType) throws GraphException {
        if (u.getName() < 0 || u.getName() >= nodeVec.size() || v.getName() < 0 || v.getName() >= nodeVec.size())
            throw new GraphException("node does not exist or there is already an edge connecting the given nodes");
        if (edgeVec.isEmpty())
            edgeVec.add(new Edge(u, v, edgeType));
        else {
            if (u.getName() >= 0 && u.getName() < nodeVec.size() && v.getName() >= 0 && v.getName() < nodeVec.size() &&
                    !edgeVec.contains(new Edge(u, v, edgeType)))
                edgeVec.add(new Edge(u, v, edgeType));
        }
    }

    public Iterator incidentEdges(Node u) throws GraphException {
        if (nodeVec.contains(u) == false)
            throw new GraphException("invalid node");
        else {
            List edgeList = new ArrayList<>();
            for (int i = 0; i < edgeVec.size(); i++) {
                if (edgeVec.get(i).firstEndpoint().equals(u) || edgeVec.get(i).secondEndpoint().equals(u))
                    edgeList.add(edgeVec.get(i));
            }
            Iterator iter = edgeList.iterator();
            if (edgeList.isEmpty())
                return null;
            else
                return iter;
        }
    }

    public Edge getEdge(Node u, Node v) throws GraphException {
        for (int i = 0; i < edgeVec.size(); i++) {
            if (edgeVec.get(i).firstEndpoint().equals(u) && edgeVec.get(i).secondEndpoint().equals(v)
                    || edgeVec.get(i).secondEndpoint().equals(u) && edgeVec.get(i).firstEndpoint().equals(v))
                return edgeVec.get(i);
        }
        throw new GraphException("no edge between these two points");
    }

    public boolean areAdjacent(Node u, Node v) throws GraphException {
        if (!(nodeVec.contains(u) && nodeVec.contains(v)))
            throw new GraphException("invalid node");
        for (int i = 0; i < edgeVec.size(); i++) {
            if (edgeVec.get(i).firstEndpoint().equals(u) && edgeVec.get(i).secondEndpoint().equals(v)
                    || edgeVec.get(i).secondEndpoint().equals(u) && edgeVec.get(i).firstEndpoint().equals(v))
                return true;
        }
        return false;
    }

}
