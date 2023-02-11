package com.fdmgroup.helpdeskapi.service;

import java.util.List;

import com.fdmgroup.helpdeskapi.model.Message;

public interface MessageService {

    Message saveMessage(Message message);

    List<Message> findAllMessages();

    Message findMessageById(long id);

    void deleteMessageById(long id);

}
