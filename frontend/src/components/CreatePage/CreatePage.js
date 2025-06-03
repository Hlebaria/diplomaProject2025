import React, { useContext, useState, useEffect } from 'react';
import QuestionCreateBox from './QuestionCreateBox';
import SettingsBox from './SettingsBox';
import { Context } from '../../Context';
import { useNavigate } from "react-router-dom";
import { pollCreateRequest } from '../../services/api'
import './CreatePage.css';

export default function CreatePage() {

    const context = useContext(Context);
    const [showResponseBubble, setShowResponseBubble] = useState(false);
    const [responseMessage, setResponseMessage] = useState('');
    const navigate = useNavigate();

    const [caption, setCaption] = useState('');
    const [description, setDescription] = useState('');
    const [publicity, setPublicity] = useState(false);
    const [platformOnly, setPlatformOnly] = useState(false);
    const [closeTime, setCloseTime] = useState('');
    const [questions, setQuestions] = useState([
    { 
        questionText: '',
        maxVotes: 1,
        minVotes: 1,
        choiceTexts: [''] 
    }
    ]);

    useEffect(() => {

        if(context.kc.token === undefined){
            navigate(`/`);
        }

    },[]);

    const createPoll = async () => {

        const pollData = {
            caption,
            description,
            publicity,
            platformOnly,
            closeTime,
            questions
        };

        // console.log("Poll Data:", pollData);

        try {
            const response = await pollCreateRequest(pollData, context.kc.token);

            setResponseMessage(response);
            setShowResponseBubble(true);
        } catch (error) {
            console.error("Error creating poll:", error);
        }
        
    };

    return (
        <div>
            <div className="page-info">
                <p className="top-text">Create Your Custom Poll</p>
            </div>

            <div className="create-page">
                <div className="content">
                    <QuestionCreateBox 
                        questions={questions}
                        setQuestions={setQuestions}
                    />
                    <SettingsBox 
                        caption={caption}
                        setCaption={setCaption}
                        description={description}
                        setDescription={setDescription}
                        publicity={publicity}
                        setPublicity={setPublicity}
                        platformOnly={platformOnly}
                        setPlatformOnly={setPlatformOnly}
                        closeTime={closeTime}
                        setCloseTime={setCloseTime}
                        createPoll={createPoll}
                    />

                    {showResponseBubble && (
                        <div className="overlay" id="overlay">
                            <div className="popup">
                                <p className="popup-message"><strong>{responseMessage.message}</strong></p>
                                <button className="popup-button" onClick=
                                {
                                    responseMessage.status ?
                                    () => navigate('/poll/' + responseMessage.id)
                                    : 
                                    () => setShowResponseBubble(false)
                                }
                                    ><b>OK</b></button>
                            </div>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
  }