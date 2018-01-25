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
	public Long id;

	public OffsetDateTime timestamp;

	public Boolean redflag;

	@OneToMany(cascade=CascadeType.ALL)
	public List<Answer> answers;

}
