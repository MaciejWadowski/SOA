package agh.edu.pl.jsf;

import agh.edu.pl.model.Category;
import agh.edu.pl.model.User;
import agh.edu.pl.repositories.interfaces.IAuthorRemoteRepository;
import agh.edu.pl.repositories.interfaces.IBookRemoteRepository;
import agh.edu.pl.repositories.interfaces.ICategoryRemoteRepository;
import agh.edu.pl.repositories.interfaces.IUserRemoteRepository;
import agh.edu.pl.util.BookQueryBuilder;
import agh.edu.pl.util.QueryFilterType;
import agh.edu.pl.util.UserQueryBuilder;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "users")
@SessionScoped
public class UsersBean {
    @EJB(lookup = "java:module/UserRepository")
    private IUserRemoteRepository userRepository;
    @EJB(lookup = "java:module/BookRepository")
    private IBookRemoteRepository bookRepository;
    @EJB(lookup = "java:module/CategoryRepository")
    private ICategoryRemoteRepository categoryRepository;
    @EJB(lookup = "java:module/AuthorRepository")
    private IAuthorRemoteRepository authorRepository;

    //deleteForm
    private Long userIdToDelete;

    //add form
    private String newUser;

    //update form
    private Long userIdToUpdate;
    private String updatedUser;

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

    public List<User> getUserList() {
        UserQueryBuilder userQueryBuilder = new UserQueryBuilder();
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
        UserQueryBuilder.Builder queryBuilder = userQueryBuilder.createQuery();
        if ((titleFilter != null && !titleFilter.isBlank()) || (startDateFilter != null && !startDateFilter.isBlank()) || (endDateFilter != null && !endDateFilter.isBlank())
        || (isbnFilter != null && !isbnFilter.isBlank())|| (authorFilter != null && !authorFilter.isBlank())|| (applicableCategories.size() > 0)) {
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
        return userRepository.customQuery(query , authorFilter, titleFilter, userFilter, isbnFilter, starDate, endDate, applicableCategories);
    }

    @Transactional
    public void addRecord() {
        User user = new User();
        user.setName(newUser);
        userRepository.save(user);
    }

    @Transactional
    public void deleteRecord() {
        userRepository.deleteById(userIdToDelete);
    }

    @Transactional
    public void updateRecord() {
        User user = new User();
        user.setName(updatedUser);
        userRepository.update(user, userIdToUpdate);
    }

    public Long getUserIdToDelete() {
        return userIdToDelete;
    }

    public void setUserIdToDelete(Long userIdToDelete) {
        this.userIdToDelete = userIdToDelete;
    }

    public String getNewUser() {
        return newUser;
    }

    public void setNewUser(String newUser) {
        this.newUser = newUser;
    }

    public Long getUserIdToUpdate() {
        return userIdToUpdate;
    }

    public void setUserIdToUpdate(Long userIdToUpdate) {
        this.userIdToUpdate = userIdToUpdate;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
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
}
