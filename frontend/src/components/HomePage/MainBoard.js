import React, { useEffect, useState, useRef } from 'react';
import { pollsGetRequest } from '../../services/api.js';
import { useNavigate } from 'react-router-dom';
import './MainBoard.css';

export default function MainBoard() {

  const [polls, setPolls] = useState([]);
  const [pollCount, setPollCount] = useState(0); 
  const [hasMore, setHasMore] = useState(true);
  const [loading, setLoading] = useState(false);
  const initialFetchDone = useRef(false);
  const navigate = useNavigate(); 

  const getPolls = async (pollCount) => {

    setLoading(true);

    try {

    const data = await pollsGetRequest(pollCount);

    if (data.length < 10) {
      setHasMore(false);
    }

    setPolls((prevPolls) => [...prevPolls, ...data]);

    } catch (error) {
      console.error("Failed to fetch polls:", error);
    } finally {
      setLoading(false);
    }

  }

  useEffect(() => {
    if (!initialFetchDone.current) {
      getPolls(0);
      initialFetchDone.current = true; // Mark initial fetch as done
    }
  }, []);

  const loadMore = () => {
    const newOffset = pollCount;
    setPollCount(newOffset);
    getPolls(newOffset);
  };

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
    <div className="board">
      <div className="main-box">
        <b><p className = "poll-box-sign">Public polls:</p></b>

        {polls.length === 0 && !loading && <p>There are no polls yet!</p>}

        {polls.map((poll) => (
          <div key={poll.id} className="poll" onClick={() => navigate(`/poll/${poll.id}`)}>
            <p className = "poll-caption">{poll.caption}</p>
            <hr/>
            <p className = "poll-info">
              <div><strong>Votes: </strong>{poll.voterCount} &nbsp;&nbsp;&nbsp; <strong>Active: </strong>{poll.open ? 'Yes' : 'No'}</div>
              <div><strong>Created by: </strong>&nbsp; {poll.creatorName} &nbsp; <strong>on</strong> {getDate(poll.birthTime)}</div>
              <div><strong>Poll Closes: </strong>{poll.closeTime ? `${getDate(poll.closeTime)} at ${getHour(poll.closeTime)} UTC` : 'Until Owner Disables Poll'}</div>
              <div>{poll.platformOnly ? 'You Need An Account To Vote' : 'Everyone Can Vote'}</div>
            </p>
          </div>
        ))}

        {hasMore && !loading && (
          <button className="load-more" onClick={() => loadMore()}>
            Load More Polls ↓
          </button>
        )}

        {loading && <p>Loading...</p>}
      </div>
    </div>
  );
}
