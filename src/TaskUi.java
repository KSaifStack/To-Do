import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.awt.*;
import java.awt.MenuItem;
import java.awt.event.ActionListener;
import java.io.InputStream;
import javafx.scene.input.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class TaskUi extends Application {
    private final StackPane rootStack = new StackPane();
    private static final String FONT_PATH = "/resources/fonts/Lexend.ttf";
    Font lexend32 = Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Lexend.ttf"), 32);
    Font lexend14 = Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Lexend.ttf"), 14);
    Font lexend12 = Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Lexend.ttf"), 12);


    private String username;
    private String taskname;
    private TrayIcon trayIcon;
    private Map<String, String> lastNotifiedStage = new HashMap<>();


    public TaskUi(String username)
    {
        this.username = username;
    }
    private AppTray tray = new AppTray();

    @Override
    public void start(Stage primaryStage) {
        InputStream fontStream = getClass().getResourceAsStream("/resources/fonts/Lato.ttf");
        if (fontStream == null) {
            System.err.println("Font resource not found!");
            lexend14 = Font.font("System", 14); // fallback font
        } else {
            lexend14 = Font.loadFont(fontStream, 14);
        }


        Pane pane = new Pane();

        InputStream logoStream = getClass().getResourceAsStream("/images/logo.png");

        if(logoStream != null){
            primaryStage.getIcons().add(new Image(logoStream));
        }
        primaryStage.setTitle("To-Do - Home");
        pane.setPrefSize(766, 378);
        pane.setStyle("-fx-background-color: #eeeeee;");
        primaryStage.setResizable(false);
        tray.setup(primaryStage, "To-Do", "/images/logo.png");
        trayIcon=tray.getTrayIcon();





        Platform.setImplicitExit(false);
        primaryStage.setOnCloseRequest(event -> {
            event.consume(); // Prevent the default close behavior
            Platform.runLater(() -> primaryStage.hide()); // Hide the stage instead of closing it
        });




        //root.getChildren().addAll(mainPage, dimBackground, overlayWrapper);



        rootStack.getChildren().addAll(pane);


        StackPane taskContainerWrapper = new StackPane();
        taskContainerWrapper.setLayoutX(12);
        taskContainerWrapper.setLayoutY(66);
        taskContainerWrapper.setPrefSize(208, 359);



        VBox taskListContainer = new VBox(10); // spacing between tasks
        taskListContainer.setLayoutY(100);
        taskListContainer.setLayoutX(12.0);
        taskListContainer.setPrefWidth(220);
        taskListContainer.setPrefHeight(359);
        taskListContainer.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-width: 1px; -fx-border-width: 0px 0px 0px 0px");
        //taskListContainer.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");

        String[][]tasks=UserData.ReturnData(username);
        if(tasks.length==0) {
            // No border

            Label taskLabels = new Label("No Tasks found...");
            taskLabels.setTextFill(Color.web("#1b1b1b"));
            taskLabels.setFont(Font.font(14));
            taskLabels.setTranslateX(50);
            taskListContainer.getChildren().add(taskLabels);

        }else {

            Arrays.sort(tasks, (a, b) -> {
                int groupCompare = b[3].compareToIgnoreCase(a[3]);
                if (groupCompare != 0) {
                    return groupCompare;
                }
                else{
                    return groupCompare;
                }
            });
            Arrays.sort(tasks,(a,b)->{
                int pa = Integer.parseInt(a[2]);
                int pb = Integer.parseInt(b[2]);
                return(Integer.compare(pb, pa));
            });

            for (String[] task : tasks) {
                taskListContainer.getChildren().add(createTaskPane(task, taskListContainer));
            }
        }
        ScrollPane scrollPane = new ScrollPane(taskListContainer);
        scrollPane.setPrefSize(400, 380);
        scrollPane.setMaxSize(400, 380);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        if(tasks.length>3){
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        }
        scrollPane.setPannable(false);



        taskContainerWrapper.getChildren().addAll(scrollPane);
        pane.getChildren().add(taskContainerWrapper);

        String firstWelcome= username.substring(0,1);
        firstWelcome=firstWelcome.toUpperCase();
        String secondWelcome = username.substring(1);
        Label Welcome = new Label("Welcome "+firstWelcome+secondWelcome+"!");
        Welcome.setLayoutX(12);
        Welcome.setLayoutY(8.1640625);
        Welcome.setPrefWidth(158);
        Welcome.setPrefHeight(33);
        Welcome.setStyle("-fx-font-size: 16px; -fx-text-fill: #1b1b1b;");
        Welcome.setFont(lexend32);
        pane.getChildren().add(Welcome);


        //I need to make the size of the sun the same size as 60,51



        Button lobutton = new Button("Logout");
        lobutton.setLayoutX(13.19);
        lobutton.setLayoutY(451.50);
        lobutton.setPrefWidth(67.00);
        lobutton.setPrefHeight(30.00);
        lobutton.setDisable(false);
        lobutton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");
        pane.getChildren().add(lobutton);
        lobutton.setOnAction(e ->{
            System.out.println("Logout Button was pressed.");
            LoginUi LoginUi= new LoginUi();
            LoginUi.start(primaryStage);
        });
        lobutton.setOnMouseEntered(e -> lobutton.setStyle(
                "-fx-background-color: #d3d3d3; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));
        lobutton.setOnMouseExited(e -> lobutton.setStyle(
                "-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));


        //Button Calanderaction = new Button("Calander goes here");
        //Calanderaction.setLayoutX(243.00);
        //Calanderaction.setLayoutY(56.46);
        //Calanderaction.setDisable(false);
        //Calanderaction.setPrefWidth(456.00);
        //Calanderaction.setPrefHeight(384.00);
        //Calanderaction.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");
        //pane.getChildren().add(Calanderaction);
        CalendarUi CalanderUi= new CalendarUi(username);

        Pane calander = CalanderUi.getPane();
        pane.getChildren().add(calander);

        Button SettingsIcon = new Button("Settings");
        SettingsIcon.setLayoutX(893.00);
        SettingsIcon.setLayoutY(4.46);
        SettingsIcon.setPrefWidth(76.00);
        SettingsIcon.setPrefHeight(30.00);
        SettingsIcon.setDisable(false);
        SettingsIcon.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");
        pane.getChildren().add(SettingsIcon);
        SettingsIcon.setOnAction(e -> {
            System.out.println("Settings Button was pressed.");
            Settings Settings = new Settings(username);
            Pane SettingPane =Settings.getContent(lexend14,lexend32);
            pane.getChildren().add(SettingPane);
        });
        SettingsIcon.setOnMouseEntered(e -> SettingsIcon.setStyle(
                "-fx-background-color: #d3d3d3; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));
        SettingsIcon.setOnMouseExited(e -> SettingsIcon.setStyle(
                "-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));


        Button PlButton = new Button("Plugins");
        PlButton.setLayoutX(819.00);
        PlButton.setLayoutY(4.46);
        PlButton.setPrefWidth(70.00);
        PlButton.setPrefHeight(30.00);
        PlButton.setDisable(false);
        PlButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");
        pane.getChildren().add(PlButton);
        PlButton.setOnAction(e -> {
            System.out.println("Plugin Button was pressed.");
            Plugins Plugins = new Plugins(username);
            Pane pluginPane =Plugins.getContent(lexend14,lexend32);
            pane.getChildren().add(pluginPane);
        });
        PlButton.setOnMouseEntered(e -> PlButton.setStyle(
                "-fx-background-color: #d3d3d3; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));
        PlButton.setOnMouseExited(e -> PlButton.setStyle(
                "-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));


        Button createtaskb = new Button("+");
        createtaskb.setFont(Font.font(60));
        createtaskb.setLayoutX(775);
        createtaskb.setLayoutY(140.00);
        createtaskb.setPrefWidth(150);
        createtaskb.setPrefHeight(150);
        createtaskb.setDisable(false);
        createtaskb.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");
        pane.getChildren().add(createtaskb);
        createtaskb.setOnMouseEntered(e -> createtaskb.setStyle(
                "-fx-background-color: #d3d3d3; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));
        createtaskb.setOnMouseExited(e -> createtaskb.setStyle(
                "-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));

        createtaskb.setOnAction(e ->{
            CreateTaskUi CreateTaskUi= new CreateTaskUi(username);
            CreateTaskUi.start(primaryStage);
        });
        Label Createtasktext= new Label();
        Createtasktext.setText("(Create Task)");
        Createtasktext.setFont(Font.font(14));
        Createtasktext.setLayoutX(810);
        Createtasktext.setLayoutY(300);
        pane.getChildren().add(Createtasktext);





        //Label Time = new Label("1/1/2025 12:00:00 AM");
        HBox timeLabelBox= new HBox();
        timeLabelBox.setLayoutX(255.00);
        timeLabelBox.setPrefWidth(455); // match width of your main pane
        timeLabelBox.setLayoutY(8);
        timeLabelBox.setAlignment(Pos.CENTER);
        Label timeLabel = new Label(LocalDateTime.now().format(DateTimeFormatter.ofPattern("M/d/yyyy h:mm:ss a")));
        LocalDateTime current = LocalDateTime.now();
        int currentHour=current.getHour();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm:ss a");
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e ->
                timeLabel.setText(LocalDateTime.now().format(formatter))));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        ImageView sun = new ImageView(new Image(getClass().getResourceAsStream("/images/sun.png")));
        ImageView moon = new ImageView(new Image(getClass().getResourceAsStream("/images/moon.png")));
        sun.setFitWidth(65);
        sun.setFitHeight(65);
        sun.setPreserveRatio(false);
        moon.setFitWidth(55);
        moon.setFitHeight(55);
        moon.setPreserveRatio(false);
        HBox.setMargin(sun, new Insets(-9, 0, 0, 0)); // top, right, bottom, left
        HBox.setMargin(moon, new Insets(-7, 0, 0, 0)); // top, right, bottom, left
        timeLabel.setPrefWidth(351);
        timeLabel.setPrefHeight(45);
        timeLabel.setStyle("-fx-font-size: 34px; -fx-text-fill: #1b1b1b;");
        timeLabel.setFont(lexend32);
        timeLabelBox.getChildren().add(timeLabel);
        if(currentHour>=18){
            timeLabelBox.setSpacing(2);
            timeLabelBox.getChildren().add(moon);
        } else {
            timeLabelBox.getChildren().add(sun);
        }
        pane.getChildren().add(timeLabelBox);

        pane.setPrefSize(980, 493); // Ensures all elements fit inside
        Scene scene = new Scene(rootStack, 980, 493); // Match the pane size exactly
        primaryStage.setScene(scene);


    }

    private String GroupGiver(String groupName) {
        // Define your available colors
        String[] colors = { "#FFA500", "#228B22", "#000080" }; // orange, forest green, navy, burgundy

        // Map each group name deterministically to one color by hashing
        int index = Math.abs(groupName.toLowerCase().hashCode()) % colors.length;

        return colors[index];
    }


    private void refreshTaskList(VBox taskListContainer) {
        taskListContainer.getChildren().clear();
        String[][] tasks = UserData.ReturnData(username);

        if (tasks.length == 0) {
            Label nuller = new Label("No Tasks found...");
            nuller.setTextFill(Color.web("#1b1b1b"));
            nuller.setFont(Font.font(14));
            nuller.setTranslateX(50);
            nuller.setPrefSize(208, 50);

            taskListContainer.getChildren().add(nuller);
        } else {
            Arrays.sort(tasks, (a, b) -> {
                int groupCompare = b[3].compareToIgnoreCase(a[3]); // compare group names
                if (groupCompare != 0) {
                    return groupCompare;
                }
                else{
                    return groupCompare;
                }
            });
            Arrays.sort(tasks,(a,b)->{
                int pa = Integer.parseInt(a[2]);
                int pb = Integer.parseInt(b[2]);
                return(Integer.compare(pb, pa));
            });

            for (String[] task : tasks) {
                String taskName = task[0];
                taskListContainer.getChildren().add(createTaskPane(task, taskListContainer));
            }

        }
    }
    private void showNotification(String title, String message) {
        trayIcon.displayMessage(title, message, TrayIcon.MessageType.WARNING);
    }
    private String buildMessage(String stage, String taskname) {
        switch (stage) {
            case "Overdue": return taskname + " is overdue!";
            case "1min":    return taskname + " is due in 1 minute! Lock in.";
            case "10min":   return taskname + " is due in 10 minutes! Lock in.";
            case "30min":   return taskname + " is due in 30 minutes! Lock in.";
            case "1hr":     return taskname + " is due in 1 hour! Lock in.";
            case "5hr":     return taskname + " is due in 5 hours! Lock in.";
            case "24hr":    return taskname + " is due in 24 hours! Lock in.";
            default:        return taskname + " is due soon!";
        }
    }


    private Button createTaskPane(String[] task, VBox taskListContainer) {
        // Font (optional, load once externally if reused)

        // Group and Time Row
        Label group = new Label(task[3]);
        group.setStyle("-fx-font-size: 12px;");
        group.setTranslateX(-10);
        Label duetime = new Label("----");
        duetime.setStyle("-fx-font-size: 14px;");

        Label warning = new Label("----");
        warning.setStyle("-fx-text-fill: #1b1b1b;");
        warning.setStyle("-fx-font-size: 14px;");
        LocalDateTime timec = UserData.DataCheckerUI(task[0]);
        Timeline countdown = new Timeline(new KeyFrame(Duration.seconds(1),event-> {
            LocalDateTime now = LocalDateTime.now();
            long secondsUntilDue = now.until(timec, ChronoUnit.SECONDS);
            lastNotifiedStage.keySet().removeIf(key -> {
                String[] parts = key.split("_", 2);
                if (parts.length < 2) return false;
                try {
                    LocalDateTime due = LocalDateTime.parse(parts[1]);
                    String stage = lastNotifiedStage.get(key);
                    return stage != null && !stage.equals("Overdue") && due.isBefore(now.minusHours(1));

                } catch (Exception e) {
                    return false;
                }
            });

            if (secondsUntilDue>0) {
                long totalSeconds = LocalDateTime.now().until(timec, ChronoUnit.SECONDS);
                long hours = totalSeconds / 3600;
                long minutes = (totalSeconds % 3600) / 60;
                long seconds = totalSeconds % 60;
                duetime.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            } else {
                duetime.setText("00:00:00");
            }
            String stage = null;
            String taskKey = taskname + "_" + timec.toString(); // composite key


            if (secondsUntilDue <= 0) {
                 stage = "Overdue";
                warning.setText("Overdue");
            } else if (secondsUntilDue <= 60) {
                stage = "1min";
                warning.setText("1 minute!");
            } else if (secondsUntilDue <= 10*60) {
                stage = "10min";
                warning.setText("10 minutes left!");
            } else if (secondsUntilDue <= 30 * 60) {
                stage = "30min";
                warning.setText("30 minutes left!");
            } else if (secondsUntilDue <= 60 * 60) {
                stage = "1hr";
                warning.setText("1 hour!");
            } else if (secondsUntilDue <= 24 * 60 * 60) {
                stage = "5hr";
                warning.setText("5 hours");
            } else if (secondsUntilDue <= 24 * 60 * 60) {
                stage = "24hr";
                warning.setText("24 hours");

            } else {
                warning.setText("Due Soon!");
            }
            if (stage != null && !stage.equals(lastNotifiedStage.get(taskKey))) {
                lastNotifiedStage.put(taskKey, stage);
                showNotification("To-Do - " + task[0], buildMessage(stage, task[0]));
            }
        }));

        countdown.setCycleCount(Timeline.INDEFINITE);
        countdown.play();

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox topRow = new HBox(group, spacer, duetime);
        topRow.setPrefWidth(184);
        topRow.setAlignment(Pos.CENTER_LEFT);
        topRow.setTranslateY(-10);




        // Task Name
        Label name = new Label(task[0]);
        name.setStyle("-fx-text-fill: #1b1b1b;");
        name.setStyle("-fx-font-size: 15px;");

        // Warning

        warning.setTranslateY(10);


        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        HBox bottomRow = new HBox(spacer2, warning);
        bottomRow.setAlignment(Pos.CENTER_RIGHT);

        // VBox to hold everything
        VBox content = new VBox(4, topRow, name, bottomRow);
        content.setPrefSize(184, 69);
        content.setPadding(new Insets(8));
        content.setAlignment(Pos.TOP_LEFT);

        // Button with VBox as graphic
        Button background = new Button();
        background.setPrefSize(184, 69);
        String bgColor = GroupGiver(task[3]);
        if(timec.isBefore(LocalDateTime.now())){
            bgColor = "#FF4C4C";
        }
        background.setStyle(
                "-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color:" +bgColor+"; " +
                        "-fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");

        String[][] dtasks= UserData.ReturnData(username);
        if (dtasks.length>3) {
            background.setTranslateX(3);
        }
        else{
            background.setTranslateX(10);
            background.setTranslateY(5);
        }


        background.setGraphic(content);

        // Mouse click opens update overlay
        background.setOnMouseClicked((MouseEvent e) -> {
            final Pane[] updateOverlay = new Pane[1];
            UpdateTaskUi updateTaskUi = new UpdateTaskUi(username, task[0], () -> {
                rootStack.getChildren().remove(updateOverlay[0]);
                refreshTaskList(taskListContainer);
            });
            updateOverlay[0] = updateTaskUi.getContent(lexend14);
            rootStack.getChildren().add(updateOverlay[0]);
        });
        // Hover style
        String finalBgColor = bgColor;
        background.setOnMouseEntered(e -> background.setStyle(
                "-fx-background-color: #d3d3d3; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));

        background.setOnMouseExited(e -> background.setStyle(
                "-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color:" +finalBgColor+"; " +
                        "-fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"));


        return background;

    }



    public static void main(String[] args) {
        launch(args);
    }
}

