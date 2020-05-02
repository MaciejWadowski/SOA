package agh.edu.pl.util;

import java.util.List;

public enum QueryFilterType {
    EQUAL, LOWER_THEN, LOWER_EQUAL_THEN, GREATER_THAN, GREATER_EQUAL_THAN,
    STARTS_WITH, CONTAINS, BETWEEN;

    public List<QueryFilterType> getFilterTypesForString() {
        return List.of(EQUAL, STARTS_WITH, CONTAINS);
    }
}
