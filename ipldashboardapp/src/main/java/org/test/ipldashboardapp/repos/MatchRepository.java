package org.test.ipldashboardapp.repos;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.test.ipldashboardapp.models.Match;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface MatchRepository extends CrudRepository<Match,Integer> {
    List<Match> findMatchByTeam1OrTeam2OrderByDateDesc(String team1, String team2, Pageable pageable);

    @Query("select m from Match m where (m.team1=:teamName or m.team2=:teamName) and m.date between :startDate and :endDate")
    List<Match> findMatchByTeamNameAndDateRange(@Param("teamName") String teamName, @Param("startDate") LocalDate startDate, @Param("endDate")  LocalDate endDate);
}
