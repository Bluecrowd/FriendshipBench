package nl.friendshipbench.api.controllers;

import nl.friendshipbench.api.Helpers.UserHelper;
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
import org.springframework.security.core.GrantedAuthority;
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

	private UserHelper userHelper = new UserHelper();

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
		CustomUserDetails principal = userHelper.principalHelper();

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
		CustomUserDetails principal = userHelper.principalHelper();

		Appointment appointment = appointmentRepository.findOne(id);

		for( GrantedAuthority authority : principal.getAuthorities())
		{
			if (authority.toString().equals("ROLE_HEALTHWORKER"))
			{
				HealthWorker healthworker = healthworkerRepository.findByUsername(principal.getUsername());
				if (appointment.getHealthWorker().equals(healthworker))
				{
					break;
				}
				else
				{
					return new ResponseEntity<>(HttpStatus.FORBIDDEN);
				}
			}
			if (authority.toString().equals("ROLE_CLIENT"))
			{
				Client client = clientRepository.findByUsername(principal.getUsername());
				if (appointment.getClient().equals(client))
				{
					break;
				}
				else
				{
					return new ResponseEntity<>(HttpStatus.FORBIDDEN);
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
		CustomUserDetails principal = userHelper.principalHelper();

		for( GrantedAuthority authority : principal.getAuthorities())
		{
			if (authority.toString().equals("ROLE_HEALTHWORKER"))
			{
				appointment.setHealthWorker(healthworkerRepository.findByUsername(principal.getUsername()));
				break;
			}
			if (authority.toString().equals("ROLE_CLIENT"))
			{
				appointment.setClient(clientRepository.findByUsername(principal.getUsername()));
				break;
			}
		}


		appointment.setStatus(AppointmentStatusEnum.PENDING);

		appointmentRepository.save(appointment);

		return new ResponseEntity<>(appointmentRepository.findOne(appointment.getId()), HttpStatus.CREATED);
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
		appointment.setId(id);

		appointmentRepository.save(appointment);

		return new ResponseEntity<>(appointmentRepository.findOne(id), HttpStatus.OK);
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
		appointment.setStatus(AppointmentStatusEnum.ACCEPTED);
		appointmentRepository.save(appointment);

		return new ResponseEntity<>(appointment, HttpStatus.OK);
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
		appointment.setStatus(AppointmentStatusEnum.CANCELLED);
		appointmentRepository.save(appointment);

		return new ResponseEntity<>(appointment, HttpStatus.OK);
	}
}
