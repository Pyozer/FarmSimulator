package application.classes;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

/**
 * Classe pour gérer les AlertsBox
 */
public class AlertDialog {

    private String title;
    private String header;
    private String content;
    private Alert.AlertType type;
    private static final Alert.AlertType DEFAULT_TYPE = Alert.AlertType.INFORMATION;

    public AlertDialog(String title, String header, String content, Alert.AlertType type) {
        this.title = title;
        this.header = header;
        this.content = content;
        this.type = type;
    }

    public AlertDialog(String title, String header, String content) {
        this(title, header, content, DEFAULT_TYPE);
    }

    /**
     * Affiche l'alert box
     */
    public void show() {
        Alert alert = new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
}
