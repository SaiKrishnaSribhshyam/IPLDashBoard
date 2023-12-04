package org.test.ipldashboardapp.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.test.ipldashboardapp.models.Team;

import java.util.List;
@Repository
public interface TeamRepository extends CrudRepository<Team, Integer> {
    Team findTeamById(int id);
    Team findTeamByTeamName(String teamName);
    //List<Team> findAllTeams();
}
