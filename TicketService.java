package com.fdmgroup.helpdeskapi.service;

import java.util.List;

import com.fdmgroup.helpdeskapi.model.Ticket;

public interface TicketService {

	Ticket saveTicket(Ticket ticket);

	List<Ticket> findAllTickets();

	Ticket findTicketById(long id);

	void deleteTicketById(long id);

	List<Ticket> findTicketsByClientId(Long id);

	List<Ticket> findTicketsByEngineerId(Long id);

	List<Ticket> findAllUnassignedTickets();

}
