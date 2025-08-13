import java.io.InputStream;
import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class LoginUi extends Application {

    private static final String BG_COLOR = "#ffffff";
    private static final String TEXT_COLOR = "#1b1b1b";
    private static final String BORDER_COLOR = "#626262";
    private static final String FONT_PATH =  "/resources/fonts/Lexend/hi;";
    private static boolean startup = true;
    public LoginUi(){
    }

    Label timeErrorLabel = new Label(null);

    @Override
    public void start(Stage primaryStage) {
        if(startup) {
            showSplash(() -> showMainApp(primaryStage));
            startup=false;
        }else{
            showMainApp(primaryStage);
        }
    }

    private void showSplash(Runnable onFinish) {
        startup=false;
        Stage splashStage = new Stage(StageStyle.TRANSPARENT);
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: transparent;");
        root.setAlignment(Pos.CENTER);



        InputStream logoStream = getClass().getResourceAsStream("/images/logo.png");
        if (logoStream == null) {
            System.err.println("Splash image not found.");
            onFinish.run();
            return;
        }

        ImageView logoView = new ImageView(new Image(logoStream));
        logoView.setFitWidth(250);
        logoView.setPreserveRatio(true);
        root.getChildren().add(logoView);

        Scene scene = new Scene(root, 300, 300, Color.TRANSPARENT);
        splashStage.setScene(scene);
        splashStage.show();

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), logoView);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), logoView);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        SequentialTransition sequence = new SequentialTransition(fadeIn, pause, fadeOut);
        sequence.setOnFinished(e -> {
            splashStage.close();
            onFinish.run();
        });
        sequence.play();
    }

    private void setStageIcon(Stage stage) {

        InputStream iconStream = getClass().getResourceAsStream("/images/logo.png");
        if (iconStream != null) {
            stage.getIcons().add(new Image(iconStream));
        } else {
            System.err.println("Window icon not found.");
        }
    }

    private void showMainApp(Stage primaryStage) {
        setStageIcon(primaryStage);
        primaryStage.setTitle("To-Do");

        Pane root = new Pane();
        root.setStyle("-fx-background-color: #eeeeee;");
        root.setPrefSize(766, 378);
        primaryStage.setResizable(false);

        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/images/logo.png")));
        logo.setFitWidth(150);
        logo.setPreserveRatio(true);
        logo.setLayoutX(308); // centered horizontally for 766px width
        logo.setLayoutY(50);  // adjust as needed

        root.getChildren().add(logo);


        Font customFont = loadCustomFont(FONT_PATH, 18);
        Font lexend32 = Font.loadFont(getClass().getResourceAsStream("/resources/fonts/lexend.ttf"), 32);
        //Title
        Label Title = new Label("TO-DO");
        Title.setLayoutX(332);
        Title.setLayoutY(0);
        Title.setPrefSize(300, 60);
        Title.setFont(lexend32 != null ? lexend32 : Font.font(36));
        Title.setStyle("-fx-text-fill: #1b1b1b;");
        //"OR"
        Label element6 = new Label("--OR--");
        element6.setLayoutX(365);
        element6.setLayoutY(241);
        element6.setPrefWidth(70);
        element6.setPrefHeight(20);
        element6.setStyle("-fx-text-fill: #1b1b1b;");
        //Settings
        Button settingicon = new Button("Settings");
        settingicon.setLayoutX(660.00);
        settingicon.setLayoutY(9.66);
        settingicon.setPrefWidth(78.00);
        settingicon.setPrefHeight(27.00);
        settingicon.setDisable(false);
        settingicon.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");
        settingicon.setOnAction(e -> {
            //Setting Actions here...
        });
        settingicon.setOnMouseEntered(e -> settingicon.setStyle(
                "-fx-background-color: #d3d3d3; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));
        settingicon.setOnMouseExited(e -> settingicon.setStyle(
                "-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));

        //LOGIN
        Button loginBtn = new Button("Log-In");
        loginBtn.setLayoutX(405);
        loginBtn.setLayoutY(203.66);
        loginBtn.setPrefWidth(116.00);
        loginBtn.setPrefHeight(104.00);
        loginBtn.setDisable(false);
        loginBtn.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");
        loginBtn.setOnMouseEntered(e -> loginBtn.setStyle(
                "-fx-background-color: #d3d3d3; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));
        loginBtn.setOnMouseExited(e -> loginBtn.setStyle(
                "-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));

        loginBtn.setOnAction(e -> showLoginWindow(primaryStage));
        //Sign-up
        Button SignUp = new Button("Sign-Up");
        SignUp.setLayoutX(245);
        SignUp.setLayoutY(203.66);
        SignUp.setPrefWidth(116.00);
        SignUp.setPrefHeight(104.00);
        SignUp.setDisable(false);
        SignUp.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");
        SignUp.setOnAction(e ->{
            showSignupWindow(primaryStage);
        });
        SignUp.setOnMouseEntered(e -> SignUp.setStyle(
                "-fx-background-color: #d3d3d3; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));
        SignUp.setOnMouseExited(e -> SignUp.setStyle(
                "-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));


        root.getChildren().add(loginBtn);
        root.getChildren().add(SignUp);
        root.getChildren().add(Title);
        root.getChildren().add(settingicon);
        root.getChildren().add(element6);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    private void showLoginWindow(Stage primaryStage) {
        primaryStage.setTitle("Login");
        VBox layout = createFormLayout("Welcome!", "Login", primaryStage);
        primaryStage.setScene(new Scene(layout, 766, 378));
    }


    private VBox createFormLayout(String titleText, String buttonText, Stage primaryStage) {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: " + BG_COLOR + ";");

        Label title = new Label(titleText);
        title.setFont(loadCustomFont(FONT_PATH, 28));
        title.setStyle("-fx-text-fill: " + TEXT_COLOR + ";");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(15);
        form.setAlignment(Pos.CENTER);

        TextField username = createStyledTextField("Username");
        PasswordField password = createStyledPasswordField("Password");

        form.add(username, 0, 0);
        form.add(password, 0, 1);

        Button actionButton = createInteractiveButton(buttonText);
        actionButton.setOnMouseEntered(e -> actionButton.setStyle(
                "-fx-background-color: #d3d3d3; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));
        actionButton.setOnMouseExited(e -> actionButton.setStyle(
                "-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));

        timeErrorLabel.setFont(loadCustomFont(FONT_PATH, 16));
        timeErrorLabel.setStyle("-fx-text-fill: " + "#bd1111" + ";");
        timeErrorLabel.setVisible(false);
        actionButton.setOnAction(e ->{
            String Username = username.getText();
            String Password = password.getText();
            Stage taskStage = new Stage();
            Username = Username.toLowerCase();
            Password = Password.toLowerCase();
            if(Username.equals("")||password.getText().equals("")){
                System.out.println("No username or password input!");
                timeErrorLabel.setText("No username or password input!");
                timeErrorLabel.setVisible(true);
            }else {
                if(UserData.usernameExists(Username)==false) {
                    System.out.println("ERROR: Cant find username Or Password.");
                    timeErrorLabel.setText("ERROR: Cant find username Or Password.");
                    timeErrorLabel.setVisible(true);

                } else if(UserData.findUser(Username,Password)==true){
                    TaskUi taskUi = new TaskUi(Username);
                    taskUi.start(primaryStage);


                }
                else{
                    System.out.println("ERROR: wrong password.");
                    timeErrorLabel.setText("ERROR: Wrong password.");
                    timeErrorLabel.setVisible(true);
                }
            }
        });

        Button backButton = new Button("Back");
        backButton.setFont(loadCustomFont(FONT_PATH, 12));
        backButton.setStyle("-fx-background-color: transparent; -fx-text-fill: " + TEXT_COLOR + ";");
        backButton.setOnAction(e -> showMainApp(primaryStage));

        layout.getChildren().addAll(title, form,actionButton,backButton,timeErrorLabel);
        return layout;
    }
    private void showSignupWindow(Stage primaryStage) {
        primaryStage.setTitle("Signup");
        VBox layout = createFormsLayout("Welcome!", "Signup", primaryStage);
        primaryStage.setScene(new Scene(layout, 766, 378));
    }


    private VBox createFormsLayout(String titleText, String buttonText, Stage primaryStage) {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: " + BG_COLOR + ";");

        Label title = new Label(titleText);
        title.setFont(loadCustomFont(FONT_PATH, 28));
        title.setStyle("-fx-text-fill: " + TEXT_COLOR + ";");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(15);
        form.setAlignment(Pos.CENTER);

        TextField username = createStyledTextField("Username");
        PasswordField password = createStyledPasswordField("Password");

        form.add(username, 0, 0);
        form.add(password, 0, 1);

        Button actionButton = createInteractiveButton(buttonText);
        actionButton.setOnMouseEntered(e -> actionButton.setStyle(
                "-fx-background-color: #d3d3d3; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));
        actionButton.setOnMouseExited(e -> actionButton.setStyle(
                "-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));

        timeErrorLabel.setFont(loadCustomFont(FONT_PATH, 28));
        timeErrorLabel.setStyle("-fx-text-fill: " + "#bd1111" + ";");
        timeErrorLabel.setVisible(false);
        actionButton.setOnAction(e ->{
            String Username = username.getText();
            String Password = password.getText();
            Username= Username.toLowerCase();
            Password = Password.toLowerCase();
            if(Username.equals("")||password.getText().equals("")){
                System.out.println("No username or password input!");
                timeErrorLabel.setText("No username or password input!");
                timeErrorLabel.setVisible(true);

            }else {
                System.out.println("Username:" + username.getText());
                System.out.println("Password: " + password.getText());
                if(UserData.findUser(Username,Password)==false) {
                    UserData.saveUser(Username, Password);
                    TaskUi taskUi = new TaskUi(Username);
                    taskUi.start(primaryStage);


                } else if(UserData.usernameExists(Username)==true){
                    System.out.println("ERROR: user is already registered within the system. ");
                    timeErrorLabel.setText("ERROR: user is already registered within the system.");
                    timeErrorLabel.setVisible(true);

                }
            }
        });

        Button backButton = new Button("Back");
        backButton.setFont(loadCustomFont(FONT_PATH, 12));
        backButton.setStyle("-fx-background-color: transparent; -fx-text-fill: " + TEXT_COLOR + ";");
        backButton.setOnAction(e -> showMainApp(primaryStage));

        layout.getChildren().addAll(title, form, actionButton, backButton,timeErrorLabel);
        return layout;
    }

    private TextField createStyledTextField(String placeholder) {
        TextField tf = new TextField();
        tf.setPromptText(placeholder);
        tf.setFont(loadCustomFont(FONT_PATH, 14));
        tf.setPrefWidth(200);
        tf.setStyle("""
            -fx-background-color: #ffffff;
            -fx-text-fill: #1b1b1b;
            -fx-border-color: #626262;
            -fx-border-radius: 2px;
            -fx-border-width: 1px;
            -fx-prompt-text-fill: #737674;
        """);
        return tf;
    }

    private PasswordField createStyledPasswordField(String placeholder) {
        PasswordField pf = new PasswordField();
        pf.setPromptText(placeholder);
        pf.setFont(loadCustomFont(FONT_PATH, 14));
        pf.setPrefWidth(200);
        pf.setStyle("""
            -fx-background-color: #ffffff;
            -fx-text-fill: #1b1b1b;
            -fx-border-color: #626262;
            -fx-border-radius: 2px;
            -fx-border-width: 1px;
            -fx-prompt-text-fill: #737674;
        """);
        return pf;
    }

    private Button createStyledButton(String text, double x, double y, String tooltip, Font font) {
        Button btn = new Button(text);
        btn.setLayoutX(x);
        btn.setLayoutY(y);
        btn.setPrefSize(308, 81);
        btn.setFont(font);
        btn.setStyle(baseButtonStyle());
        btn.setTooltip(new Tooltip(tooltip));

        addHoverAnimation(btn);
        return btn;
    }

    private Button createInteractiveButton(String text) {
        Button btn = new Button(text);
        btn.setFont(loadCustomFont(FONT_PATH, 14));
        btn.setPrefSize(140, 45);
        btn.setStyle(baseButtonStyle());
        addHoverAnimation(btn);
        return btn;
    }

    private void addHoverAnimation(Button btn) {
        btn.addEventFilter(MouseEvent.MOUSE_PRESSED, e ->
                btn.setBackground(new Background(new BackgroundFill(Color.web("#c2c2c2"), new CornerRadii(4), Insets.EMPTY)))
        );
        btn.addEventFilter(MouseEvent.MOUSE_RELEASED, e ->
                btn.setBackground(new Background(new BackgroundFill(Color.web("#ffffff"), new CornerRadii(4), Insets.EMPTY)))
        );
    }

    private String baseButtonStyle() {
        return String.format("""
            -fx-background-color: #ffffff;
            -fx-text-fill: %s;
            -fx-border-color: %s;
            -fx-border-radius: 4px;
            -fx-background-radius: 4px;
            -fx-border-width: 1px;
        """, TEXT_COLOR, BORDER_COLOR);
    }

    private Font loadCustomFont(String path, double size) {
        try (InputStream fontStream = getClass().getResourceAsStream(path)) {
            if (fontStream != null) {
                return Font.loadFont(fontStream, size);
            }
        } catch (Exception e) {
            System.err.println("Failed to load font: " + path);
        }
        return Font.getDefault();
    }

    public static void main(String[] args) {
        launch(args);
    }
}