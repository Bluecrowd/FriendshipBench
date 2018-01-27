package nl.friendshipbench.api.controllers;

import nl.friendshipbench.api.enums.AppointmentStatusEnum;
import nl.friendshipbench.api.models.Appointment;
import nl.friendshipbench.api.models.Client;
import nl.friendshipbench.api.models.HealthWorker;
import nl.friendshipbench.api.repositories.*;
import nl.friendshipbench.oauth2.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jan-Bert on 27-1-2018.
 */
@RestController
public class AppointmentController
{
	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private HealthworkerRepository healthworkerRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private BenchRepository benchRepository;

	@CrossOrigin
	@GetMapping(value = "/appointments")
	public ResponseEntity<Iterable<Appointment>> getAllAppointmentsIHave() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

		Iterable<Appointment> appointments = null;

		for( GrantedAuthority authority : principal.getAuthorities())
		{
			if (authority.toString().equals("ROLE_HEALTHWORKER"))
			{
				HealthWorker healthworker = healthworkerRepository.findByUsername(principal.getUsername());
				appointments = appointmentRepository.findByHealthWorker(healthworker);
				break;
			}
			if (authority.toString().equals("ROLE_CLIENT"))
			{
				Client client = clientRepository.findByUsername(principal.getUsername());
				appointments = appointmentRepository.findByClient(client);
				break;
			}
		}

		return new ResponseEntity<>(appointments, HttpStatus.OK);
	}

	@CrossOrigin
	@PostMapping(value = "/appointments")
	public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

		for( GrantedAuthority authority : principal.getAuthorities())
		{
			if (authority.toString().equals("ROLE_HEALTHWORKER"))
			{
				HealthWorker healthworker = healthworkerRepository.findByUsername(principal.getUsername());
				appointment.healthWorker = healthworker;
				break;
			}
			if (authority.toString().equals("ROLE_CLIENT"))
			{
				Client client = clientRepository.findByUsername(principal.getUsername());
				appointment.client = client;
				break;
			}
		}


		appointment.status = AppointmentStatusEnum.PENDING;

		appointmentRepository.save(appointment);

		return new ResponseEntity<Appointment>(appointmentRepository.findOne(appointment.id), HttpStatus.CREATED);
	}

	@CrossOrigin
	@PutMapping(value = "/appointments/{id}")
	public ResponseEntity<Appointment> updateAppointment(@PathVariable("id") long id, @RequestBody Appointment appointment) {
		appointment.id = id;

		appointmentRepository.save(appointment);

		return new ResponseEntity<Appointment>(appointmentRepository.findOne(id), HttpStatus.OK);
	}

	@CrossOrigin
	@PutMapping(value = "/appointments/{id}/accept")
	public ResponseEntity<Appointment> setAccepted(@PathVariable("id") long id)
	{
		Appointment appointment = appointmentRepository.findOne(id);
		appointment.status = AppointmentStatusEnum.ACCEPTED;
		appointmentRepository.save(appointment);

		return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
	}

	@CrossOrigin
	@PutMapping(value = "/appointments/{id}/cancel")
	public ResponseEntity<Appointment> setCancelled(@PathVariable("id") long id)
	{
		Appointment appointment = appointmentRepository.findOne(id);
		appointment.status = AppointmentStatusEnum.CANCELLED;
		appointmentRepository.save(appointment);

		return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
	}

}
