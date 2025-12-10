package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ServiceRequestController {

    @FXML private Label displayName;
    @FXML private Label displayId;
    @FXML private Label displayRole;
    @FXML private Label displayDepartment;
    @FXML private ComboBox<String> serviceCategoryCombo;
    @FXML private TextArea descriptionArea;
    @FXML private RadioButton lowRadioButton;
    @FXML private RadioButton mediumRadioButton;
    @FXML private RadioButton highRadioButton;
    @FXML private RadioButton criticalRadioButton;

    private ToggleGroup urgencyToggleGroup;

    @FXML
    public void initialize() {
        // Setup urgency toggle group
        urgencyToggleGroup = new ToggleGroup();
        lowRadioButton.setToggleGroup(urgencyToggleGroup);
        lowRadioButton.setUserData("Low");
        mediumRadioButton.setToggleGroup(urgencyToggleGroup);
        mediumRadioButton.setUserData("Medium");
        highRadioButton.setToggleGroup(urgencyToggleGroup);
        highRadioButton.setUserData("High");
        highRadioButton.setSelected(true);
        criticalRadioButton.setToggleGroup(urgencyToggleGroup);
        criticalRadioButton.setUserData("Critical");

        // Display user info
        UserSession session = UserSession.getInstance();
        displayName.setText(session.getName());
        displayId.setText(session.getId());
        displayRole.setText(session.getRole());
        displayDepartment.setText(session.getDepartment());

        // Setup service categories
        ObservableList<String> categories = FXCollections.observableArrayList(
                "IT Support (Computer, Network, Software)",
                "Facilities Maintenance (Repairs, Cleaning)",
                "Academic Support (Advising, Course Issues)",
                "Library Services (Books, Research Help)",
                "Student Services (Counseling, Accommodations)",
                "Administrative Services (Documents, Forms)",
                "Financial Services (Tuition, Fees, Payments)",
                "Campus Security & Safety",
                "Health Services (Medical, Counseling)",
                "Catering & Food Services",
                "Other General Inquiry"
        );
        serviceCategoryCombo.setItems(categories);
    }

    @FXML
    public void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/UserInfo.fxml"));
            Stage stage = (Stage) displayName.getScene().getWindow();
            stage.setScene(new Scene(root, 550, 550));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSubmit() {
        // Validate inputs
        String category = serviceCategoryCombo.getValue();
        String description = descriptionArea.getText().trim();
        RadioButton selected = (RadioButton) urgencyToggleGroup.getSelectedToggle();

        if (category == null || description.isEmpty() || selected == null) {
            showAlert("Error", "Please fill in all fields!");
            return;
        }

        String urgency = selected.getUserData().toString();
        UserSession session = UserSession.getInstance();
        String requestId = generateRequestId(session.getId());

        // Save to file
        RequestStorage.saveRequestToFile(
                requestId,
                session.getName(),
                session.getId(),
                session.getRole(),
                session.getDepartment(),
                category,
                description,
                urgency
        );

        // Show success
        showSuccessAlert(requestId);

        // Clear form
        serviceCategoryCombo.setValue(null);
        descriptionArea.clear();
    }

    private String generateRequestId(String userId) {
        String time = String.valueOf(System.currentTimeMillis());
        return "SR-" + userId + "-" + time.substring(time.length() - 6);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String requestId) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Request Submitted");
        alert.setContentText("Request ID: " + requestId + "\nSaved to: requests/" + requestId + ".txt");
        alert.showAndWait();
    }
}