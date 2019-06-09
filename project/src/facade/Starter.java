package facade;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import controller.ClientViewController;
import controller.NewCReqController;
import data.CReqsMapper;
import data.DBinit;
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
        //checking if concurrent company works with a specific plant

        /*System.out.println("Checking info from external resource>>");
        System.out.println("Does NONGreenOffice work with camomiles?");
        System.out.println(checkPlant("camomile"));
        System.out.println("Does NONGreenOffice work with indonesian hunt planter?");
        System.out.println(checkPlant("indonesian hunt planter"));
        System.out.println("Does NONGreenOffice work with cactuses?");
        System.out.println(checkPlant("cactus"));
        */

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
        CReqsMapper creqsBase = new CReqsMapper();

        restHandler() throws SQLException, ClassNotFoundException {
        }

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            List<ClientRequest> newCreqs = new ArrayList<>();
            try {
                newCreqs = creqsBase.filterByType(ClientRequest.Type.planned);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            StringBuilder stringBuilder = new StringBuilder();
            JSONArray data = new JSONArray();
            for (ClientRequest creq : newCreqs) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", creq.getcReqID());
                jsonObject.put("status", creq.getStatus());
                jsonObject.put("clientID", creq.getClientID());
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
                Scene scene = new Scene(root, 890, 600);
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
                Scene scene = new Scene(root, 600, 640);
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
                Scene scene = new Scene(root, 600, 640);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
