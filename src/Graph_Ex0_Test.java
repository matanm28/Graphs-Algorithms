import org.apache.commons.lang3.time.StopWatch;

import java.util.List;
import java.util.Random;

/**
 * This is a very basic test class to test mainly the names of the classes & methods.
 */

public class Graph_Ex0_Test {
    static int seed = 31;
    static Random _rnd = new Random(seed);
    static int v_size = 100;
    static int e_size = v_size * 3;
    static IGraph g0 = new UndirectedGraph(), g1;
    static IGraphAlgorithms ga;

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        test1();
        stopWatch.split();
        System.out.println(stopWatch.formatSplitTime());
        test2();
        stopWatch.split();
        System.out.println(stopWatch.formatSplitTime());
        System.out.println(g0);
        stopWatch.split();
        System.out.println(stopWatch.formatSplitTime());
        test3();
        stopWatch.stop();
        System.out.println(stopWatch.formatSplitTime());
    }

    public static void test1() {
        for (int i = 0; i < v_size; i++) {
            INodeData n = new NodeData();
            g0.addNode(n);
        }
        while (g0.edgeSize() < e_size) {
            int a = nextRnd(0, v_size);
            int b = nextRnd(0, v_size);
            g0.connect(a, b);
        }
        //System.out.println(g0);
    }

    public static void test2() {
        g0.removeEdge(9, 3);
        g0.removeEdge(9, 3);
        g0.removeNode(0);
        g0.removeNode(0);
        g0.removeNode(2);
        g0.removeNode(8);
    }

    public static void test3() {
        ga = new GraphAlgorithms(g0);
        g1 = ga.copy();
        ga.init(g1);
        boolean isConnected = ga.isConnected();
        int dist19 = ga.shortestPathDist(1, 9);
        int dist91 = ga.shortestPathDist(1, 9);
        List<INodeData> sp = ga.shortestPath(1, 9);
        System.out.println(g1);
        System.out.println("Is connected: " + isConnected);
        System.out.println("shortest path: 1,9 dist=" + dist19);
        System.out.println("shortest path: 9,1 dist=" + dist91);
        for (int i = 0; i < sp.size(); i++) {
            System.out.println(" " + sp.get(i));
        }
    }

    public static int nextRnd(int min, int max) {
        double v = nextRnd(0.0 + min, (double) max);
        int ans = (int) v;
        return ans;
    }

    public static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max - min;
        double ans = d * dx + min;
        return ans;
    }
}
