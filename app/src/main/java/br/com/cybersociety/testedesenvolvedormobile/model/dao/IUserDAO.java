package br.com.cybersociety.testedesenvolvedormobile.model.dao;

import br.com.cybersociety.testedesenvolvedormobile.model.entities.User;

public interface IUserDAO {

    void update(User user);

    User getUser();
}
