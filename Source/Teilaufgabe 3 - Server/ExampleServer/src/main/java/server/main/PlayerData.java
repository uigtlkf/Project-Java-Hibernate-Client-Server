package server.main;

import java.util.Objects;

public class PlayerData {
    private final String firstName;
    private final String lastName;
    private final String uAccount;

    public PlayerData(String firstName, String lastName, String uAccount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.uAccount = uAccount;
    }

    public PlayerData() {
        this.firstName = "";
        this.lastName = "";
        this.uAccount = "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerData that = (PlayerData) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(uAccount, that.uAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, uAccount);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUAccount() {
        return uAccount;
    }
}
