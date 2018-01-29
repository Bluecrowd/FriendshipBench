package nl.friendshipbench.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Created by Jan-Bert on 23-1-2018.
 */
@Entity
public class Answer
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private Boolean answer;

	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(referencedColumnName = "id")
	private Question question;

	public Question getQuestion()
	{
		return question;
	}

	public void setQuestion(Question question)
	{
		this.question = question;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Boolean getAnswer()
	{
		return answer;
	}

	public void setAnswer(Boolean answer)
	{
		this.answer = answer;
	}
}
