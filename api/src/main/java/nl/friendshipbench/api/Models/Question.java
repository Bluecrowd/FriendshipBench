package nl.friendshipbench.api.models;

import javax.persistence.*;

/**
 * Created by Jan-Bert on 22-1-2018.
 */
@Entity
public class Question
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String question_text;

	@Column(nullable = false)
	private Boolean active;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getQuestion_text()
	{
		return question_text;
	}

	public void setQuestion_text(String question_text)
	{
		this.question_text = question_text;
	}

	public Boolean getActive()
	{
		return active;
	}

	public void setActive(Boolean active)
	{
		this.active = active;
	}
}
