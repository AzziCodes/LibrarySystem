package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan {
    private final String loanID; //TODO: Automate loanID assignment
    private final LocalDate takeoutDate;
    private final LocalDate dueDate;
    private final User customer;
    private double lateFee;
    private LoanStatus loanStatus;
    private final Copy copy;
    private final double lateFeePerDay = 0.5;
    private final int maxFee = 10;
    private final int gracePeriodDays = 2;

    public Loan(String loanID, LocalDate dueDate, User customer, Copy copy) {
        this.loanID = loanID;
        this.takeoutDate = LocalDate.now();
        this.dueDate = dueDate;
        this.customer = customer;
        this.copy = copy;
        this.lateFee = 0; // Default
        this.loanStatus = LoanStatus.ACTIVE; // Default
        copy.setStatus(CopyStatus.CHECKED_OUT);
    }

    public String getLoanID() { return loanID; }
    public LocalDate getTakeoutDate() { return takeoutDate; }
    public LocalDate getDueDate() { return dueDate; }
    public User getCustomer() { return customer; }
    public LoanStatus getLoanStatus() { return loanStatus; }
    public Copy getCopy() { return copy; }
    public double getLateFeePerDay() { return lateFeePerDay; }
    public int getMaxFee() { return maxFee; }
    public int getGracePeriodDays() { return gracePeriodDays; }

    public double getLateFee() { return lateFee; }

    public boolean checkLate() {
        if (dueDate.isBefore(LocalDate.now())) {
            loanStatus = LoanStatus.OVERDUE;
            FeePolicy feePolicy = new FeePolicy(lateFeePerDay, maxFee, gracePeriodDays);
            lateFee = feePolicy.calculateFee(daysLate());
            return true;
        }
        return false;
    }

    public long daysLate() {
        if (dueDate.isAfter(LocalDate.now())) {
            return 0;
        } else {
            return ChronoUnit.DAYS.between(dueDate, LocalDate.now());
        }
    }

    public double returnBook() {
        if (!checkLate()) {
            loanStatus = LoanStatus.RETURNED;
            getCopy().setStatus(CopyStatus.AVAILABLE);
            return 0.0;
        } else {
            loanStatus = LoanStatus.RETURNED;
            getCustomer().addFee(lateFee);
            getCopy().setStatus(CopyStatus.AVAILABLE);
            return lateFee;
        }
    }
}
