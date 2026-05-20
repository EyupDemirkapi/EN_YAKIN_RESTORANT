package com.example.enyakinrestorant;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static final String FILE_PATH = "src/main/resources/users.json";

    public List<User> getUsers() {
        List<User> liste = new ArrayList<>();
        File file = FileHelper.findFile(FILE_PATH);

        if (!file.exists()) return liste;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String satir;
            while ((satir = br.readLine()) != null) {
                sb.append(satir.trim());
            }

            String icerik = sb.toString();
            if (icerik.equals("[]") || icerik.isEmpty()) return liste;

            String[] objeler = icerik.split("\\},\\s*\\{");

            for (String obj : objeler) {
                obj = obj.replace("[", "").replace("]", "").replace("{", "").replace("}", "");

                String username = ayıkla(obj, "username");
                String password = ayıkla(obj, "password");
                String securityQuestion = ayıkla(obj, "securityQuestion");
                String securityAnswer = ayıkla(obj, "securityAnswer");
                String city = ayıkla(obj, "city");
                String district = ayıkla(obj, "district");
                double x = Double.parseDouble(ayıkla(obj, "x"));
                double y = Double.parseDouble(ayıkla(obj, "y"));

                String favRaw = ayıkla(obj, "favRestaurants").replace("\"", "");
                List<String> favRestaurants = new ArrayList<>();
                for (String f : favRaw.split("-")) {
                    if (!f.isEmpty()) favRestaurants.add(f);
                }

                User u = new User();
                u.setUsername(username);
                u.setPassword(password);
                u.setSecurityQuestion(securityQuestion);
                u.setSecurityAnswer(securityAnswer);
                u.setCity(city);
                u.setDistrict(district);
                u.setX(x);
                u.setY(y);
                u.setFavRestaurants(favRestaurants);
                liste.add(u);
            }
        } catch (Exception e) {
            System.out.println("Kullanıcı JSON okuma hatası: " + e.getMessage());
        }
        return liste;
    }
    public void saveUsers(List<User> liste) {
        File file = FileHelper.findFile(FILE_PATH);
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            StringBuilder json = new StringBuilder("[\n");

            for (int i = 0; i < liste.size(); i++) {
                User u = liste.get(i);
                json.append("  {\n");
                json.append("    \"username\": \"").append(u.getUsername()).append("\",\n");
                json.append("    \"password\": \"").append(u.getPassword()).append("\",\n");
                json.append("    \"securityQuestion\": \"").append(u.getSecurityQuestion()).append("\",\n");
                json.append("    \"securityAnswer\": \"").append(u.getSecurityAnswer()).append("\",\n");
                json.append("    \"city\": \"").append(u.getCity()).append("\",\n");
                json.append("    \"district\": \"").append(u.getDistrict()).append("\",\n");
                json.append("    \"x\": ").append(u.getX()).append(",\n");
                json.append("    \"y\": ").append(u.getY()).append(",\n");

                String favlar = String.join("-", u.getFavRestaurants());
                json.append("    \"favRestaurants\": \"").append(favlar).append("\"\n");
                json.append("  }");

                if (i < liste.size() - 1) json.append(",");
                json.append("\n");
            }
            json.append("]");

            out.print(json.toString());
        } catch (IOException e) {
            System.out.println("Kullanıcı JSON yazma hatası!");
        }
    }
    public void addUser(User newUser) {
        List<User> mevcutlar = getUsers();
        mevcutlar.add(newUser);
        saveUsers(mevcutlar);
    }
    public boolean register(String username, String password, String city, String district,
                            double x, double y, String securityQuestion, String securityAnswer) {
        List<User> mevcutlar = getUsers();
        for (User u : mevcutlar) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return false;
            }
        }
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        u.setCity(city);
        u.setDistrict(district);
        u.setX(x);
        u.setY(y);
        u.setSecurityQuestion(securityQuestion);
        u.setSecurityAnswer(securityAnswer);
        u.setFavRestaurants(new ArrayList<>());

        mevcutlar.add(u);
        saveUsers(mevcutlar);
        return true;
    }
    public User login(String username, String password) {
        List<User> mevcutlar = getUsers();
        for (User u : mevcutlar) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }
    public boolean resetPassword(String username, String question, String answer, String newPassword) {
        List<User> mevcutlar = getUsers();
        for (User u : mevcutlar) {
            if (u.getUsername().equals(username) && u.getSecurityQuestion().equals(question) && u.getSecurityAnswer().equalsIgnoreCase(answer)) {
                u.setPassword(newPassword);
                saveUsers(mevcutlar);
                return true;
            }
        }
        return false;
    }
    public void updateUserLocation(String username, String city, String district, double x, double y) {
        List<User> mevcutlar = getUsers();
        for (User u : mevcutlar) {
            if (u.getUsername().equals(username)) {
                u.setCity(city);
                u.setDistrict(district);
                u.setX(x);
                u.setY(y);
                saveUsers(mevcutlar);
                break;
            }
        }
    }
    public void updateFavRestaurants(String username, List<String> yeniFavListesi) {
        List<User> mevcutlar = getUsers();
        for (User u : mevcutlar) {
            if (u.getUsername().equals(username)) {
                u.setFavRestaurants(yeniFavListesi);
                saveUsers(mevcutlar);
                break;
            }
        }
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