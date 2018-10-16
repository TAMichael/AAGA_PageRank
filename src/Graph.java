import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class Graph {
	private int n; //Nombre de Noeud
	private int nbEdges; //Nombre d'Edge
	private List<Edge> edgeList; // List des edges
	private Map<Integer, Integer> degree; // List des edges
	
	public Graph(int n, int nbEdges, List<Edge> edgeList, Map<Integer, Integer> degree) {
		this.n = n;
		this.nbEdges = nbEdges;
		this.edgeList = edgeList;
		this.degree = degree;
	}
	
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public int getNbEdges() {
		return nbEdges;
	}
	public void setNbEdges(int nbEdges) {
		this.nbEdges = nbEdges;
	}
	public List<Edge> getEdgeList() {
		return edgeList;
	}
	public void setEdgeList(List<Edge> edgeList) {
		this.edgeList = edgeList;
	}
	public Map<Integer, Integer> getDegree() {
		return degree;
	}
	public void setDegree(Map<Integer, Integer> degree) {
		this.degree = degree;
	}
	
}
