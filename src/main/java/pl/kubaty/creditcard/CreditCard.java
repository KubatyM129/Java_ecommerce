package pl.kubaty.creditcard;

import java.math.BigDecimal;

public class CreditCard {
    private BigDecimal creditLimit;
    private BigDecimal balance;

    private boolean isCreditCardAlreadyAssigned() {
        return creditLimit != null;
    }

    private boolean isCreditBelowThreshold(BigDecimal creditLimit) {
        return creditLimit.compareTo(BigDecimal.valueOf(100)) < 0;
    }

    private boolean isWithdrawOverLimit(BigDecimal value) {
        return balance.compareTo(value) < 0;
    }

    public void assignCredit(BigDecimal creditLimit) {
        if(isCreditCardAlreadyAssigned()) {
            throw new ReassignLimitException();
        }

        if(isCreditBelowThreshold(creditLimit)) {
            throw new CreditBelowThresholdException();
        }

        this.creditLimit = creditLimit;
        this.balance = creditLimit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void withdraw(BigDecimal value) {
        if(isWithdrawOverLimit(value)) {
            throw new WithdrawOverLimitException();
        }
        balance = balance.subtract(value);
    }
}
