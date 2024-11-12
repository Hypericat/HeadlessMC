package ux;

public enum OptionTypes {
    DEV(new Option<>("Dev", false)),
    PORT(new Option<>("Port", 25565)),
    PREMIUM(new Option<>("Premium", false)),
    BOT_COUNT(new Option<>("BotCount", 1)),
    VERSION_ID(new Option<>("VersionID", "1.21.1")),
    PROTOCOL_ID(new Option<>("ProtocolID", 767)),
    ACCOUNT_NAME(new Option<>("FreeAccountName", "Bot")),
    SERVER_ADDRESS(new Option<>("ServerAddress", "127.0.0.1")),
    RENDER_DISTANCE(new Option<>("RenderDistance", 5));


    private final Option<?> option;

    <T> OptionTypes(Option<T> option) {
        this.option = option;
    }

    public <T> Option<T> get() {
        //probably fine?
        return new Option<>((Option<T>) this.option);
    }

}
