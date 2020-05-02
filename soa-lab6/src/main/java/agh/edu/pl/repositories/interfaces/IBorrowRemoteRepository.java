package agh.edu.pl.repositories.interfaces;

import agh.edu.pl.model.Borrow;
import agh.edu.pl.repositories.interfaces.CustomRepository;

import javax.ejb.Remote;

@Remote
public interface IBorrowRemoteRepository extends CustomRepository<Long, Borrow> {
}
