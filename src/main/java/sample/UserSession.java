package sample;

public class UserSession {
    private static UserSession instance;

    private String name;
    private String id;
    private String role;  // Back to single role
    private String department;

    private UserSession() {
        // Private constructor for singleton
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setUserData(String name, String id, String role, String department) {
        this.name = name;
        this.id = id;
        this.role = role;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getDepartment() {
        return department;
    }

    public void clear() {
        this.name = null;
        this.id = null;
        this.role = null;
        this.department = null;
    }
}