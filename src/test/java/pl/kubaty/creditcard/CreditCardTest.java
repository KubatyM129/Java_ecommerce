package pl.kubaty.creditcard;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class CreditCardTest {

    @Test
    void itAllowsAssignCreditLimit() {
        // Arrange
        CreditCard card = new CreditCard();
        // Act
        card.assignCredit(BigDecimal.valueOf(1000));
        // Assert
        assertEquals(
                BigDecimal.valueOf(1000),
                card.getBalance());
    }

    @Test
    void itDenyCreditLimitBellowThreshold() {
        CreditCard card = new CreditCard();

        assertThrows(
                CreditBelowThresholdException.class,
                () -> card.assignCredit(BigDecimal.valueOf(50)));
    }
    @Test
    void itCantAssignLimitTwice() {
        CreditCard card = new CreditCard();

        card.assignCredit(BigDecimal.valueOf(100));

        assertThrows(ReassignLimitException.class,
                () -> card.assignCredit(BigDecimal.valueOf(100)));
    }

    @Test
    void itAllowsToWithdraw() {
        CreditCard card = new CreditCard();
        card.assignCredit(BigDecimal.valueOf(1000));
        card.withdraw(BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(900),card.getBalance());
    }

    @Test
    void itCantWithdrawOverTheLimit() {
        CreditCard card = new CreditCard();

        card.assignCredit(BigDecimal.valueOf(1000));

        assertThrows(WithdrawOverLimitException.class,
                () -> card.withdraw(BigDecimal.valueOf(1001)));

    }



}
