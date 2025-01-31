import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './UserActivities.css';
import { getUserOwnedPollsRequest, getUserTakenPollsRequest} from '../../services/api.js';

export default function UserActivities( {token} ) {

    const [ownedPolls, setOwnedPolls] = useState([]);
    const [votedPolls, setVotedPolls] = useState([]);
    const [showOwnedPolls, setShowOwnedPolls] = useState(false);
    const [showVotedPolls, setShowVotedPolls] = useState(true);
    const [loading, setLoading] = useState(false);

    const navigate = useNavigate(); 

    useEffect(() => {

        const fetchOwnedPolls = async ({token}) => {

            setLoading(true);
    
            try {
    
                const data = await getUserOwnedPollsRequest(token);
                setOwnedPolls(data);
    
            } catch (error) {
                console.error("Failed to fetch polls:", error);
            } finally {
                setLoading(false);
            }
    
        }
    
        const fetchVotedPolls = async ({token}) => {
    
            setLoading(true);
    
            try {
        
                const data = await getUserTakenPollsRequest(token);
                setVotedPolls(data);
    
            } catch (error) {
                console.error("Failed to fetch polls:", error);
            } finally {
                setLoading(false);
            }
    
        }
        
        fetchOwnedPolls({ token });
        fetchVotedPolls({ token });


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

    return (
        <div className="user-activities">
            <div className="activities-header">
                <button 
                    className={showVotedPolls ? 'active' : ''} 
                    onClick={() => {setShowOwnedPolls(false); setShowVotedPolls(true);}}
                >
                    Taken Polls
                </button>
                <button 
                    className={showOwnedPolls ? 'active' : ''} 
                    onClick={() => {setShowOwnedPolls(true); setShowVotedPolls(false);}}
                >
                    My Polls
                </button>
            </div>
            <hr/>
            <div className="activities-list">
            {loading ? (
                    <p>Loading polls...</p>
                ) : showOwnedPolls ? (
                    ownedPolls.length === 0 ? (
                        <p>There are no polls yet!</p>
                    ) : (
                        ownedPolls.map((poll) => (
                            <div
                                key={poll.id}
                                className="poll"
                                onClick={() => navigate(`/poll/${poll.id}`)}
                            >
                                <p className="poll-caption">{poll.caption}</p>
                                <hr />
                                <div className="poll-info">
                                    <div>
                                        <strong>Votes: </strong>{poll.voterCount} &nbsp;&nbsp;&nbsp;
                                        <strong>Active: </strong>{poll.open ? 'Yes' : 'No'}
                                    </div>
                                    <div>
                                        <strong>Created: </strong>{getDate(poll.birthTime)}
                                    </div>
                                    <div>
                                        <strong>Poll Closes: </strong>
                                        {poll.closeTime
                                            ? `${getDate(poll.closeTime)} at ${getHour(poll.closeTime)}`
                                            : 'Until Owner Disables Poll'}
                                    </div>
                                    <div>
                                        {poll.platformOnly
                                            ? 'You Need An Account To Vote'
                                            : 'Everyone Can Vote'}
                                    </div>
                                    <div>
                                        {poll.publicity
                                            ? 'Public Poll'
                                            : 'Private Poll'}
                                    </div>
                                </div>
                            </div>
                        ))
                    )
                ) : votedPolls.length === 0 ? (
                    <p>There are no polls yet!</p>
                ) : (
                    votedPolls.map((poll) => (
                        <div
                            key={poll.id}
                            className="poll"
                            onClick={() => navigate(`/poll/${poll.id}`)}
                        >
                            <p className="poll-caption">{poll.caption}</p>
                            <hr />
                            <div className="poll-info">
                                <div>
                                    <strong>Votes: </strong>{poll.voterCount} &nbsp;&nbsp;&nbsp;
                                    <strong>Active: </strong>{poll.open ? 'Yes' : 'No'}
                                </div>
                                <div>
                                    <strong>Created by: </strong>{poll.creatorName} &nbsp;
                                    <strong>on </strong>{getDate(poll.birthTime)}
                                </div>
                                <div>
                                    <strong>Poll Closes: </strong>
                                    {poll.closeTime
                                        ? `${getDate(poll.closeTime)} at ${getHour(poll.closeTime)}`
                                        : 'Until Owner Disables Poll'}
                                </div>
                                <div>
                                    {poll.platformOnly
                                        ? 'You Need An Account To Vote'
                                        : 'Everyone Can Vote'}
                                </div>
                            </div>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
}

