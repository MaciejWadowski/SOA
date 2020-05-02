package agh.edu.pl.repositories;

import agh.edu.pl.model.Book;
import agh.edu.pl.model.Category;
import agh.edu.pl.model.User;
import agh.edu.pl.repositories.interfaces.IUserRemoteRepository;

import javax.ejb.Stateless;
import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Stateless
public class UserRepository implements IUserRemoteRepository {
    @PersistenceContext(name ="JPA")
    private EntityManager em;

    @Override
    public List<User> findAll() {
        try {
            return em.createQuery("FROM User ", User.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public void save(User object) {
        em.persist(object);
    }

    @Override
    public void deleteById(Long id) {
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
    }

    @Override
    public void update(User object, Long id) {
        User user = em.find(User.class, id);
        if (object.getName() != null)
            user.setName(object.getName());
    }

    @Override
    public List<User> customQuery(String query, String author, String title, String user, String isbn, Date startDate, Date endDate, List<Category> categories) {
        Query q = em.createQuery(query, User.class);
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
}
