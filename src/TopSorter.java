import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

class Kante{
    private String start;
    private String ende;

    Kante(){}
    public void setStart(String Knoten){start=Knoten;}
    public void setEnde(String Knoten){ende=Knoten;}
    public String getStart(){return start;}
    public String getEnde(){return ende;}
}
class Graph{
    private Vector<Kante> graph = new Vector<Kante>();

    public List sort(String elem) {
        Set<String> S = new LinkedHashSet<String>();
        for (int i = 0; i < graph.size(); i++) {
            String ktmp = graph.elementAt(i).getStart();
            int hatInput = 0;
            for (int j = 0; j < graph.size(); j++) {
                if(ktmp.equals(graph.elementAt(i).getEnde())){
                    hatInput = 1;
                }
            }
            if(hatInput==0){
                S.add(ktmp);
            }
        }
        List<String> sorted = new ArrayList<>();
        String startK, endK;
        while(!S.isEmpty()){
            Iterator<String> it = S.iterator();
            startK = it.next();

            S.remove(startK);
            sorted.add(startK);

            for (int i = 0; i < graph.size(); i++) {
                //vergleiche Start Knoten mit aktuellen
                if (startK.equals(graph.elementAt(i).getStart())){
                    endK = graph.elementAt(i).getEnde();
                    graph.remove(i);
                    i--;

                    int hatInput = 0;
                    for (int j = 0; j < graph.size(); j++) {
                        if(endK.equals(graph.elementAt(i).getEnde())){
                            hatInput = 1;
                        }
                    }
                    if(hatInput==0){
                        S.add(endK);
                    }
                }
            }
        }
        if (!graph.isEmpty()){
            System.exit(1);
        }
        return sorted;
    }

    Graph(ArrayList<String> kList, ArrayList<ArrayList<String>> vList){
        for(ArrayList<String> kanten : vList){
            Kante k = new Kante();
            k.setStart(kanten.get(0));
            k.setEnde(kanten.get(1));
            graph.add(k);
        }
    }
}

public class TopSorter {
    ArrayList<ArrayList<String>> vList ;
    ArrayList<String> kList;

    public TopSorter(String path){
        vList = new ArrayList<ArrayList<String>>();
        kList = new ArrayList<>();
        readFile(path);
    }

    private void readFile(String path){
        //Load File
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            //Read Line by Line, write to List, count vertices
            while (line != null) {
                if(line.startsWith("V")){
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add(line.split(",")[1]);
                    tmp.add(line.split(",")[2]);
                    vList.add(tmp);
                }else if(line.startsWith("K")){
                    kList.add(line.split(",")[1]);
                }
                line = br.readLine();
            }
            System.out.println(kList.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void buildGraph(){
        System.out.println("BUILD THE GRAPH HERE");
    }
    public static void main(String[] args){
        String file = "C:\\Users\\David\\IdeaProjects\\TopSortShelly\\src\\input.txt";
        TopSorter ts = new TopSorter(file);
        ts.buildGraph();
    }
}
/* KHANS ALGO
L ← Empty list that will contain the sorted elements
S ← Set of all nodes with no incoming edges

while S is non-empty do
    remove a node n from S
    add n to tail of L
    for each node m with an edge e from n to m do
        remove edge e from the graph
        if m has no other incoming edges then
            insert m into S

    if graph has edges then
        return error (graph has at least one cycle)
    else
        return L (a topologically sorted order)
*/