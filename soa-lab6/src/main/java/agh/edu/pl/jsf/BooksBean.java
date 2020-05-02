package agh.edu.pl.jsf;

import agh.edu.pl.model.Author;
import agh.edu.pl.model.Book;
import agh.edu.pl.model.Borrow;
import agh.edu.pl.model.Category;
import agh.edu.pl.repositories.AuthorRepository;
import agh.edu.pl.repositories.BookRepository;
import agh.edu.pl.repositories.CategoryRepository;
import agh.edu.pl.repositories.interfaces.IAuthorRemoteRepository;
import agh.edu.pl.repositories.interfaces.IBookRemoteRepository;
import agh.edu.pl.repositories.interfaces.ICatalogRemoteRepository;
import agh.edu.pl.repositories.interfaces.ICategoryRemoteRepository;
import agh.edu.pl.util.BookQueryBuilder;
import agh.edu.pl.util.QueryFilterType;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "front")
@SessionScoped
public class BooksBean {
    @EJB(lookup = "java:module/BookRepository")
    private IBookRemoteRepository bookRepository;
    @EJB(lookup = "java:module/CategoryRepository")
    private ICategoryRemoteRepository categoryRepository;
    @EJB(lookup = "java:module/AuthorRepository")
    private IAuthorRemoteRepository authorRepository;

    private List<Category> categories;

    private String error;
    // add form
    private String title;
    private Long authorId;
    private Long categoryId;

    //delete form
    private Long deleteBookId;

    //update form
    private Long bookIdToUpdate;
    private String newTitle;
    private Long newAuthorId;
    private Long newCategoryId;

    // filters
    private String authorFilter;
    private QueryFilterType authorFilterType;
    private String titleFilter;
    private QueryFilterType titleFilterType;
    private String isbnFilter;
    private QueryFilterType isbnFilterType;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    private String startDateFilter;
    private String endDateFilter;
    private List<Long> categoryFilter;
    private String userFilter;
    private QueryFilterType userFilterType;


    public List<Book> getBookList() {
        error = "";
        BookQueryBuilder bookQueryBuilder = new BookQueryBuilder();
        List<Category> applicableCategories = new ArrayList<>();
        if (categoryFilter != null) {
            for (int i = 0; i < categoryFilter.size(); i++) {
                final int finalI = i;
                Category c = categories.stream().filter(e -> e.getId().equals(categoryFilter.get(finalI))).findFirst().orElse(null);
                applicableCategories.add(c);
            }
        }
        Date starDate = null;
        Date endDate = null;
        if (startDateFilter != null && !startDateFilter.isBlank()) {
            try {
                starDate = formatter.parse(startDateFilter);
            } catch (ParseException e) {
                error = "Invalid startDate format";
                startDateFilter = null;
            }
        }
        if (endDateFilter != null && !endDateFilter.isBlank()) {
            try {
                endDate = formatter.parse(endDateFilter);
            } catch (ParseException e) {
                error += "\n Invalid endDateFormat";
                endDateFilter = null;
            }
        }
        BookQueryBuilder.Builder queryBuilder = bookQueryBuilder.createQuery();
        if ((userFilter != null && !userFilter.isBlank()) || (startDateFilter != null && !startDateFilter.isBlank()) || (endDateFilter != null && !endDateFilter.isBlank())) {
            queryBuilder = queryBuilder.complexQueryBuilder();
        } else {
            queryBuilder = queryBuilder.simpleQueryBuilder();
        }
        String query = queryBuilder
                .applyCategoryFilter(applicableCategories)
                .applyUserFilter( userFilterType, userFilter)
                .applyIsbnFilter(isbnFilterType, isbnFilter)
                .applyAuthorFilter(authorFilterType, authorFilter)
                .applyTitleFilter(titleFilterType, titleFilter)
                .applyBorrowDateFilter(starDate, endDate)
                .build();
        return bookRepository.customQuery(query , authorFilter, titleFilter, userFilter, isbnFilter, starDate, endDate, applicableCategories);
    }

    public List<QueryFilterType> getFiltersForString() {
        return QueryFilterType.EQUAL.getFilterTypesForString();
    }

    public List<Category> getCategories() {
        if (categories != null) return categories;
        else {
            categories = categoryRepository.findAll();
            return categories;
        }
    }

    @Transactional
    public void addRecord() {
        try {
            Book book = new Book();
            book.setAuthor(authorRepository.findById(authorId));
            book.setCategory(categoryRepository.findById(categoryId));
            book.setName(title);
            bookRepository.save(book);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void deleteRecord() {
        try {
            bookRepository.deleteById(deleteBookId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void updateRecord() {
        try {
            Book book = new Book();
            book.setCategory(categoryRepository.findById(newCategoryId));
            book.setAuthor(authorRepository.findById(newAuthorId));
            book.setName(newTitle);
            bookRepository.update(book, bookIdToUpdate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getDeleteBookId() {
        return deleteBookId;
    }

    public void setDeleteBookId(Long deleteBookId) {
        this.deleteBookId = deleteBookId;
    }

    public Long getBookIdToUpdate() {
        return bookIdToUpdate;
    }

    public void setBookIdToUpdate(Long bookIdToUpdate) {
        this.bookIdToUpdate = bookIdToUpdate;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    public Long getNewAuthorId() {
        return newAuthorId;
    }

    public void setNewAuthorId(Long newAuthorId) {
        this.newAuthorId = newAuthorId;
    }

    public Long getNewCategoryId() {
        return newCategoryId;
    }

    public void setNewCategoryId(Long newCategoryId) {
        this.newCategoryId = newCategoryId;
    }

    public String getAuthorFilter() {
        return authorFilter;
    }

    public void setAuthorFilter(String authorFilter) {
        this.authorFilter = authorFilter;
    }

    public QueryFilterType getAuthorFilterType() {
        return authorFilterType;
    }

    public void setAuthorFilterType(QueryFilterType authorFilterType) {
        this.authorFilterType = authorFilterType;
    }

    public String getTitleFilter() {
        return titleFilter;
    }

    public void setTitleFilter(String titleFilter) {
        this.titleFilter = titleFilter;
    }

    public QueryFilterType getTitleFilterType() {
        return titleFilterType;
    }

    public void setTitleFilterType(QueryFilterType titleFilterType) {
        this.titleFilterType = titleFilterType;
    }

    public String getIsbnFilter() {
        return isbnFilter;
    }

    public void setIsbnFilter(String isbnFilter) {
        this.isbnFilter = isbnFilter;
    }

    public QueryFilterType getIsbnFilterType() {
        return isbnFilterType;
    }

    public void setIsbnFilterType(QueryFilterType isbnFilterType) {
        this.isbnFilterType = isbnFilterType;
    }

    public SimpleDateFormat getFormatter() {
        return formatter;
    }

    public void setFormatter(SimpleDateFormat formatter) {
        this.formatter = formatter;
    }

    public String getStartDateFilter() {
        return startDateFilter;
    }

    public void setStartDateFilter(String startDateFilter) {
        this.startDateFilter = startDateFilter;
    }

    public String getEndDateFilter() {
        return endDateFilter;
    }

    public void setEndDateFilter(String endDateFilter) {
        this.endDateFilter = endDateFilter;
    }

    public List<Long> getCategoryFilter() {
        return categoryFilter;
    }

    public void setCategoryFilter(List<Long> categoryFilter) {
        this.categoryFilter = categoryFilter;
    }

    public String getUserFilter() {
        return userFilter;
    }

    public void setUserFilter(String userFilter) {
        this.userFilter = userFilter;
    }

    public QueryFilterType getUserFilterType() {
        return userFilterType;
    }

    public void setUserFilterType(QueryFilterType userFilterType) {
        this.userFilterType = userFilterType;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
