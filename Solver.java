package hw3.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.HashSet;
import java.util.Set;

import java.util.Comparator;

public class Solver {

    private class searchNode{
        private WorldState worldstate;
        private int nMoves;
        private searchNode previous;
        public searchNode(WorldState ws) {
            worldstate = ws;
            nMoves = 0;
            previous = null;
        }

    }

    Stack<WorldState> wsstack;
    MinPQ<searchNode> pq;
    int nMoves;


    private static class solverComparator implements Comparator<searchNode>{
        @Override
        public int compare(searchNode a, searchNode b) {
            return (a.nMoves + a.worldstate.estimatedDistanceToGoal()) - (b.nMoves + b.worldstate.estimatedDistanceToGoal());
        }
    }

    public Solver(WorldState initial) {
        // searchNode initialNode = new searchNode(initial);
        wsstack = new Stack<>();
        pq = new MinPQ<>(new solverComparator());

        searchNode sn = new searchNode(initial);
        pq.insert(sn);
        Set<WorldState> keyset = new HashSet<>();
        keyset.add(initial);

        while(!pq.isEmpty()) {
           sn = pq.delMin();
           if (!sn.worldstate.isGoal()) {
               for (WorldState neighb : sn.worldstate.neighbors()) {
                   if(keyset.contains(neighb)) {
                       continue;
                   }
                   keyset.add(neighb);
                   searchNode neighbNode = new searchNode(neighb);
                   neighbNode.nMoves = sn.nMoves + 1;
                   neighbNode.previous = sn;
                   pq.insert(neighbNode);
                   //System.out.println(neighb + "  " + neighbNode.nMoves + "  " + neighb.estimatedDistanceToGoal());
               }
               //nMoves++;
           } else {
               break;
           }

        }

        nMoves = sn.nMoves;
        for(int i = 0; i <= nMoves; i++) {
            wsstack.push(sn.worldstate);
            sn = sn.previous;
        }

        keyset = null;
    }
    public int moves() {
        return nMoves;
    }
    public Iterable<WorldState> solution() {

        return wsstack;
    }


}
