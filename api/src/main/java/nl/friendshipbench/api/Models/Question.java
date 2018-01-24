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
	public Long id;

	public String question_text;

	public Boolean active;

	@Column(unique = true)
	public Long question_order;
}
