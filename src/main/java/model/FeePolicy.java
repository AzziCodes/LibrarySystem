package model;

public class FeePolicy {
    // Currencies in GBP
    private final double feePerDay;
    private final double maxFee;
    private final int gracePeriodDays;

    public FeePolicy(double feePerDay, double maxFee, int gracePeriodDays) {
        this.feePerDay = feePerDay;
        this.maxFee = maxFee;
        this.gracePeriodDays = gracePeriodDays;
    }

    public double getFeePerDay() { return feePerDay; }
    public double getMaxFee() { return maxFee; }
    public int getGracePeriodDays() { return gracePeriodDays; }

    public double calculateFee(long daysLate) {
        if (daysLate > gracePeriodDays) {
            double fee = feePerDay * daysLate;
            return Math.min(fee, maxFee);
        } else {
            return 0.0;
        }
    }
}
