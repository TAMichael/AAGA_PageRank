 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
 
public class MergeFile {
 
    public static void main(String[] args) throws FileNotFoundException {
        if(args.length != 7) {
            System.out.println("7 arguments needed in this order");
            System.out.println("a15.txt in.txt out.txt a1.txt a2.txt a5.txt a9.txt");
            return;
        }
        Map<Integer, Double> a15 = readFileIntDouble(args[0]);
        Map<Integer, Integer> tmp = readFileIntInt(args[1]);
        Map<Double, Integer> res = new HashMap<>();
       
        for(Integer key: a15.keySet()) {
            res.put(a15.get(key), tmp.get(key));
        }
        tmp.clear();
        printFileDoubleInt("graph1.txt", res);
        res.clear();
       
        tmp = readFileIntInt(args[2]);
        for(Integer key: a15.keySet()) {
            res.put(a15.get(key), tmp.get(key));
        }
        tmp.clear();
        printFileDoubleInt("graph2.txt", res);
        res.clear();
       
        Map<Integer, Double> tmp2 = new HashMap<>();
        Map<Double, Double> res2 = new HashMap<>();
        tmp2 = readFileIntDouble(args[3]);
        for(Map.Entry<Integer, Double> entry: a15.entrySet()) {
            res2.put(entry.getValue(), tmp2.get(entry.getKey()));
        }
        tmp2.clear();
        printFileDoubleDouble("graph3.txt", res2);
        res2.clear();
       
        tmp2 = readFileIntDouble(args[4]);
        for(Map.Entry<Integer, Double> entry: a15.entrySet()) {
            res2.put(entry.getValue(), tmp2.get(entry.getKey()));
        }
        tmp2.clear();
        printFileDoubleDouble("graph4.txt", res2);
        res2.clear();
       
        tmp2 = readFileIntDouble(args[5]);
        for(Map.Entry<Integer, Double> entry: a15.entrySet()) {
            res2.put(entry.getValue(), tmp2.get(entry.getKey()));
        }
        tmp2.clear();
        printFileDoubleDouble("graph5.txt", res2);
        res2.clear();
       
        tmp2 = readFileIntDouble(args[6]);
        for(Map.Entry<Integer, Double> entry: a15.entrySet()) {
            res2.put(entry.getValue(), tmp2.get(entry.getKey()));
        }
        tmp2.clear();
        printFileDoubleDouble("graph6.txt", res2);
        res2.clear();
       
 
    }
   
    public static void printFileDoubleInt(String file, Map<Double,Integer> map) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(file);
        for(Map.Entry<Double, Integer> entry: map.entrySet()) {
            pw.printf("%.8f %d\n", entry.getKey(), entry.getValue());
        }
        pw.close();
    }
   
    public static void printFileDoubleDouble(String file, Map<Double,Double> map) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(file);
        for(Map.Entry<Double, Double> entry: map.entrySet()) {
            pw.printf("%.8f %.8f\n", entry.getKey(), entry.getValue());
        }
        pw.close();
    }
   
    public static Map<Integer, Integer> readFileIntInt(String file) throws FileNotFoundException{
        HashMap<Integer,Integer> map = new HashMap<>();
        Scanner sc = new Scanner(new File(file));
        int v1;
        int v2;
        while(sc.hasNext()) {
            v1 = sc.nextInt();
            v2 = sc.nextInt();
            map.put(v1, v2);
        }
        sc.close();
        return map;
    }
   
   
    public static Map<Integer, Double> readFileIntDouble(String file) throws FileNotFoundException{
        HashMap<Integer,Double> map = new HashMap<>();
        Scanner sc = new Scanner(new File(file));
        int v1;
        double v2;
        while(sc.hasNext()) {
            v1 = sc.nextInt();
            v2 = sc.nextDouble();
   
            map.put(v1, v2);
        }
        sc.close();
        return map;
    }
   
 
}