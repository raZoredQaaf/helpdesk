package com.fdmgroup.helpdeskapi.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fdmgroup.helpdeskapi.model.Message;
import com.fdmgroup.helpdeskapi.repository.MessageRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImp implements MessageService {

    Logger logger = LoggerFactory.getLogger(MessageServiceImp.class);

    @NonNull
    MessageRepository messageRepository;

    @Override
    public Message saveMessage(Message message) {
        logger.info("MessageServiceImp - Saving message: {}", message);
        return messageRepository.save(message);
    }

    @Override
    public List<Message> findAllMessages() {
        logger.info("MessageServiceImp - Finding all messages");
        return messageRepository.findAll();
    }

    @Override
    public Message findMessageById(long id) {
        logger.info("MessageServiceImp - Finding message by id: {}", id);
        return messageRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteMessageById(long id) {
        logger.info("MessageServiceImp - Deleting message by id: {}", id);
        messageRepository.deleteById(id);
    }

}
