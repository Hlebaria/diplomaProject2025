import React, { useEffect, useContext } from 'react';
import UserInfo from './UserInfo';
import UserActivities from './UserActivities';
import { Context } from "../../Context";
import { useNavigate } from 'react-router-dom';
import './UserPage.css';

export default function UserPage() {

    const context = useContext(Context);

    const navigate = useNavigate(); 

    useEffect(() => {

        if(!context.kc.token){
            navigate(`/`);
        }

    },[]);

    return (
        <div className="user-container">
            <UserInfo/>
            <UserActivities token={context.kc.token}/>
        </div>
    );
}