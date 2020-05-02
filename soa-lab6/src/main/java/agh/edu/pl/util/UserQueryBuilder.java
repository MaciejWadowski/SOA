package agh.edu.pl.util;

import agh.edu.pl.model.Category;

import java.util.Date;
import java.util.List;

public class UserQueryBuilder {

    public Builder createQuery() {
        return new Builder();
    }

    public class Builder {
        private StringBuilder query;

        public Builder () {
            query = new StringBuilder();
            query.append("SELECT DISTINCT u FROM User u ");
        }

        public Builder complexQueryBuilder () {
            query.append("JOIN Borrow bb on bb.user = u JOIN Book b on bb.book = b WHERE 1=1");
            return this;
        }

        public Builder simpleQueryBuilder() {
            query.append("WHERE 1=1");
            return this;
        }

        public Builder applyAuthorFilter(QueryFilterType type, String input) {
            if (input != null && !input.isBlank()) {
                query.append(" AND b.author.name ").append(operatorConstruct(type, ":author", null));
            }
            return this;
        }
        public Builder applyCategoryFilter(List<Category> categoryList) {
            if (categoryList!= null && categoryList.size() > 0) {
                query.append(" AND b.category in :categories");
            }
            return this;
        }
        public Builder applyTitleFilter(QueryFilterType type, String input) {
            if (input != null && !input.isBlank()) {
                query.append(" AND b.name ").append(operatorConstruct(type, ":name", null));
            }
            return this;
        }
        public Builder applyIsbnFilter(QueryFilterType type, String input) {
            if (input != null&& !input.isBlank()) {
                query.append(" AND b.isbn ").append(operatorConstruct(type, ":isbn", null));
            }
            return this;
        }
        public Builder applyBorrowDateFilter(Date input1, Date input2) {
            if (input1 != null && input2 != null) {
                query.append(" AND bb.borrowDate ").append(operatorConstruct(QueryFilterType.BETWEEN, ":startDate", ":endDate"));
            } else if (input1 != null ) {
                query.append(" AND bb.borrowDate ").append(operatorConstruct(QueryFilterType.GREATER_EQUAL_THAN,":date", null));
            } else if (input2 != null) {
                query.append(" AND bb.borrowDate ").append(operatorConstruct(QueryFilterType.LOWER_EQUAL_THEN,":date", null));
            }
            return this;
        }
        public Builder applyUserFilter(QueryFilterType type, String input) {
            if (input != null && !input.isBlank()) {
                query.append(" AND u.name ").append(operatorConstruct(type, ":user", null));
            }
            return this;
        }
        public String build() {
            return query.toString();
        }
    }
    public String operatorConstruct(QueryFilterType type, String input1, String input2) {
        if (type == QueryFilterType.EQUAL) {
            return "=" + input1;
        } else if (type == QueryFilterType.STARTS_WITH) {
            return "LIKE CONCAT( " + input1 + ",'%')";
        } else if (type == QueryFilterType.CONTAINS) {
            return "LIKE CONCAT('%', " + input1 + ", '%')";
        } else if (type == QueryFilterType.GREATER_THAN) {
            return ">" + input1;
        } else if (type == QueryFilterType.GREATER_EQUAL_THAN) {
            return ">=" + input1;
        } else if (type == QueryFilterType.LOWER_THEN) {
            return "<" + input1;
        } else if (type == QueryFilterType.LOWER_EQUAL_THEN) {
            return "<=" + input1;
        } else if (type == QueryFilterType.BETWEEN) {
            return "BETWEEN " + input1 + " AND " + input2;
        }
        return "";
    }
}
