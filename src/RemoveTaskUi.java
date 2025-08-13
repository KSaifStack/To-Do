// UI for Removing tasks
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RemoveTaskUi {
    private final String username;
    private final String taskname;
    private final Runnable onUpdate;
    public RemoveTaskUi(String username, String taskname,Runnable onUpdate) {
        this.username = username;
        this.taskname = taskname;
        this.onUpdate = onUpdate;
    }
    public Pane getContent(Font lexend14,Font lexend32){
        Pane pane = new Pane();
        pane.setPrefSize(980, 493);
        pane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.15);");

        InputStream fontStream = getClass().getResourceAsStream("/resources/fonts/Lato.ttf");
        if (fontStream == null) {
            System.err.println("Font resource not found!");
            lexend14 = Font.font("System", 14); // fallback font
        } else {
            lexend14 = Font.loadFont(fontStream, 14);
        }
        Region background = new Region();
        background.setLayoutX(150);
        background.setLayoutY(155);
        background.setPrefSize(299, 176); // full height to avoid bottom gap
        background.setStyle("-fx-background-color: #ffffff; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");
        pane.getChildren().add(background);

        Label Label = new Label("Are you sure you would like to remove:");
        Label.setAlignment(Pos.CENTER);
        Label.setFont(lexend14);
        Label.setPrefSize(425,85);
        Label.setLayoutX(85);
        Label.setLayoutY(130);
        pane.getChildren().add(Label);

        Label Name = new Label("'"+taskname+"'");
        Name.setAlignment(Pos.CENTER);
        Name.setFont(lexend14);
        Name.setPrefSize(425,85);
        Name.setLayoutX(85);
        Name.setLayoutY(150);
        pane.getChildren().add(Name);


        Button Yes = new Button("Yes");
        Yes.setStyle("-fx-background-color: #ffffff; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");
        Yes.setLayoutX(173);
        Yes.setLayoutY(230);
        Yes.setFont(lexend14);
        Yes.setPrefSize(99,78);
        Yes.setOnMouseEntered(e -> Yes.setStyle(
                "-fx-background-color: #d3d3d3; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));
        Yes.setOnMouseExited(e -> Yes.setStyle(
                "-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));
        Yes.setOnAction(e-> {
            UserData.removeTask(username, taskname);
            onUpdate.run();

        });

        Button No = new Button("No");
        No.setStyle("-fx-background-color: #ffffff; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");
        No.setLayoutX(325);
        No.setLayoutY(230);
        No.setFont(lexend14);
        No.setPrefSize(99,78);
        No.setOnMouseEntered(e -> No.setStyle(
                "-fx-background-color: #d3d3d3; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));
        No.setOnMouseExited(e -> No.setStyle(
                "-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));
        No.setOnAction(e ->{
            pane.setVisible(false);
        });
        pane.getChildren().addAll(Yes,No);

        return pane;
    }
}