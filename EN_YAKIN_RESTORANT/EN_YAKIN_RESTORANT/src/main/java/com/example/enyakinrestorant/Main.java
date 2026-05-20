package com.example.enyakinrestorant;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== BACKEND MOTORU VE A* ALGORİTMASI TEST BAŞLIYOR ===");

        User testKullanici = new User();
        testKullanici.setUsername("demir123");
        testKullanici.setCity("İstanbul");
        testKullanici.setDistrict("Kadıköy");
        testKullanici.setX(200.0);
        testKullanici.setY(200.0);

        System.out.println("Kullanıcı Konumu: " + testKullanici.getCity() + " - " + testKullanici.getDistrict() + " (" + testKullanici.getX() + ", " + testKullanici.getY() + ")");

        RestorantAlgo algo = new RestorantAlgo();

        System.out.println("\n--- TEST 1: KADIKÖY'DEKİ RESTORANLARIN GERÇEK SOKAK MESAFESİNE GÖRE SIRALANMASI ---");
        List<RestorantAlgo.MesafeSonucu> mesafeSonuclari = algo.enYakinRestorantlariBul(testKullanici, "", "", "Mesafe");

        if (mesafeSonuclari.isEmpty()) {
            System.out.println("UYARI: Hiç restoran bulunamadı! 'resources/restorantlar.json' dosyasını ve konumları kontrol edin.");
        } else {
            for (RestorantAlgo.MesafeSonucu sonuc : mesafeSonuclari) {
                System.out.println("\n[Restoran]: " + sonuc.getRestorant().getName() + " | Tür: " + sonuc.getRestorant().getType() + " | Puan: " + sonuc.getRestorant().getRating());
                System.out.println("Gerçek Sokak Mesafesi: " + String.format("%.2f", sonuc.getMesafe()) + " piksel");

                System.out.print("A* Takip Edilen Sokak Rotası: ");
                List<MapNode> rota = sonuc.getRota();
                for (int i = 0; i < rota.size(); i++) {
                    System.out.print(rota.get(i).getId());
                    if (i < rota.size() - 1) {
                        System.out.print(" -> ");
                    }
                }
                System.out.println();
            }
        }

        System.out.println("\n--- TEST 2: SADECE 'WiFi' ÖZELLİĞİ OLAN VE PUANA GÖRE SIRALANMIŞ RESTORANLAR ---");
        List<RestorantAlgo.MesafeSonucu> puanSonuclari = algo.enYakinRestorantlariBul(testKullanici, "", "WiFi", "Puan");

        for (RestorantAlgo.MesafeSonucu sonuc : puanSonuclari) {
            System.out.println("Restoran: " + sonuc.getRestorant().getName() + " | Puan: " + sonuc.getRestorant().getRating() + " | Özellikler: " + sonuc.getRestorant().getFeatures());
        }

        System.out.println("\n================ TEST BİTTİ ================");
    }
}