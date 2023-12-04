package org.test.ipldashboardapp.controllers;

import org.springframework.web.bind.annotation.*;
import org.test.ipldashboardapp.models.Match;
import org.test.ipldashboardapp.models.Team;
import org.test.ipldashboardapp.services.TeamService;

import java.util.List;


@RestController
@RequestMapping("/team")
@CrossOrigin
public class TeamController {

    private TeamService teamService;
    public TeamController(TeamService teamService){
        this.teamService=teamService;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/{teamName}")
    public Team getTeamByName(@PathVariable(name = "teamName") String teamName){
        if(teamName !=null)
            return teamService.getTeamByName(teamName);

        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value= "/{teamName}/matches")
    public List<Match> getMatchesByTeamAndYear(@PathVariable(name = "teamName") String teamName, @RequestParam int year){
        return teamService.getMatchesByTeamAndYear(teamName,year);
    }
}
