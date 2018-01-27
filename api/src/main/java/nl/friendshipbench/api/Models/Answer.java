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
	public Long id;

	public Boolean answer;

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
}
