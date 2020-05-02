package agh.edu.pl.repositories;

import agh.edu.pl.model.Borrow;
import agh.edu.pl.repositories.interfaces.IBorrowRemoteRepository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class BorrowRepository implements IBorrowRemoteRepository {
    @PersistenceContext(name ="JPA")
    private EntityManager em;

    @Override
    public List<Borrow> findAll() {
        try {
            return em.createQuery("FROM Borrow", Borrow.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Borrow findById(Long id) {
        return em.find(Borrow.class, id);
    }

    @Override
    public void save(Borrow object) {
        em.persist(object);
    }

    @Override
    public void deleteById(Long id) {
        Borrow borrow = em.find(Borrow.class, id);
        if (borrow != null) {
            em.remove(borrow);
        }
    }

    @Override
    public void update(Borrow object, Long id) {
        Borrow borrow = em.find(Borrow.class, id);
        if (object.getBook() != null)
            borrow.setBook(object.getBook());
        if (object.getBorrowDate() != null)
            borrow.setBorrowDate(object.getBorrowDate());
        if (object.getExpireDate() != null)
            borrow.setExpireDate(object.getExpireDate());
        if (object.getUser() != null)
            borrow.setUser(object.getUser());
    }
}
