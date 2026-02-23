public class LoanCalculator {

    private double annualRate;
    private int years;
    private double loanAmount;

    public LoanCalculator(double annualRate, int years, double loanAmount) {
        this.annualRate = annualRate;
        this.years = years;
        this.loanAmount = loanAmount;
    }

    public double calculateMonthlyPayment() {
        double monthlyRate = annualRate / 1200.0;
        int n = years * 12;

        if (monthlyRate == 0) {
            return loanAmount / n;
        }

        return loanAmount * monthlyRate /
                (1 - Math.pow(1 + monthlyRate, -n));
    }

    public double calculateTotalPayment() {
        return calculateMonthlyPayment() * years * 12;
    }
}
