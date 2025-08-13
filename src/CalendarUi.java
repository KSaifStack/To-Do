//Calander ui for main page should return a button with the follow parts inside of it.
//should be able to take date and time and data based off username to display data in a calander format.
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.time.*;
import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;

public class CalendarUi {
    private final String username;
    private YearMonth displayedMonth;

    public CalendarUi(String username) {
        this.username = username;
        this.displayedMonth = YearMonth.now(); // start with current month
    }

    public Pane getPane() {
        Font lexend32 = Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Lexend.ttf"), 32);
        Font lexend14 = Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Lexend.ttf"), 14);
        Font lexend12 = Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Lato.ttf"), 12);
        Font lexend8 = Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Lato.ttf"), 8);

        Pane background = new Pane();
        background.setLayoutX(243.00);
        background.setLayoutY(56.46);
        background.setPrefSize(455, 382);
        background.setStyle("-fx-background-color: #ffffff; -fx-border-color: #626262; -fx-border-radius: 2px; -fx-border-width: 1px;");
        HBox monthLabelBox = new HBox();
        monthLabelBox.setPrefWidth(455); // match width of your main pane
        monthLabelBox.setTranslateY(1);
        monthLabelBox.setAlignment(Pos.CENTER);

        Label monthLabel = new Label();
        monthLabel.setFont(lexend32);
        monthLabelBox.getChildren().add(monthLabel);
        background.getChildren().add(monthLabelBox);

        Button backArrow = new Button("<");
        Button nextArrow = new Button(">");
        setupArrowStyle(backArrow, lexend14);
        setupArrowStyle(nextArrow, lexend14);

        backArrow.setTranslateX(5);
        backArrow.setTranslateY(8);
        nextArrow.setTranslateX(405);
        nextArrow.setTranslateY(8);

        background.getChildren().addAll(backArrow, nextArrow);

        String[] days = {"Sun", "Mon", "Tues", "Wed", "Thurs", "Fri", "Sat"};
        for (int i = 0; i < days.length; i++) {
            Button colBg = new Button();
            colBg.setPrefSize(65, 330);
            colBg.setTranslateX(i * 65);
            colBg.setTranslateY(50);
            colBg.setStyle("-fx-background-color: #ffffff; -fx-border-color: #626262; -fx-border-width: 1px 1px 0px 1px;");
            background.getChildren().add(colBg);

            Label dayLabel = new Label(days[i]);
            dayLabel.setFont(lexend12);
            dayLabel.setPrefSize(65, 26);
            dayLabel.setTranslateX(i * 65);
            dayLabel.setTranslateY(45);
            dayLabel.setAlignment(Pos.CENTER);
            dayLabel.setStyle("-fx-background-color: #ffffff; -fx-border-color: #626262; -fx-border-width: 1px;");
            background.getChildren().add(dayLabel);
        }

        GridPane calendarGrid = new GridPane();
        calendarGrid.setHgap(13);
        calendarGrid.setVgap(6);
        calendarGrid.setLayoutX(6);
        calendarGrid.setLayoutY(75);
        background.getChildren().add(calendarGrid);

        Runnable updateCalendar = () -> {
            calendarGrid.getChildren().clear();

            LocalDateTime currentTime = LocalDateTime.now();
            int currentDay = currentTime.getDayOfMonth();
            int currentMonth = currentTime.getMonthValue();
            int currentYear = currentTime.getYear();

            LocalDate firstOfMonth = displayedMonth.atDay(1);
            DayOfWeek firstDayOfWeek = firstOfMonth.getDayOfWeek();
            LocalDate startDate = firstOfMonth.minusDays(firstDayOfWeek.getValue() % 7);

            String monthName = displayedMonth.getMonth().toString();
            String prettyMonth = monthName.substring(0, 1).toUpperCase() + monthName.substring(1).toLowerCase();
            monthLabel.setText(prettyMonth + " " + displayedMonth.getYear());

            String[][] tasks = UserData.ReturnData(username);
            int totalCells = 42;
            int col = 0, row = 0;

            Button taskLabel= new Button();
            for (int i = 1; i <= totalCells; i++) {
                Pane cell = new Pane();
                cell.setStyle("-fx-background-color: #ffffff; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-border-width: 1px;");
                cell.setOnMouseEntered(e -> {cell.setStyle("-fx-background-color: #d3d3d3; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-border-width: 1px;");
                });
                cell.setOnMouseExited(e -> cell.setStyle("-fx-background-color: #ffffff; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-border-width: 1px;"));
                cell.setPrefSize(52, 45);

                LocalDate cellDate = startDate.plusDays(i - 1);
                Label dayOf = new Label(String.valueOf(cellDate.getDayOfMonth()));
                dayOf.setFont(lexend12);
                dayOf.setTranslateX(5);

                if (!cellDate.getMonth().equals(displayedMonth.getMonth())) {
                    dayOf.setStyle("-fx-text-fill: #a0a0a0;");

                }

                cell.getChildren().add(dayOf);

                int tcount = 0;
                for (String[] task : tasks) {
                    LocalDateTime taskDateTime = UserData.DataCheckerUI(task[0]);
                    if (taskDateTime.toLocalDate().equals(cellDate)) {
                        tcount++;
                        if (tcount == 1) {
                             taskLabel = new Button(task[4].substring(11, 13) + " " + task[4].substring(17) + "|" + task[0]);
                            if (cellDate.getMonthValue() == currentMonth &&
                                    cellDate.getYear() == currentYear &&
                                    cellDate.getDayOfMonth() == currentDay) {
                                taskLabel.setStyle("-fx-background-color: #767676; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");

                            }
                            else{
                                taskLabel.setStyle(cell.getStyle());
                                taskLabel.toFront();
                            }
                            taskLabel.setFont(lexend8);
                            taskLabel.setLayoutY(18);
                            taskLabel.setLayoutX(3);
                            taskLabel.setPrefSize(46, 20);
                            cell.getChildren().add(taskLabel);
                        } else if (tcount >= 2) {
                            cell.getChildren().removeIf(n -> n instanceof Button);
                            Button moreLabel = new Button("2+");
                            if (cellDate.getMonthValue() == currentMonth &&
                                    cellDate.getYear() == currentYear &&
                                    cellDate.getDayOfMonth() == currentDay) {
                                moreLabel.setStyle("-fx-background-color: #767676; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");
                            }
                            else {
                                moreLabel.setStyle("-fx-background-color: #ffffff; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-border-width: 1px;");
                            }
                            moreLabel.setFont(lexend12);
                            moreLabel.setLayoutY(13);
                            moreLabel.setLayoutX(9);
                            moreLabel.setPrefSize(34, 28);
                            moreLabel.setWrapText(true);
                            cell.getChildren().add(moreLabel);
                        }
                    }
                }

                if (cellDate.getMonthValue() == currentMonth &&
                        cellDate.getYear() == currentYear &&
                        cellDate.getDayOfMonth() == currentDay) {
                    cell.setStyle("-fx-background-color: #767676; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");
                    cell.setOnMouseEntered(e -> cell.setStyle("-fx-background-color: #767676; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"));
                    cell.setOnMouseExited(e -> cell.setStyle("-fx-background-color: #767676; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;"));
                }

                calendarGrid.add(cell, col, row);
                col++;
                if (col > 6) {
                    col = 0;
                    row++;
                }
            }
        };

        backArrow.setOnAction(e -> {
            displayedMonth = displayedMonth.minusMonths(1);
            updateCalendar.run();
        });

        nextArrow.setOnAction(e -> {
            displayedMonth = displayedMonth.plusMonths(1);
            updateCalendar.run();
        });

        updateCalendar.run(); // Initial render

        return background;
    }

    private void setupArrowStyle(Button arrow, Font font) {
        arrow.setFont(font);
        arrow.setPrefSize(45, 30);
        arrow.setStyle("-fx-background-color: #ffffff; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-border-width: 1px;");
        arrow.setOnMouseEntered(e -> arrow.setStyle("-fx-background-color: #d3d3d3; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-border-width: 1px;"));
        arrow.setOnMouseExited(e -> arrow.setStyle("-fx-background-color: #ffffff; -fx-border-color: #626262; -fx-border-radius: 4px; -fx-border-width: 1px;"));
    }
}
