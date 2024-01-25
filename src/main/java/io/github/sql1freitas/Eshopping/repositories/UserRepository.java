package io.github.sql1freitas.Eshopping.repositories;

import io.github.sql1freitas.Eshopping.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository <Users, Long> {

    UserDetails findByLogin(String login);

}
