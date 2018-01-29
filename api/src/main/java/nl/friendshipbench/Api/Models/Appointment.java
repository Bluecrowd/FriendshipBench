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
	private Long id;

	private OffsetDateTime timestamp;

	@Enumerated(EnumType.ORDINAL)
	private AppointmentStatusEnum status;

	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(referencedColumnName = "id")
	private Bench bench;

	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(referencedColumnName = "id")
	private Client client;

	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(referencedColumnName = "id")
	private HealthWorker healthWorker;


	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public OffsetDateTime getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(OffsetDateTime timestamp)
	{
		this.timestamp = timestamp;
	}

	public AppointmentStatusEnum getStatus()
	{
		return status;
	}

	public void setStatus(AppointmentStatusEnum status)
	{
		this.status = status;
	}

	public Bench getBench()
	{
		return bench;
	}

	public void setBench(Bench bench)
	{
		this.bench = bench;
	}

	public Client getClient()
	{
		return client;
	}

	public void setClient(Client client)
	{
		this.client = client;
	}

	public HealthWorker getHealthWorker()
	{
		return healthWorker;
	}

	public void setHealthWorker(HealthWorker healthWorker)
	{
		this.healthWorker = healthWorker;
	}
}
