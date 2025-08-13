//UI for setting features
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Settings {
    private String username = null;
    //Runnable
    public Settings(String username){
        this.username=username;
    }
    public Pane getContent(Font lexend14,Font lexend32){
        Pane pane = new Pane();
        pane.setPrefSize(980, 493);
        pane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.15);");
        //--
        Region background = new Region();
        background.setLayoutX(165);
        background.setLayoutY(10);
        background.setPrefSize(605, 372); // full height to avoid bottom gap
        background.setStyle("-fx-background-color: #ffffff; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");
        pane.getChildren().add(background);

        Button settingsLabel = new Button("Settings");
        settingsLabel.setStyle("-fx-background-color: #ffffff; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");
        settingsLabel.setPrefSize(192,59);
        settingsLabel.setFont(lexend32);
        settingsLabel.setLayoutX(369);
        settingsLabel.setLayoutY(17);
        pane.getChildren().add(settingsLabel);

        //--
        Button back = new Button("Back");
        back.setStyle("-fx-background-color: #ffffff; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");
        back.setPrefSize(73,60);
        back.setFont(lexend14);
        back.setLayoutX(172);
        back.setLayoutY(317);
        back.setOnMouseEntered(e -> back.setStyle(
                "-fx-background-color: #d3d3d3; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));
        back.setOnMouseExited(e -> back.setStyle(
                "-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"
        ));
        back.setOnAction(e->{
            pane.setVisible(false);
        });

        pane.getChildren().add(back);
        //--
        return pane;
    }


}
