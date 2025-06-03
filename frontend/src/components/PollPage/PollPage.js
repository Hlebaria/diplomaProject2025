import React, { useState, useEffect, useContext } from 'react';
import { useParams } from 'react-router-dom';
import { pollFullGetRequest, pollUpdateRequest } from '../../services/api.js';
import { Context } from '../../Context';
import QuestionsBox from './QuestionsBox.js';
import PollInfoBox from './PollInfoBox.js';
import './PollPage.css'

export default function PollPage() {

    const context = useContext(Context);

    const { id } = useParams();
    const [loading, setLoading] = useState(true);
    const [poll, setPoll] = useState();

    useEffect(() => {

        const fetchPoll = async () => {
            try {

                const data = await pollFullGetRequest(id);
                setPoll(data);
                setLoading(false);

            } catch (err) {
                console.error('Failed to fetch poll:', err);
                setLoading(false);
            }
        };

        fetchPoll();

    },[]);

    const updatePoll = async (close, showResults, publicity) => { 

        let data = {
            open: !close,
            showResults: showResults,
            publicity: publicity
        }

        try {
            const response = await pollUpdateRequest(id, data, context.kc.token);

            if(response.success){
                // fetchPoll();
            }

        } catch (error) {
            console.error("Error updating poll:", error);
        }

    };

    if (loading) return <p>Loading...</p>;
    if (!poll) return <p>Poll not found.</p>;

    return (
        <div className="poll-container">
            <PollInfoBox poll={poll} id={id} token={context.kc.token} updatePoll={updatePoll}/>
            <QuestionsBox poll={poll} id={id} token={context.kc.token}/>
        </div>
    );
}

    // {
    //     creatorName: '',
    //     caption: '',
    //     pollText: '',
    //     birthTime: '',
    //     closeTime: '',
    //     open: true,
    //     showResults: false,
    //     publicity: true,
    //     platformOnly: false,
    //     voterCount: 0,
    //     questions: [
    //         { 
    //             questionId: 0,
    //             questionText: '',
    //             maxVotes: 1,
    //             minVotes: 1,
    //             choices:[
    //                 {
    //                 choiceId: 0,
    //                 text: '',
    //                 votes: 0
    //                 }
    //             ]
    //         }
    //     ]
    // }