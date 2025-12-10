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

    @FXML
    private Label displayName;

    @FXML
    private Label displayId;

    @FXML
    private Label displayRole;

    @FXML
    private Label displayDepartment;

    @FXML
    private ComboBox<String> serviceCategoryCombo;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private RadioButton lowRadioButton;

    @FXML
    private RadioButton mediumRadioButton;

    @FXML
    private RadioButton highRadioButton;

    @FXML
    private RadioButton criticalRadioButton;

    private ToggleGroup urgencyToggleGroup;  // Manually created ToggleGroup

    @FXML
    public void initialize() {
        // Create and set up ToggleGroup for urgency
        urgencyToggleGroup = new ToggleGroup();

        // Set toggle group for each radio button
        lowRadioButton.setToggleGroup(urgencyToggleGroup);
        lowRadioButton.setUserData("Low");

        mediumRadioButton.setToggleGroup(urgencyToggleGroup);
        mediumRadioButton.setUserData("Medium");

        highRadioButton.setToggleGroup(urgencyToggleGroup);
        highRadioButton.setUserData("High");
        highRadioButton.setSelected(true);  // Set High as default

        criticalRadioButton.setToggleGroup(urgencyToggleGroup);
        criticalRadioButton.setUserData("Critical");

        // Display user information from session
        UserSession session = UserSession.getInstance();
        displayName.setText(session.getName());
        displayId.setText(session.getId());
        displayRole.setText(session.getRole());
        displayDepartment.setText(session.getDepartment());

        // Initialize service categories
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInfo.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) displayName.getScene().getWindow();
            stage.setScene(new Scene(root, 550, 550));
            stage.setTitle("User Information");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSubmit() {
        // Get service request details
        String category = serviceCategoryCombo.getValue();
        String description = descriptionArea.getText().trim();
        RadioButton selectedUrgency = (RadioButton) urgencyToggleGroup.getSelectedToggle();

        // Validate input
        if (category == null || description.isEmpty() || selectedUrgency == null) {
            showAlert("Error", "Please fill in all service request fields!");
            return;
        }

        String urgency = selectedUrgency.getUserData().toString();

        // Get user data from session
        UserSession session = UserSession.getInstance();

        // Generate request ID
        String requestId = generateRequestId(session.getId());

        // Display confirmation
        System.out.println("\n" + "=".repeat(50));
        System.out.println("SERVICE REQUEST CONFIRMATION");
        System.out.println("=".repeat(50));
        System.out.println("Request ID: " + requestId);
        System.out.println("User: " + session.getName() + " (" + session.getId() + ")");
        System.out.println("Role: " + session.getRole());
        System.out.println("Department: " + session.getDepartment());
        System.out.println("Service Category: " + category);
        System.out.println("Urgency Level: " + urgency);
        System.out.println("Description: " + description);
        System.out.println("Submission Time: " + java.time.LocalDateTime.now());
        System.out.println("=".repeat(50) + "\n");

        // Show success message
        showSuccessAlert(requestId);

        // Clear form (optional)
        serviceCategoryCombo.setValue(null);
        descriptionArea.clear();
    }

    private String generateRequestId(String userId) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        return "SR-" + userId + "-" + timestamp.substring(timestamp.length() - 6);
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
        alert.setTitle("Request Submitted Successfully!");
        alert.setHeaderText("Your service request has been submitted.");
        alert.setContentText("Request ID: " + requestId + "\n\nThank you for helping us make a good environment!");
        alert.showAndWait();
    }
}