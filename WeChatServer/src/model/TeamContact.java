package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "team_contact")
public class TeamContact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int    id;
	
	private int teamId;
	
	private int contactId;	
	
	public TeamContact() {}
	
	public TeamContact(int teamId, int contactId)
	{
		this.teamId = teamId;
		this.contactId = contactId;
	}
	
	public int getTeamId()
	{
		return teamId;
	}
	
	public void setTeamId(int teamId)
	{
		this.teamId = teamId;
	}
	
	public int getContactId()
	{
		return this.contactId;
	}
	
	public void setContactId(int contactId)
	{
		this.contactId = contactId;
	}
	
	public String toString()
	{
		return "Contact Id : " + this.contactId + " Team Id: " + this.teamId;
	}
}
