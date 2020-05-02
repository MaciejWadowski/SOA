package agh.edu.pl.repositories;

import agh.edu.pl.model.Book;
import agh.edu.pl.model.Borrow;
import agh.edu.pl.model.Catalog;
import agh.edu.pl.model.Category;
import agh.edu.pl.repositories.interfaces.IBookRemoteRepository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Stateless
public class BookRepository implements IBookRemoteRepository {
    @PersistenceContext(name ="JPA")
    private EntityManager em;

    @Override
    public List<Book> findAll() {
        try {
            return em.createQuery("FROM Book", Book.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Book> customQuery(String query, String author, String title, String user, String isbn, Date startDate, Date endDate, List<Category >categories) {
        Query q = em.createQuery(query, Book.class);
        if (author!= null && !author.isBlank())
            q.setParameter("author", author);
        if (title != null&& !title.isBlank())
            q.setParameter("name", title);
        if (user != null&& !user.isBlank())
            q.setParameter("user", user);
        if (isbn != null&& !isbn.isBlank())
            q.setParameter("isbn", isbn);
        if (startDate != null && endDate != null) {
            q.setParameter("startDate", startDate);
            q.setParameter("endDate", endDate);
        } else if (endDate != null)
            q.setParameter("date", endDate);
        else if (startDate != null)
            q.setParameter("date", startDate);
        if (categories != null && categories.size() > 0) {
            q.setParameter("categories", categories);
        }
        try {
            return  q.getResultList();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book findById(Long id) {
        return em.find(Book.class, id);
    }

    @Override
    public void save(Book book) {
        Query query = em.createQuery(
                     "SELECT b " +
                             "FROM Book b " +
                             "WHERE b.name = :name " +
                             "AND b.author = :author " +
                             "AND b.category = :category");
        query.setParameter("name", book.getName());
        query.setParameter("author", book.getAuthor());
        query.setParameter("category", book.getCategory());
        query.setMaxResults(1);
        List<Book> books = query.getResultList();
        Catalog catalog = new Catalog();
        catalog.setBooked(false);
        if (books.size() == 0) {
            em.persist(book);
            catalog.setBook(book);
            em.persist(catalog);
        } else {
            catalog.setBook(books.get(0));
            em.persist(catalog);
        }
    }

    @Override
    public void deleteById(Long id) {
        Book book = em.find(Book.class, id);
        if (book != null) {
            em.remove(book);
        }
    }

    @Override
    public void update(Book object, Long id) {
        Book book = em.find(Book.class, id);
        if (object.getName() != null)
            book.setName(object.getName());
        if (object.getAuthor() != null)
            book.setAuthor(object.getAuthor());
        if (object.getCategory() != null)
            book.setCategory(object.getCategory());
        if (object.getIsbn() != null)
            book.setIsbn(object.getIsbn());
    }

    @Override
    public List<Book> fetchAvailableBooks() {
        Query query = em.createNativeQuery(
                "select bb.id, bb.isbn, bb.name, bb.author_id, bb.category_id FROM (SELECT b.*, count(*) FROM books b join catalog c on c.book_id=b.id WHERE c.is_booked=0 group by b.id having count(*) > 0) as bb;",
        Book.class);
        List<Book> books = query.getResultList();
        return books;
    }


}
