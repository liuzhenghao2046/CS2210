package Assignment5;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/12/3.
 */
public class RoadMap {
    private Graph graph;
    private int startingNode;
    private int destinationNode;
    private int initialMoney;
    private int toll;
    private int gain;
    static ArrayList<String> lineList = new ArrayList<>();
    //Constructor( read txt files and store the data)
    RoadMap(String inputFile) throws MapException {
        String encoding = "UTF-8";
        File file = new File(inputFile);
        if (!file.exists())
            throw new MapException("input file does not exist");

        InputStreamReader read = null;
        try {
            read = new InputStreamReader(new FileInputStream(file), encoding);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            int i = 0;
            while (i < 8) {
                lineTxt = bufferedReader.readLine();
                if (i == 1)
                    this.startingNode = Integer.parseInt(lineTxt);
                else if (i == 2)
                    this.destinationNode = Integer.parseInt(lineTxt);
                else if (i == 5)
                    this.initialMoney = Integer.parseInt(lineTxt);
                else if (i == 6)
                    this.toll = Integer.parseInt(lineTxt);
                else if (i == 7)
                    this.gain = Integer.parseInt(lineTxt);
                ++i;
            }
            lineTxt = bufferedReader.readLine();
            int n = lineTxt.length();
            int m = 0;
            while (lineTxt != null) {
                if (lineTxt == null)
                    break;
                lineList.add(lineTxt);
                lineTxt = bufferedReader.readLine();
                ++m;
            }
            read.close();
            char[][] mapArray = new char[m][n];
            int nodeNum = 0, nodeNum2 = -1, l = 0;
            ArrayList<Node> nodeArrayList = new ArrayList<>();
            for (int j = 0; j < m; j++) {
                String str = lineList.get(j);
                for (int k = 0; k < n; k++) {
                    mapArray[j][k] = str.charAt(k);
                    if (str.charAt(k) == '+') {
                        nodeArrayList.add(new Node(nodeNum));
                        nodeNum++;
                    } else {
                        nodeArrayList.add(new Node(nodeNum2));
                        nodeNum2--;
                    }
                    l++;
                }
            }
            // 0 for free road  1 for toll road  -1 for compensation road
            this.graph = new Graph(nodeNum);
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < n; k++) {
                    if (lineList.get(j).indexOf("+") == -1)
                        break;
                    if (mapArray[j][k] == 'F' || mapArray[j][k] == 'T' || mapArray[j][k] == 'C'
                            && mapArray[j][k - 1] == '+' && mapArray[j][k + 1] == '+') {
                        try {
                            if (mapArray[j][k] == 'F') {

                                this.graph.insertEdge(nodeArrayList.get(k - 1 + j * n), nodeArrayList.get(k + 1 + j * n), 0);
                            } else if (mapArray[j][k] == 'T')
                                this.graph.insertEdge(nodeArrayList.get(k - 1 + j * n), nodeArrayList.get(k + 1 + j * n), -1);
                            else if (mapArray[j][k] == 'C')
                                this.graph.insertEdge(nodeArrayList.get(k - 1 + j * n), nodeArrayList.get(k + 1 + j * n), 1);
                        } catch (GraphException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            for (int k = 0; k < n; k++) {
                if (mapArray[0][k] != '+')
                    continue;
                for (int j = 0; j < m; j++) {

                    if (mapArray[j][k] == 'F' || mapArray[j][k] == 'T' || mapArray[j][k] == 'C'
                            && mapArray[j - 1][k] == '+' && mapArray[j + 1][k] == '+') {
                        try {
                            if (mapArray[j][k] == 'F')

                                this.graph.insertEdge(nodeArrayList.get(k + (j - 1) * n), nodeArrayList.get(k + (j + 1) * n), 0);
                            else if (mapArray[j][k] == 'T')

                                this.graph.insertEdge(nodeArrayList.get(k + (j - 1) * n), nodeArrayList.get(k + (j + 1) * n), -1);
                            else if (mapArray[j][k] == 'C') {
                                this.graph.insertEdge(nodeArrayList.get(k + (j - 1) * n), nodeArrayList.get(k + (j + 1) * n), 1);
                            }
                        } catch (GraphException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public Graph getGraph() {
        return this.graph;
    }

    public int getStartingNode() {
        return this.startingNode;
    }

    public int getDestinationNode() {
        return this.destinationNode;
    }

    public int getInitialMoney() {
        return this.initialMoney;
    }

    public Iterator findPath(int start, int destination, int initialMoney) {
        int m = 0, n = 0, o = 0, p = 0;
        m = (start / ((lineList.get(0).length() + 1) / 2)) * 2;
        n = start % ((lineList.get(0).length() + 1) / 2) * 2;
        o = (destination / ((lineList.get(0).length() + 1) / 2)) * 2;
        p = destination % ((lineList.get(0).length() + 1) / 2) * 2;
        int x = lineList.get(0).length();
        int y = lineList.size();
        char[][] mapArray = new char[y + 2][x + 2];
        for (int i = 0; i < y + 2; i++) {
            String str = "";
            if (i > 0 && i <= lineList.size()) {
                str = lineList.get(i - 1);
            }
            for (int j = 0; j < x + 2; j++) {
                if (i == 0 || j == 0 || i == y + 1 || j == x + 1)
                    mapArray[i][j] = '0';
                else
                    mapArray[i][j] = str.charAt(j - 1);
            }
        }
        int i = m + 1, j = n + 1;
        List nodeSolution = new ArrayList<Node>();
        nodeSolution.add(Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j - 1) / 2)));
        Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j - 1) / 2)).setMark(true);
        while (!(i == o + 1 && j == p + 1)) {
            if (initialMoney < 0) {
                return null;
            } else if (mapArray[i + 1][j] == 'C' && i + 2 <= y && !Graph.nodeVec.get(((x + 1) * (i + 1) / 4 + (j - 1) / 2)).getMark()) {
                i = i + 2;
                initialMoney = initialMoney - this.gain;
                nodeSolution.add(Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j - 1) / 2)));
                Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j - 1) / 2)).setMark(true);
                continue;
            } else if (mapArray[i][j + 1] == 'C' && j + 2 <= x && !Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j + 1) / 2)).getMark()) {
                j = j + 2;
                initialMoney = initialMoney - this.gain;
                nodeSolution.add(Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j - 1) / 2)));
                Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j - 1) / 2)).setMark(true);
                continue;
            } else if (mapArray[i][j - 1] == 'C' && j - 2 >= 0 && !Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j - 3) / 2)).getMark()) {
                j = j - 2;
                initialMoney = initialMoney - this.gain;
                nodeSolution.add(Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j - 1) / 2)));
                Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j - 1) / 2)).setMark(true);;
            } else if (mapArray[i + 1][j] == 'F' && i + 2 <= y && !Graph.nodeVec.get(((x + 1) * (i + 1) / 4 + (j - 1) / 2)).getMark()) {
                i = i + 2;
                nodeSolution.add(Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j - 1) / 2)));
                Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j - 1) / 2)).setMark(true);
                continue;
            } else if (mapArray[i][j + 1] == 'F' && j + 2 <= x && !Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j + 1) / 2)).getMark()) {
                j = j + 2;
                nodeSolution.add(Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j - 1) / 2)));
                Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j - 1) / 2)).setMark(true);
                continue;
            } else if (mapArray[i][j - 1] == 'F' && j - 2 >= 0 && !Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j - 3) / 2)).getMark()) {
                j = j - 2;
                nodeSolution.add(Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j - 1) / 2)));
                Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j - 1) / 2)).setMark(true);
                continue;
            } else if (mapArray[i + 1][j] == 'T' && i + 2 <= y && !Graph.nodeVec.get(((x + 1) * (i + 1) / 4 + (j - 1) / 2)).getMark()) {
                i = i + 2;
                nodeSolution.add(Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j - 1) / 2)));
                initialMoney = initialMoney - this.toll;
                Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j - 1) / 2)).setMark(true);
                continue;
            } else if (mapArray[i][j + 1] == 'T' && j + 2 <= x && !Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j + 1) / 2)).getMark()) {
                j = j + 2;
                nodeSolution.add(Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j - 1) / 2)));
                initialMoney = initialMoney - this.toll;
                Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j - 1) / 2)).setMark(true);
                continue;
            } else if (mapArray[i][j - 1] == 'T' && j - 2 >= 0 && !Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j - 3) / 2)).getMark()) {
                j = j - 2;
                nodeSolution.add(Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j - 1) / 2)));
                initialMoney = initialMoney - this.toll;
                Graph.nodeVec.get(((x + 1) * (i - 1) / 4 + (j - 1) / 2)).setMark(true);
                continue;
            }
        }
        Iterator iter = nodeSolution.iterator();
        return iter;
    }
}
