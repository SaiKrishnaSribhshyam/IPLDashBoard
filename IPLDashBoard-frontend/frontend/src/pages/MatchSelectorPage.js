import React from "react";
import { Select } from "react-dropdown-select";
import "./MatchSelectorPage.css"
import { useState } from 'react'
import { useNavigate } from "react-router-dom";

export let MatchSelectorPage = () => {
    let options1= [
        {
            id: 1,
            value: 'Sunrisers Hyderabad'
        }
    ];

    let options2 = [
        {
            id:1,
            year: 2005
        }
    ]
    let [ value,setValue ]=useState('selectTeam');
    let [ year,setYear ]=useState(2005);
    let navigate = useNavigate();
    let redirect= () => {
        navigate(`/teams/${value}/matches/${year}`);
    }
    return (
        <div className="selector"> 
            <div className="teamNameSelector"> 
                <Select options={options1} 
                        defaultValue={value}
                        labelField="value" 
                        valueField="id"
                        onChange={(value) => setValue(value) }></Select>
            </div>
            <div className="yearSelector"> 
                <Select options={options2} 
                        defaultValue={year}
                        labelField="year" 
                        valueField="id"
                        onChange={(value) => setYear(value) }></Select>                
            </div>
            <button onClick={redirect} > GetMatches</button>
        </div>
    );
}