package pl.agh.kis.soa.beans;

import pl.agh.kis.soa.model.Book;
import pl.agh.kis.soa.model.BookManager;
import pl.agh.kis.soa.model.BookType;
import pl.agh.kis.soa.model.Currency;

import javax.faces.bean.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@ManagedBean(name = "display")
@ApplicationScoped
public class DisplayBean {

    private Double minimumPrice;
    private Double maximumPrice;
    private String author;
    private List<BookType> typesToHide;
    private BookManager bookList = new BookManager();
    private Currency currency = Currency.PLN;

    public Double calculateSum() {
        return getBooksFromCart().stream().mapToDouble(Book::getPrice).reduce(0, Double::sum);
    }

    public List<Book> getBookList() {
        return bookList
                .convertToCurrency(currency)
                .filterByAuthor(author)
                .filterByBottomPrice(minimumPrice)
                .filterByTopPrice(maximumPrice)
                .filterByBookType(typesToHide)
                .toPlainList();
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public List<Book> getBooksFromCart() {
        return bookList.toPlainList().stream().filter(Book::getSelectedCategories).collect(Collectors.toList());
    }

    public Currency[] getCurrencyTypes() {
        return Currency.values();
    }


    public Double getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(Double minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public Double getMaximumPrice() {
        return maximumPrice;
    }

    public void setMaximumPrice(Double maximumPrice) {
        this.maximumPrice = maximumPrice;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<BookType> getTypesToHide() {
        return typesToHide;
    }

    public void setTypesToHide(List<BookType> typesToHide) {
        this.typesToHide = typesToHide;
    }

    public BookType[] getBookTypes() {
        return BookType.values();
    }
}
