package nl.friendshipbench.api.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Created by Jan-Bert on 23-1-2018.
 */
@Entity
public class Questionnaire
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private OffsetDateTime timestamp;

	private Boolean redflag;

	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(referencedColumnName = "id")
	private Client client;

	@OneToMany(cascade=CascadeType.ALL)
	private List<Answer> answers;

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

	public Boolean getRedflag()
	{
		return redflag;
	}

	public void setRedflag(Boolean redflag)
	{
		this.redflag = redflag;
	}

	public Client getClient()
	{
		return client;
	}

	public void setClient(Client client)
	{
		this.client = client;
	}

	public List<Answer> getAnswers()
	{
		return answers;
	}

	public void setAnswers(List<Answer> answers)
	{
		this.answers = answers;
	}
}
