import javafx.application.Platform;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.text.*;
import java.util.*;
public class AppTray {

    private TrayIcon trayIcon;
    private boolean firstHide = true;

    public TrayIcon getTrayIcon(){
        return trayIcon;
    }

    public void setup(Stage stage, String tooltip, String iconPath) {
        if (!SystemTray.isSupported()) {
            System.out.println("System tray not supported");
            return;
        }

        // Load icon
        URL iconUrl = getClass().getResource(iconPath);
        if (iconUrl == null) {
            System.out.println("Tray icon image not found: " + iconPath);
            return;
        }
        Image trayImage = Toolkit.getDefaultToolkit().getImage(iconUrl);

        // Popup menu
        PopupMenu popup = new PopupMenu();

        // Restore logic
        Runnable showStage = () -> {
            stage.setOpacity(1);           // visible again
            stage.setIconified(false);     // restore from minimized state
            stage.show();                  // ensure it's shown
            stage.toFront();               // bring forward
            stage.requestFocus();          // grab focus
        };

        // Toggle logic
        Runnable toggleStage = () -> {
            if (stage.isShowing() && stage.getOpacity() > 0) {
                hideToTray(stage); // minimize & invisible
            } else {
                showStage.run();
            }
        };

        // Menu items
        MenuItem openItem = new MenuItem("Open");
        openItem.addActionListener(e -> {
            Platform.runLater(() -> {
                if (stage.isIconified()) stage.setIconified(false);
                if (!stage.isShowing()) stage.show();
                stage.toFront();
                stage.requestFocus();
            });
        });


        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(e -> {
            System.out.println("Goodboyee");
            SystemTray.getSystemTray().remove(trayIcon);
            Platform.exit();
            System.exit(0);
        });

        popup.addSeparator();
        popup.addSeparator();
        popup.add(openItem);
        popup.addSeparator();
        popup.add(exitItem);
        popup.addSeparator();
        popup.addSeparator();

        // Tray icon setup
        trayIcon = new TrayIcon(trayImage, tooltip, popup);
        trayIcon.setImageAutoSize(true);

        // Left-click toggles
        ActionListener clickListener = e -> Platform.runLater(toggleStage);
        trayIcon.addActionListener(clickListener);

        try {
            SystemTray.getSystemTray().add(trayIcon);
        } catch (AWTException e) {
            System.out.println("Unable to add tray icon: " + e.getMessage());
        }
    }
    private void showStage(Stage stage) {
        if (stage != null) {
            stage.show();
            stage.toFront();
        }
    }

    public void hideToTray(Stage stage) {
        stage.setIconified(true); // minimize
        stage.setOpacity(0);      // invisible
        if (firstHide && trayIcon != null) {
            trayIcon.displayMessage(
                    "To-Do",
                    "To-Do is still running in the background.",
                    TrayIcon.MessageType.INFO
            );
            firstHide = false;
        }
    }


}


