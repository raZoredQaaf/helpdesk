package com.fdmgroup.helpdeskapi.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fdmgroup.helpdeskapi.model.Ticket;
import com.fdmgroup.helpdeskapi.repository.TicketRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketServiceImp implements TicketService {

	Logger logger = LoggerFactory.getLogger(TicketServiceImp.class);

	@NonNull
	TicketRepository ticketRepository;

	@Override
	public Ticket saveTicket(Ticket ticket) {
		logger.info("TicketServiceImp - Saving ticket: {}", ticket);
		return ticketRepository.save(ticket);
	}

	@Override
	public List<Ticket> findAllTickets() {
		logger.info("TicketServiceImp - Finding all tickets");
		return ticketRepository.findAll();
	}

	@Override
	public Ticket findTicketById(long id) {
		logger.info("TicketServiceImp - Finding ticket by id: {}", id);
		return ticketRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteTicketById(long id) {
		logger.info("TicketServiceImp - Deleting ticket by id: {}", id);
		ticketRepository.deleteById(id);
	}

	@Override
	public List<Ticket> findTicketsByEngineerId(Long id) {
		logger.info("TicketServiceImp - Finding tickets by engineer id: {}", id);
		return ticketRepository.findTicketsByEngineerId(id);
	}

	@Override
	public List<Ticket> findTicketsByClientId(Long id) {
		logger.info("TicketServiceImp - Finding tickets by client id: {}", id);
		return ticketRepository.findTicketsByClientId(id);
	}

	@Override
	public List<Ticket> findAllUnassignedTickets() {
		logger.info("TicketServiceImp - Finding all unassigned tickets");
		return ticketRepository.findTicketsByEngineerIdIsNullAndResolvedFalse();
	}

}
