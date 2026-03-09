import java.util.ArrayList;
import java.util.List;

public class Wallet {
    private String owner;
    private Owner ownerObject;
    private final List<String> cards;
    private double cash;

    public Wallet(String owner, double initialCash) {
        this.owner = owner;
        this.cash = initialCash;
        this.cards = new ArrayList<>();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setOwner(Owner owner) {
        this.ownerObject = owner;
    }

    public Owner getOwnerObject() {
        return ownerObject;
    }

    public List<String> getCards() {
        return cards;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive, got: " + amount);
        }
        this.cash += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdraw amount must be positive, got: " + amount);
        }
        if (amount > this.cash) {
            throw new InsufficientFundsException(
                    "Insufficient funds: balance is " + this.cash + ", requested " + amount);
        }
        this.cash -= amount;
    }

    public void addCard(String bankName, String accountNumber) {
        String cardInfo = bankName + " (" + accountNumber + ")";
        this.cards.add(cardInfo);
    }

    public String removeCard() {
        if (!cards.isEmpty()) {
            return cards.removeLast();
        }
        return null;
    }

    public boolean hasCard(String bankName, String accountNumber) {
        return cards.contains(bankName + " (" + accountNumber + ")");
    }

    public boolean hasEnoughFunds(double amount) {
        return this.cash >= amount;
    }

    @Override
    public String toString() {
        return String.format("Wallet[owner=%s, cash=%.2f, cards=%d]", owner, cash, cards.size());
    }
}