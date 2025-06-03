import React, { useState, useEffect } from 'react';
import { checkOwnerRequest } from '../../services/api';
import './PollInfoBox.css';

export default function PollInfoBox({ poll, id, token, updatePoll }) {

    const [showSettings, setShowSettings] = useState(false);
    const [publicity, setPublicity] = useState(poll.publicity);
    const [close, setClose] = useState(poll.close);
    const [showResults, setShowResults] = useState(poll.showResults);
    const [showEdit, setShowEdit] = useState(false);

    useEffect(() => {

        const checkOwner = async () => {
            try {
                const response = await checkOwnerRequest(id, token);
                if(response){
                    setShowEdit(true);
                }
            } catch (err) {
                console.error('Failed to check if user owns the poll.', err);
            }
        };

        checkOwner();

    },[]);

    function getDate(fullDateTime){
        let birthDate = new Date(fullDateTime);
        let formattedDate = birthDate.toLocaleDateString();
        return formattedDate;
    }
    
    function getHour(fullDateTime){
        let birthDate = new Date(fullDateTime);
        let formattedTime = birthDate.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
        return formattedTime;
    }

    return(
        <div className = "poll-box">
            <div className = "edit-div">
                {showEdit && (
                    <button className = "edit-button" onClick={() => setShowSettings(!showSettings)}><strong>Edit</strong></button>
                )}
                {showSettings && (
                  <div className="edit-menu">
                    <ul>
                        <li>
                            <label>Make Poll {publicity ? 'Public' : 'Private'}</label>
                            <input 
                                type="checkbox" 
                                id="publicity" 
                                name="publicity" 
                                checked={publicity}
                                onChange={(e) => setPublicity(e.target.checked)}/>
                        </li>
                        { poll.open && (
                        <li>
                            <label>Close Poll Forever</label>
                            <input 
                                type="checkbox" 
                                id="close" 
                                name="close" 
                                checked={close}
                                onChange={(e) => setClose(e.target.checked)}/>
                        </li>
                        )}
                        <li>
                            <label>Show Vote Results</label>
                            <input 
                                type="checkbox" 
                                id="showResults" 
                                name="showResults"
                                checked={showResults}
                                onChange={(e) => setShowResults(e.target.checked)}/>
                        </li>
                    </ul>
                    <button className = "update-button" onClick = {() => {updatePoll(close, showResults, publicity); setShowSettings(false);}}><b>Update</b></button>
                    <button className="close-edit-menu" onClick={() => setShowSettings(false)}><strong>Close</strong></button>
                  </div>
                )}
            </div>
            <h1 className="poll-caption">{poll.caption}</h1>
            <hr/>
            <p className="poll-description">{poll.pollText}</p>
            <hr/>
            <div className="poll-meta">
                <div>Created By: <strong>{poll.creatorName}</strong></div>
                <div>Votes: <strong>{poll.voterCount}</strong></div>
                <div>Status: <strong>{poll.open ? 'Active' : 'Closed'}</strong></div>
                <div>
                    Created: <strong>{getDate(poll.birthTime)}</strong> - 
                    Closes: <strong>{poll.closeTime ? `${getDate(poll.closeTime)} at ${getHour(poll.closeTime)} UTC` : 'Until Owner Disables Poll'}</strong>
                </div>
                <div>{poll.publicity ? 'Public Poll' : 'Private Poll'}</div>
                <div>{poll.platformOnly ? 'Account Required To Vote' : 'Everyone Can Vote'}</div>
            </div>
        </div>
    );
}