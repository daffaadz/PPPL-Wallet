import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WalletTest {

    private Wallet wallet;

    @BeforeAll
    static void initClass() {
        System.out.println("WalletTest Started");
    }

    @AfterAll
    static void cleanClass() {
        System.out.println("WalletTest Finished");
    }

    @BeforeEach
    void initMethod() {
        wallet = new Wallet("Daffa", 50000.0);
        System.out.println("[Setup] " + wallet);
    }

    @AfterEach
    void cleanMethod() {
        wallet = null;
    }

    @Test
    @Order(1)
    void testConstructorAndFields() {
        System.out.println("Testing Order 1");
        assertEquals("Daffa", wallet.getOwner(), "Owner name does not match");
        assertEquals(50000.0, wallet.getCash(), "Initial balance must be 50,000");
        assertNotNull(wallet.getCards(), "Card list cannot be null");
        assertTrue(wallet.getCards().isEmpty(), "Card list must be empty on init");
    }

    @Test
    @Order(2)
    void testDepositMoney() {
        System.out.println("Testing Order 2");
        wallet.deposit(25000.0);
        assertEquals(75000.0, wallet.getCash(), "Balance must be 75,000 after deposit");
    }

    @Test
    @Order(3)
    void testWithdrawMoneySuccess() {
        System.out.println("Testing Order 3");
        assertTrue(wallet.hasSufficientFunds(20000.0), "Should have sufficient funds");
        boolean success = wallet.withdraw(20000.0);
        assertTrue(success, "Withdraw must succeed");
        assertEquals(30000.0, wallet.getCash(), "Remaining balance must be 30,000");
    }

    @Test
    @Order(4)
    void testWithdrawMoneyInsufficientBalance() {
        System.out.println("Testing Order 4");
        assertFalse(wallet.hasSufficientFunds(100000.0), "Should not have sufficient funds");
        boolean success = wallet.withdraw(100000.0);
        assertFalse(success, "Withdraw must fail due to insufficient funds");
        assertEquals(50000.0, wallet.getCash(), "Balance must not change");
    }

    @Test
    @Order(5)
    void testAddAndRemoveCard() {
        System.out.println("Testing Order 5");
        wallet.addCard("BNI", "123456");
        assertEquals(1, wallet.getCards().size(), "Card list size must be 1");
        assertTrue(wallet.hasCard("BNI", "123456"), "Wallet must contain the added card");

        String removedCard = wallet.removeCard();
        assertEquals("BNI (123456)", removedCard, "Removed card must match");
        assertTrue(wallet.getCards().isEmpty(), "Card list must be empty after removal");
    }

    @Test
    @Order(6)
    void testToString() {
        System.out.println("Testing Order 6");
        assertEquals("Wallet[owner=Daffa, cash=50000.00, cards=0]", wallet.toString());
        wallet.addCard("BCA", "789");
        assertEquals("Wallet[owner=Daffa, cash=50000.00, cards=1]", wallet.toString());
    }
}