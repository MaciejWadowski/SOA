package com.mastertheboss.repository.interfaces;


import com.mastertheboss.model.User;

import javax.ejb.Remote;

@Remote
public interface IUserRepository extends Repository<Long, User> {
}
