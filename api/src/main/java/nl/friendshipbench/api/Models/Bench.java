package nl.friendshipbench.api.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Jan-Bert on 22-1-2018.
 */
@Entity
public class Bench
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	
	public String streetname;

	public String housenumber;

	public String province;

	public String district;
}
