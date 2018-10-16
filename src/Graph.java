import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class Graph {
	private BigInteger n; //Nombre de Noeud
	private BigInteger nbEdges; //Nombre d'Edge
	private List<Edge> edgeList; // List des edges
	private Map<BigInteger, Integer> degree; // List des edges
	
	public Graph(BigInteger n, BigInteger nbEdges, List<Edge> edgeList, Map<BigInteger, Integer> degree) {
		this.n = n;
		this.nbEdges = nbEdges;
		this.edgeList = edgeList;
		this.degree = degree;
	}
	
	public BigInteger getN() {
		return n;
	}
	public void setN(BigInteger n) {
		this.n = n;
	}
	public BigInteger getNbEdges() {
		return nbEdges;
	}
	public void setNbEdges(BigInteger nbEdges) {
		this.nbEdges = nbEdges;
	}
	public List<Edge> getEdgeList() {
		return edgeList;
	}
	public void setEdgeList(List<Edge> edgeList) {
		this.edgeList = edgeList;
	}
	public Map<BigInteger, Integer> getDegree() {
		return degree;
	}
	public void setDegree(Map<BigInteger, Integer> degree) {
		this.degree = degree;
	}
	
}
