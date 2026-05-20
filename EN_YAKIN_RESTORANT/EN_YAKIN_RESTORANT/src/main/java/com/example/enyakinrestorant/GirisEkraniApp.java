package com.example.enyakinrestorant;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.File;

public class GirisEkraniApp extends Application {

    private Stage anaSahne;
    private UserManager userManager = new UserManager();

    @Override
    public void start(Stage primaryStage) {
        this.anaSahne = primaryStage;
        primaryStage.setTitle("Lezzet Rotası - Giriş Sistemi");

        girişPaneliniGoster();
        primaryStage.show();
    }
    private void girişPaneliniGoster() {
        VBox root = yeniTasarlanmisKutu();

        Label baslik = new Label("LEZZET ROTASI");
        baslik.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        baslik.setTextFill(Color.web("#2c3e50"));

        Label altBaslik = new Label("En Yakın Restoranı Keşfet");
        altBaslik.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        altBaslik.setTextFill(Color.web("#7f8c8d"));

        GridPane form = new GridPane();
        form.setVgap(10);
        form.setHgap(10);
        form.setAlignment(Pos.CENTER);

        TextField txtUser = new TextField();
        txtUser.setPromptText("Kullanıcı Adı");
        txtUser.setPrefWidth(250);
        txtUser.setStyle("-fx-background-radius: 5; -fx-padding: 8;");

        PasswordField txtPass = new PasswordField();
        txtPass.setPromptText("Şifre");
        txtPass.setPrefWidth(250);
        txtPass.setStyle("-fx-background-radius: 5; -fx-padding: 8;");

        form.add(txtUser, 0, 0);
        form.add(txtPass, 0, 1);

        Button btnGiris = new Button("Giriş Yap");
        btnGiris.setPrefWidth(250);
        btnGiris.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-padding: 10;");

        Button btnKayit = new Button("Kayıt Ol");
        btnKayit.setPrefWidth(250);
        btnKayit.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-padding: 10; -fx-cursor: hand;");

        Button btnSifre = new Button("Şifremi Unuttum");
        btnSifre.setPrefWidth(250);
        btnSifre.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-padding: 10; -fx-cursor: hand;");

        Label lblHata = new Label();
        lblHata.setTextFill(Color.RED);

        btnGiris.setOnAction(e -> {
            String username = txtUser.getText().trim();
            String password = txtPass.getText();

            if (username.isEmpty() || password.isEmpty()) {
                lblHata.setText("Lütfen boş alan bırakmayın!");
                return;
            }

            User user = userManager.login(username, password);
            if (user != null) {
                lblHata.setTextFill(Color.GREEN);
                lblHata.setText("Giriş Başarılı! Harita açılıyor...");

                App anaUygulama = new App();
                anaUygulama.setAktifKullanici(user);
                anaUygulama.anaEkranıUçur(new Stage());
                anaSahne.close();
            } else {
                lblHata.setTextFill(Color.RED);
                lblHata.setText("Hatalı kullanıcı adı veya şifre!");
            }
        });

        btnKayit.setOnAction(e -> kayitPaneliniGoster());
        btnSifre.setOnAction(e -> sifremiUnuttumPaneliniGoster());

        root.getChildren().addAll(baslik, altBaslik, form, btnGiris, lblHata, btnKayit, btnSifre);
        Scene scene = new Scene(root, 400, 500);
        sahneAyarlariniYap(scene);
    }
    private void kayitPaneliniGoster() {
        VBox root = yeniTasarlanmisKutu();

        Label baslik = new Label("Yeni Hesap Oluştur");
        baslik.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        baslik.setTextFill(Color.web("#2c3e50"));

        GridPane form = new GridPane();
        form.setVgap(10);
        form.setHgap(10);
        form.setAlignment(Pos.CENTER);

        TextField txtUser = new TextField();
        txtUser.setPromptText("Kullanıcı Adı");
        txtUser.setPrefWidth(250);
        txtUser.setStyle("-fx-background-radius: 5; -fx-padding: 8;");

        PasswordField txtPass = new PasswordField();
        txtPass.setPromptText("Şifre");
        txtPass.setPrefWidth(250);
        txtPass.setStyle("-fx-background-radius: 5; -fx-padding: 8;");

        ComboBox<String> cbSoru = new ComboBox<>();
        cbSoru.getItems().addAll("İlk evcil hayvanınızın adı?", "En sevdiğiniz yemek?", "Doğduğunuz şehir?");
        cbSoru.setValue("En sevdiğiniz yemek?");
        cbSoru.setPrefWidth(250);

        TextField txtCevap = new TextField();
        txtCevap.setPromptText("Güvenlik Cevabı");
        txtCevap.setPrefWidth(250);
        txtCevap.setStyle("-fx-background-radius: 5; -fx-padding: 8;");

        form.add(txtUser, 0, 0);
        form.add(txtPass, 0, 1);
        form.add(cbSoru, 0, 2);
        form.add(txtCevap, 0, 3);

        Button btnKayit = new Button("Kayıt Olmayı Tamamla");
        btnKayit.setPrefWidth(250);
        btnKayit.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-padding: 10;");

        Button btnGeri = new Button("Geri Dön");
        btnGeri.setPrefWidth(250);
        btnGeri.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-padding: 10; -fx-cursor: hand;");
        Label lblHata = new Label();

        btnKayit.setOnAction(e -> {
            String user = txtUser.getText().trim();
            String pass = txtPass.getText();
            String soru = cbSoru.getValue();
            String cevap = txtCevap.getText().trim();

            if (user.isEmpty() || pass.isEmpty() || cevap.isEmpty()) {
                lblHata.setTextFill(Color.RED);
                lblHata.setText("Lütfen tüm alanları doldurun!");
                return;
            }
            boolean basarili = userManager.register(user, pass, "İstanbul", "Kadıköy", 220.0, 196.0, soru, cevap);
            if (basarili) {
                lblHata.setTextFill(Color.GREEN);
                lblHata.setText("Kayıt Başarılı! Giriş yapabilirsiniz.");
                txtUser.clear();
                txtPass.clear();
                txtCevap.clear();
            } else {
                lblHata.setTextFill(Color.RED);
                lblHata.setText("Bu kullanıcı adı zaten alınmış!");
            }
        });

        btnGeri.setOnAction(e -> girişPaneliniGoster());

        root.getChildren().addAll(baslik, form, btnKayit, lblHata, btnGeri);
        Scene scene = new Scene(root, 450, 520);
        sahneAyarlariniYap(scene);
    }
    private void sifremiUnuttumPaneliniGoster() {
        VBox root = yeniTasarlanmisKutu();

        Label baslik = new Label("Şifre Sıfırlama");
        baslik.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        baslik.setTextFill(Color.web("#2c3e50"));

        GridPane form = new GridPane();
        form.setVgap(10);
        form.setHgap(10);
        form.setAlignment(Pos.CENTER);

        TextField txtUser = new TextField();
        txtUser.setPromptText("Kullanıcı Adınız");
        txtUser.setPrefWidth(250);
        txtUser.setStyle("-fx-background-radius: 5; -fx-padding: 8;");

        ComboBox<String> cbSoru = new ComboBox<>();
        cbSoru.getItems().addAll("İlk evcil hayvanınızın adı?", "En sevdiğiniz yemek?", "Doğduğunuz şehir?");
        cbSoru.setValue("En sevdiğiniz yemek?");
        cbSoru.setPrefWidth(250);

        TextField txtCevap = new TextField();
        txtCevap.setPromptText("Cevabınız");
        txtCevap.setPrefWidth(250);
        txtCevap.setStyle("-fx-background-radius: 5; -fx-padding: 8;");

        PasswordField txtYeniPass = new PasswordField();
        txtYeniPass.setPromptText("Yeni Şifre");
        txtYeniPass.setPrefWidth(250);
        txtYeniPass.setStyle("-fx-background-radius: 5; -fx-padding: 8;");

        form.add(txtUser, 0, 0);
        form.add(cbSoru, 0, 1);
        form.add(txtCevap, 0, 2);
        form.add(txtYeniPass, 0, 3);

        Button btnSifırla = new Button("Şifreyi Güncelle");
        btnSifırla.setPrefWidth(250);
        btnSifırla.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-padding: 10;");

        Button btnGeri = new Button("Geri Dön");
        btnGeri.setPrefWidth(250);
        btnGeri.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-padding: 10; -fx-cursor: hand;");
        Label lblHata = new Label();

        btnSifırla.setOnAction(e -> {
            String user = txtUser.getText().trim();
            String soru = cbSoru.getValue();
            String cevap = txtCevap.getText().trim();
            String yeniSifre = txtYeniPass.getText();

            if (user.isEmpty() || cevap.isEmpty() || yeniSifre.isEmpty()) {
                lblHata.setTextFill(Color.RED);
                lblHata.setText("Lütfen tüm alanları doldurun!");
                return;
            }

            boolean basarili = userManager.resetPassword(user, soru, cevap, yeniSifre);
            if (basarili) {
                lblHata.setTextFill(Color.GREEN);
                lblHata.setText("Şifreniz başarıyla güncellendi!");
            } else {
                lblHata.setTextFill(Color.RED);
                lblHata.setText("Bilgiler eşleşmedi! Şifre değiştirilemedi.");
            }
        });

        btnGeri.setOnAction(e -> girişPaneliniGoster());

        root.getChildren().addAll(baslik, form, btnSifırla, lblHata, btnGeri);
        Scene scene = new Scene(root, 450, 520);
        sahneAyarlariniYap(scene);
    }
    private VBox yeniTasarlanmisKutu() {
        VBox kutu = new VBox(20);
        kutu.setPadding(new Insets(30));
        kutu.setAlignment(Pos.CENTER);

        File bgFile = FileHelper.findFile("src/main/resources/arkaplan.jpg");
        if (bgFile.exists()) {
            BackgroundImage bg = new BackgroundImage(
                    new Image(bgFile.toURI().toString()),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true)
            );
            BackgroundFill bgFill = new BackgroundFill(
                    Color.web("#f5f6fa", 0.85),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            );
            kutu.setBackground(new Background(new BackgroundFill[]{bgFill}, new BackgroundImage[]{bg}));
        } else {
            kutu.setStyle("-fx-background-color: #f5f6fa;");
        }
        return kutu;
    }

    private void sahneAyarlariniYap(Scene scene) {
        String css = ".text-input:focused { -fx-prompt-text-fill: #7f8c8d; }";
        scene.getStylesheets().add("data:text/css," + css.replaceAll(" ", "%20").replaceAll("#", "%23"));
        anaSahne.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}