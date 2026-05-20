package com.example.enyakinrestorant;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class RestorantAlgo {

    public static class MesafeSonucu implements Comparable<MesafeSonucu> {
        private Restorant restorant;
        private double mesafe;
        private List<MapNode> rota;

        public MesafeSonucu(Restorant restorant, double mesafe, List<MapNode> rota) {
            this.restorant = restorant;
            this.mesafe = mesafe;
            this.rota = rota;
        }

        public Restorant getRestorant() { return restorant; }
        public double getMesafe() { return mesafe; }
        public List<MapNode> getRota() { return rota; }

        @Override
        public int compareTo(MesafeSonucu o) {
            return Double.compare(this.mesafe, o.mesafe);
        }
    }

    public List<MesafeSonucu> enYakinRestorantlariBul(User user, String secilenTur, String secilenOzellik, String siralaTipi) {
        RestorantManager manager = new RestorantManager();
        GraphService graphService = new GraphService();
        AStarAlgo aStar = new AStarAlgo();

        List<Restorant> tumRestorantlar = manager.getRestorantlar();
        PriorityQueue<MesafeSonucu> kuyruk = new PriorityQueue<>();

        MapNode userNode = graphService.enYakinNodeBul(user.getDistrict(), user.getX(), user.getY());

        for (Restorant r : tumRestorantlar) {
            if (!r.getCity().equalsIgnoreCase(user.getCity())) {
                continue;
            }

            if (secilenTur != null && !secilenTur.isEmpty() && !r.getType().equalsIgnoreCase(secilenTur)) {
                continue;
            }

            if (secilenOzellik != null && !secilenOzellik.isEmpty() && !r.getFeatures().contains(secilenOzellik)) {
                continue;
            }

            double toplamMesafe = 0;
            List<MapNode> rota = new ArrayList<>();

            if (r.getDistrict().equalsIgnoreCase(user.getDistrict())) {
                MapNode restorantNode = graphService.enYakinNodeBul(r.getDistrict(), r.getX(), r.getY());
                if (userNode != null && restorantNode != null) {
                    rota = aStar.enKisaYolBul(r.getDistrict(), graphService, userNode, restorantNode);
                    toplamMesafe = Math.sqrt(Math.pow(userNode.getX() - user.getX(), 2) + Math.pow(userNode.getY() - user.getY(), 2));

                    for (int i = 0; i < rota.size() - 1; i++) {
                        MapNode n1 = rota.get(i);
                        MapNode n2 = rota.get(i + 1);
                        toplamMesafe += Math.sqrt(Math.pow(n2.getX() - n1.getX(), 2) + Math.pow(n2.getY() - n1.getY(), 2));
                    }

                    toplamMesafe += Math.sqrt(Math.pow(restorantNode.getX() - r.getX(), 2) + Math.pow(restorantNode.getY() - r.getY(), 2));
                } else {
                    toplamMesafe = Math.sqrt(Math.pow(r.getX() - user.getX(), 2) + Math.pow(r.getY() - user.getY(), 2));
                }
            } else {
                // Different district: use Euclidean distance
                toplamMesafe = Math.sqrt(Math.pow(r.getX() - user.getX(), 2) + Math.pow(r.getY() - user.getY(), 2));
            }

            kuyruk.add(new MesafeSonucu(r, toplamMesafe, rota));
        }

        List<MesafeSonucu> siraliListe = new ArrayList<>();
        while (!kuyruk.isEmpty()) {
            siraliListe.add(kuyruk.poll());
        }

        if ("Puan".equalsIgnoreCase(siralaTipi)) {
            for (int i = 0; i < siraliListe.size() - 1; i++) {
                for (int j = 0; j < siraliListe.size() - i - 1; j++) {
                    if (siraliListe.get(j).getRestorant().getRating() < siraliListe.get(j + 1).getRestorant().getRating()) {
                        MesafeSonucu temp = siraliListe.get(j);
                        siraliListe.set(j, siraliListe.get(j + 1));
                        siraliListe.set(j + 1, temp);
                    }
                }
            }
        }

        return siraliListe;
    }
}