package com.fdmgroup.helpdeskapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.helpdeskapi.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
