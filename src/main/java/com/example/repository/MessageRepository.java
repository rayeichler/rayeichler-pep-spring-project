package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
   @Query("select m from Message m where m.posted_by = :posted_by")
   List<Message> findAllByPostedBy(@Param("posted_by") Integer posted_by);
}
