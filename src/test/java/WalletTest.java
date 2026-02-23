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
        assertEquals("Daffa", wallet.getOwner(), "Owner name does not match");
        assertEquals(50000.0, wallet.getCash(), "Initial balance must be 50,000");
        assertNotNull(wallet.getCards(), "Card list cannot be null");
        assertTrue(wallet.getCards().isEmpty(), "Card list must be empty on init");
        System.out.println("Testing Order 1 Done");
    }

    @Test
    @Order(2)
    void testDepositMoney() {
        wallet.deposit(25000.0);
        assertEquals(75000.0, wallet.getCash(), "Balance must be 75,000 after deposit");
        System.out.println("Testing Order 2 Done");
    }

    @Test
    @Order(3)
    void testWithdrawMoneySuccess() {
        assertTrue(wallet.hasEnoughFunds(20000.0), "Should have enough funds");
        boolean success = wallet.withdraw(20000.0);
        assertTrue(success, "Withdraw must succeed");
        assertEquals(30000.0, wallet.getCash(), "Remaining balance must be 30,000");
        System.out.println("Testing Order 3 Done");
    }

    @Test
    @Order(4)
    void testWithdrawMoneyMoreThanBalance() {
        assertFalse(wallet.hasEnoughFunds(100000.0), "Should not have enough funds");
        boolean success = wallet.withdraw(100000.0);
        assertFalse(success, "Withdraw must fail due to not enough funds");
        assertEquals(50000.0, wallet.getCash(), "Balance must not change");
        System.out.println("Testing Order 4 Done");
    }

    @Test
    @Order(5)
    void testAddAndRemoveCard() {
        wallet.addCard("BNI", "123456");
        assertEquals(1, wallet.getCards().size(), "Card list size must be 1");
        assertTrue(wallet.hasCard("BNI", "123456"), "Wallet must contain the added card");

        String removedCard = wallet.removeCard();
        assertEquals("BNI (123456)", removedCard, "Removed card must match");
        assertTrue(wallet.getCards().isEmpty(), "Card list must be empty after removal");
        System.out.println("Testing Order 5 Done");
    }
}