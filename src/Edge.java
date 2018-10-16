import java.math.BigInteger;

public class Edge {
	
	private BigInteger src;
	private BigInteger tgt;
	
	public Edge(BigInteger src, BigInteger tgt) {
		this.src=src;
		this.tgt=tgt;
	}
	
	public BigInteger getSrc() {
		return src;
	}
	public void setSrc(BigInteger src) {
		this.src = src;
	}
	public BigInteger getTgt() {
		return tgt;
	}
	public void setTgt(BigInteger tgt) {
		this.tgt = tgt;
	}
}
