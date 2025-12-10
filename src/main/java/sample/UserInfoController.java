package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserInfoController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField idField;

    @FXML
    private RadioButton studentRadioButton;

    @FXML
    private RadioButton lecturerRadioButton;

    @FXML
    private RadioButton staffRadioButton;

    @FXML
    private RadioButton facultyRadioButton;

    @FXML
    private RadioButton administratorRadioButton;

    @FXML
    private ComboBox<String> departmentCombo;

    private ToggleGroup roleToggleGroup;

    @FXML
    public void initialize() {
        // Create and set up ToggleGroup
        roleToggleGroup = new ToggleGroup();

        // Set toggle group for each radio button
        studentRadioButton.setToggleGroup(roleToggleGroup);
        studentRadioButton.setUserData("Student");

        lecturerRadioButton.setToggleGroup(roleToggleGroup);
        lecturerRadioButton.setUserData("Lecturer");

        staffRadioButton.setToggleGroup(roleToggleGroup);
        staffRadioButton.setUserData("Staff");

        facultyRadioButton.setToggleGroup(roleToggleGroup);
        facultyRadioButton.setUserData("Faculty");

        administratorRadioButton.setToggleGroup(roleToggleGroup);
        administratorRadioButton.setUserData("Administrator");

        // Initialize department options
        ObservableList<String> departments = FXCollections.observableArrayList(
                "Computer Science",
                "Chemical Engineering",
                "Mechanical Engineering",
                "Information System",
                "Civil Engineering",
                "Electromechanical Engineering",
                "Textile Engineering",
                "Garment Engineering",
                "Information Technology",
                "Biomedical Engineering",
                "Agricultural Engineering ",
                "Water supply and environmental Engineering",
                "Department of Water resources and Irrigation Engineering",
                "Hydraulic and water resources Engineering",
                "Electrical and Computer Engineering",
                "Urban planning and design",
                "Construction technology and management",
                "Industrial Engineering",
                "None of them"
        );
        departmentCombo.setItems(departments);
    }

    @FXML
    public void handleContinue() {
        // Get user input
        String name = nameField.getText().trim();
        String id = idField.getText().trim();
        RadioButton selectedRole = (RadioButton) roleToggleGroup.getSelectedToggle();
        String department = departmentCombo.getValue();

        // Validate input
        if (name.isEmpty() || id.isEmpty() || selectedRole == null || department == null) {
            showAlert("Error", "Please fill in all fields and select a role!");
            return;
        }

        String role = selectedRole.getUserData().toString();

        // Store user data in a shared class
        UserSession.getInstance().setUserData(name, id, role, department);

        // Load the next page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ServiceRequest.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(new Scene(root, 650, 550));
            stage.setTitle("Service Request Form");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load service request page!");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}