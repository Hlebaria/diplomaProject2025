import React from 'react';
import './QuestionCreateBox.css';

export default function QuestionCreateBox({ questions, setQuestions} ) {

  const addQuestion = () => {
    setQuestions([...questions, { questionText: '', maxVotes: 1, minVotes: 1,choiceTexts: [''] }]);
  };

  const updateQuestionText = (index, questionText) => {
    const newQuestions = [...questions];
    newQuestions[index].questionText = questionText;
    setQuestions(newQuestions);
  };

  const updateQuestionMaxVotes = (index, maxVotes) => {

    const newQuestions = [...questions];

    if(maxVotes < 1){
      maxVotes = 1;
    }

    if(maxVotes < newQuestions[index].minVotes){
      updateQuestionMinVotes(index, maxVotes)
    }

    if(maxVotes > newQuestions[index].choiceTexts.length){
      maxVotes = newQuestions[index].choiceTexts.length;
    }

    newQuestions[index].maxVotes = maxVotes;
    setQuestions(newQuestions);
  }

  const updateQuestionMinVotes = (index, minVotes) => {

    console.log(minVotes);
    const newQuestions = [...questions];

    if(minVotes < 1){
      minVotes = 1;
    }

    if(minVotes > newQuestions[index].maxVotes){
      minVotes = newQuestions[index].maxVotes;
    }

    newQuestions[index].minVotes = minVotes;
    setQuestions(newQuestions);
  }

  const addChoice = (questionIndex) => {
    const newQuestions = [...questions];
    newQuestions[questionIndex].choiceTexts.push('');
    setQuestions(newQuestions);
  };

  const updateChoice = (questionIndex, choiceIndex, text) => {
    const newQuestions = [...questions];
    newQuestions[questionIndex].choiceTexts[choiceIndex] = text;
    setQuestions(newQuestions);
  };

  return (
    <div className="question-box">
      <h2>Your Questions</h2>
      {questions.map((question, questionIndex) => (
        <div key={questionIndex} className="question-item">
          <textarea
            className='question-textarea'
            type="text"
            placeholder="Enter your question ..."
            value={question.questionText}
            onChange={(e) => updateQuestionText(questionIndex, e.target.value)}
            onInput={(e) => {
              e.target.style.height = 'auto';
              e.target.style.height = `${e.target.scrollHeight}px`;
            }}
          />
          <div className="choices">
            {question.choiceTexts.map((choice, choiceIndex) => (
              <input
                key={choiceIndex}
                type="text"
                placeholder="Enter a choice..."
                value={choice}
                onChange={(e) =>
                  updateChoice(questionIndex, choiceIndex, e.target.value)
                }
              />
            ))}
            <button className='question-box-button' onClick={() => addChoice(questionIndex)}>Add Choice</button>
            <div className="settings">
              <div className="choice-setting">
                <label>Min Choices:</label>
                <div className="spinner">
                  <button className='question-box-button' onClick={() => updateQuestionMinVotes(questionIndex, question.minVotes - 1)}>
                    -
                  </button>
                  <span>{question.minVotes}</span>
                  <button className='question-box-button' onClick={() => updateQuestionMinVotes(questionIndex, question.minVotes + 1)}>
                    +
                  </button>
                </div>
              </div>
              <div className="choice-setting">
                <label>Max Choices:</label>
                <div className="spinner">
                  <button className='question-box-button' onClick={() => updateQuestionMaxVotes(questionIndex, question.maxVotes - 1)}>
                    -
                  </button>
                  <span>{question.maxVotes}</span>
                  <button className='question-box-button' onClick={() => updateQuestionMaxVotes(questionIndex, question.maxVotes + 1)}>
                    +
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      ))}
      <button className='question-box-button' onClick={addQuestion}>Add Question</button>
    </div>
  );
}
