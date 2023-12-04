import React from "react";
import {useState, useEffect} from 'react'
import { MatchTile } from "../components/MatchTile";
import { useParams } from "react-router-dom";

export const MatchPage = () => {
    let [matchData,setMatchData]=useState([]);
    let {teamName, year}=useParams();
    
    useEffect(() => {
        const apiCall = async () => {
            const response= await fetch(`http://localhost:8080/team/${teamName}/matches?year=${year}`);
            const match=await response.json();
            setMatchData(match);
        }
        apiCall();
    },[matchData]);

    return (
        <div className="MatchPage">
            <h1>Match Page</h1>
            { matchData.map( match =>  <MatchTile teamName={teamName} match={match} /> )}
        </div>
    );
}