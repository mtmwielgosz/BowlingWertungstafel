package systems.hedgehog.model;

public enum Bonus {
    STRIKE("X"), SPARE("/"), MISS("-");

    private String sign;

    public String getSign() {
        return sign;
    }

    Bonus(String sign) {
        this.sign = sign;
    }
}
