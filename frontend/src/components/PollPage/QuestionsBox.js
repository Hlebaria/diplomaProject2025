import React, { useState, useEffect } from 'react';
import { voteRequest, existingVoteCheckRequest } from '../../services/api';
import { useNavigate } from "react-router-dom";
import './QuestionsBox.css';

export default function QuestionsBox({ poll, id, token }) {

    const questions = poll.questions;

    const [selectedChoices, setSelectedChoices] = useState([]);
    const [responseMessage, setResponseMessage] = useState('');
    const [showResponseBubble, setShowResponseBubble] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {

        const existingVoteCheck = async (pollId) =>{

            const response = await existingVoteCheckRequest(pollId, token);
            if(response !== null){
                setSelectedChoices(response);
            }
            else{
                setSelectedChoices([]);
            }
    
        }

        existingVoteCheck(id);

    }, []);

    useEffect(() => {
        console.log("FINAL DATA: ", selectedChoices);
    }, [selectedChoices]);


    const castVote = async (vote) => { 

        try {
            const response = await voteRequest(vote, id, token);
            setResponseMessage(response);
            setShowResponseBubble(true);

        } catch (error) {
            console.error("Error creating poll:", error);
        }

    };

    function handleChoice(choiceId, questionId) {

        setSelectedChoices(prev => {
            const questionIndex = prev.findIndex(q => q.questionId === questionId);
            const question = questions.find(q => q.questionId === questionId);
    
            if (!question) {
                console.error(`Invalid questionId: ${questionId}`);
                return prev;
            }
    
            if (!question.choices.some(c => c.choiceId === choiceId)) {
                console.error(`Invalid choiceId: ${choiceId} for questionId: ${questionId}`);
                return prev;
            }

            if (questionIndex === -1) {

                return [
                    ...prev,
                    { questionId, choiceIds: [choiceId] }
                ];
            }

            const currentChoiceIds = prev[questionIndex].choiceIds;
            const isSelected = currentChoiceIds.includes(choiceId);

            if (isSelected) {
                const updatedChoices = currentChoiceIds.filter(c => c !== choiceId);

                if (updatedChoices.length === 0) {
                    return prev.filter((_, i) => i !== questionIndex);
                }

                return prev.map((q, i) =>
                    i === questionIndex ? { ...q, choiceIds: updatedChoices } : q
                );

            } else {

                if (currentChoiceIds.length < question.maxVotes) {

                    const updatedChoices = [...currentChoiceIds, choiceId];

                    return prev.map((q, i) =>
                        i === questionIndex ? { ...q, choiceIds: updatedChoices } : q
                    );
                }

                if (question.maxVotes === 1) {
                    const currentChoiceIds = [choiceId];

                    return prev.map((q, i) =>
                        i === questionIndex ? { ...q, choiceIds: currentChoiceIds } : q
                    );
                }

                return prev;
            }
        });
    }

    return(
        <div>
            {questions.map((question, index) => (
                <div key={index} className="question-item">
                    <h2>Q{index + 1}: {question.questionText}</h2>
                    <div className="choices-container">
                        {question.choices.map((choice, choiceIndex) => (
                            <button 
                                key={choiceIndex} 
                                className={
                                    selectedChoices.find(q => q.questionId === question.questionId)?.choiceIds.includes(choice.choiceId)
                                    ? 
                                    'choice-button selected' : 'choice-button'
                                }
                                onClick={() => handleChoice(choice.choiceId, question.questionId)}
                            >
                                <span><b>{choice.text}</b></span>
                                {poll.showResults && (
                                    <span>{choice.votes} votes</span>
                                )} 
                            </button>
                        ))}
                    </div>
                </div>
            ))}
            {showResponseBubble && (
                <div className="overlay" id="overlay">
                    <div className="popup">
                        <p className="popup-message"><strong>{responseMessage.message}</strong></p>
                        <button className="popup-button" onClick=
                        {
                            responseMessage.status ?
                            () => navigate('/')
                            : 
                            () => setShowResponseBubble(false)
                        }
                            ><b>OK</b></button>
                    </div>
                </div>
            )}

            <button className="submit-button" onClick={() => castVote(selectedChoices)}>
                <strong>Submit</strong>
            </button>
        </div>
    );
}
