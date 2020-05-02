package agh.edu.pl.repositories;

import agh.edu.pl.model.Author;
import agh.edu.pl.model.Book;
import agh.edu.pl.model.Category;
import agh.edu.pl.repositories.interfaces.IAuthorRemoteRepository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Stateless
public class AuthorRepository implements IAuthorRemoteRepository {
    @PersistenceContext(name ="JPA")
    private EntityManager em;

    @Override
    public List<Author> findAll() {
        try {
            return em.createQuery("FROM Author ", Author.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Author findById(Long id) {
        return em.find(Author.class, id);
    }

    @Override
    public void save(Author object) {
        em.persist(object);
    }

    @Override
    public void deleteById(Long id) {
        Author author = em.find(Author.class, id);
        if (author != null) {
            em.remove(author);
        }
    }

    @Override
    public void update(Author object, Long id) {
        Author author = em.find(Author.class, object);
        if (author.getName() != null)
            author.setName(object.getName());
    }

    @Override
    public Author getAuthorByName(String name) {
        return findAll().stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Author> customQuery(String query, String author, String title, String user, String isbn, Date startDate, Date endDate, List<Category> categories) {
        Query q = em.createQuery(query, Author.class);
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
    public Author getMostPopularAuthor() {
        List<Author> list = em.createNativeQuery("SELECT DISTINCT a.* FROM (SELECT a.*, count(*) FROM authors a join books b on b.author_id=a.id join borrows bb on bb.book_id=b.id group by a.id order by count(*) desc limit 1) as a", Author.class).getResultList();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            Author author = new Author();
            author.setName("Brak wypozyczen w bazie");
            return author;
        }
    }
}
