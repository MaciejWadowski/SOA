package agh.edu.pl.jsf;

import agh.edu.pl.model.Author;
import agh.edu.pl.model.Category;
import agh.edu.pl.repositories.AuthorRepository;
import agh.edu.pl.repositories.interfaces.IAuthorRemoteRepository;
import agh.edu.pl.repositories.interfaces.IBookRemoteRepository;
import agh.edu.pl.repositories.interfaces.ICategoryRemoteRepository;
import agh.edu.pl.util.AuthorQueryBuilder;
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

@ManagedBean(name = "authors")
@SessionScoped
public class AuthorsBean {
    @EJB(lookup = "java:module/AuthorRepository")
    private IAuthorRemoteRepository authorRepository;
    @EJB(lookup = "java:module/BookRepository")
    private IBookRemoteRepository bookRepository;
    @EJB(lookup = "java:module/CategoryRepository")
    private ICategoryRemoteRepository categoryRepository;

    //delete form
    private Long authorIdToDelete;

    //add form
    private String newAuthor;

    //update form
    private Long authorIdToUpdate;
    private String updateNameAuthor;

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
    private Author mostPopularAuthor = new Author();

    public List<Author> getAuthorList() {
        AuthorQueryBuilder authorQueryBuilder = new AuthorQueryBuilder();
        List<Category> applicableCategories = new ArrayList<>();
        if (categoryFilter != null) {
            for (int i = 0; i < categoryFilter.size(); i++) {
                final int finalI = i;
                Category c = categoryRepository.findAll().stream().filter(e -> e.getId().equals(categoryFilter.get(finalI))).findFirst().orElse(null);
                applicableCategories.add(c);
            }
        }
        Date starDate = null;
        Date endDate = null;
        if (startDateFilter != null && !startDateFilter.isBlank()) {
            try {
                starDate = formatter.parse(startDateFilter);
            } catch (ParseException e) {
                startDateFilter = null;
            }
        }
        if (endDateFilter != null && !endDateFilter.isBlank()) {
            try {
                endDate = formatter.parse(endDateFilter);
            } catch (ParseException e) {
                endDateFilter = null;
            }
        }
        AuthorQueryBuilder.Builder queryBuilder = authorQueryBuilder.createQuery();
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
        return authorRepository.customQuery(query , authorFilter, titleFilter, userFilter, isbnFilter, starDate, endDate, applicableCategories);
    }

    public Author showMostPopularAuthor() {
        return authorRepository.getMostPopularAuthor();
    }

    @Transactional
    public void addRecord() {
        Author author = new Author();
        author.setName(newAuthor);
        authorRepository.save(author);
    }

    @Transactional
    public void deleteRecord() {
        authorRepository.deleteById(authorIdToDelete);
    }

    @Transactional
    public void updateRecord() {
        Author author = new Author();
        author.setName(updateNameAuthor);
        authorRepository.update(author, authorIdToUpdate);
    }

    public Long getAuthorIdToDelete() {
        return authorIdToDelete;
    }

    public void setAuthorIdToDelete(Long authorIdToDelete) {
        this.authorIdToDelete = authorIdToDelete;
    }

    public String getNewAuthor() {
        return newAuthor;
    }

    public void setNewAuthor(String newAuthor) {
        this.newAuthor = newAuthor;
    }

    public Long getAuthorIdToUpdate() {
        return authorIdToUpdate;
    }

    public void setAuthorIdToUpdate(Long authorIdToUpdate) {
        this.authorIdToUpdate = authorIdToUpdate;
    }

    public String getUpdateNameAuthor() {
        return updateNameAuthor;
    }

    public void setUpdateNameAuthor(String updateNameAuthor) {
        this.updateNameAuthor = updateNameAuthor;
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

    public Author getMostPopularAuthor() {
        return authorRepository.getMostPopularAuthor();
    }

    public void setMostPopularAuthor(Author mostPopularAuthor) {
        this.mostPopularAuthor = mostPopularAuthor;
    }
}
