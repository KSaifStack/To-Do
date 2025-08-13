// UI for creating tasks
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class CreateTaskUi {

    //--
    private final String username;
    public CreateTaskUi(String username) {
        this.username = username;
    }
    //--

    Font lexend32 = Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Lexends.ttf"), 32);
    Font lexend14 = Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Lexends.ttf"), 14);

    private Label timeErrorLabel = new Label();
    String taskname;
    String taskdesc;
    int taskrank;
    String taskgroup;
    String dueInput;


    public void start(Stage primaryStage) {
        String[][] tasks = UserData.ReturnData(username);
        InputStream iconStream = getClass().getResourceAsStream("/images/logo.png");
        if (iconStream != null) {
            primaryStage.getIcons().add(new Image(iconStream));
        } else {
            System.err.println("Window icon not found.");
        }

        primaryStage.setTitle("Create New Task");
        primaryStage.setResizable(false);

        BorderPane root = new BorderPane();
        root.setPrefSize(706, 346);
        root.setStyle("-fx-background-color: #eeeeee;");
        root.setPadding(new Insets(20));

        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER);



        Label taskLabel = new Label("What would you like to name your Task?");
        taskLabel.setFont(lexend32 != null ? lexend32 : Font.font(24));
        taskLabel.setStyle("-fx-text-fill: #1b1b1b;");
        taskLabel.setWrapText(true);
        taskLabel.setMaxWidth(600);
        taskLabel.setAlignment(Pos.CENTER);

        TextArea inputArea = new TextArea();

        inputArea.setPromptText("Enter your task!");
        inputArea.setFont(lexend14 != null ? lexend14 : Font.font(14));
        inputArea.setPrefHeight(64);
        inputArea.setMaxWidth(435);
        inputArea.setStyle(
                "-fx-control-inner-background: #ffffff;" +
                        "-fx-background-color: #ffffff;" +
                        "-fx-text-fill: #1b1b1b;" +
                        "-fx-border-color: #626262;" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-radius: 4px;" +
                        "-fx-prompt-text-fill: #737674;"
        );

        Button arrowAction = new Button("→");
        arrowAction.setFont(lexend14 != null ? lexend14 : Font.font(14));
        arrowAction.setPrefSize(186, 55);
        arrowAction.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-text-fill: #1b1b1b;" +
                        "-fx-border-color: #626262;" +
                        "-fx-border-radius: 4px;" +
                        "-fx-background-radius: 4px;" +
                        "-fx-border-width: 1px;"
        );
        arrowAction.setOnMouseEntered(e -> arrowAction.setStyle(
                "-fx-background-color: #d3d3d3; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));
        arrowAction.setOnMouseExited(e -> arrowAction.setStyle(
                "-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));


        timeErrorLabel.setFont(lexend32 != null ? lexend32 : Font.font(16));
        timeErrorLabel.setStyle("-fx-text-fill: #bd1111;");
        timeErrorLabel.setTranslateY(-20);
        timeErrorLabel.setVisible(false);
        arrowAction.setOnAction(e -> {
            String input = inputArea.getText().trim();
            boolean doubled = false;
            for(String [] task:tasks){
                if(task[0].equals(input)){
                    doubled=true;
                }
            }
            if (input.isEmpty()) {
                System.out.println("-------------");
                System.out.println("ERROR: No task name printed.");
                System.out.println("-------------");
                timeErrorLabel.setText("ERROR: No task name printed.");
                timeErrorLabel.setVisible(true);

            }
                else if(doubled==true){
                System.out.println("ERROR: Task cannot have the same name.");
            } else {
                taskname=input;
                System.out.println("Task Name: "+taskname);
                showwindow1(primaryStage);


            }

        });

        arrowAction.addEventFilter(MouseEvent.MOUSE_RELEASED, e ->
                arrowAction.setBackground(new Background(new BackgroundFill(Color.web("#ffffff"), new CornerRadii(4), null)))
        );

        HBox buttonBox = new HBox(arrowAction);
        buttonBox.setAlignment(Pos.CENTER);

        centerBox.getChildren().addAll(taskLabel, inputArea, timeErrorLabel,buttonBox);
        root.setCenter(centerBox);

        // Bottom-left Go Back Button
        Button goBack = new Button("Go Back");
        goBack.setFont(lexend14 != null ? lexend14 : Font.font(14));
        goBack.setPrefSize(106, 30);
        goBack.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-text-fill: #1b1b1b;" +
                        "-fx-border-color: #626262;" +
                        "-fx-border-radius: 4px;" +
                        "-fx-background-radius: 4px;" +
                        "-fx-border-width: 1px;"
        );
        goBack.setOnMouseEntered(e -> goBack.setStyle(
                "-fx-background-color: #d3d3d3; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));
        goBack.setOnMouseExited(e -> goBack.setStyle(
                "-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));

        goBack.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            goBack.setBackground(new Background(new BackgroundFill(Color.web("#c2c2c2"), new CornerRadii(4), null)));
            System.out.println("Go back pressed");
            TaskUi TaskUi= new TaskUi(username);
            TaskUi.start(primaryStage);
        });

        goBack.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            goBack.setBackground(new Background(new BackgroundFill(Color.web("#ffffff"), new CornerRadii(4), null)));
        });

        HBox bottomLeft = new HBox(goBack);
        bottomLeft.setPadding(new Insets(0, 0, 10, 10));
        bottomLeft.setAlignment(Pos.BOTTOM_LEFT);
        root.setBottom(bottomLeft);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    //--
    private void showwindow1(Stage stage) {
        Label Title = new Label("List a description of this task");
        //--
        Title.setFont(lexend32 != null ? lexend32 : Font.font(24));
        Title.setStyle("-fx-text-fill: #1b1b1b;");
        Title.setWrapText(true);
        Title.setMaxWidth(600);
        Title.setAlignment(Pos.CENTER);
        //--
        TextArea inputArea = new TextArea();

        inputArea.setPromptText("Enter your task!");
        inputArea.setFont(lexend14 != null ? lexend14 : Font.font(14));
        inputArea.setPrefHeight(64);
        inputArea.setMaxWidth(435);
        inputArea.setStyle(
                "-fx-control-inner-background: #ffffff;" +
                        "-fx-background-color: #ffffff;" +
                        "-fx-text-fill: #1b1b1b;" +
                        "-fx-border-color: #626262;" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-radius: 4px;" +
                        "-fx-prompt-text-fill: #737674;"
        );

        Button arrowAction = new Button("→");
        arrowAction.setFont(lexend14 != null ? lexend14 : Font.font(14));
        arrowAction.setPrefSize(186, 55);
        arrowAction.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-text-fill: #1b1b1b;" +
                        "-fx-border-color: #626262;" +
                        "-fx-border-radius: 4px;" +
                        "-fx-background-radius: 4px;" +
                        "-fx-border-width: 1px;"
        );
        arrowAction.setOnMouseEntered(e -> arrowAction.setStyle(
                "-fx-background-color: #d3d3d3; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));
        arrowAction.setOnMouseExited(e -> arrowAction.setStyle(
                "-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));

        VBox layout = new VBox(20, Title);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #eeeeee;");
        stage.setScene(new Scene(layout, 766, 378));
        stage.setTitle("Create New Task-Desc");
        stage.show();
        arrowAction.addEventFilter(MouseEvent.MOUSE_PRESSED, e ->
                arrowAction.setBackground(new Background(new BackgroundFill(Color.web("#c2c2c2"), new CornerRadii(4), null))));
        timeErrorLabel.setFont(lexend32 != null ? lexend32 : Font.font(16));
        timeErrorLabel.setStyle("-fx-text-fill: #bd1111;");
        timeErrorLabel.setTranslateY(-20);
        timeErrorLabel.setVisible(false);
        arrowAction.setOnAction(e -> {
            String input = inputArea.getText().trim();
            if (input.isEmpty()) {
                System.out.println("ERROR: No task name printed.");
                timeErrorLabel.setText("ERROR: No task name printed.");
                timeErrorLabel.setVisible(true);
            } else {
                taskdesc=input;
                System.out.println("Task desc: "+taskdesc);
                showwindow2(stage);


            }

        });

        arrowAction.addEventFilter(MouseEvent.MOUSE_RELEASED, e ->
                arrowAction.setBackground(new Background(new BackgroundFill(Color.web("#ffffff"), new CornerRadii(4), null)))
        );

        HBox buttonBox = new HBox(arrowAction);
        buttonBox.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(inputArea, timeErrorLabel,buttonBox);


    }
    //Rank
    private void showwindow2(Stage stage) {
        Font lexend32 = Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Lexend.ttf"), 32);
        Font lexend30 = Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Lexend.ttf"), 30);
        Font lexend14 = Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Lexend.ttf"), 14);

        Pane pane = new Pane();
        pane.setPrefSize(766, 378);
        pane.setStyle("-fx-background-color: #eeeeee;");

        Label title = new Label("Please rank this task!");
        title.setFont(lexend32 != null ? lexend32 : Font.font(32));
        title.setStyle("-fx-text-fill: #1b1b1b;");
        title.setLayoutY(60);
        title.setPrefWidth(766); // Same as pane width
        title.setAlignment(Pos.CENTER);
        title.setWrapText(true);
        pane.getChildren().add(title);



        Label lowestLabel = new Label("(Lowest)");
        lowestLabel.setLayoutX(69);
        lowestLabel.setLayoutY(290);
        lowestLabel.setFont(lexend14 != null ? lexend14 : Font.font(14));
        lowestLabel.setStyle("-fx-text-fill: #1b1b1b;");
        pane.getChildren().add(lowestLabel);

        Label highestLabel = new Label("(Highest)");
        highestLabel.setLayoutX(643);
        highestLabel.setLayoutY(290);
        highestLabel.setFont(lexend14 != null ? lexend14 : Font.font(14));
        highestLabel.setStyle("-fx-text-fill: #1b1b1b;");
        pane.getChildren().add(highestLabel);


        for (int i = 0; i < 5; i++) {
            Button btn = new Button(String.valueOf(i + 1));
            btn.setLayoutX(43.8 + i * 144); // spacing between buttons
            btn.setLayoutY(180.0);
            btn.setPrefSize(105, 105);
            btn.setFont(lexend30 != null ? lexend30 : Font.font(30));
            btn.setStyle(
                    "-fx-background-color: #ffffff;" +
                            "-fx-text-fill: #1b1b1b;" +
                            "-fx-border-color: #626262;" +
                            "-fx-border-radius: 4px;" +
                            "-fx-background-radius: 4px;" +
                            "-fx-border-width: 1px;"
            );

            btn.setOnMouseEntered(e -> btn.setStyle(
                    "-fx-background-color: #d3d3d3; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
            ));
            btn.setOnMouseExited(e -> btn.setStyle(
                    "-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
            ));

            btn.setOnAction(e-> {
                taskrank=Integer.parseInt(btn.getText());
                System.out.println("-------------");
                System.out.println("Button "+btn.getText()+" was pressed.");
                System.out.println("Task rank is "+taskrank+".");
                System.out.println("-------------");
                showwindow3(stage);

            });

            pane.getChildren().add(btn);
        }

        stage.setTitle("Create New Task - Rank");
        stage.setScene(new Scene(pane));
        stage.show();
    }

    private void showwindow3(Stage stage) {
        Label title = new Label("What type of task is this?");
        title.setFont(lexend32 != null ? lexend32 : Font.font(32));
        title.setStyle("-fx-text-fill: #1b1b1b;");
        title.setLayoutY(60);
        title.setPrefWidth(766); // Same as pane width
        title.setAlignment(Pos.CENTER);
        title.setWrapText(true);
        TextArea inputArea = new TextArea();

        inputArea.setPromptText("Enter your task!");
        inputArea.setFont(lexend14 != null ? lexend14 : Font.font(14));
        inputArea.setPrefHeight(64);
        inputArea.setMaxWidth(435);
        inputArea.setStyle(
                "-fx-control-inner-background: #ffffff;" +
                        "-fx-background-color: #ffffff;" +
                        "-fx-text-fill: #1b1b1b;" +
                        "-fx-border-color: #626262;" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-radius: 4px;" +
                        "-fx-prompt-text-fill: #737674;"
        );

        Button arrowAction = new Button("→");
        arrowAction.setFont(lexend14 != null ? lexend14 : Font.font(14));
        arrowAction.setPrefSize(186, 55);
        arrowAction.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-text-fill: #1b1b1b;" +
                        "-fx-border-color: #626262;" +
                        "-fx-border-radius: 4px;" +
                        "-fx-background-radius: 4px;" +
                        "-fx-border-width: 1px;"
        );
        arrowAction.setOnMouseEntered(e -> arrowAction.setStyle(
                "-fx-background-color: #d3d3d3; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));
        arrowAction.setOnMouseExited(e -> arrowAction.setStyle(
                "-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));


        VBox layout = new VBox(20, title);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #eeeeee;");
        stage.setScene(new Scene(layout, 766, 378));
        stage.setTitle("Create New Task-Group");
        stage.show();
        arrowAction.addEventFilter(MouseEvent.MOUSE_PRESSED, e ->
                arrowAction.setBackground(new Background(new BackgroundFill(Color.web("#c2c2c2"), new CornerRadii(4), null))));

        timeErrorLabel.setFont(lexend32 != null ? lexend32 : Font.font(16));
        timeErrorLabel.setStyle("-fx-text-fill: #bd1111;");
        timeErrorLabel.setTranslateY(-20);
        timeErrorLabel.setVisible(false);
        arrowAction.setOnAction(e -> {
            String input = inputArea.getText().trim();
            if (input.isEmpty()) {
                System.out.println("ERROR: No task name found.");
                timeErrorLabel.setText("ERROR: No task name found.");
                timeErrorLabel.setVisible(true);
            } else {
                taskgroup=input;
                System.out.println("Task group: "+taskgroup);
                showwindow4(stage);


            }

        });

        arrowAction.addEventFilter(MouseEvent.MOUSE_RELEASED, e ->
                arrowAction.setBackground(new Background(new BackgroundFill(Color.web("#ffffff"), new CornerRadii(4), null)))
        );

        HBox buttonBox = new HBox(arrowAction);
        buttonBox.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(inputArea, timeErrorLabel,buttonBox);



    }
    private void showwindow4(Stage stage) {
        // Title Label
        Label title = new Label("What time is this task due?");
        title.setFont(lexend32 != null ? lexend32 : Font.font(32));
        title.setStyle("-fx-text-fill: #1b1b1b;");
        title.setWrapText(true);
        title.setAlignment(Pos.CENTER);
        title.setMaxWidth(766); // span full window width

        // DateTime Picker (already 450px wide)
        CustomDatePicker datapicker = new CustomDatePicker();
        datapicker.setTranslateY(-40);
        // Arrow Button
        Button arrowAction = new Button("→");
        arrowAction.setFont(lexend14 != null ? lexend14 : Font.font(14));
        arrowAction.setPrefSize(100, 40);
        arrowAction.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-text-fill: #1b1b1b;" +
                        "-fx-border-color: #626262;" +
                        "-fx-border-radius: 4px;" +
                        "-fx-background-radius: 4px;" +
                        "-fx-border-width: 1px;"
        );
        arrowAction.setOnMouseEntered(e -> arrowAction.setStyle(
                "-fx-background-color: #d3d3d3; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));
        arrowAction.setOnMouseExited(e -> arrowAction.setStyle(
                "-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));
        VBox layout = new VBox(30);
        timeErrorLabel.setFont(lexend32 != null ? lexend32 : Font.font(16));
        timeErrorLabel.setStyle("-fx-text-fill: #bd1111;");
        timeErrorLabel.setTranslateY(-20);
        timeErrorLabel.setVisible(false);
        arrowAction.setOnAction(e -> {
            if(datapicker.getDateTime()==null){
                System.out.println("ERROR: Please enter proper time.");
                timeErrorLabel.setText("ERROR: Please enter proper time.");
                timeErrorLabel.setVisible(true);
            }
            else{
                String input = CustomDatePicker.DateTimeUtils.formatDateTime(datapicker.getDateTime());
                dueInput=input;
                LocalDateTime dueDate = LocalDateTime.parse(dueInput,DateTimeFormatter.ofPattern("yyyy MM dd hh mm a"));
                UserData.SaveTask(username,taskname, taskdesc, taskrank, taskgroup,dueDate);
                TaskUi TaskUi= new TaskUi(username);
                TaskUi.start(stage);
            }
        });

        // Layout
         // spacing between nodes
        layout.setAlignment(Pos.CENTER); // center everything
        layout.setStyle("-fx-background-color: #eeeeee;");
        layout.setPrefSize(766, 378);

        // Add nodes in order
        arrowAction.setTranslateY(-40);
        arrowAction.setTranslateX(-10);
        layout.getChildren().addAll(title, datapicker, arrowAction,timeErrorLabel);

        // Set scene
        stage.setScene(new Scene(layout, 766, 378));
        stage.setTitle("Create New Task-Time");
        stage.show();
    }

}



