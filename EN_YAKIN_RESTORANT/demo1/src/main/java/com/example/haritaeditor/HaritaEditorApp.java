package com.example.haritaeditor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HaritaEditorApp extends Application {

    private static class EditorNode {
        String id;
        double x, y;
        EditorNode(String id, double x, double y) { this.id = id; this.x = x; this.y = y; }
    }

    private static class EditorEdge {
        EditorNode u, v;
        EditorEdge(EditorNode u, EditorNode v) { this.u = u; this.v = v; }
    }

    private List<EditorNode> nodeList = new ArrayList<>();
    private List<EditorEdge> edgeList = new ArrayList<>();

    private boolean nodeModu = true;
    private EditorNode ilkSeciliNode = null;
    private int nodeSayac = 1;

    private Canvas canvas;
    private GraphicsContext gc;
    private Image haritaResmi = null;

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        HBox kontrolPaneli = new HBox(15);

        ComboBox<String> ilceSecici = new ComboBox<>();
        ilceSecici.getItems().addAll("Kadıköy", "Beşiktaş");
        ilceSecici.setValue("Kadıköy");

        Button btnNodeModu = new Button("Düğüm (Node) Oluştur");
        Button btnEdgeModu = new Button("Yol (Edge) Oluştur");
        Button btnKodUret = new Button("Java Kodunu Konsola Bas");
        Button btnTemizle = new Button("Hepsini Sıfırla");

        btnNodeModu.setStyle("-fx-background-color: #ffcccc; -fx-font-weight: bold;");
        btnEdgeModu.setStyle("-fx-background-color: #e1e1e1;");

        kontrolPaneli.getChildren().addAll(
                new Label("İlçe:"), ilceSecici,
                btnNodeModu, btnEdgeModu,
                btnKodUret, btnTemizle
        );

        canvas = new Canvas(800, 600);
        gc = canvas.getGraphicsContext2D();

        resimYukle("Kadıköy");
        ekraniCiz();

        ilceSecici.setOnAction(e -> {
            nodeList.clear();
            edgeList.clear();
            ilkSeciliNode = null;
            nodeSayac = 1;
            resimYukle(ilceSecici.getValue());
            ekraniCiz();
        });

        btnNodeModu.setOnAction(e -> {
            nodeModu = true;
            ilkSeciliNode = null;
            btnNodeModu.setStyle("-fx-background-color: #ffcccc; -fx-font-weight: bold;");
            btnEdgeModu.setStyle("-fx-background-color: #e1e1e1;");
            ekraniCiz();
        });

        btnEdgeModu.setOnAction(e -> {
            nodeModu = false;
            btnEdgeModu.setStyle("-fx-background-color: #cce5ff; -fx-font-weight: bold;");
            btnNodeModu.setStyle("-fx-background-color: #e1e1e1;");
            ekraniCiz();
        });

        canvas.setOnMouseClicked(e -> {
            double x = e.getX();
            double y = e.getY();

            if (e.getButton() == MouseButton.SECONDARY) {
                EditorNode silinecekNode = enYakinNodeBul(x, y);
                if (silinecekNode != null) {
                    nodeList.remove(silinecekNode);
                    Iterator<EditorEdge> it = edgeList.iterator();
                    while (it.hasNext()) {
                        EditorEdge edge = it.next();
                        if (edge.u == silinecekNode || edge.v == silinecekNode) {
                            it.remove();
                        }
                    }
                    if (ilkSeciliNode == silinecekNode) {
                        ilkSeciliNode = null;
                    }
                    ekraniCiz();
                }
                return;
            }

            if (nodeModu) {
                TextInputDialog dialog = new TextInputDialog("Kavsak" + nodeSayac);
                dialog.setTitle("Kavşak İsmi");
                dialog.setHeaderText("Kavşak/Sokak adı girin (Örn: Rihtim, Carsi):");
                dialog.setContentText("İsim:");
                dialog.showAndWait().ifPresent(isim -> {
                    String prefix = ilceSecici.getValue().equals("Kadıköy") ? "K_" : "B_";
                    nodeList.add(new EditorNode(prefix + isim.trim(), x, y));
                    nodeSayac++;
                    ekraniCiz();
                });
            } else {
                EditorNode tiklanan = enYakinNodeBul(x, y);
                if (tiklanan != null) {
                    if (ilkSeciliNode == null) {
                        ilkSeciliNode = tiklanan;
                    } else {
                        if (ilkSeciliNode != tiklanan) {
                            boolean varMi = false;
                            Iterator<EditorEdge> it = edgeList.iterator();
                            while (it.hasNext()) {
                                EditorEdge edge = it.next();
                                if ((edge.u == ilkSeciliNode && edge.v == tiklanan) || (edge.u == tiklanan && edge.v == ilkSeciliNode)) {
                                    it.remove();
                                    varMi = true;
                                    break;
                                }
                            }
                            if (!varMi) {
                                edgeList.add(new EditorEdge(ilkSeciliNode, tiklanan));
                            }
                        }
                        ilkSeciliNode = null;
                    }
                    ekraniCiz();
                }
            }
        });

        btnTemizle.setOnAction(e -> {
            nodeList.clear();
            edgeList.clear();
            ilkSeciliNode = null;
            nodeSayac = 1;
            ekraniCiz();
        });

        btnKodUret.setOnAction(e -> {
            String ilce = ilceSecici.getValue();
            String degiskenIsmi = ilce.equals("Kadıköy") ? "kadikoy" : "besiktas";

            System.out.println("\n================ " + ilce.toUpperCase() + " GRAF KODLARI ================");
            System.out.println("List<MapNode> " + degiskenIsmi + "Nodes = new ArrayList<>();");
            for (EditorNode n : nodeList) {
                System.out.println(String.format("%sNodes.add(new MapNode(\"%s\", %.0f, %.0f));", degiskenIsmi, n.id, n.x, n.y));
            }
            System.out.println("ilceNodeListesi.put(\"" + ilce + "\", " + degiskenIsmi + "Nodes);");

            System.out.println("\nList<MapEdge> " + degiskenIsmi + "Edges = new ArrayList<>();");
            for (EditorEdge edge : edgeList) {
                double mesafe = Math.sqrt(Math.pow(edge.v.x - edge.u.x, 2) + Math.pow(edge.v.y - edge.u.y, 2));
                System.out.println(String.format("%sEdges.add(new MapEdge(\"%s\", \"%s\", %.0f));", degiskenIsmi, edge.u.id, edge.v.id, mesafe));
            }
            System.out.println("ilceEdgeListesi.put(\"" + ilce + "\", " + degiskenIsmi + "Edges);");
            System.out.println("===============================================================\n");
        });

        root.getChildren().addAll(kontrolPaneli, canvas);
        Scene scene = new Scene(root, 820, 650);
        primaryStage.setTitle("A* Yol Belirleme Editörü");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void resimYukle(String ilce) {
        try {
            String dosyaAdi = ilce.equals("Kadıköy") ? "kadikoy.png" : "besiktas.png";
            File file = new File("src/main/resources/" + dosyaAdi);
            if (file.exists()) {
                haritaResmi = new Image(file.toURI().toString(), 800, 600, false, true);
            } else {
                haritaResmi = null;
            }
        } catch (Exception ex) {
            haritaResmi = null;
        }
    }

    private void ekraniCiz() {
        gc.clearRect(0, 0, 800, 600);
        if (haritaResmi != null) {
            gc.drawImage(haritaResmi, 0, 0);
        } else {
            gc.setFill(Color.LIGHTGRAY);
            gc.fillRect(0, 0, 800, 600);
            gc.setFill(Color.BLACK);
            gc.fillText("Harita resmi bulunamadı! src/main/resources/ klasörünü kontrol edin.", 50, 50);
        }

        gc.setLineWidth(3);
        gc.setStroke(Color.BLUE);
        for (EditorEdge edge : edgeList) {
            gc.strokeLine(edge.u.x, edge.u.y, edge.v.x, edge.v.y);
        }

        for (EditorNode n : nodeList) {
            if (n == ilkSeciliNode) {
                gc.setFill(Color.GOLD);
            } else {
                gc.setFill(Color.RED);
            }
            gc.fillOval(n.x - 6, n.y - 6, 12, 12);
            gc.setFill(Color.BLACK);
            gc.fillText(n.id, n.x + 8, n.y + 4);
        }
    }

    private EditorNode enYakinNodeBul(double x, double y) {
        EditorNode enYakin = null;
        double enKucukMesafe = 20;
        for (EditorNode n : nodeList) {
            double m = Math.sqrt(Math.pow(n.x - x, 2) + Math.pow(n.y - y, 2));
            if (m < enKucukMesafe) {
                enKucukMesafe = m;
                enYakin = n;
            }
        }
        return enYakin;
    }

    public static void main(String[] args) {
        launch(args);
    }
}