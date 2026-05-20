package com.example.enyakinrestorant;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RestorantManager {
    private static final String FILE_PATH = "src/main/resources/restorantlar.json";

    public List<Restorant> getRestorantlar() {
        File file = FileHelper.findFile(FILE_PATH);

        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String satir;
                while ((satir = br.readLine()) != null) {
                    sb.append(satir.trim());
                }
            }
            String icerik = sb.toString();
            if (icerik.equals("[]") || icerik.isEmpty()) return new ArrayList<>();

            List<Restorant> liste = new ArrayList<>();
            String[] objeler = icerik.split("\\},\\s*\\{");

            for (String obj : objeler) {
                obj = obj.replace("[", "").replace("]", "").replace("{", "").replace("}", "");

                String name = ayıkla(obj, "name");
                String type = ayıkla(obj, "type");
                String city = ayıkla(obj, "city");
                String district = ayıkla(obj, "district");
                double x = Double.parseDouble(ayıkla(obj, "x"));
                double y = Double.parseDouble(ayıkla(obj, "y"));
                double rating = Double.parseDouble(ayıkla(obj, "rating"));

                String featuresRaw = ayıkla(obj, "features").replace("\"", "");
                List<String> features = new ArrayList<>();
                for (String f : featuresRaw.split("-")) {
                    if (!f.isEmpty()) features.add(f);
                }
                liste.add(new Restorant(name, type, city, district, x, y, rating, features));
            }
            return liste;
        } catch (Exception e) {
            System.out.println("JSON okuma hatası: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    public void saveRestorantlar(List<Restorant> liste) {
        File file = FileHelper.findFile(FILE_PATH);
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            StringBuilder json = new StringBuilder("[\n");

            for (int i = 0; i < liste.size(); i++) {
                Restorant r = liste.get(i);
                json.append("  {\n");
                json.append("    \"name\": \"").append(r.getName()).append("\",\n");
                json.append("    \"type\": \"").append(r.getType()).append("\",\n");
                json.append("    \"city\": \"").append(r.getCity()).append("\",\n");
                json.append("    \"district\": \"").append(r.getDistrict()).append("\",\n");
                json.append("    \"x\": ").append(r.getX()).append(",\n");
                json.append("    \"y\": ").append(r.getY()).append(",\n");
                json.append("    \"rating\": ").append(r.getRating()).append(",\n");

                String ozellikler = String.join("-", r.getFeatures());
                json.append("    \"features\": \"").append(ozellikler).append("\"\n");
                json.append("  }");

                if (i < liste.size() - 1) json.append(",");
                json.append("\n");
            }
            json.append("]");

            out.print(json.toString());
        } catch (IOException e) {
            System.out.println("JSON yazma hatası!");
        }
    }
    public void addRestorant(Restorant newRestorant) {
        List<Restorant> mevcutlar = getRestorantlar();
        mevcutlar.add(newRestorant);
        saveRestorantlar(mevcutlar);
    }
    private String ayıkla(String metin, String anahtar) {
        int baslangic = metin.indexOf("\"" + anahtar + "\":");
        if (baslangic == -1) return "";
        baslangic += anahtar.length() + 3;

        int bitis = metin.indexOf(",", baslangic);
        if (bitis == -1) bitis = metin.length();

        return metin.substring(baslangic, bitis).trim().replace("\"", "");
    }
}