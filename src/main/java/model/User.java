package model;

import org.mindrot.jbcrypt.BCrypt;
import java.util.Objects;

public class User {
    private String username;
    private String passwordHash;
    private final String firstName;
    private final String lastName;
    private UserRole role;
    private String email;

    public User(String username, String passwordOrHash, boolean isHashed, String firstName, String lastName) {
        this.username = username;
        this.passwordHash = isHashed ? passwordOrHash : BCrypt.hashpw(passwordOrHash, BCrypt.gensalt());
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = UserRole.MEMBER;
    }

    // convenience constructor
    public User(String username, String plainPassword, String firstName, String lastName) {
        this(username, plainPassword, false, firstName, lastName);
    }

    public String getUsername() { return username; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public UserRole getRole() { return role; }
    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) {
        this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
    }
    public void setUsername(String username) { this.username = username; }
    public void setRole(UserRole role) { this.role = role; }

    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, passwordHash);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return String.format("User[%s %s, username=%s, role=%s, email=%s]",
                firstName, lastName, username, role, email);
    }
}
