package org.test.ipldashboardapp.data;

import java.time.LocalDate;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.test.ipldashboardapp.models.Match;

public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {
    public final Logger logger=LoggerFactory.logger(MatchDataProcessor.class);

    @Override
    public Match process(MatchInput matchInput) throws Exception {
        // TODO Auto-generated method stub
        Match match=new Match();
        match.setCity(matchInput.getCity());
        match.setDate(LocalDate.parse(matchInput.getDate()));
        match.setId(Long.valueOf(matchInput.getId()));
        match.setTeam1(matchInput.getTeam1());
        match.setTeam2(matchInput.getTeam2());
        match.setPlayerOfMatch(matchInput.getPlayer_of_Match());
        match.setTeam1Players(matchInput.getTeam1Players());
        match.setTeam2Players(matchInput.getTeam2Players());
        match.setWinningTeam(matchInput.getWinningTeam());
        match.setTossDecision(matchInput.getTossDecision());
        match.setTossWinner(matchInput.getTossWinner());
        match.setUmpire1(matchInput.getUmpire1());
        match.setUmpire2(matchInput.getUmpire2());
        match.setVenue(matchInput.getVenue());

        return match;

        //throw new UnsupportedOperationException("Unimplemented method 'process'");
    }

}