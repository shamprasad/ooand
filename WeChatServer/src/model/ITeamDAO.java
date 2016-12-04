package model;

import java.util.List;

interface ITeamDAO {
	void save(Team group);
	
	List<Team> list();

	List<Team> listTeam(int userId);
}
