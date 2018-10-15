import java.io.IOException;

public class Test {

	public static void main(String[] args) {
		
		PageRank pr = new PageRank();
		
		try {
			pr.prodMatVect(null,null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
