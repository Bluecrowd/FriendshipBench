package nl.friendshipbench.api.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

	@JsonIgnoreProperties({"question_text", "active", "question_order"})
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(referencedColumnName = "id")
	public Question question;
}
