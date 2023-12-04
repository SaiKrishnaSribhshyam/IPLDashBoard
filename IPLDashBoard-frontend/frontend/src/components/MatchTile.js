import React from "react";
import { Link } from 'react-router-dom'

export const MatchTile = ({match, teamName}) => {
    let otherTeam= match.team1===teamName?match.team2:match.team1;
    let otherTeamLink=`/teams/${otherTeam}`;
    let isThisTeamWon= (match.winningTeam===teamName);
    return (
        <div className={isThisTeamWon?'wonMatchTile' : 'lostMatchTile'} id={match.id}>
            <h2> vs <Link to={otherTeamLink}>{otherTeam} </Link> </h2>
            <p> held at {match.venue} on {match.date} </p>
            <p> won by {match.winningTeam} </p>
        </div>
    );
}