package pl.agh.kis.soa.model;

public enum Currency {
    PLN(1.0), EUR(4.39), USD(3.94);
    private double average;

    Currency(double average) {
        this.average = average;
    }

    public double getAverage() {
        return average;
    }
}
