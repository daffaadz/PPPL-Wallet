import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WalletTest {
    private Wallet wallet;

    @BeforeEach
    void setUp() {
        wallet = new Wallet("Daffa", 50000.0);
    }

    @Test
    void testConstructorAndFields() {
        assertEquals("Daffa", wallet.getOwner(), "Owner name do not matched");
        assertEquals(50000.0, wallet.getCash(), "Saldo must be 50.000");
        assertNotNull(wallet.getCards(), "Card list cannot be null");
    }

    @Test
    void testDepositMoney() {
        wallet.deposit(25000.0);
        assertEquals(75000.0, wallet.getCash(), "Saldo must be 75.000 after deposit");
    }

    @Test
    void testWithdrawMoneySuccess() {
        boolean success = wallet.withdraw(20000.0);
        assertTrue(success, "Withdraw must be success or true");
        assertEquals(30000.0, wallet.getCash(), "Left over saldo must be 30.000");
    }

    @Test
    void testWithdrawMoneyInsufficientBalance() {
        // Test withdraw more than saldo
        boolean success = wallet.withdraw(100000.0);
        assertFalse(success, "Withdraw must be failed");
        assertEquals(50000.0, wallet.getCash(), "Saldo must not change");
    }

    @Test
    void testAddAndRemoveCard() {
        wallet.addCard("BNI", "123456");
        assertEquals(1, wallet.getCards().size(), "Card length must be 1");

        String removedCard = wallet.removeCard();
        assertEquals("BNI (123456)", removedCard, "Deleted card must be the same");
        assertTrue(wallet.getCards().isEmpty(), "Card list must be empty");
    }
}