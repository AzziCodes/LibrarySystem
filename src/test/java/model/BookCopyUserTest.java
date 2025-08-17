package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.mindrot.jbcrypt.BCrypt;

class BookCopyUserTest {

    @Test
    void bookEqualityAndToString() {
        Book b1 = new Book("Dune", "Frank Herbert", "9780441172719", LocalDate.of(1965, 8, 1));
        Book b2 = new Book("Dune — different edition", "F. Herbert", "9780441172719", LocalDate.of(1965, 1, 1));

        // same ISBN => considered equal in our model
        assertEquals(b1, b2);
        assertEquals(b1.hashCode(), b2.hashCode());
        assertTrue(b1.toString().contains("ISBN"));
    }

    @Test
    void copyEqualityAndDefaults() {
        Book book = new Book("The Hobbit", "J.R.R. Tolkien", "9780261102217", LocalDate.of(1937, 9, 21));
        Copy c1 = new Copy("C-0001", book);
        Copy c2 = new Copy("C-0001", book);
        Copy c3 = new Copy("C-0002", book);

        // default condition and status
        assertEquals(CopyCondition.NEW, c1.getCondition());
        assertEquals(CopyStatus.AVAILABLE, c1.getStatus());

        // equality by copyId
        assertEquals(c1, c2);
        assertNotEquals(c1, c3);

        // toString contains the copyId
        assertTrue(c1.toString().contains("C-0001"));
    }

    @Test
    void userPasswordHashingAndVerification() {
        // use convenience constructor (plaintext password)
        User alice = new User("alice", "password123", "Alice", "Smith");
        assertTrue(alice.checkPassword("password123"));
        assertFalse(alice.checkPassword("badpassword"));

        // simulate loading from DB with pre-hashed password
        String hashed = BCrypt.hashpw("secret", BCrypt.gensalt());
        User bob = new User("bob", hashed, true, "Bob", "Lee"); // using isHashed=true constructor
        assertTrue(bob.checkPassword("secret"));
        assertFalse(bob.checkPassword("notsecret"));
    }
}
