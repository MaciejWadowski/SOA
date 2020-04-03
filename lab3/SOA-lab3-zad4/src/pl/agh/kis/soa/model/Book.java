package pl.agh.kis.soa.model;

public class Book {
    private String title;
    private String author;
    private BookType type;
    private Double price;
    private Currency currency;
    private Currency currentCurrency;
    private Integer pages;
    private Integer id;
    private Boolean selectedCategories;

    public Book() {}

    public Boolean getSelectedCategories() {
        return selectedCategories;
    }

    public void setSelectedCategories(Boolean selectedCategories) {
        this.selectedCategories = selectedCategories;
    }

    public Book(String title, String author, BookType type, Double price, Currency currency, Currency currentCurrency, Integer pages, Integer id, Boolean selectedCategories) {
        this.title = title;
        this.author = author;
        this.type = type;
        this.price = price;
        this.currency = currency;
        this.currentCurrency = currentCurrency;
        this.pages = pages;
        this.id = id;
        this.selectedCategories = selectedCategories;
    }

    public Currency getCurrentCurrency() {
        return currentCurrency;
    }

    public void setCurrentCurrency(Currency currentCurrency) {
        this.currentCurrency = currentCurrency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BookType getType() {
        return type;
    }

    public void setType(BookType type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}
