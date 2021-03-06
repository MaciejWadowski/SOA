package agh.edu.pl.repositories.interfaces;

import agh.edu.pl.model.Author;
import agh.edu.pl.model.Category;
import agh.edu.pl.model.User;

import javax.ejb.Remote;
import java.util.Date;
import java.util.List;

@Remote
public interface IAuthorRemoteRepository extends CustomRepository<Long, Author> {
    Author getAuthorByName(String name);
    List<Author> customQuery(String query, String author, String title, String user, String isbn, Date startDate, Date endDate, List<Category>categories);
    Author getMostPopularAuthor();
}
