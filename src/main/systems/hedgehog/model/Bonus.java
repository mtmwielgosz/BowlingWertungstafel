package systems.hedgehog.model;

public enum Bonus {
    STRIKE("X", Messages.STRIKE_HEADER, Messages.STRIKE),
    SPARE("/", Messages.SPARE_HEADER, Messages.SPARE),
    MISS("-", Messages.MISS_HEADER, Messages.MISS),
    BLANK(" ", Messages.BLANK, Messages.BLANK),
    ERROR("\\", Messages.ERROR_HEADER, Messages.ERROR);

    private final String sign;
    private final String messageHeader;
    private final String message;

    public String getSign() {
        return sign;
    }

    public String getMessageHeader() {
        return messageHeader;
    }

    public String getMessage() {
        return message;
    }

    Bonus(String sign, String messageHeader, String message) {
        this.sign = sign;
        this.messageHeader = messageHeader;
        this.message = message;
    }

}
