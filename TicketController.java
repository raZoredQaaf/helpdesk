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
import com.fdmgroup.helpdeskapi.model.Ticket;
import com.fdmgroup.helpdeskapi.model.User;
import com.fdmgroup.helpdeskapi.service.MessageService;
import com.fdmgroup.helpdeskapi.service.TicketService;
import com.fdmgroup.helpdeskapi.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

	Logger logger = LoggerFactory.getLogger(TicketController.class);

	@NonNull
	private TicketService ticketService;

	@NonNull
	private UserService userService;

	@NonNull
	private MessageService messageService;

	@Operation(summary = "Save a ticket")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Ticket created"), })
	@PostMapping
	public ResponseEntity<?> saveTicket(@RequestBody Ticket ticket) {
		logger.info("Saving ticket: {}", ticket);
		return new ResponseEntity<>(ticketService.saveTicket(ticket), HttpStatus.CREATED);
	}

	@Operation(summary = "Find all tickets")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Tickets found"), })
	@GetMapping
	public ResponseEntity<?> findAllTickets() {
		logger.info("Finding all tickets");
		List<Ticket> tickets = ticketService.findAllTickets();
		return new ResponseEntity<>(tickets, HttpStatus.OK);
	}

	@Operation(summary = "Find all unassigned tickets")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Tickets found"), })
	@GetMapping("/unassigned")
	public ResponseEntity<?> findAllUnassignedTickets() {
		logger.info("Finding all unassigned tickets");
		List<Ticket> unassignedTickets = ticketService.findAllUnassignedTickets();
		return new ResponseEntity<>(unassignedTickets, HttpStatus.OK);
	}

	@Operation(summary = "Update a ticket")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ticket found"),
			@ApiResponse(responseCode = "404", description = "Ticket not found"), })
	@PutMapping
	public ResponseEntity<?> updateTicket(@RequestBody Ticket ticket) {
		if (ticketService.findTicketById(ticket.getId()) != null) {
			logger.info("Updating ticket: {}", ticket);
			return new ResponseEntity<>(ticketService.saveTicket(ticket), HttpStatus.OK);
		} else {
			logger.warn("Ticket not found: {}", ticket);
			return new ResponseEntity<>("Ticket not found", HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Resolve a ticket by its id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ticket resolved"),
			@ApiResponse(responseCode = "404", description = "Ticket not found"), })
	@GetMapping("/resolve/{id}")
	public ResponseEntity<?> resolveTicketById(@PathVariable Long id) {
		Ticket resolvedTicket = ticketService.findTicketById(id);
		if (resolvedTicket != null) {
			logger.info("Resolving ticket: {}", resolvedTicket);
			resolvedTicket.setResolved(true);
			return new ResponseEntity<>(ticketService.saveTicket(resolvedTicket), HttpStatus.OK);
		} else {
			logger.warn("Ticket not found: {}", resolvedTicket);
			return new ResponseEntity<>("Ticket not found", HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Re-open a ticket by its id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ticket re-opened"),
			@ApiResponse(responseCode = "404", description = "Ticket not found"), })
	@GetMapping("/reopen/{id}")
	public ResponseEntity<?> reopenTicketById(@PathVariable Long id) {
		Ticket reopenedTicket = ticketService.findTicketById(id);
		if (reopenedTicket != null) {
			logger.info("Re-opening ticket: {}", reopenedTicket);
			reopenedTicket.setResolved(false);
			return new ResponseEntity<>(ticketService.saveTicket(reopenedTicket), HttpStatus.OK);
		} else {
			logger.warn("Ticket not found: {}", reopenedTicket);
			return new ResponseEntity<>("Ticket not found", HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Assign a ticket to an engineer")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ticket assigned"),
			@ApiResponse(responseCode = "404", description = "Ticket not found"), })
	@GetMapping("/engineer/{engineerId}/addto/{ticketId}")
	public ResponseEntity<?> assignTicketEngineerById(@PathVariable Long engineerId, @PathVariable Long ticketId) {
		Ticket ticketToAssign = ticketService.findTicketById(ticketId);
		if (ticketToAssign != null) {
			logger.info("Assigning ticket: {} to engineer: {}", ticketToAssign, engineerId);
			ticketToAssign.setEngineerId(engineerId);
			return new ResponseEntity<>(ticketService.saveTicket(ticketToAssign), HttpStatus.OK);
		} else {
			logger.warn("Ticket not found: {}", ticketToAssign);
			return new ResponseEntity<>("Ticket not found", HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Find a ticket by id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ticket found"),
			@ApiResponse(responseCode = "404", description = "Ticket not found"), })
	@GetMapping("/{id}")
	public ResponseEntity<?> findTicketById(@PathVariable long id) {
		if (ticketService.findTicketById(id) != null) {
			logger.info("Finding ticket by id: {}", id);
			return new ResponseEntity<>(ticketService.findTicketById(id), HttpStatus.OK);
		} else {
			logger.warn("Ticket not found: {}", id);
			return new ResponseEntity<>("Ticket not found", HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Find tickets by engineer id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Tickets found"), })
	@GetMapping("/engineer/{id}")
	public ResponseEntity<?> findTicketsByEngineerId(@PathVariable Long id) {
		List<Ticket> tickets = ticketService.findTicketsByEngineerId(id);
		logger.info("Finding tickets by engineer id: {}", id);
		return new ResponseEntity<>(tickets, HttpStatus.OK);
	}

	@Operation(summary = "Find tickets by client id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Tickets found"), })
	@GetMapping("/client/{id}")
	public ResponseEntity<?> findTicketsByClientId(@PathVariable Long id) {
		logger.info("Finding tickets by client id: {}", id);
		List<Ticket> tickets = ticketService.findTicketsByClientId(id);
		return new ResponseEntity<>(tickets, HttpStatus.OK);
	}

	@Operation(summary = "Delete a ticket by id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ticket deleted"),
			@ApiResponse(responseCode = "404", description = "Ticket not found"), })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTicketById(@PathVariable Long id) {
		if (ticketService.findTicketById(id) != null) {
			logger.info("Deleting ticket by id: {}", id);
			ticketService.deleteTicketById(id);
			return new ResponseEntity<>("Ticket deleted", HttpStatus.OK);
		} else {
			logger.warn("Ticket not found: {}", id);
			return new ResponseEntity<>("Ticket not found", HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Add a message to a ticket by ticket id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ticket found"),
			@ApiResponse(responseCode = "404", description = "Ticket not found"), })
	@PutMapping("/addMessage/{ticketId}")
	public ResponseEntity<?> addMessageToTicketByTicketId(@PathVariable Long ticketId, @RequestBody Message message) {
		if (ticketService.findTicketById(ticketId) != null) {
			logger.info("Adding message to ticket: {}", ticketId);
			Ticket ticket = ticketService.findTicketById(ticketId);
			Message retrievedMessage = new Message();
			retrievedMessage.setBody(message.getBody());
			retrievedMessage.setDateCreated(message.getDateCreated());
			User user = userService.findUserById(message.getUser().getId());
			retrievedMessage.setUser(user);
			ticket.addMessage(retrievedMessage);
			ticketService.saveTicket(ticket);
			return new ResponseEntity<>(ticket, HttpStatus.OK);
		} else {
			logger.warn("Ticket not found: ticket id: {}", ticketId);
			return new ResponseEntity<>("Ticket not found", HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Delete a message from a ticket by ticket id and message id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ticket and message found"),
			@ApiResponse(responseCode = "404", description = "Ticket or message id not found"), })
	@DeleteMapping("/deleteMessage/{ticketId}/{messageId}")
	public ResponseEntity<?> deleteMessageByTicketIdAndMessageId(@PathVariable Long ticketId,
			@PathVariable Long messageId) {
		if (ticketService.findTicketById(ticketId) != null && messageService.findMessageById(messageId) != null) {
			logger.info("Deleting message: {} from ticket: {}", messageId, ticketId);
			Ticket ticket = ticketService.findTicketById(ticketId);
			Message message = messageService.findMessageById(messageId);
			ticket.removeMessage(message);
			ticketService.saveTicket(ticket);
			return new ResponseEntity<>(ticket, HttpStatus.OK);
		} else {
			logger.warn("Ticket or message not found: ticket id: {} message id: {}", ticketId, messageId);
			return new ResponseEntity<>("Ticket id or message id not found", HttpStatus.NOT_FOUND);
		}
	}

}
