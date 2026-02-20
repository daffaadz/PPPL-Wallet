import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WalletTest {

    private Wallet wallet;

    @BeforeAll
    static void initClass() {
        System.out.println("--- Memulai WalletTest ---");
    }

    @BeforeEach
    void initMethod() {
        System.out.println("Membuat Objek Wallet...");
        wallet = new Wallet("Daffa", 50000.0);
    }

    @AfterEach
    void cleanMethod() {
        System.out.println("Membersihkan objek setelah test...");
        wallet = null;
    }

    @AfterAll
    static void cleanClass() {
        System.out.println("--- WalletTest Selesai ---");
    }

    @Test
    @Order(1)
    void testConstructorAndFields() {
        assertEquals("Daffa", wallet.getOwner(), "Owner name do not matched");
        assertEquals(50000.0, wallet.getCash(), "Saldo must be 50.000");
        assertNotNull(wallet.getCards(), "Card list cannot be null");
    }

    @Test
    @Order(2)
    void testDepositMoney() {
        wallet.deposit(25000.0);
        assertEquals(75000.0, wallet.getCash(), "Saldo must be 75.000 after deposit");
    }

    @Test
    @Order(3)
    void testWithdrawMoneySuccess() {
        boolean success = wallet.withdraw(20000.0);
        assertTrue(success, "Withdraw must be success or true");
        assertEquals(30000.0, wallet.getCash(), "Left over saldo must be 30.000");
    }

    @Test
    @Order(4)
    void testWithdrawMoneyInsufficientBalance() {
        boolean success = wallet.withdraw(100000.0);
        assertFalse(success, "Withdraw must be failed");
        assertEquals(50000.0, wallet.getCash(), "Saldo must not change");
    }

    @Test
    @Order(5)
    void testAddAndRemoveCard() {
        wallet.addCard("BNI", "123456");
        assertEquals(1, wallet.getCards().size(), "Card length must be 1");

        String removedCard = wallet.removeCard();
        assertEquals("BNI (123456)", removedCard, "Deleted card must be the same");
        assertTrue(wallet.getCards().isEmpty(), "Card list must be empty");
    }
}