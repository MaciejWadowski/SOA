package agh.edu.pl.repositories.interfaces;

import agh.edu.pl.model.Book;
import agh.edu.pl.model.Category;

import javax.ejb.Remote;
import java.util.Date;
import java.util.List;

@Remote
public interface IBookRemoteRepository extends CustomRepository<Long, Book> {
    List<Book> fetchAvailableBooks();
    List<Book> customQuery(String query, String author, String title, String user, String isbn, Date startDate, Date endDate, List<Category>categories);
}
