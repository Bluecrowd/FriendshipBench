package nl.friendshipbench.api.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.OffsetDateTime;

/**
 * Created by Jan-Bert on 29-1-2018.
 */
@Entity
public class Messages
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String user_id;

	@Column(nullable = false)
	private String user_name;

	@Type(type="blob")
	private String message;

	@Column(nullable = false)
	private OffsetDateTime time;

	@Column(nullable = false)
	private String room;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getUser_id()
	{
		return user_id;
	}

	public void setUser_id(String user_id)
	{
		this.user_id = user_id;
	}

	public String getUser_name()
	{
		return user_name;
	}

	public void setUser_name(String user_name)
	{
		this.user_name = user_name;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public OffsetDateTime getTime()
	{
		return time;
	}

	public void setTime(OffsetDateTime time)
	{
		this.time = time;
	}

	public String getRoom()
	{
		return room;
	}

	public void setRoom(String room)
	{
		this.room = room;
	}
}
