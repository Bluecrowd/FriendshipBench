package nl.friendshipbench.api.models;

import javax.persistence.*;

/**
 * Created by Jan-Bert on 22-1-2018.
 */
@Entity
public class Bench
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String streetname;

	private String housenumber;

	private String province;

	private String district;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getStreetname()
	{
		return streetname;
	}

	public void setStreetname(String streetname)
	{
		this.streetname = streetname;
	}

	public String getHousenumber()
	{
		return housenumber;
	}

	public void setHousenumber(String housenumber)
	{
		this.housenumber = housenumber;
	}

	public String getProvince()
	{
		return province;
	}

	public void setProvince(String province)
	{
		this.province = province;
	}

	public String getDistrict()
	{
		return district;
	}

	public void setDistrict(String district)
	{
		this.district = district;
	}
}
