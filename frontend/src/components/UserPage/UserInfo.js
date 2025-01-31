import React, { useEffect, useState, useContext } from 'react';
import { pollsGetRequest } from '../../services/api.js';
import { Context } from "../../Context";
import { useNavigate } from 'react-router-dom';
import './UserInfo.css'

export default function UserInfo() {

    const context = useContext(Context);

    return (
        <div className="user-info">
            <h2>User Information</h2>
            <hr/>
            <p><strong>Username:</strong> {context.user.username}</p>
            <p><strong>Email:</strong> {context.user.email}</p>
        </div>
    );
}


