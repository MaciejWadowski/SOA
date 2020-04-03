package pl.agh.kis.soa.model;

public enum BookType {
    FICTION("Fiction"), DRAMA("Drama"), ROMANCE("Romance"), LEGEND("Legend"), CRIMINAL("Criminal"), ACTION_AND_ADVENTURE("Action and adventure"), HUMOUR("Humour");
    private String label;

    BookType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
