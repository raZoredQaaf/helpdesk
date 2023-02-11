package com.fdmgroup.helpdeskapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.helpdeskapi.model.Message;
import com.fdmgroup.helpdeskapi.service.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private Logger logger = LoggerFactory.getLogger(MessageController.class);

    @NonNull
    private MessageService messageService;

    @Operation(summary = "Save a message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Message created"),
    })
    @PostMapping
    public ResponseEntity<?> saveMessage(@RequestBody Message message) {
        logger.info("Saving message: {}", message);
        return new ResponseEntity<>(messageService.saveMessage(message), HttpStatus.CREATED);
    }

    @Operation(summary = "Find all messages")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Messages found"),
    })
    @GetMapping
    public ResponseEntity<?> findAllMessages() {
        logger.info("Finding all messages");
        List<Message> messages = messageService.findAllMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @Operation(summary = "Find an message by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message found"),
            @ApiResponse(responseCode = "404", description = "Message not found"),
    })
    @PutMapping
    public ResponseEntity<?> updateMessage(@RequestBody Message message) {
        if (messageService.findMessageById(message.getId()) != null) {
            logger.info("Updating message: {}", message);
            return new ResponseEntity<>(messageService.saveMessage(message), HttpStatus.OK);
        } else {
            logger.warn("Message not found: {}", message);
            return new ResponseEntity<>("Message not found", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Find a message by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message found"),
            @ApiResponse(responseCode = "404", description = "Message not found"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findMessageById(@PathVariable long id) {
        if (messageService.findMessageById(id) != null) {
            logger.info("Finding message by id: {}", id);
            return new ResponseEntity<>(messageService.findMessageById(id), HttpStatus.OK);
        } else {
            logger.warn("Message not found: {}", id);
            return new ResponseEntity<>("Message not found", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a message by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message deleted"),
            @ApiResponse(responseCode = "404", description = "Message not found"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessageById(@PathVariable long id) {
        if (messageService.findMessageById(id) != null) {
            messageService.deleteMessageById(id);
            logger.info("Deleting message by id: {}", id);
            return new ResponseEntity<>("Message deleted", HttpStatus.OK);
        } else {
            logger.warn("Message not found: {}", id);
            return new ResponseEntity<>("Message not found", HttpStatus.NOT_FOUND);
        }
    }

}
