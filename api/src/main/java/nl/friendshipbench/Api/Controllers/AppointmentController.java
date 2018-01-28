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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Appointment controller defines all possible HTTP methods for the appointments
 *
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

	/**
	 * This method will return all the appointments a logged in user has
	 * user can be either healthworker or client
	 *
	 * @method HTTP GET
	 * @endpoint /api/appointments
	 * @return all apointments bound to a healthworker or client
	 */
	@CrossOrigin
	@PreAuthorize("hasAuthority('ROLE_CLIENT') or hasAuthority('ROLE_HEALTHWORKER')")
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

	/**
	 * Get a specific appointment the logged in user has
	 *
	 * @method HTTP GET
	 * @endpoint /api/appointments/{id}
	 * @param id
	 * @return specific appointment
	 */
	@CrossOrigin
	@PreAuthorize("hasAuthority('ROLE_CLIENT') or hasAuthority('ROLE_HEALTHWORKER')")
	@GetMapping(value = "/appointments/{id}")
	public ResponseEntity<Appointment> getAppointmentById(@PathVariable("id") long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

		Appointment appointment = appointmentRepository.findOne(id);

		for( GrantedAuthority authority : principal.getAuthorities())
		{
			if (authority.toString().equals("ROLE_HEALTHWORKER"))
			{
				HealthWorker healthworker = healthworkerRepository.findByUsername(principal.getUsername());
				if (appointment.healthWorker.equals(healthworker))
				{
					break;
				}
				else
				{
					return new ResponseEntity<Appointment>(HttpStatus.FORBIDDEN);
				}
			}
			if (authority.toString().equals("ROLE_CLIENT"))
			{
				Client client = clientRepository.findByUsername(principal.getUsername());
				if (appointment.client.equals(client))
				{
					break;
				}
				else
				{
					return new ResponseEntity<Appointment>(HttpStatus.FORBIDDEN);
				}
			}
		}

		return new ResponseEntity<>(appointment, HttpStatus.OK);
	}

	/**
	 * This method makes it possible for either a healthworker or client to create a appointment
	 *
	 * @method HTTP POST
	 * @endpoint /api/appointments
	 * @param appointment
	 * @return Response
	 */
	@CrossOrigin
	@PreAuthorize("hasAuthority('ROLE_CLIENT') or hasAuthority('ROLE_HEALTHWORKER')")
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

	/**
	 * This method makes it possible to update a specific appointment
	 *
	 * @method HTTP PUT
	 * @endpoint /api/appointments/{id}
	 * @param id
	 * @param appointment
	 * @return
	 */
	@CrossOrigin
	@PreAuthorize("hasAuthority('ROLE_CLIENT') or hasAuthority('ROLE_HEALTHWORKER')")
	@PutMapping(value = "/appointments/{id}")
	public ResponseEntity<Appointment> updateAppointment(@PathVariable("id") long id, @RequestBody Appointment appointment) {
		appointment.id = id;

		appointmentRepository.save(appointment);

		return new ResponseEntity<Appointment>(appointmentRepository.findOne(id), HttpStatus.OK);
	}

	/**
	 * This method makes it possible to accept a planned appointment
	 *
	 * @method HTTP PUT
	 * @endpoint /api/appointments/{id}/accept
	 * @param id
	 * @return response
	 */
	@CrossOrigin
	@PreAuthorize("hasAuthority('ROLE_CLIENT') or hasAuthority('ROLE_HEALTHWORKER')")
	@PutMapping(value = "/appointments/{id}/accept")
	public ResponseEntity<Appointment> setAccepted(@PathVariable("id") long id)
	{
		Appointment appointment = appointmentRepository.findOne(id);
		appointment.status = AppointmentStatusEnum.ACCEPTED;
		appointmentRepository.save(appointment);

		return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
	}

	/**
	 * This method makes it possible to cancel a specific appointment
	 *
	 * @method HTTP PUT
	 * @endpoint /api/appointments/{id}/cancel
	 * @param id
	 * @return
	 */
	@CrossOrigin
	@PreAuthorize("hasAuthority('ROLE_CLIENT') or hasAuthority('ROLE_HEALTHWORKER')")
	@PutMapping(value = "/appointments/{id}/cancel")
	public ResponseEntity<Appointment> setCancelled(@PathVariable("id") long id)
	{
		Appointment appointment = appointmentRepository.findOne(id);
		appointment.status = AppointmentStatusEnum.CANCELLED;
		appointmentRepository.save(appointment);

		return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
	}
}
