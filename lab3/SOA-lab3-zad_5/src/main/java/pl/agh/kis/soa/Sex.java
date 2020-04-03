package pl.agh.kis.soa;

public enum Sex {
    MALE(1,"Mężczyzna"), FEMALE(2,"kobieta");
    private String label;
    private Integer value;

    Sex(Integer value, String label) {
        this.label = label;
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
