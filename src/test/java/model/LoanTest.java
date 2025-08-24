package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class LoanTest {
    @Test
    void testLoanCreationDefaults() {
        // Create a test user account and book
        Book b1 = new Book("Dune", "Frank Herbert", "9780441172719", LocalDate.of(1965, 8, 1));
        Book b2 = new Book("Dune — different edition", "F. Herbert", "9780441172719", LocalDate.of(1965, 1, 1));
        User newUser = new User("Tester", "abc123", "Test", "Ting");
        Copy c1 = new Copy("A123", b1);
        Copy c2 = new Copy ("A124", b1);
        Loan l1 = new Loan("L123", LocalDate.now().plusYears(1), newUser, c1);
        assertSame(LoanStatus.ACTIVE, l1.getLoanStatus());
        l1.checkLate();
        LocalDate now = LocalDate.now();
        assertEquals(LocalDate.now(), l1.getTakeoutDate());
        assertEquals(0.0, l1.getLateFee());
    }

    @Test
    void testCheckLate_NotLate() {
        Book b1 = new Book("Dune", "Frank Herbert", "9780441172719", LocalDate.of(1965, 8, 1));
        User newUser = new User("Tester", "abc123", "Test", "Ting");
        Copy c1 = new Copy("A123", b1);
        Loan l1 = new Loan("L123", LocalDate.now().plusDays(1), newUser, c1);
        assertFalse(l1.checkLate());
        assertSame(LoanStatus.ACTIVE, l1.getLoanStatus());
        assertEquals(0.0, l1.getLateFee());
    }

    @Test
    void testCheckLate_LateWithinGracePeriod() {
        Book b1 = new Book("Dune", "Frank Herbert", "9780441172719", LocalDate.of(1965, 8, 1));
        User newUser = new User("Tester", "abc123", "Test", "Ting");
        Copy c1 = new Copy("A123", b1);
        Loan l1 = new Loan("L123", LocalDate.now().minusDays(1), newUser, c1);
        assertTrue(l1.checkLate());
        assertSame(LoanStatus.OVERDUE, l1.getLoanStatus());
        assertEquals(0.0, l1.getLateFee());
    }

    @Test
    void testCheckLate_LateBeyondGracePeriod() {
        Book b1 = new Book("Dune", "Frank Herbert", "9780441172719", LocalDate.of(1965, 8, 1));
        User newUser = new User("Tester", "abc123", "Test", "Ting");
        Copy c1 = new Copy("A123", b1);
        Loan l1 = new Loan("L123", LocalDate.now().minusDays(5), newUser, c1);
        assertTrue(l1.checkLate());
        assertSame(LoanStatus.OVERDUE, l1.getLoanStatus());
        assertEquals(l1.getLateFeePerDay() * l1.daysLate(), l1.getLateFee());
    }

    @Test
    void testReturnBook_NotLate() {
        Book b1 = new Book("Dune", "Frank Herbert", "9780441172719", LocalDate.of(1965, 8, 1));
        User newUser = new User("Tester", "abc123", "Test", "Ting");
        Copy c1 = new Copy("A123", b1);
        Loan l1 = new Loan("L123", LocalDate.now().plusDays(5), newUser, c1);
        assertEquals(0.0, l1.returnBook());
        assertSame(LoanStatus.RETURNED, l1.getLoanStatus());
        assertSame(CopyStatus.AVAILABLE, c1.getStatus());
    }

    @Test
    void testReturnBook_LateBeyondGracePeriod() {
        Book b1 = new Book("Dune", "Frank Herbert", "9780441172719", LocalDate.of(1965, 8, 1));
        User newUser = new User("Tester", "abc123", "Test", "Ting");
        Copy c1 = new Copy("A123", b1);
        Loan l1 = new Loan("L123", LocalDate.now().minusDays(5), newUser, c1);
        double expectedFee = l1.getLateFeePerDay() * l1.daysLate();
        assertEquals(expectedFee, l1.returnBook());
        assertSame(LoanStatus.RETURNED, l1.getLoanStatus());
        assertSame(CopyStatus.AVAILABLE, c1.getStatus());
        assertEquals(expectedFee, newUser.getOutstandingFees());
    }



}
