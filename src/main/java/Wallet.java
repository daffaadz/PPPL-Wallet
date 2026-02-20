import java.util.ArrayList;
import java.util.List;

public class Wallet {
    private String owner;
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
        if (amount > 0) {
            this.cash += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > this.cash) {
            System.out.println("Not enough money woe!!");
            return false;
        }
        this.cash -= amount;
        return true;
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
}