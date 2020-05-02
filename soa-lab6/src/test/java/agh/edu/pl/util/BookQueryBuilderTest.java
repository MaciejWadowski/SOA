package agh.edu.pl.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BookQueryBuilderTest {

    BookQueryBuilder queryBuilder;
    String title;
    String isbn;

    @BeforeEach
    void setUp() {
        queryBuilder = new BookQueryBuilder();
        title = "xd";
        isbn = "lol";
    }

    @Test
    public void shouldInjectOnlyTitle() {
        String query = queryBuilder.createQuery()
                .applyAuthorFilter(QueryFilterType.STARTS_WITH, null)
                .applyTitleFilter(QueryFilterType.EQUAL, title)
                .applyIsbnFilter(QueryFilterType.STARTS_WITH, null)
                .applyUserFilter(QueryFilterType.GREATER_THAN, null)
                .applyBorrowDateFilter(null, null)
                .applyCategoryFilter(null)
                .build();
        assertEquals("SELECT DISTINCT b FROM Book b JOIN Borrow bb on bb.book = b JOIN User u on bb.user = u WHERE 1=1 AND b.name =:name", query);
    }

    @Test
    public void shouldInjectDatesAndTitle() {
        String query = queryBuilder.createQuery()
                .applyAuthorFilter(QueryFilterType.STARTS_WITH, null)
                .applyTitleFilter(QueryFilterType.EQUAL, title)
                .applyIsbnFilter(QueryFilterType.STARTS_WITH, null)
                .applyUserFilter(QueryFilterType.GREATER_THAN, null)
                .applyBorrowDateFilter(new Date(), new Date())
                .applyCategoryFilter(null)
                .build();
        assertEquals("SELECT DISTINCT b FROM Book b JOIN Borrow bb on bb.book = b JOIN User u on bb.user = u WHERE 1=1 AND b.name =:name " +
                "AND bb.borrowDate BETWEEN :startDate AND :endDate", query);
    }

    @Test
    public void shouldInjectSingleDate() {
        String query = queryBuilder.createQuery()
                .applyAuthorFilter(QueryFilterType.STARTS_WITH, null)
                .applyTitleFilter(QueryFilterType.EQUAL, title)
                .applyIsbnFilter(QueryFilterType.STARTS_WITH, null)
                .applyUserFilter(QueryFilterType.GREATER_THAN, null)
                .applyBorrowDateFilter( new Date(), null)
                .applyCategoryFilter(null)
                .build();
        assertEquals("SELECT DISTINCT b FROM Book b JOIN Borrow bb on bb.book = b JOIN User u on bb.user = u WHERE 1=1 AND b.name =:name " +
                "AND bb.borrowDate >=:date", query);
    }

    @Test
    public void shouldInjectAuthorWithLikeOperator() {
        String query = queryBuilder.createQuery()
                .applyAuthorFilter(QueryFilterType.STARTS_WITH, "x")
                .applyTitleFilter(QueryFilterType.EQUAL, null)
                .applyIsbnFilter(QueryFilterType.STARTS_WITH, null)
                .applyUserFilter(QueryFilterType.GREATER_THAN, null)
                .applyBorrowDateFilter( null, null)
                .applyCategoryFilter(null)
                .build();
        assertEquals("SELECT DISTINCT b FROM Book b JOIN Borrow bb on bb.book = b JOIN User u on bb.user = u WHERE 1=1 AND b.author.name LIKE CONCAT('%', :author)", query);
    }

    @Test
    public void shouldNotInjectAnything() {
        String query = queryBuilder.createQuery()
                .applyAuthorFilter(QueryFilterType.STARTS_WITH, null)
                .applyTitleFilter(QueryFilterType.EQUAL, null)
                .applyIsbnFilter(QueryFilterType.STARTS_WITH, null)
                .applyUserFilter(QueryFilterType.GREATER_THAN, null)
                .applyBorrowDateFilter( null, null)
                .applyCategoryFilter(null)
                .build();
        assertEquals("SELECT DISTINCT b FROM Book b JOIN Borrow bb on bb.book = b JOIN User u on bb.user = u WHERE 1=1", query);
    }
    @Test
    public void shouldGetAll() {
        String query = new AuthorQueryBuilder().createQuery()
                .applyAuthorFilter(QueryFilterType.STARTS_WITH, null)
                .applyTitleFilter(QueryFilterType.EQUAL, null)
                .applyIsbnFilter(QueryFilterType.STARTS_WITH, null)
                .applyUserFilter(QueryFilterType.GREATER_THAN, null)
                .applyBorrowDateFilter( null, null)
                .applyCategoryFilter(null)
                .build();
        assertEquals("SELECT DISTINCT b FROM Book b JOIN Borrow bb on bb.book = b JOIN User u on bb.user = u WHERE 1=1", query);
    }
}