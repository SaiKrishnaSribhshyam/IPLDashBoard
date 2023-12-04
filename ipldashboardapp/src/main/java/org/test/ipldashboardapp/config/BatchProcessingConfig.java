package org.test.ipldashboardapp.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;
import org.test.ipldashboardapp.data.MatchDataProcessor;
import org.test.ipldashboardapp.data.MatchInput;
import org.test.ipldashboardapp.models.Match;

import javax.sql.DataSource;
import java.util.Arrays;


@Configuration
public class BatchProcessingConfig {
    private final String[] fields=new String[]{"ID","City","Date","Season","MatchNumber","Team1","Team2","Venue","TossWinner","TossDecision","SuperOver","WinningTeam","WonBy","Margin","method","Player_of_Match","Team1Players","Team2Players","Umpire1","Umpire2"};

    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Bean
    public FlatFileItemReader<MatchInput> getReader(){
        return new FlatFileItemReaderBuilder<MatchInput>()
                .name("MatchDataReader")
                .resource(new ClassPathResource("IPL_Matches_2008_2022.csv"))
                .delimited()
                .names(fields)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<MatchInput>(){
                    {
                        setTargetType(MatchInput.class);
                    }
                })
                .linesToSkip(1)
                .build();
    }

    @Bean
    public MatchDataProcessor getProcessor(){
        return new MatchDataProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Match> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Match>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO match (ID,CITY,DATE,TEAM1,TEAM2,VENUE,TOSS_WINNER,TOSS_DECISION,WINNING_TEAM,PLAYER_OF_MATCH," +
                        "TEAM1PLAYERS,TEAM2PLAYERS,UMPIRE1,UMPIRE2) VALUES (:id, :city, :date, :team1, :team2, :venue, " +
                        ":tossWinner, :tossDecision, :winningTeam, :playerOfMatch, :team1Players, :team2Players, :umpire1, :umpire2)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importMatchJob( JobCompletionListener listener, Step step1) {
        return jobBuilderFactory.get("importMatchJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Match> writer) {
        return stepBuilderFactory.get("step1")
                .<MatchInput, Match>chunk(10)
                .reader(getReader())
                .processor(getProcessor())
                .writer(writer)
                .build();
    }
}




