package org.example.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import  org.example.POJO.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    User findByEmailId(@Param("email")String email);
}
