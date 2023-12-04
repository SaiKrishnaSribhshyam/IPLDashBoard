package org.test.ipldashboardapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.test.ipldashboardapp.models.Team;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;

@Component
public class JobCompletionListener implements JobExecutionListener {

    private final EntityManager entityManager;
    private final Logger logger= LoggerFactory.getLogger(JobCompletionListener.class);

    @Autowired
    public JobCompletionListener(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("Batch Job started");
    }

    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        logger.info("Batch Job ENDED");
        HashMap<String,Team> teamsInfo=new HashMap<>();
        entityManager.createQuery("select distinct m.team1,count(m.team1) from Match m group by m.team1",Object[].class)
                .getResultList()
                .stream()
                .map(e -> new Team((String) e[0],((Long)e[1]).intValue()))
                .forEach(team -> teamsInfo.put(team.getTeamName(),team));

        entityManager.createQuery("select distinct m.team2,count(m.team2) from Match m group by m.team2",Object[].class)
                .getResultList()
                .stream()
                .forEach(e->  {
                    int currMatches=teamsInfo.get((String) e[0]).getTotalMatches();
                    teamsInfo.get((String) e[0]).setTotalMatches(currMatches+((Long)e[1]).intValue());
                });
        entityManager.createQuery("select distinct m.winningTeam,count(m.winningTeam) from Match m group by m.winningTeam",Object[].class)
                .getResultList()
                .stream()
                .forEach(e -> {
                    if(teamsInfo.containsKey((String) e[0]))
                        teamsInfo.get((String)e[0]).setTotalWins(((Long)e[1]).intValue());
                });

        teamsInfo.values()
                .stream()
                .forEach(e -> entityManager.persist(e));
    }
}
