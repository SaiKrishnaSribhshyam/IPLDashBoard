import React from "react";
import { useEffect,useState } from "react";
import { MatchTile } from "../components/MatchTile";
import { MatchDetailHeader } from "../components/MatchDetailHeader";
import { useParams } from 'react-router-dom'
import "./Team.css"
import { PieChart } from "react-minimal-pie-chart"


export const TeamPage = () => {
    const [team,setTeam]=useState({matchList:[10]});
    let { teamName }=useParams();
    console.log( "teamName in path " + teamName);

    useEffect (() => {
        const apiCall = async () => {
            const response= await fetch(`http://localhost:8080/team/${teamName}`);
            const team = await response.json();
            setTeam(team);
        }
        apiCall();
    },[teamName])
    
    if(!team || !team.id)
        return (
            <h1> Team not found</h1>
        );

    return (
        <div className="TeamPage">
            <div className="teamName"> 
                <h1>{team.teamName}</h1> 
            </div>
            <div class="winLoss">
                Wins/Losses
                <PieChart
                    data={[
                    { title: 'Wins', value: team.totalWins, color: '#03fc9d' },
                    { title: 'Losses', value: team.totalMatches-team.totalWins, color: 'indianred' },
                    ]}
                />;
            </div>
            
            <MatchDetailHeader />
            
            {team.matchList.map( match => <MatchTile match={match} teamName={team.teamName}/>)}
            <div> <a href="a">More</a> </div>
        </div>
    );
}