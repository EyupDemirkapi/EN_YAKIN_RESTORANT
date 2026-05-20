package com.example.enyakinrestorant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphService {

    private Map<String, List<MapNode>> ilceNodeListesi;
    private Map<String, List<MapEdge>> ilceEdgeListesi;

    public GraphService() {
        ilceNodeListesi = new HashMap<>();
        ilceEdgeListesi = new HashMap<>();
        grafiOlustur();
    }

    private void grafiOlustur() {
        List<MapNode> kadikoyNodes = new ArrayList<>();
        kadikoyNodes.add(new MapNode("K_Boga", 220, 196));
        kadikoyNodes.add(new MapNode("K_SogutluCesme", 239, 231));
        kadikoyNodes.add(new MapNode("K_Ayrilik Cesmesi", 194, 147));
        kadikoyNodes.add(new MapNode("K_Fikirtepe", 314, 185));
        kadikoyNodes.add(new MapNode("K_Merdivenkoy", 463, 220));
        kadikoyNodes.add(new MapNode("K_Suadiye", 529, 510));
        kadikoyNodes.add(new MapNode("K_Kozyatagi", 534, 413));
        kadikoyNodes.add(new MapNode("K_Erenkoy", 456, 403));
        kadikoyNodes.add(new MapNode("K_Caddebostan", 401, 410));
        kadikoyNodes.add(new MapNode("K_Feneryolu", 306, 341));
        kadikoyNodes.add(new MapNode("K_Göztepe", 422, 318));
        kadikoyNodes.add(new MapNode("K_Moda", 160, 313));
        kadikoyNodes.add(new MapNode("K_Fenerbahce", 268, 398));
        kadikoyNodes.add(new MapNode("K_Acibadem", 287, 109));
        kadikoyNodes.add(new MapNode("K_Kosuyolu", 202, 87));
        kadikoyNodes.add(new MapNode("K_Bostanci", 638, 505));
        ilceNodeListesi.put("Kadıköy", kadikoyNodes);

        List<MapEdge> kadikoyEdges = new ArrayList<>();
        kadikoyEdges.add(new MapEdge("K_Kosuyolu", "K_Acibadem", 88));
        kadikoyEdges.add(new MapEdge("K_Acibadem", "K_Fikirtepe", 80));
        kadikoyEdges.add(new MapEdge("K_Fikirtepe", "K_Göztepe", 172));
        kadikoyEdges.add(new MapEdge("K_Ayrilik Cesmesi", "K_Kosuyolu", 61));
        kadikoyEdges.add(new MapEdge("K_Ayrilik Cesmesi", "K_Acibadem", 101));
        kadikoyEdges.add(new MapEdge("K_Ayrilik Cesmesi", "K_Boga", 55));
        kadikoyEdges.add(new MapEdge("K_Boga", "K_Moda", 131));
        kadikoyEdges.add(new MapEdge("K_Moda", "K_SogutluCesme", 114));
        kadikoyEdges.add(new MapEdge("K_SogutluCesme", "K_Boga", 40));
        kadikoyEdges.add(new MapEdge("K_SogutluCesme", "K_Fikirtepe", 88));
        kadikoyEdges.add(new MapEdge("K_Boga", "K_Acibadem", 110));
        kadikoyEdges.add(new MapEdge("K_SogutluCesme", "K_Feneryolu", 128));
        kadikoyEdges.add(new MapEdge("K_Feneryolu", "K_Göztepe", 118));
        kadikoyEdges.add(new MapEdge("K_Feneryolu", "K_Fenerbahce", 68));
        kadikoyEdges.add(new MapEdge("K_Fenerbahce", "K_Caddebostan", 133));
        kadikoyEdges.add(new MapEdge("K_Caddebostan", "K_Suadiye", 162));
        kadikoyEdges.add(new MapEdge("K_Feneryolu", "K_Caddebostan", 118));
        kadikoyEdges.add(new MapEdge("K_Caddebostan", "K_Erenkoy", 56));
        kadikoyEdges.add(new MapEdge("K_Erenkoy", "K_Suadiye", 129));
        kadikoyEdges.add(new MapEdge("K_Erenkoy", "K_Kozyatagi", 78));
        kadikoyEdges.add(new MapEdge("K_Suadiye", "K_Bostanci", 110));
        kadikoyEdges.add(new MapEdge("K_Kozyatagi", "K_Bostanci", 139));
        kadikoyEdges.add(new MapEdge("K_Merdivenkoy", "K_Kozyatagi", 205));
        kadikoyEdges.add(new MapEdge("K_Merdivenkoy", "K_Fikirtepe", 154));
        kadikoyEdges.add(new MapEdge("K_Kozyatagi", "K_Göztepe", 146));
        kadikoyEdges.add(new MapEdge("K_Göztepe", "K_Merdivenkoy", 107));
        ilceEdgeListesi.put("Kadıköy", kadikoyEdges);

        List<MapNode> besiktasNodes = new ArrayList<>();
        besiktasNodes.add(new MapNode("B_Akaretler", 293, 554));
        besiktasNodes.add(new MapNode("B_Mecidiye", 401, 446));
        besiktasNodes.add(new MapNode("B_Ciragan", 364, 515));
        besiktasNodes.add(new MapNode("B_Ortaköy", 457, 422));
        besiktasNodes.add(new MapNode("B_Kurucesme", 468, 388));
        besiktasNodes.add(new MapNode("B_Adakent", 393, 384));
        besiktasNodes.add(new MapNode("B_Zincirlikuyu", 376, 340));
        besiktasNodes.add(new MapNode("B_Nispetiye", 415, 310));
        besiktasNodes.add(new MapNode("B_Kultur", 472, 297));
        besiktasNodes.add(new MapNode("B_Balmumcu", 346, 416));
        besiktasNodes.add(new MapNode("B_Gayrettepe", 294, 389));
        besiktasNodes.add(new MapNode("B_Turkali", 298, 511));
        besiktasNodes.add(new MapNode("B_Ihlamur", 306, 445));
        besiktasNodes.add(new MapNode("B_Akatlar", 384, 165));
        besiktasNodes.add(new MapNode("B_Bebek", 532, 218));
        besiktasNodes.add(new MapNode("B_Levent", 332, 204));
        besiktasNodes.add(new MapNode("B_Konaklar", 326, 85));
        besiktasNodes.add(new MapNode("B_Harbiye", 240, 530));
        ilceNodeListesi.put("Beşiktaş", besiktasNodes);

        List<MapEdge> besiktasEdges = new ArrayList<>();
        besiktasEdges.add(new MapEdge("B_Konaklar", "B_Akatlar", 99));
        besiktasEdges.add(new MapEdge("B_Akatlar", "B_Bebek", 157));
        besiktasEdges.add(new MapEdge("B_Bebek", "B_Kultur", 99));
        besiktasEdges.add(new MapEdge("B_Kultur", "B_Kurucesme", 91));
        besiktasEdges.add(new MapEdge("B_Kurucesme", "B_Ortaköy", 35));
        besiktasEdges.add(new MapEdge("B_Ortaköy", "B_Mecidiye", 61));
        besiktasEdges.add(new MapEdge("B_Mecidiye", "B_Ciragan", 79));
        besiktasEdges.add(new MapEdge("B_Ciragan", "B_Akaretler", 81));
        besiktasEdges.add(new MapEdge("B_Akaretler", "B_Harbiye", 58));
        besiktasEdges.add(new MapEdge("B_Harbiye", "B_Turkali", 61));
        besiktasEdges.add(new MapEdge("B_Akaretler", "B_Turkali", 43));
        besiktasEdges.add(new MapEdge("B_Turkali", "B_Ihlamur", 67));
        besiktasEdges.add(new MapEdge("B_Ihlamur", "B_Gayrettepe", 57));
        besiktasEdges.add(new MapEdge("B_Gayrettepe", "B_Balmumcu", 59));
        besiktasEdges.add(new MapEdge("B_Ihlamur", "B_Balmumcu", 49));
        besiktasEdges.add(new MapEdge("B_Balmumcu", "B_Adakent", 57));
        besiktasEdges.add(new MapEdge("B_Balmumcu", "B_Mecidiye", 63));
        besiktasEdges.add(new MapEdge("B_Adakent", "B_Zincirlikuyu", 47));
        besiktasEdges.add(new MapEdge("B_Zincirlikuyu", "B_Levent", 143));
        besiktasEdges.add(new MapEdge("B_Levent", "B_Konaklar", 119));
        besiktasEdges.add(new MapEdge("B_Levent", "B_Akatlar", 65));
        besiktasEdges.add(new MapEdge("B_Zincirlikuyu", "B_Nispetiye", 49));
        besiktasEdges.add(new MapEdge("B_Nispetiye", "B_Kultur", 58));
        besiktasEdges.add(new MapEdge("B_Adakent", "B_Nispetiye", 77));
        besiktasEdges.add(new MapEdge("B_Adakent", "B_Ortaköy", 74));
        besiktasEdges.add(new MapEdge("B_Nispetiye", "B_Akatlar", 149));
        besiktasEdges.add(new MapEdge("B_Kultur", "B_Akatlar", 159));
        ilceEdgeListesi.put("Beşiktaş", besiktasEdges);
    }

    public List<MapNode> getNodesByIlce(String ilce) {
        if (ilce == null) return new ArrayList<>();
        if (ilce.equalsIgnoreCase("Kadıköy") || ilce.equalsIgnoreCase("Kadikoy")) {
            return ilceNodeListesi.getOrDefault("Kadıköy", new ArrayList<>());
        } else if (ilce.equalsIgnoreCase("Beşiktaş") || ilce.equalsIgnoreCase("Besiktas")) {
            return ilceNodeListesi.getOrDefault("Beşiktaş", new ArrayList<>());
        }
        return new ArrayList<>();
    }

    public List<MapEdge> getEdgesByIlce(String ilce) {
        if (ilce == null) return new ArrayList<>();
        if (ilce.equalsIgnoreCase("Kadıköy") || ilce.equalsIgnoreCase("Kadikoy")) {
            return ilceEdgeListesi.getOrDefault("Kadıköy", new ArrayList<>());
        } else if (ilce.equalsIgnoreCase("Beşiktaş") || ilce.equalsIgnoreCase("Besiktas")) {
            return ilceEdgeListesi.getOrDefault("Beşiktaş", new ArrayList<>());
        }
        return new ArrayList<>();
    }

    public MapNode enYakinNodeBul(String ilce, double x, double y) {
        List<MapNode> nodes = getNodesByIlce(ilce);
        MapNode enYakin = null;
        double enKucukMesafe = Double.MAX_VALUE;

        for (MapNode node : nodes) {
            double mesafe = Math.sqrt(Math.pow(node.getX() - x, 2) + Math.pow(node.getY() - y, 2));
            if (mesafe < enKucukMesafe) {
                enKucukMesafe = mesafe;
                enYakin = node;
            }
        }
        return enYakin;
    }
}