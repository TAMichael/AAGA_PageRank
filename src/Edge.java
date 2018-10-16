
public class Edge {
	
	private int src;
	private int tgt;
	
	public Edge(int src, int tgt) {
		this.src=src;
		this.tgt=tgt;
	}
	
	public int getSrc() {
		return src;
	}
	public void setSrc(int src) {
		this.src = src;
	}
	public int getTgt() {
		return tgt;
	}
	public void setTgt(int tgt) {
		this.tgt = tgt;
	}
}
