package application.classes;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * Classe pour gérer les AlertsBox
 */
public class MaterialDialog {

    private String title;
    private String header;
    private String content;
    private Alert.AlertType type;
    private StackPane parent;

    private static final Alert.AlertType DEFAULT_TYPE = Alert.AlertType.INFORMATION;

    public MaterialDialog(StackPane parent, String title, String header, String content, Alert.AlertType type) {
        this.parent = parent;
        this.title = title;
        this.header = header;
        this.content = content;
        this.type = type;
    }

    public MaterialDialog(StackPane parent, String title, String header, String content) {
        this(parent, title, header, content, DEFAULT_TYPE);
    }

    /**
     * Affiche l'alert box
     */
    public void show() {
        JFXDialogLayout alert = new JFXDialogLayout();
        alert.setHeading(new Label(title));
        alert.setBody(new Label(content));

        JFXDialog jfxDialog = new JFXDialog(parent, alert, JFXDialog.DialogTransition.CENTER);
        jfxDialog.show(parent);
    }
}
