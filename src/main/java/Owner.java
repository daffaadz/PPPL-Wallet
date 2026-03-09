import java.util.Objects;

public class Owner {
    private final String id;
    private final String name;
    private final String email;

    public Owner(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Owner other)) return false;
        return Objects.equals(id, other.id)
                && Objects.equals(name, other.name)
                && Objects.equals(email, other.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }

    @Override
    public String toString() {
        return String.format("Owner[id=%s, name=%s, email=%s]", id, name, email);
    }
}
