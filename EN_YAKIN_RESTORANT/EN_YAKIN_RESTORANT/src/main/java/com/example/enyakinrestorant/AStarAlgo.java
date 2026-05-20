package com.example.enyakinrestorant;

import java.util.*;

public class AStarAlgo {

    public static class NodeWrapper implements Comparable<NodeWrapper> {
        private MapNode node;
        private double gCost;
        private double fCost;
        private NodeWrapper parent;

        public NodeWrapper(MapNode node, double gCost, double fCost, NodeWrapper parent) {
            this.node = node;
            this.gCost = gCost;
            this.fCost = fCost;
            this.parent = parent;
        }

        public MapNode getNode() { return node; }
        public double getGCost() { return gCost; }
        public NodeWrapper getParent() { return parent; }

        @Override
        public int compareTo(NodeWrapper o) {
            return Double.compare(this.fCost, o.fCost);
        }
    }

    private double hesaplaOklid(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public List<MapNode> enKisaYolBul(String ilce, GraphService graphService, MapNode start, MapNode target) {
        if (start == null || target == null) return new ArrayList<>();

        List<MapEdge> edges = graphService.getEdgesByIlce(ilce);
        List<MapNode> allNodes = graphService.getNodesByIlce(ilce);

        PriorityQueue<NodeWrapper> openList = new PriorityQueue<>();
        Map<String, Double> closedList = new HashMap<>();

        openList.add(new NodeWrapper(start, 0, hesaplaOklid(start.getX(), start.getY(), target.getX(), target.getY()), null));

        NodeWrapper targetWrapper = null;

        while (!openList.isEmpty()) {
            NodeWrapper current = openList.poll();

            if (closedList.containsKey(current.getNode().getId())) {
                continue;
            }

            if (current.getNode().getId().equals(target.getId())) {
                targetWrapper = current;
                break;
            }

            closedList.put(current.getNode().getId(), current.gCost);

            for (MapEdge edge : edges) {
                MapNode komsu = null;
                if (edge.getStartId().equals(current.getNode().getId())) {
                    komsu = bulNodeById(allNodes, edge.getEndId());
                } else if (edge.getEndId().equals(current.getNode().getId())) {
                    komsu = bulNodeById(allNodes, edge.getStartId());
                }

                if (komsu == null) continue;

                double yeniGCost = current.gCost + edge.getCost();

                if (closedList.containsKey(komsu.getId()) && closedList.get(komsu.getId()) <= yeniGCost) {
                    continue;
                }

                double hCost = hesaplaOklid(komsu.getX(), komsu.getY(), target.getX(), target.getY());
                double fCost = yeniGCost + hCost;

                openList.add(new NodeWrapper(komsu, yeniGCost, fCost, current));
            }
        }

        List<MapNode> rota = new ArrayList<>();
        if (targetWrapper != null) {
            NodeWrapper temp = targetWrapper;
            while (temp != null) {
                rota.add(0, temp.getNode());
                temp = temp.getParent();
            }
        }
        return rota;
    }

    private MapNode bulNodeById(List<MapNode> nodes, String id) {
        for (MapNode n : nodes) {
            if (n.getId().equals(id)) return n;
        }
        return null;
    }
}