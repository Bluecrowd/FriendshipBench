package nl.friendshipbench.api.models;

import nl.friendshipbench.api.enums.AppointmentStatusEnum;

import javax.persistence.*;
import java.time.OffsetDateTime;

/**
 * Created by Jan-Bert on 27-1-2018.
 */
@Entity
public class Appointment
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	public OffsetDateTime timestamp;

	@Enumerated(EnumType.ORDINAL)
	public AppointmentStatusEnum status;

	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(referencedColumnName = "id")
	public Bench bench;

	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(referencedColumnName = "id")
	public Client client;

	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(referencedColumnName = "id")
	public HealthWorker healthWorker;

}
