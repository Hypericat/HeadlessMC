package auth;

import java.util.Optional;
import java.util.UUID;

public class Account {
    private final String name;
    private final String sessionID;
    private UUID uuid;

    public Account(Optional<String> name, String sessionID, UUID uuid) {
        this.name = name.orElse("Bot");
        this.sessionID = sessionID;
        this.uuid = uuid;
    }

    public Account(String name) {
        this(Optional.of(name), null, null);
    }

    public Account() {
        this(Optional.empty(), null, null);
    }


    public String getSessionID() {
        return sessionID;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String toStringCensored() {
        return "{Name : " + name + ", UUID : " + uuid + ", Premium : " + isPremium() + "}";
    }
    public String toString() {
        return "{Name : " + name + ", UUID : " + uuid + ", Premium : " + isPremium() + ", Session ID : " + sessionID + "}";
    }

    public boolean isPremium() {
        return this.sessionID != null;
    }
}
