package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.JoinTable;
import java.util.Collection;

@Entity
public class GroupContact {
	@Id
	private int id;
	
	private int groupId;
	
	private int contactId;
}
