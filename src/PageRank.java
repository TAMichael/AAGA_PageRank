import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

public class PageRank {
	private String dirLinksFile = "/users/Etu7/3407737/eclipse-workspace/AAGA_PageRank/files/alr21--dirLinks--enwiki-20071018.txt";
	private static int n = 13834639;
	private int debutFile = 0;
	private int alpha = 0;
	private int nbIteration = 20;
	private double s =0;






	public ArrayList<Double> prodMatVect(ArrayList<ArrayList<Double>> m, ArrayList<Double> a) throws IOException{

		ArrayList<Double> res = new ArrayList<>();

		ArrayList<Double> poids = new ArrayList<>();

		ArrayList<Integer> degree = new ArrayList<>();

		//		try (Stream<String> stream = Files.lines(Paths.get(dirLinksFile))) {
		//			stream.forEach(l -> {
		//				String[] tab = l.split("\\s");
		//				if(n < Integer.parseInt(tab[0])) {
		//					n = Integer.parseInt(tab[0]);
		//				}
		//
		//				if(n < Integer.parseInt(tab[1])) {
		//					n = Integer.parseInt(tab[1]);
		//				}
		//
		//			});
		//		}

		for(int i=0; i<n; i++) {
			degree.add(0);
		}


		try (Stream<String> stream = Files.lines(Paths.get(dirLinksFile))) {
			stream.forEach(l -> {
				String[] tab = l.split("\\s");
				int src = Integer.parseInt(tab[0]);
				int tgt = Integer.parseInt(tab[1]);
				degree.set(src, degree.get(src)+1);     
			});
		}

		for(int i=0; i<n ;i++) {

		}

		System.out.println(n);

		return null;

	}

	public ArrayList<Double> powerIte(ArrayList<ArrayList<Double>> m, ArrayList<Double> a) throws IOException{

		ArrayList<Double> p = new ArrayList<>();
		ArrayList<Double> p2 = new ArrayList<>();
		ArrayList<Double> p3 = new ArrayList<>();

		ArrayList<Integer> degree = new ArrayList<>();

		for(int i=0; i<n; i++) {
			degree.add(0);
			p.add(1.0/n);
		}

		// Changer le fichier virer les noeuds dont rien de pointe sur et ne pointe sur rien

		try (Stream<String> stream = Files.lines(Paths.get(dirLinksFile))) {
			stream.forEach(l -> {
				String[] tab = l.split("\\s");
				int src = Integer.parseInt(tab[0]);
				degree.set(src, degree.get(src)+1);     
			});
		}

		for (int k=0; k<nbIteration; k++) {
			try (Stream<String> stream = Files.lines(Paths.get(dirLinksFile))) {
				stream.forEach(l -> {
					String[] tab = l.split("\\s");
					int tgt = Integer.parseInt(tab[0]);
					p2.set(tgt, p2.get(tgt)+(p.get(tgt)*(1/degree.get(tgt))));	
				});
			}
			
			for(int i=0; i<n; i++) {
				p2.set(i, (p2.get(i)*(1-alpha))+(alpha/n));
			}
			
			for(int i=0; i<n; i++) {
				s+=p2.get(i);
			}
			
			for(int i=0; i<n; i++) {
				p.set(i, p.get(i)+((1-s)/n));
			}
			
			p3=new ArrayList<>(p);
			p = new ArrayList<>(p2);
			p2 = new ArrayList<>(p3);
		}

		return p;

	}

}
