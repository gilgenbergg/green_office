package facade;

import com.sun.net.httpserver.HttpServer;
import data.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Admin;
import model.Client;
import model.Landscaper;
import web.RootHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
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

    AuthMapper authMapper = new AuthMapper();
    CReqsMapper cReqs = new CReqsMapper();
    InstructionsMapper instructionsMapper = new InstructionsMapper();
    PlantsMapper plantsBase = new PlantsMapper();
    PReqsMapper pReqs = new PReqsMapper();
    ResourcesMapper resourcesMapper = new ResourcesMapper();
    UsersMapper users = new UsersMapper();

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
        int PORT = 27015;

        DBinit.initConection();
        System.out.println("Call to connect from main app is made...");

        System.out.println("Server started.\nListening connection on port: "+PORT+"...\n");
        HttpServer myServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        myServer.createContext("/", new RootHandler());
        myServer.setExecutor(null);
        myServer.start();
        launch(args);
 }

    public static void showClientView(Client clientByUserID) {
    }

    public static void showAdminView(Admin adminByUserID) {
    }

    public static void showLandscaperView(Landscaper landscaperByUserID) {
    }

    public static void showResgisteView() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("Green Office");
        Parent root = FXMLLoader.load(getClass().getResource("AuthView.fxml"));
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
    }

    public static void showSignInView() {
        try {
            String fxmlFile = "/fxml/SignIn.fxml";
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root = null;
            root = (AnchorPane) loader.load(Starter.class.getClass().getResourceAsStream(fxmlFile));
            Scene scene = new Scene(root, 384, 275);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
