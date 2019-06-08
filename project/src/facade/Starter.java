package facade;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Admin;
import model.Client;
import model.Landscaper;

import java.io.IOException;
import java.sql.SQLException;

public class Starter extends Application {

    private static Stage stage;
    public static Facade facade;

    static {
        try {
            facade = new FacadeImpl();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            facade = new FacadeImpl();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Starter() throws SQLException, ClassNotFoundException {
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        /*int PORT = 27015;

        DBinit.initConection();
        System.out.println("Call to connect from main app is made...");

        System.out.println("Server started.\nListening connection on port: "+PORT+"...\n");
        HttpServer myServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        myServer.createContext("/", new RootHandler());
        myServer.setExecutor(null);
        myServer.start();
        */
        launch(args);
 }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("Green Office");
        //Parent root = FXMLLoader.load(getClass().getResource("AuthView.fxml"));
        //stage.setScene(new Scene(root, 300, 275));
        showAuthView();
        stage.show();
    }

    public static void showAuthView() {
        try {
            String fxmlFile = "/resources/AuthView.fxml";
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root = null;
            root = (AnchorPane) loader.load(Starter.class.getClass().getResourceAsStream(fxmlFile));
            Scene scene = new Scene(root, 580, 435);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showRegisterView() {
        try {
            String fxmlFile = "/resources/RegistrationView.fxml";
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root = null;
            root = (AnchorPane) loader.load(Starter.class.getClass().getResourceAsStream(fxmlFile));
            Scene scene = new Scene(root, 600, 480);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showClientView(Client clientByUserID) {
        try {
            String fxmlFile = "/resources/ClientView.fxml";
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root = null;
            root = (AnchorPane) loader.load(Starter.class.getClass().getResourceAsStream(fxmlFile));
            Scene scene = new Scene(root, 600, 900);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showAdminView(Admin adminByUserID) {
        try {
            String fxmlFile = "/resources/AdminView.fxml";
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root = null;
            root = (AnchorPane) loader.load(Starter.class.getClass().getResourceAsStream(fxmlFile));
            Scene scene = new Scene(root, 600, 900);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showLandscaperView(Landscaper landscaperByUserID) {
        try {
            String fxmlFile = "/resources/LandscaperView.fxml";
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root = null;
            root = (AnchorPane) loader.load(Starter.class.getClass().getResourceAsStream(fxmlFile));
            Scene scene = new Scene(root, 600, 900);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
