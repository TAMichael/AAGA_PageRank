import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class PageRank {
	private static String dirLinksFile = "D:/Users/kciM/eclipse-workspace/AAGA_PageRank/files/alr21--dirLinks--enwiki-20071018.txt";
	private static String fileResultat = "D:/Users/kciM/eclipse-workspace/AAGA_PageRank/files/pageRank_Alpha15_Ite20.txt";


	private static int n;
	private static List<Edge> edgeList;
	private static double alpha = 0.15;
	private static int nbIteration = 20;
	private static ArrayList<Double> p1;
	private static ArrayList<Double> p2;

	private static double s;

	private static Graph extractInfoFichier(String dirLinksFile2) throws IOException {
		n = 0;
		edgeList=new ArrayList<>();


		// On récupère les informations crutiales, les edges ainsi que le nombre de sites
		try (Stream<String> stream = Files.lines(Paths.get(dirLinksFile2))) {
			stream.forEach(l -> {
				String[] tab = l.split("\\s");
				int src = Integer.parseInt(tab[0]);
				int tgt = Integer.parseInt(tab[1]);
				Edge e = new Edge(src, tgt);
				edgeList.add(e);
				n = Math.max(Math.max(n, src), tgt);
			});
		}


		int nbEdges = edgeList.size();

		Map<Integer, Integer> degree = new HashMap<>();

		// On calcul les degrees sortant de chaque site
		for (int i=0; i<nbEdges; i++){
			Integer oldDegree = degree.get(edgeList.get(i).getSrc());
			if(oldDegree == null) {
				degree.put(edgeList.get(i).getSrc(), new Integer(1));
			}else {
				degree.put(edgeList.get(i).getSrc(), new Integer(oldDegree.intValue() + 1));
			}
		}


		n++;
		return new Graph(n, nbEdges, edgeList, degree);
	}


	public static ArrayList<Double> powerIte(Graph graph) throws IOException{

		int nbNodes =graph.getN();

		p1 = new ArrayList<>();
		ArrayList<Double> p3;

		ArrayList<Integer> degree = new ArrayList<>();

		// Initialisation de p1 (On a autant de chance de commencer sur n'importe quel site)
		for(int i=0; i<nbNodes; i++) {
			p1.add(1.0/nbNodes);
		}

		// Changer le fichier virer les noeuds dont rien de pointe sur et ne pointe sur rien


		for (int k=0; k<nbIteration; k++) {	
			p2 = new ArrayList<>(Collections.nCopies(nbNodes, 0.0));

			// Simule un deplacement aleatoire 
			for(int i=0; i<graph.getNbEdges(); i++) {

				Edge currEdge = graph.getEdgeList().get(i);
				int src = currEdge.getSrc();
				int tgt = currEdge.getTgt();

				double oldValue = p2.get(tgt);		
				p2.set(tgt, oldValue + (double)(p1.get(src)/(graph.getDegree().get(src)))); 
				
			}

			// Normalisation		
			s = 0;

			for(int i=0; i<nbNodes; i++) {
				double newValue = p2.get(i)*(1-alpha)+(alpha/nbNodes);
				p2.set(i, newValue);
				
				s += newValue;
			}

			double normalisationToAdd =(1-s)/nbNodes;

			for(int i=0; i<nbNodes; i++) {
				p2.set(i, p2.get(i)+normalisationToAdd);
			}

			p3=p1;
			p1=p2;
			p2=p3;
			System.out.println("K : "+k);
		}

		return p1;

	}



	public static void main(String[] args) {

		try {
			final long startTime = System.currentTimeMillis();

			Graph graph = extractInfoFichier(args[0]);
			System.out.println("N: "+graph.getN()+", nbEdges: "+graph.getNbEdges());
			final long intTime = System.currentTimeMillis();
			
			System.out.println("Execution time after read file: " + (intTime - startTime)/1000 +" s");


			List<Double> res = powerIte(graph);
			
			File ff=new File(args[1]); // définir l'arborescence
			ff.createNewFile();
			FileWriter ffw=new FileWriter(ff);

			for(int i=0; i<graph.getN(); i++) {	
				ffw.write(i+" "+res.get(i)+"\n"); 
			}
			ffw.close();
			
			final long endTime = System.currentTimeMillis();
			
			System.out.println("Total execution time: " + (endTime - startTime)/1000 +" s");


		}catch (Exception e) {e.printStackTrace();}


	}


}
