package facade;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import controller.*;
import data.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Admin;
import model.Client;
import model.ClientRequest;
import model.Landscaper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public Starter() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("Green Office");
        showAuthView();
        stage.show();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        //for schecking REST API service which provides the information about plants GreenOffice works with
        int PORT = 27015;
        DBinit.initConection();
        System.out.println("Successful database connection...");
        System.out.println("Server started.\nListening connection on port: "+PORT+"...\n");
        HttpServer myServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        myServer.createContext("/allPlannedCreqs", new restHandler());
        myServer.setExecutor(null);
        myServer.start();

        //Starting UI
        launch(args);
 }

    static class restHandler implements HttpHandler {

        restHandler() throws SQLException, ClassNotFoundException {
        }

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            List<ClientRequest> newCreqs = new ArrayList<>();
            try {
                newCreqs = facade.filterByType(ClientRequest.Type.planned);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            StringBuilder stringBuilder = new StringBuilder();
            JSONArray data = new JSONArray();
            for (ClientRequest creq : newCreqs) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", creq.getCReqID());
                jsonObject.put("status", creq.getStatus());
                jsonObject.put("clientID", creq.getClientID());
                jsonObject.put("type", creq.getPlantName());
                data.add(jsonObject);
            }
            String response = data.toString();
            Headers headers = httpExchange.getResponseHeaders();
            headers.set("Content-type", "application/json; charset=UTF-8");
            httpExchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream streamm = httpExchange.getResponseBody();
            streamm.write(response.getBytes());
            streamm.close();
        }
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
                ClientViewController cvc = loader.getController();
                cvc.setData(clientByUserID);
                Scene scene = new Scene(root, 857, 635);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void newCReqView(Integer uid) throws IOException {
            try {
                String fxmlFile = "/resources/NewCReqView.fxml";
                FXMLLoader loader = new FXMLLoader();
                AnchorPane root = null;
                root = (AnchorPane) loader.load(Starter.class.getClass().getResourceAsStream(fxmlFile));
                NewCReqController ncreqControl = loader.getController();
                ncreqControl.setData(uid);
                Scene scene = new Scene(root, 800, 500);
                stage.setScene(scene);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }

        public static void showAdminView(Admin adminByUserID) {
            try {
                String fxmlFile = "/resources/AdminView.fxml";
                FXMLLoader loader = new FXMLLoader();
                AnchorPane root = null;
                root = (AnchorPane) loader.load(Starter.class.getClass().getResourceAsStream(fxmlFile));
                AdminController avc = loader.getController();
                avc.setData(adminByUserID.getUID());
                Scene scene = new Scene(root, 762, 583);
                stage.setScene(scene);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }

        public static void showNewPlantView(Integer adminID) {
            try {
                String fxmlFile = "/resources/NewPlantView.fxml";
                FXMLLoader loader = new FXMLLoader();
                AnchorPane root = null;
                root = (AnchorPane) loader.load(Starter.class.getClass().getResourceAsStream(fxmlFile));
                NewPlantController npc = loader.getController();
                npc.setData(adminID);
                Scene scene = new Scene(root, 762, 583);
                stage.setScene(scene);
            } catch (IOException | SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        public static void showLandscaperView(Landscaper landscaperByUserID) {
            try {
                String fxmlFile = "/resources/LandscaperView.fxml";
                FXMLLoader loader = new FXMLLoader();
                AnchorPane root = null;
                root = (AnchorPane) loader.load(Starter.class.getClass().getResourceAsStream(fxmlFile));
                LandscaperController lvc = loader.getController();
                lvc.setData(landscaperByUserID);
                Scene scene = new Scene(root, 665, 550);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public static void showGardeningView(Integer cReqID) {
        try {
            String fxmlFile = "/resources/GardeningView.fxml";
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root = null;
            root = (AnchorPane) loader.load(Starter.class.getClass().getResourceAsStream(fxmlFile));
            GardeningController gc = loader.getController();
            gc.setData(cReqID);
            Scene scene = new Scene(root, 600, 500);
            stage.setScene(scene);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void CheckPurchaseView(Integer pReqID) {
        try {
            String fxmlFile = "/resources/CheckPurchaseView.fxml";
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root = null;
            root = (AnchorPane) loader.load(Starter.class.getClass().getResourceAsStream(fxmlFile));
            CheckPurchaseController cpc = loader.getController();
            cpc.setData(pReqID);
            Scene scene = new Scene(root, 665, 550);
            stage.setScene(scene);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void CReqEditorView(Integer cReqID, Integer adminID) {
        try {
            String fxmlFile = "/resources/CReqEditorView.fxml";
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root = null;
            root = (AnchorPane) loader.load(Starter.class.getClass().getResourceAsStream(fxmlFile));
            ClientReqEditorController crec = loader.getController();
            crec.setData(cReqID, adminID);
            Scene scene = new Scene(root, 600, 640);
            stage.setScene(scene);
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void NewPurchaseView(Integer adminID) {
        try {
            String fxmlFile = "/resources/NewPurchaseView.fxml";
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root = null;
            root = (AnchorPane) loader.load(Starter.class.getClass().getResourceAsStream(fxmlFile));
            NewPurchaseController npc = loader.getController();
            npc.setData(adminID);
            Scene scene = new Scene(root, 640, 580);
            stage.setScene(scene);
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
