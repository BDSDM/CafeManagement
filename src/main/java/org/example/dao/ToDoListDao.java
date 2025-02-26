package org.example.dao;

import org.example.POJO.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoListDao extends JpaRepository<ToDoList, Long> {
    List<ToDoList> findByUserId(Integer userId);
}
