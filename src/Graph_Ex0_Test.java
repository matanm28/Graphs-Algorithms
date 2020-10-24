import org.apache.commons.lang3.time.StopWatch;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.util.CombinatoricsUtils;

/**
 * This is a very basic test class to test mainly the names of the classes & methods.
 */

public class Graph_Ex0_Test {
    static int seed = 31;
    static Random _rnd = new Random(seed);
    static int v_size = 1000;
    static int e_size = v_size * 10;
    static IGraph g0 = new UndirectedGraph(), g1;
    static IGraphAlgorithms ga;

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        System.out.println("Starting Sanity Test\n");
        mainTest();
        System.out.printf("Sanity Test Passed at %s\n",stopWatch.formatTime());
        System.out.println("--".repeat(25));
        System.out.println("Starting secondaries tests\n");
        secondaryTest();
        System.out.println("All Secondaries tests passed\n");
        System.out.printf("All Test Passed at total time of: %s\n",stopWatch.formatTime());
    }

    public static void mainTest() {
        IGraph graph = new UndirectedGraph();
        NodeData.zeroizeNodeKeyCount();
        for (int i = 0; i < 10; ++i) {
            graph.addNode(new NodeData());
        }
        graph.connect(0, 2);
        graph.connect(0, 4);
        graph.connect(0, 2);
        graph.connect(0, 4);
        graph.connect(0, 5);
        graph.connect(1, 4);
        graph.connect(1, 5);
        graph.connect(2, 3);
        graph.connect(2, 4);
        graph.connect(4, 5);
        graph.connect(4, 6);
        graph.connect(4, 7);
        graph.connect(7, 9);
        graph.connect(9, 8);
        assert (graph.getNode(4).hasNi(5));
        assert (!graph.getNode(1).hasNi(3));
        IGraphAlgorithms graphAlgorithms = new GraphAlgorithms(graph);
        boolean connected = graphAlgorithms.isConnected();
        assert (connected);
        List<INodeData> path = new ArrayList<>();
        path.add(graph.getNode(1));
        path.add(graph.getNode(4));
        path.add(graph.getNode(6));
        assert (graphAlgorithms.shortestPath(1, 6).equals(path));
        assert (graphAlgorithms.shortestPathDist(3, 8) == 5);
        assert (graphAlgorithms.shortestPathDist(3, 9) == 4);
        assert (graphAlgorithms.shortestPath(4, 4).equals(new ArrayList<>()));
        assert (graphAlgorithms.shortestPathDist(5, 5) == 0);
    }

    public static void secondaryTest() {
        NodeData.zeroizeNodeKeyCount();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        test1();
        stopWatch.split();
        System.out.printf("First Test Passed in %s%n\n", stopWatch.formatSplitTime());
        test2();
        stopWatch.split();
        System.out.printf("Second Test Passed in %s%n\n", stopWatch.formatSplitTime());
        test3(false);
        stopWatch.stop();
        System.out.println(stopWatch.formatSplitTime());
        System.out.printf("Third Test Passed in %s%n\n", stopWatch.formatSplitTime());
    }


    public static void test1() {
        for (int i = 0; i < v_size; i++) {
            INodeData n = new NodeData();
            g0.addNode(n);
        }
        long vertexNumber = g0.nodeSize();
        BigInteger maxEdges = BigInteger.valueOf(Math.multiplyExact(vertexNumber, vertexNumber - 1));
        long size = Math.min(maxEdges.longValue(), e_size);
        int triesWithNoEdge = 0;
        while (g0.edgeSize() < size) {
            int a = nextRnd(0, v_size + 1);
            int b = nextRnd(0, v_size + 1);
            int sizeBeforeConnect = g0.edgeSize();
            g0.connect(a, b);
            if (sizeBeforeConnect == g0.edgeSize()) {
                triesWithNoEdge++;
            } else {
                triesWithNoEdge = 0;
            }
            if (triesWithNoEdge == 100) {
                break;
            }

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

    public static void test3(boolean printGraph) {
        ga = new GraphAlgorithms(g0);
        g1 = ga.copy();
        ga.init(g1);
        boolean isConnected = ga.isConnected();
        int dist19 = ga.shortestPathDist(1, 9);
        int dist91 = ga.shortestPathDist(1, 9);
        List<INodeData> sp = ga.shortestPath(1, 9);
        if (printGraph) {
            System.out.println(g1);
        }
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















