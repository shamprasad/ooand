package modeltest;

import java.util.List;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import model.*;

public class TestMain {
	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Spring.xml");
		
		ContactDAO contactDAO = context.getBean(ContactDAO.class);
		
		Contact contact = new Contact("GOOSE");
		contactDAO.save(contact);
		
		List<Contact> list = contactDAO.list();
		
		for(Contact c : list){
			System.out.println("Contact List from database via Hibernate:: " + c.getName());
		}
		
		TeamDAO teamDAO = context.getBean(TeamDAO.class);
		Team team = new Team("Team1");
		teamDAO.save(team);
		
		List<Team> teamList = teamDAO.list();
		
		for(Team t: teamList)
		{
			System.out.println("Group List from database via Hibernate:: " + t.getName());
		}
		
		TeamContactDAO teamContactDAO = context.getBean(TeamContactDAO.class);
		TeamContact teamContact = new TeamContact(1, 1);
		
		teamContactDAO.save(teamContact);
		
		List<TeamContact> teamContactList = teamContactDAO.list();
		
		for(TeamContact tc: teamContactList)
		{
			System.out.println("TeamConact list from database via hibernate: " + tc);
		}
		
		List<Contact> contactList = teamContactDAO.listContact(1);
		
		for(Contact c: contactList)
		{
			System.out.println("TeamConact list from database via hibernate: " + c.toString());
		}
		
		context.close();
		
	}
}



