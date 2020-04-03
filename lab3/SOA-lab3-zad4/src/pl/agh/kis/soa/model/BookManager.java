package pl.agh.kis.soa.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class BookManager {

    private List<Book> books = new LinkedList<>();

    public BookManager() {
            books.add(new Book("Adventorous book", "John Hopkins", BookType.ACTION_AND_ADVENTURE, 25.39, Currency.USD,Currency.USD, 589,1,false));
            books.add(new Book("Interesting book", "Marry Fairy", BookType.CRIMINAL, 19.99, Currency.EUR,Currency.EUR, 200,2,false));
            books.add(new Book("Weird and funny one", "Sam Ismail", BookType.HUMOUR, 49.99, Currency.PLN,Currency.PLN, 300,3,false));
            books.add(new Book("So much love book", "Michael Scarn", BookType.ROMANCE, 14.99, Currency.PLN, Currency.PLN,158,4,false));
            books.add(new Book("Sad detective", "Sam Ismail", BookType.CRIMINAL, 13.99, Currency.USD, Currency.USD,496,5,false));
            books.add(new Book("Drama queen", "Sam Ismail", BookType.DRAMA, 99.99, Currency.PLN, Currency.PLN, 1230,6,false));
            books.add(new Book("South Park", "John Hopkins", BookType.ROMANCE, 12.99, Currency.USD, Currency.USD,125,7,false));
            books.add(new Book("Life is so hard", "Michael Scarn", BookType.LEGEND, 49.99, Currency.EUR, Currency.EUR,898,8,false));
            books.add(new Book("The Office", "Michael Scarn", BookType.HUMOUR, 89.99, Currency.PLN, Currency.PLN, 723,9,false));
            books.add(new Book("Threat level midnight", "Sam Ismail", BookType.FICTION, 9.99, Currency.USD,Currency.USD, 58,10,false));
            books.add(new Book("Wow I am so fake", "Sam Ismail", BookType.FICTION, 149.99, Currency.PLN,Currency.PLN,  1333,1,false));
            books.add(new Book("Evil one", "Sam Ismail", BookType.LEGEND, 666.0, Currency.PLN,Currency.PLN,  666,12,false));
    };

    public BookManager(BookManager old) {
        this.books = new LinkedList<>(old.books);
    }


    public double convert(Currency from, double amount, Currency to) {
        double value = from.getAverage();
        double pln = amount * (1/value);
        return BigDecimal.valueOf(pln * to.getAverage()).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    protected BookManager clone() {
        return new BookManager(this);
    }

    public BookManager filterByAuthor(String author) {
        if (author != null && !author.equals("")) {
            BookManager newObj = this.clone();
            newObj.books = newObj.books.stream().filter(el -> el.getAuthor().contains(author)).collect(Collectors.toList());
            return newObj;
        } else {
            return this;
        }
    }

    public BookManager filterByBottomPrice(Double priceFrom) {
        if (priceFrom != null) {
            BookManager newObj = this.clone();
            newObj.books = newObj.books.stream().filter(el -> el.getPrice() >= priceFrom).collect(Collectors.toList());
            return newObj;
        } else {
            return this;
        }
    }

    public BookManager filterByTopPrice(Double priceTo) {
        if (priceTo != null) {
            BookManager newObj = this.clone();
            newObj.books = newObj.books.stream().filter(el -> el.getPrice() <= priceTo).collect(Collectors.toList());
            return newObj;
        } else {
            return this;
        }
    }

    public BookManager filterByBookType(List<BookType> bookTypes) {
        if (bookTypes != null) {
            BookManager newObj = this.clone();
            newObj.books = newObj.books.stream().filter(el -> !bookTypes.contains(el.getType())).collect(Collectors.toList());
            return newObj;
        } else {
            return this;
        }
    }

    public BookManager convertToCurrency(Currency currency) {
        if (currency != null) {
            BookManager newObj = this.clone();
            for (int i = 0; i < newObj.books.size(); i++) {
                newObj.books.get(i).setPrice(newObj.convert(newObj.books.get(i).getCurrentCurrency(), newObj.books.get(i).getPrice(), currency));
                newObj.books.get(i).setCurrentCurrency(currency);
            }
            return newObj;
        } else {
            return this;
        }
    }

    public List<Book> toPlainList() {
        return books;
    }
}
