import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

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
        wallet.withdraw(20000.0);
        assertEquals(30000.0, wallet.getCash(), "Remaining balance must be 30,000");
        System.out.println("Testing Order 3 Done");
    }

    @Test
    @Order(4)
    void testWithdrawMoneyMoreThanBalance() {
        assertFalse(wallet.hasEnoughFunds(100000.0), "Should not have enough funds");
        assertThrows(InsufficientFundsException.class, () -> wallet.withdraw(100000.0),
                "Withdraw must throw InsufficientFundsException");
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

    @ParameterizedTest(name = "deposit valid amount: {0}")
    @ValueSource(doubles = {1000.0, 5000.0, 10000.0, 25000.0, 100000.0})
    @Order(6)
    void testDepositValidAmount(double amount) {
        double before = wallet.getCash();
        wallet.deposit(amount);
        assertEquals(before + amount, wallet.getCash(),
                "Balance must increase by deposited amount");
    }

    @ParameterizedTest(name = "deposit invalid amount: {0}")
    @ValueSource(doubles = {-1000.0, -5000.0, -0.01, -100000.0})
    @Order(7)
    void testDepositInvalidAmount(double amount) {
        assertThrows(IllegalArgumentException.class, () -> wallet.deposit(amount),
                "Deposit with negative amount must throw IllegalArgumentException");
    }

    @ParameterizedTest(name = "valid withdraw – deposit={0}, withdraw={1}, expected={2}")
    @CsvFileSource(resources = "/valid_withdraw.csv", numLinesToSkip = 1)
    @Order(8)
    void testWithdrawValidData(double deposit, double withdraw, double expectedTotal) {
        Wallet testWallet = new Wallet("Test", 0);
        testWallet.deposit(deposit);
        if (withdraw > 0) {
            testWallet.withdraw(withdraw);
        }
        assertEquals(expectedTotal, testWallet.getCash(),
                "Cash after deposit/withdraw must equal expectedTotal");
    }

    @ParameterizedTest(name = "invalid withdraw – initialDeposit={0}, withdraw={1}, exception={2}")
    @CsvFileSource(resources = "/invalid_withdraw.csv", numLinesToSkip = 1)
    @Order(9)
    void testWithdrawInvalidData(double initialDeposit, double withdraw, String exceptionType) {
        Wallet testWallet = new Wallet("Test", initialDeposit);
        if ("InsufficientFundsException".equals(exceptionType)) {
            assertThrows(InsufficientFundsException.class, () -> testWallet.withdraw(withdraw),
                    "Expected InsufficientFundsException");
        } else {
            assertThrows(IllegalArgumentException.class, () -> testWallet.withdraw(withdraw),
                    "Expected IllegalArgumentException");
        }
    }


    static Stream<Arguments> ownerProvider() {
        return Stream.of(
                Arguments.of(new Owner("O001", "Daffa", "daffa@example.com")),
                Arguments.of(new Owner("O002", "Zhafir", "zhafir@example.com")),
                Arguments.of(new Owner("O003", "Vidky", "vidky@example.com"))
        );
    }

    @ParameterizedTest(name = "setOwner – {0}")
    @MethodSource("ownerProvider")
    @Order(10)
    void testSetOwner(Owner owner) {
        wallet.setOwner(owner);
        Owner stored = wallet.getOwnerObject();
        assertNotNull(stored, "Stored owner must not be null after setOwner");
        assertEquals(owner.getId(), stored.getId(), "Owner id must match");
        assertEquals(owner.getName(), stored.getName(), "Owner name must match");
        assertEquals(owner.getEmail(), stored.getEmail(), "Owner email must match");
    }
}