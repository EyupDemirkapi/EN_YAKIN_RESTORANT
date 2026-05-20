package com.example.enyakinrestorant;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private boolean koyuTema = false;
    private boolean menuAcik = false;
    private String gecerliGörünüm = "KART";

    private BorderPane anaPanel;
    private VBox solMenu;
    private StackPane icerikAlani;

    private User aktifKullanici;
    private UserManager userManager = new UserManager();
    private GraphService graphService = new GraphService();
    private RestorantAlgo restorantAlgo = new RestorantAlgo();

    private final String EV_SVG = "M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z";
    private final String ARAMA_SVG = "M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z";
    private final String KALP_SVG = "M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.5 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z";
    private final String PROFIL_SVG = "M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z";
    private final String KART_GÖRÜNÜM_SVG = "M4 14h6v-6H4v6zm0 7h6v-6H4v6zm7 0h6v-6h-6v6zm7 0h6v-6h-6v6zm-7-7h6v-6h-6v6zm7 0h6v-6h-6v6z";
    private final String HARITA_GÖRÜNÜM_SVG = "M20.5 3l-.16.03L15 5.1 9 3 3.36 4.9c-.21.07-.36.25-.36.48v15.12c0 .22.14.4.35.47l.17.03L9 18.9l6 2.1 5.64-1.9c.21-.07.36-.25.36-.48V3.48c0-.22-.14-.4-.35-.47z";
    private final String GERI_SVG = "M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z";

    public void setAktifKullanici(User user) {
        this.aktifKullanici = user;
    }

    public void anaEkranıUçur(Stage stage) {
        try {
            this.start(stage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void start(Stage primaryStage) {
        if (aktifKullanici == null) {
            List<User> users = userManager.getUsers();
            if (!users.isEmpty()) {
                aktifKullanici = users.get(0);
            } else {
                aktifKullanici = new User();
                aktifKullanici.setUsername("Eyüp");
                aktifKullanici.setPassword("123");
                aktifKullanici.setCity("İstanbul");
                aktifKullanici.setDistrict("Kadıköy");
                aktifKullanici.setX(220);
                aktifKullanici.setY(196);
                aktifKullanici.setSecurityQuestion("En sevdiğiniz yemek?");
                aktifKullanici.setSecurityAnswer("Mantı");
                aktifKullanici.setFavRestaurants(new ArrayList<>());
            }
        }

        anaPanel = new BorderPane();
        icerikAlani = new StackPane();

        solMenu = new VBox(25);
        solMenu.setPadding(new Insets(25, 10, 25, 10));
        solMenu.setPrefWidth(65);
        solMenu.setAlignment(Pos.TOP_CENTER);

        Button btnTetikleyici = new Button("☰");
        btnTetikleyici.setFont(Font.font("Segoe UI", 18));

        Button btnAnaMenu = sembolluButonOlustur(EV_SVG, " Ana Menü");
        Button btnArama = sembolluButonOlustur(ARAMA_SVG, " Restoran Ara");
        Button btnFavori = sembolluButonOlustur(KALP_SVG, " Favorilerim");
        Button btnProfil = sembolluButonOlustur(PROFIL_SVG, " Profilim");

        solMenu.getChildren().addAll(btnTetikleyici, btnAnaMenu, btnArama, btnFavori, btnProfil);
        anaPanel.setLeft(solMenu);
        anaPanel.setCenter(icerikAlani);

        btnTetikleyici.setOnAction(e -> menuTetikle());
        btnAnaMenu.setOnAction(e -> sayfaDegistir(anaMenuSayfasiOlustur()));
        btnArama.setOnAction(e -> sayfaDegistir(aramaSayfasiOlustur("", "", "", "Mesafe")));
        btnFavori.setOnAction(e -> sayfaDegistir(favoriSayfasiOlustur()));
        btnProfil.setOnAction(e -> sayfaDegistir(profilSayfasiOlustur()));

        sayfaDegistir(anaMenuSayfasiOlustur());
        temaUygula();

        Scene scene = new Scene(anaPanel, 1200, 850);
        primaryStage.setMinWidth(1150);
        primaryStage.setMinHeight(800);
        primaryStage.setTitle("Lezzet Rotası Pro");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button sembolluButonOlustur(String svgYolu, String metin) {
        Button btn = new Button();
        SVGPath svg = new SVGPath();
        svg.setContent(svgYolu);
        svg.setScaleX(1.3);
        svg.setScaleY(1.3);

        Label lblMetin = new Label(metin);
        lblMetin.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        lblMetin.setVisible(false);
        lblMetin.setManaged(false);

        HBox icerik = new HBox(12, svg, lblMetin);
        icerik.setAlignment(Pos.CENTER_LEFT);
        btn.setGraphic(icerik);
        btn.setPrefWidth(190);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setUserData(new Object[]{svg, lblMetin});
        return btn;
    }

    private void menuTetikle() {
        menuAcik = !menuAcik;
        double hedefGenislik = menuAcik ? 210 : 65;
        solMenu.setPrefWidth(hedefGenislik);
        for (var child : solMenu.getChildren()) {
            if (child instanceof Button && !((Button) child).getText().equals("☰")) {
                Object[] data = (Object[]) child.getUserData();
                Label lbl = (Label) data[1];
                lbl.setVisible(menuAcik);
                lbl.setManaged(menuAcik);
            }
        }
    }

    private void sayfaDegistir(javafx.scene.Node yeniSayfa) {
        icerikAlani.getChildren().clear();
        icerikAlani.getChildren().add(yeniSayfa);
        temaUygula();
    }

    private StackPane anaMenuSayfasiOlustur() {
        StackPane katman = new StackPane();
        VBox kutu = new VBox(25);
        kutu.setAlignment(Pos.CENTER);
        kutu.setPadding(new Insets(50));
        kutu.setStyle("-fx-background-color: transparent;");

        Label lblSlogan = new Label("Sokağın Sesini Dinle, Lezzeti Rotala!");
        lblSlogan.setFont(Font.font("Segoe UI", FontWeight.BOLD, 36));
        Label lblDetay = new Label("A* algoritması ile akıllı sokak takibi ve en optimize gurme rehberi.");
        lblDetay.setFont(Font.font("Segoe UI", 18));

        kutu.getChildren().addAll(lblSlogan, lblDetay);
        katman.getChildren().add(kutu);
        return katman;
    }

    private ScrollPane profilSayfasiOlustur() {
        VBox kutu = new VBox(25);
        kutu.setPadding(new Insets(35));
        kutu.setAlignment(Pos.TOP_LEFT);

        Label baslik = new Label("Profil & Hesap Yönetimi");
        baslik.setFont(Font.font("Segoe UI", FontWeight.BOLD, 26));

        GridPane hesapGrid = new GridPane();
        hesapGrid.setVgap(15);
        hesapGrid.setHgap(15);
        hesapGrid.setPadding(new Insets(15));
        hesapGrid.setStyle("-fx-background-radius: 10;");
        hesapGrid.setId("FORM_KUTUSU");

        TextField txtUsername = new TextField(aktifKullanici.getUsername());
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Yeni şifre girin");

        hesapGrid.add(new Label("Kullanıcı Adı:"), 0, 0);
        hesapGrid.add(txtUsername, 1, 0);
        hesapGrid.add(new Label("Yeni Şifre:"), 0, 1);
        hesapGrid.add(txtPassword, 1, 1);

        GridPane konumGrid = new GridPane();
        konumGrid.setVgap(15);
        konumGrid.setHgap(15);
        konumGrid.setPadding(new Insets(15));
        konumGrid.setStyle("-fx-background-radius: 10;");
        konumGrid.setId("FORM_KUTUSU");

        ComboBox<String> cbIlce = new ComboBox<>();
        cbIlce.getItems().addAll("Kadıköy", "Beşiktaş");
        cbIlce.setValue(aktifKullanici.getDistrict());

        TextField txtX = new TextField(String.valueOf((int) aktifKullanici.getX()));
        TextField txtY = new TextField(String.valueOf((int) aktifKullanici.getY()));

        konumGrid.add(new Label("İlçe Bölgesi:"), 0, 0);
        konumGrid.add(cbIlce, 1, 0);
        konumGrid.add(new Label("Harita X Koordinatı:"), 0, 1);
        konumGrid.add(txtX, 1, 1);
        konumGrid.add(new Label("Harita Y Koordinatı:"), 0, 2);
        konumGrid.add(txtY, 1, 2);

        HBox temaSatir = new HBox(15);
        temaSatir.setAlignment(Pos.CENTER_LEFT);
        Label lblTema = new Label("Pro Karanlık Tema:");
        CheckBox chkTema = new CheckBox();
        chkTema.setSelected(koyuTema);
        chkTema.setOnAction(e -> {
            koyuTema = chkTema.isSelected();
            temaUygula();
        });
        temaSatir.getChildren().addAll(lblTema, chkTema);

        Button btnKaydet = new Button("Değişiklikleri Güvenle Kaydet");
        btnKaydet.getStyleClass().add("primary-button");
        btnKaydet.setOnAction(e -> {
            try {
                double nx = Double.parseDouble(txtX.getText());
                double ny = Double.parseDouble(txtY.getText());
                String yeniUser = txtUsername.getText().trim();

                if (yeniUser.isEmpty()) return;

                aktifKullanici.setDistrict(cbIlce.getValue());
                aktifKullanici.setX(nx);
                aktifKullanici.setY(ny);
                aktifKullanici.setUsername(yeniUser);
                if (!txtPassword.getText().isEmpty()) {
                    aktifKullanici.setPassword(txtPassword.getText());
                }

                List<User> tumu = userManager.getUsers();
                for (int i = 0; i < tumu.size(); i++) {
                    if (tumu.get(i).getSecurityQuestion().equals(aktifKullanici.getSecurityQuestion())) {
                        tumu.set(i, aktifKullanici);
                        break;
                    }
                }
                userManager.saveUsers(tumu);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Tüm sistem ve hesap ayarlarınız güncellendi!", ButtonType.OK);
                alert.showAndWait();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });

        kutu.getChildren().addAll(baslik, new Label("Hesap Ayarları"), hesapGrid, new Label("Lojistik Konum Ayarları"), konumGrid, temaSatir, btnKaydet);

        ScrollPane sp = new ScrollPane(kutu);
        sp.setFitToWidth(true);
        return sp;
    }

    private VBox aramaSayfasiOlustur(String aramaKelimesi, String ozellik, String minPuanStr, String sirala) {
        VBox anaKutu = new VBox(20);
        anaKutu.setPadding(new Insets(25));

        HBox aramaSatiri = new HBox(12);
        aramaSatiri.setAlignment(Pos.CENTER_LEFT);
        aramaSatiri.setPadding(new Insets(15));
        aramaSatiri.setId("FORM_KUTUSU");

        TextField txtArama = new TextField(aramaKelimesi);
        txtArama.setPromptText("Gurme restoran ara...");
        txtArama.setPrefWidth(240);

        Button btnBüyüteç = new Button();
        SVGPath svgB = new SVGPath(); svgB.setContent(ARAMA_SVG); svgB.setFill(Color.web("#3498DB"));
        btnBüyüteç.setGraphic(svgB); btnBüyüteç.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

        ComboBox<String> cbFiltreler = new ComboBox<>();
        cbFiltreler.getItems().addAll("Tümü", "WiFi", "Otopark", "Dış Mekan");
        cbFiltreler.setValue(ozellik.isEmpty() ? "Tümü" : ozellik);

        ComboBox<String> cbPuan = new ComboBox<>();
        cbPuan.getItems().addAll("Tüm Puanlar", "4.0+", "4.5+", "4.7+");
        cbPuan.setValue(minPuanStr.isEmpty() ? "Tüm Puanlar" : minPuanStr);

        RadioButton rbMesafe = new RadioButton("Mesafe");
        RadioButton rbPuan = new RadioButton("Puan");
        ToggleGroup tgSirala = new ToggleGroup();
        rbMesafe.setToggleGroup(tgSirala); rbPuan.setToggleGroup(tgSirala);
        if ("Puan".equals(sirala)) rbPuan.setSelected(true); else rbMesafe.setSelected(true);

        aramaSatiri.getChildren().addAll(txtArama, btnBüyüteç, cbFiltreler, cbPuan, rbMesafe, rbPuan);

        HBox görünümSatiri = new HBox(0);
        görünümSatiri.setAlignment(Pos.CENTER);
        Button btnKartMod = new Button(); btnKartMod.setPrefWidth(250);
        SVGPath svgK = new SVGPath(); svgK.setContent(KART_GÖRÜNÜM_SVG); btnKartMod.setGraphic(svgK);
        Button btnHaritaMod = new Button(); btnHaritaMod.setPrefWidth(250);
        SVGPath svgH = new SVGPath(); svgH.setContent(HARITA_GÖRÜNÜM_SVG); btnHaritaMod.setGraphic(svgH);

        if ("KART".equals(gecerliGörünüm)) {
            btnKartMod.setStyle("-fx-background-color: #3498DB; -fx-background-radius: 10 0 0 10;"); svgK.setFill(Color.WHITE);
            btnHaritaMod.setStyle("-fx-background-color: #E0E0E0; -fx-background-radius: 0 10 10 0;"); svgH.setFill(Color.BLACK);
        } else {
            btnHaritaMod.setStyle("-fx-background-color: #3498DB; -fx-background-radius: 0 10 10 0;"); svgH.setFill(Color.WHITE);
            btnKartMod.setStyle("-fx-background-color: #E0E0E0; -fx-background-radius: 10 0 0 10;"); svgK.setFill(Color.BLACK);
        }
        görünümSatiri.getChildren().addAll(btnKartMod, btnHaritaMod);

        ScrollPane icerikKaydirici = new ScrollPane();
        icerikKaydirici.setFitToWidth(true);
        icerikKaydirici.setPrefHeight(550);

        String filtreOzellik = cbFiltreler.getValue().equals("Tümü") ? "" : cbFiltreler.getValue();
        String siralaTipi = rbPuan.isSelected() ? "Puan" : "Mesafe";

        List<RestorantAlgo.MesafeSonucu> sonuclar = restorantAlgo.enYakinRestorantlariBul(aktifKullanici, "", filtreOzellik, siralaTipi);
        List<RestorantAlgo.MesafeSonucu> filtrelenmis = new ArrayList<>();

        double minPuanLimit = 0.0;
        if (cbPuan.getValue().contains("4.0")) minPuanLimit = 4.0;
        else if (cbPuan.getValue().contains("4.5")) minPuanLimit = 4.5;
        else if (cbPuan.getValue().contains("4.7")) minPuanLimit = 4.7;

        for (var s : sonuclar) {
            boolean isimUygun = aramaKelimesi.isEmpty() || s.getRestorant().getName().toLowerCase().contains(aramaKelimesi.toLowerCase());
            boolean puanUygun = s.getRestorant().getRating() >= minPuanLimit;
            if (isimUygun && puanUygun) filtrelenmis.add(s);
        }

        Runnable tetikleArama = () -> {
            sayfaDegistir(aramaSayfasiOlustur(txtArama.getText().trim(), cbFiltreler.getValue(), cbPuan.getValue(), rbPuan.isSelected() ? "Puan" : "Mesafe"));
        };

        btnBüyüteç.setOnAction(e -> tetikleArama.run());
        txtArama.setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ENTER) tetikleArama.run(); });
        cbFiltreler.setOnAction(e -> tetikleArama.run());
        cbPuan.setOnAction(e -> tetikleArama.run());
        rbMesafe.setOnAction(e -> tetikleArama.run());
        rbPuan.setOnAction(e -> tetikleArama.run());
        btnKartMod.setOnAction(e -> { gecerliGörünüm = "KART"; tetikleArama.run(); });
        btnHaritaMod.setOnAction(e -> { gecerliGörünüm = "HARITA"; tetikleArama.run(); });

        if ("KART".equals(gecerliGörünüm)) {
            FlowPane kartlarPaneli = new FlowPane();
            kartlarPaneli.setHgap(20); kartlarPaneli.setVgap(20);
            kartlarPaneli.setPadding(new Insets(10));
            for (var s : filtrelenmis) {
                kartlarPaneli.getChildren().add(restoranKartiOlustur(s, () -> sayfaDegistir(aramaSayfasiOlustur(aramaKelimesi, ozellik, minPuanStr, sirala))));
            }
            icerikKaydirici.setContent(kartlarPaneli);
        } else {
            icerikKaydirici.setContent(haritaGörünümüOlustur(filtrelenmis, txtArama, cbFiltreler, cbPuan, rbPuan));
        }

        anaKutu.getChildren().addAll(aramaSatiri, görünümSatiri, icerikKaydirici);
        return anaKutu;
    }

    private VBox restoranKartiOlustur(RestorantAlgo.MesafeSonucu sonuc, Runnable geriDonusu) {
        VBox kart = new VBox(0);
        kart.setPrefWidth(290);
        kart.setId("RESTORAN_KARTI");

        StackPane resimAlani = new StackPane();
        resimAlani.setPrefHeight(130);
        
        String isim = sonuc.getRestorant().getName().toLowerCase(new java.util.Locale("tr", "TR"));
        String resimIsmi;
        if (isim.contains("kebap")) {
            resimIsmi = "kebap.jpg";
        } else if (isim.contains("döner") || isim.contains("doner")) {
            resimIsmi = "doner.jpg";
        } else if (isim.contains("dondurma")) {
            resimIsmi = "dondurma.jpg";
        } else if (isim.contains("waffle")) {
            resimIsmi = "waffle.jpg";
        } else if (isim.contains("sushi")) {
            resimIsmi = "sushi.jpg";
        } else if (isim.contains("moda")) {
            resimIsmi = "cafe.jpg";
        } else if (isim.contains("pizza")) {
            resimIsmi = "pizaa.jpg";
        } else if (isim.contains("burger")) {
            resimIsmi = "burger.jpg";
        } else if (isim.contains("balik") || isim.contains("balık")) {
            resimIsmi = "balık.jpg";
        } else if (isim.contains("akaretler") || isim.contains("kahve")) {
            resimIsmi = "kahve.jpg";
        } else if (isim.contains("kumpir")) {
            resimIsmi = "kumpir.jpg";
        } else if (isim.contains("bebek")) {
            resimIsmi = "bebek.jpg";
        } else {
            resimIsmi = "varsayilan_rest.png";
        }

        File imgFile = FileHelper.findFile("src/main/resources/" + resimIsmi);
        if (imgFile.exists()) {
            ImageView iv = new ImageView(new Image(imgFile.toURI().toString(), 290, 130, false, true));
            iv.setStyle("-fx-background-radius: 12 12 0 0;");
            resimAlani.getChildren().add(iv);
        } else {
            resimAlani.setStyle("-fx-background-color: #DDD; -fx-background-radius: 12 12 0 0;");
        }

        Button btnFav = new Button();
        SVGPath svgK = new SVGPath(); svgK.setContent(KALP_SVG);
        boolean favMi = aktifKullanici.getFavRestaurants().contains(sonuc.getRestorant().getName());
        svgK.setContent(KALP_SVG); svgK.setFill(favMi ? Color.RED : Color.WHITE);
        btnFav.setGraphic(svgK); btnFav.setStyle("-fx-background-color: rgba(0,0,0,0.4); -fx-background-radius: 20;");
        StackPane.setAlignment(btnFav, Pos.TOP_RIGHT);
        StackPane.setMargin(btnFav, new Insets(10));

        btnFav.setOnAction(e -> {
            if (aktifKullanici.getFavRestaurants().contains(sonuc.getRestorant().getName())) {
                aktifKullanici.getFavRestaurants().remove(sonuc.getRestorant().getName());
                svgK.setFill(Color.WHITE);
            } else {
                aktifKullanici.getFavRestaurants().add(sonuc.getRestorant().getName());
                svgK.setFill(Color.RED);
            }
            userManager.updateFavRestaurants(aktifKullanici.getUsername(), aktifKullanici.getFavRestaurants());
        });
        resimAlani.getChildren().add(btnFav);

        VBox altGövde = new VBox(10);
        altGövde.setPadding(new Insets(15));

        Label lblIsim = new Label(sonuc.getRestorant().getName());
        lblIsim.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));

        Label lblDetay = new Label(sonuc.getRestorant().getType() + "  •  ⭐ " + sonuc.getRestorant().getRating());
        lblDetay.setFont(Font.font("Segoe UI", 13));
        lblDetay.setTextFill(Color.GRAY);

        Label lblMesafe = new Label("Yol: " + String.format("%.1f", sonuc.getMesafe()) + " metre");
        lblMesafe.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        lblMesafe.setTextFill(Color.web("#3498DB"));

        altGövde.getChildren().addAll(lblIsim, lblDetay, lblMesafe);
        kart.getChildren().addAll(resimAlani, altGövde);

        kart.setOnMouseClicked(e -> {
            if (e.getTarget() != btnFav && e.getTarget() != svgK) {
                sayfaDegistir(restoranDetaySayfasiOlustur(sonuc, geriDonusu));
            }
        });
        return kart;
    }

    private VBox haritaGörünümüOlustur(List<RestorantAlgo.MesafeSonucu> sonuclar, TextField txtArama, ComboBox<String> cbFiltreler, ComboBox<String> cbPuan, RadioButton rbPuan) {
        VBox kutu = new VBox(10);
        kutu.setAlignment(Pos.CENTER);

        HBox haritaSeciciSatir = new HBox(12);
        haritaSeciciSatir.setAlignment(Pos.CENTER);
        haritaSeciciSatir.setPadding(new Insets(5, 0, 5, 0));

        Label lblSecici = new Label("Aktif Harita Bölgesi:");
        lblSecici.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));

        ComboBox<String> cbHaritaSecici = new ComboBox<>();
        cbHaritaSecici.getItems().addAll("Kadıköy", "Beşiktaş");
        cbHaritaSecici.setValue(aktifKullanici.getDistrict());
        cbHaritaSecici.setPrefWidth(150);

        cbHaritaSecici.setOnAction(e -> {
            String secilenIlce = cbHaritaSecici.getValue();
            if (!secilenIlce.equalsIgnoreCase(aktifKullanici.getDistrict())) {
                aktifKullanici.setDistrict(secilenIlce);
                if (secilenIlce.equalsIgnoreCase("Kadıköy")) {
                    aktifKullanici.setX(220);
                    aktifKullanici.setY(196);
                } else {
                    aktifKullanici.setX(293);
                    aktifKullanici.setY(554);
                }

                List<User> tumu = userManager.getUsers();
                for (User u : tumu) {
                    if (u.getUsername().equals(aktifKullanici.getUsername())) {
                        u.setDistrict(aktifKullanici.getDistrict());
                        u.setX(aktifKullanici.getX());
                        u.setY(aktifKullanici.getY());
                        break;
                    }
                }
                userManager.saveUsers(tumu);
                sayfaDegistir(aramaSayfasiOlustur(txtArama.getText().trim(), cbFiltreler.getValue(), cbPuan.getValue(), rbPuan.isSelected() ? "Puan" : "Mesafe"));
            }
        });

        haritaSeciciSatir.getChildren().addAll(lblSecici, cbHaritaSecici);

        Canvas canvas = new Canvas(950, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        String dosyaAdi = aktifKullanici.getDistrict().equals("Kadıköy") ? "kadikoy.png" : "besiktas.png";
        File file = FileHelper.findFile("src/main/resources/" + dosyaAdi);
        if (file.exists()) {
            gc.drawImage(new Image(file.toURI().toString(), 950, 600, false, true), 0, 0);
        }

        gc.setLineWidth(3);
        gc.setStroke(Color.web("#3498DB", 0.6));
        List<MapEdge> edges = graphService.getEdgesByIlce(aktifKullanici.getDistrict());
        List<MapNode> nodes = graphService.getNodesByIlce(aktifKullanici.getDistrict());
        for (var edge : edges) {
            MapNode n1 = null, n2 = null;
            for (var n : nodes) {
                if (n.getId().equals(edge.getStartId())) n1 = n;
                if (n.getId().equals(edge.getEndId())) n2 = n;
            }
            if (n1 != null && n2 != null) {
                gc.strokeLine(n1.getX() * (950.0/800.0), n1.getY() * (600.0/600.0), n2.getX() * (950.0/800.0), n2.getY() * (600.0/600.0));
            }
        }

        for (var s : sonuclar) {
            if (!s.getRestorant().getDistrict().equalsIgnoreCase(aktifKullanici.getDistrict())) {
                continue;
            }
            double rx = s.getRestorant().getX() * (950.0/800.0);
            double ry = s.getRestorant().getY() * (600.0/600.0);
            gc.setFill(Color.web("#E74C3C"));
            gc.fillOval(rx - 8, ry - 8, 16, 16);
            gc.setStroke(Color.WHITE); gc.setLineWidth(2); gc.strokeOval(rx - 8, ry - 8, 16, 16);
            gc.setFill(koyuTema ? Color.WHITE : Color.BLACK);
            gc.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
            gc.fillText(s.getRestorant().getName(), rx + 10, ry + 4);
        }

        double ux = aktifKullanici.getX() * (950.0/800.0);
        double uy = aktifKullanici.getY() * (600.0/600.0);
        gc.setFill(Color.web("#2ECC71"));
        gc.fillOval(ux - 10, uy - 10, 20, 20);
        gc.setStroke(Color.WHITE); gc.strokeOval(ux - 10, uy - 10, 20, 20);

        canvas.setOnMouseClicked(e -> {
            double tikX = e.getX() * (800.0/950.0);
            double tikY = e.getY() * (600.0/600.0);
            aktifKullanici.setX(tikX);
            aktifKullanici.setY(tikY);

            List<User> tümü = userManager.getUsers();
            for (User u : tümü) {
                if (u.getUsername().equals(aktifKullanici.getUsername())) {
                    u.setX(tikX);
                    u.setY(tikY);
                    break;
                }
            }
            userManager.saveUsers(tümü);

            sayfaDegistir(aramaSayfasiOlustur(txtArama.getText().trim(), cbFiltreler.getValue(), cbPuan.getValue(), rbPuan.isSelected() ? "Puan" : "Mesafe"));
        });

        kutu.getChildren().addAll(haritaSeciciSatir, canvas);
        return kutu;
    }

    private VBox restoranDetaySayfasiOlustur(RestorantAlgo.MesafeSonucu sonuc, Runnable geriDonusu) {
        VBox anaKutu = new VBox(20);
        anaKutu.setPadding(new Insets(25));

        Button btnGeri = new Button(" Pencerelere Geri Dön");
        SVGPath svgG = new SVGPath(); svgG.setContent(GERI_SVG); svgG.setFill(Color.web("#7F8C8D"));
        btnGeri.setGraphic(svgG);
        btnGeri.setOnAction(e -> geriDonusu.run());

        HBox baslikSatir = new HBox(20);
        baslikSatir.setAlignment(Pos.CENTER_LEFT);
        Label lblIsim = new Label(sonuc.getRestorant().getName());
        lblIsim.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        baslikSatir.getChildren().add(lblIsim);

        Canvas canvas = new Canvas(950, 380);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        boolean ayniIlce = aktifKullanici.getDistrict().equalsIgnoreCase(sonuc.getRestorant().getDistrict());
        String dosyaAdi = sonuc.getRestorant().getDistrict().equalsIgnoreCase("Kadıköy") ? "kadikoy.png" : "besiktas.png";
        File file = FileHelper.findFile("src/main/resources/" + dosyaAdi);
        if (file.exists()) {
            gc.drawImage(new Image(file.toURI().toString(), 950, 380, false, true), 0, 0);
        }

        if (ayniIlce) {
            gc.setLineWidth(5);
            gc.setStroke(Color.web("#2ECC71"));
            List<MapNode> rota = sonuc.getRota();
            if (rota != null && !rota.isEmpty()) {
                gc.strokeLine(aktifKullanici.getX() * (950.0/800.0), aktifKullanici.getY() * (380.0/600.0),
                              rota.get(0).getX() * (950.0/800.0), rota.get(0).getY() * (380.0/600.0));

                for (int i = 0; i < rota.size() - 1; i++) {
                    MapNode n1 = rota.get(i);
                    MapNode n2 = rota.get(i + 1);
                    gc.strokeLine(n1.getX() * (950.0/800.0), n1.getY() * (380.0/600.0), n2.getX() * (950.0/800.0), n2.getY() * (380.0/600.0));
                }

                gc.strokeLine(rota.get(rota.size() - 1).getX() * (950.0/800.0), rota.get(rota.size() - 1).getY() * (380.0/600.0),
                              sonuc.getRestorant().getX() * (950.0/800.0), sonuc.getRestorant().getY() * (380.0/600.0));
            } else {
                gc.strokeLine(aktifKullanici.getX() * (950.0/800.0), aktifKullanici.getY() * (380.0/600.0),
                              sonuc.getRestorant().getX() * (950.0/800.0), sonuc.getRestorant().getY() * (380.0/600.0));
            }

            gc.setFill(Color.web("#3498DB"));
            gc.fillOval(aktifKullanici.getX() * (950.0/800.0) - 9, aktifKullanici.getY() * (380.0/600.0) - 9, 18, 18);
        } else {
            gc.setFill(Color.web("rgba(0, 0, 0, 0.6)"));
            gc.fillRect(0, 0, 950, 380);

            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
            gc.fillText("Farklı İlçede Bulunduğunuz İçin Yol Tarifi Gösterilemiyor", 240, 150);

            gc.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
            gc.setFill(Color.web("#E2E8F0"));
            gc.fillText("Restoran " + sonuc.getRestorant().getDistrict() + " ilçesindedir. Sizin konumunuz ise " + aktifKullanici.getDistrict() + " ilçesindedir.", 240, 190);
            gc.fillText("Yol tarifini görmek için 'Profilim' sayfasından bölgenizi " + sonuc.getRestorant().getDistrict() + " olarak seçiniz.", 240, 220);
        }

        gc.setFill(Color.web("#E74C3C"));
        gc.fillOval(sonuc.getRestorant().getX() * (950.0/800.0) - 9, sonuc.getRestorant().getY() * (380.0/600.0) - 9, 18, 18);

        GridPane detayKutusu = new GridPane();
        detayKutusu.setVgap(12); detayKutusu.setHgap(40);
        detayKutusu.setPadding(new Insets(20));
        detayKutusu.setId("FORM_KUTUSU");

        detayKutusu.add(new Label("Mutfak Türü:"), 0, 0);
        Label d1 = new Label(sonuc.getRestorant().getType()); d1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        detayKutusu.add(d1, 1, 0);

        detayKutusu.add(new Label("Gurme Skor:"), 0, 1);
        Label d2 = new Label("⭐ " + sonuc.getRestorant().getRating()); d2.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        detayKutusu.add(d2, 1, 1);

        detayKutusu.add(new Label("Özellik Grupları:"), 2, 0);
        String ozelliklerMetni = String.join(", ", sonuc.getRestorant().getFeatures());
        Label d3 = new Label(ozelliklerMetni); d3.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        detayKutusu.add(d3, 3, 0);

        detayKutusu.add(new Label(ayniIlce ? "A* Optimize Mesafe:" : "Kuş Uçuşu Mesafe:"), 2, 1);
        Label d4 = new Label(String.format("%.1f", sonuc.getMesafe()) + " metre");
        d4.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16)); d4.setTextFill(ayniIlce ? Color.web("#2ECC71") : Color.web("#E67E22"));
        detayKutusu.add(d4, 3, 1);

        anaKutu.getChildren().addAll(btnGeri, baslikSatir, canvas, detayKutusu);
        return anaKutu;
    }

    private VBox favoriSayfasiOlustur() {
        VBox anaKutu = new VBox(20);
        anaKutu.setPadding(new Insets(25));

        Label baslik = new Label("Favori İstasyonlarım");
        baslik.setFont(Font.font("Segoe UI", FontWeight.BOLD, 26));

        HBox filtreSatiri = new HBox(15);
        filtreSatiri.setAlignment(Pos.CENTER_LEFT);
        TextField txtAra = new TextField(); txtAra.setPromptText("Favorilerinde ara...");
        ComboBox<String> cbSirala = new ComboBox<>(); cbSirala.getItems().addAll("Konuma Göre", "Puana Göre"); cbSirala.setValue("Konuma Göre");
        filtreSatiri.getChildren().addAll(txtAra, cbSirala);

        ScrollPane kaydirici = new ScrollPane();
        kaydirici.setFitToWidth(true);

        Runnable favoriYenile = () -> {
            FlowPane panel = new FlowPane(); panel.setHgap(20); panel.setVgap(20);
            String siralaTipi = cbSirala.getValue().equals("Puana Göre") ? "Puan" : "Mesafe";
            List<RestorantAlgo.MesafeSonucu> sonuclar = restorantAlgo.enYakinRestorantlariBul(aktifKullanici, "", "", siralaTipi);
            for (var s : sonuclar) {
                if (aktifKullanici.getFavRestaurants().contains(s.getRestorant().getName())) {
                    if (txtAra.getText().isEmpty() || s.getRestorant().getName().toLowerCase().contains(txtAra.getText().toLowerCase())) {
                        panel.getChildren().add(restoranKartiOlustur(s, () -> sayfaDegistir(favoriSayfasiOlustur())));
                    }
                }
            }
            kaydirici.setContent(panel);
        };

        txtAra.textProperty().addListener((obs, oldV, newV) -> favoriYenile.run());
        cbSirala.setOnAction(e -> favoriYenile.run());
        favoriYenile.run();

        anaKutu.getChildren().addAll(baslik, filtreSatiri, kaydirici);
        return anaKutu;
    }

    private void temaUygula() {
        String arkaPlanRenk = koyuTema ? "rgba(18, 18, 18, 0.7)" : "rgba(248, 249, 250, 0.7)";
        String menuArkaPlan = koyuTema ? "rgba(26, 26, 26, 0.85)" : "rgba(255, 255, 255, 0.85)";
        String yaziRenk = koyuTema ? "#E2E8F0" : "#1A202C";
        String formArkaPlan = koyuTema ? "#2D3748" : "#FFFFFF";
        String borderRenk = koyuTema ? "#4A5568" : "#CBD5E0";
        String aktifRenk = koyuTema ? "#4FACFE" : "#3498DB";

        File bgFile = FileHelper.findFile("src/main/resources/arkaplan.jpg");
        if (bgFile.exists()) {
            BackgroundImage bg = new BackgroundImage(
                    new Image(bgFile.toURI().toString()),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true)
            );
            anaPanel.setBackground(new Background(bg));
        }

        anaPanel.setStyle("-fx-font-family: 'Segoe UI';");
        icerikAlani.setStyle("-fx-background-color: " + arkaPlanRenk + ";");
        solMenu.setStyle("-fx-background-color: " + menuArkaPlan + "; -fx-border-color: " + (koyuTema ? "#2D2D2D" : "#E5E9F0") + "; -fx-border-width: 0 1 0 0;");

        String anaCss =
                ".root { -fx-text-fill: " + yaziRenk + "; }" +
                        ".label { -fx-text-fill: " + yaziRenk + "; }" +
                        ".button { -fx-background-color: transparent; -fx-text-fill: " + yaziRenk + "; -fx-cursor: hand; -fx-background-radius: 8; -fx-font-family: 'Segoe UI'; }" +
                        ".primary-button { -fx-background-color: #3498DB; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 8; }" +
                        ".text-input { -fx-background-color: " + formArkaPlan + "; -fx-text-fill: " + yaziRenk + "; -fx-border-color: " + borderRenk + "; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 8; -fx-prompt-text-fill: #718096; }" +
                        ".text-input:focused { -fx-prompt-text-fill: #718096; }" +
                        ".combo-box { -fx-background-color: " + formArkaPlan + "; -fx-text-fill: " + yaziRenk + "; -fx-border-color: " + borderRenk + "; -fx-border-radius: 6; -fx-background-radius: 6; }" +
                        ".combo-box .label { -fx-text-fill: " + yaziRenk + "; }" +
                        ".check-box { -fx-text-fill: " + yaziRenk + "; }" +
                        ".radio-button { -fx-text-fill: " + yaziRenk + "; }" +
                        ".scroll-pane { -fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent; }" +
                        "#FORM_KUTUSU { -fx-background-color: " + (koyuTema ? "#1E293B" : "#FFFFFF") + "; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 4); -fx-border-color: " + borderRenk + "; -fx-border-radius: 10; }" +
                        "#RESTORAN_KARTI { -fx-background-color: " + (koyuTema ? "#1E293B" : "#FFFFFF") + "; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 12, 0, 0, 4); }";

        if (anaPanel.getScene() != null) {
            anaPanel.getScene().getStylesheets().clear();
            anaPanel.getScene().getStylesheets().add("data:text/css," + anaCss.replaceAll(" ", "%20").replaceAll("#", "%23"));
        }

        for (var child : solMenu.getChildren()) {
            if (child instanceof Button) {
                Button b = (Button) child;
                if (b.getText().equals("☰")) {
                    b.setStyle("-fx-text-fill: " + yaziRenk + "; -fx-background-color: transparent;");
                } else {
                    Object[] data = (Object[]) b.getUserData();
                    SVGPath svg = (SVGPath) data[0];
                    Label lbl = (Label) data[1];
                    svg.setFill(Color.web(aktifRenk));
                    b.setStyle("-fx-background-color: transparent;");
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}