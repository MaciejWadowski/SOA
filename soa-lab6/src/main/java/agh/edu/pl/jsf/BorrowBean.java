package agh.edu.pl.jsf;

import agh.edu.pl.model.Book;
import agh.edu.pl.model.Borrow;
import agh.edu.pl.model.Catalog;
import agh.edu.pl.repositories.interfaces.IBookRemoteRepository;
import agh.edu.pl.repositories.interfaces.IBorrowRemoteRepository;
import agh.edu.pl.repositories.interfaces.ICatalogRemoteRepository;
import agh.edu.pl.repositories.interfaces.IUserRemoteRepository;
import clojure.lang.ILookup;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ManagedBean
@SessionScoped
public class BorrowBean {
    @EJB(lookup = "java:module/BorrowRepository")
    private IBorrowRemoteRepository borrowRepository;
    @EJB(lookup = "java:module/BookRepository")
    private IBookRemoteRepository bookRepository;
    @EJB(lookup = "java:module/CatalogRepository")
    private ICatalogRemoteRepository catalogRepository;
    @EJB(lookup = "java:module/UserRepository")
    private IUserRemoteRepository userRepository;
    //borrow form
    private Long borrowBookId;
    private Long borrowUserId;

    //unborrow form
    private Long unBorrowBookId;
    private Long unBorrowUserId;

    @Transactional
    public String borrow() {
        Catalog catalog = catalogRepository.findAvailableCatalog(borrowBookId);
        if (catalog != null) {
            catalog.setBooked(true);
            catalogRepository.update(catalog,catalog.getId());
            Borrow borrow = new Borrow();
            borrow.setUser(userRepository.findById(borrowUserId));
            borrow.setBook(bookRepository.findById(borrowBookId));
            borrow.setBorrowDate(new Date());

            Date expireDate = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(expireDate);
            c.add(Calendar.DATE, 7);
            expireDate = c.getTime();

            borrow.setExpireDate(expireDate);
            borrowRepository.save(borrow);
        }
        return "index.xhtml";
    }

    public List<Book> getAvailableBooks() {
        return bookRepository.fetchAvailableBooks();
    }

    public Long getBorrowBookId() {
        return borrowBookId;
    }

    public void setBorrowBookId(Long borrowBookId) {
        this.borrowBookId = borrowBookId;
    }

    public Long getBorrowUserId() {
        return borrowUserId;
    }

    public void setBorrowUserId(Long borrowUserId) {
        this.borrowUserId = borrowUserId;
    }

    public Long getUnBorrowBookId() {
        return unBorrowBookId;
    }

    public void setUnBorrowBookId(Long unBorrowBookId) {
        this.unBorrowBookId = unBorrowBookId;
    }

    public Long getUnBorrowUserId() {
        return unBorrowUserId;
    }

    public void setUnBorrowUserId(Long unBorrowUserId) {
        this.unBorrowUserId = unBorrowUserId;
    }
}
