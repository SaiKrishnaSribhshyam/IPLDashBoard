package org.test.ipldashboardapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.test.ipldashboardapp.models.Match;
import org.test.ipldashboardapp.models.Team;
import org.test.ipldashboardapp.repos.MatchRepository;
import org.test.ipldashboardapp.repos.TeamRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class TeamService {
    private TeamRepository teamRepository;
    private MatchRepository matchRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository,MatchRepository matchRepository){
        this.teamRepository=teamRepository;
        this.matchRepository=matchRepository;
    }

    public Team getTeamByName(String teamName){
        Team team= teamRepository.findTeamByTeamName(teamName);
        Pageable pageable= PageRequest.of(0,5);
        team.setMatchList(matchRepository.findMatchByTeam1OrTeam2OrderByDateDesc(teamName,teamName,pageable));
        return team;
    }

    public List<Match> getMatchesByTeamAndYear(String teamName, int year){
        LocalDate startDate=LocalDate.of(year,1,1);
        LocalDate endDate=LocalDate.of(year+1,1,1);

        return matchRepository.findMatchByTeamNameAndDateRange(teamName,startDate,endDate);
    }
}
