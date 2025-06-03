
// dev / prod usecase variable injection
const API_URL = (window.REACT_APP_BACKEND_URL ||  'http://localhost:5000') + '/api';

export const fetchHelloMessage = async (token) => {

    if(token === undefined){
        token = "";
    }
    
    try {
        const response = await fetch(`${API_URL}/hello`, {
            headers: {
                'Authorization': token
            }
        });
        const data = await response.text();

        return data;
    } catch (error) {
        console.error('Error fetching the hello message:', error);
        throw error;
    }
};

export const pollsGetRequest = async (pageCount) => {
   
    try {
        const response = await fetch(`${API_URL}/polls?loadedCount=${pageCount}`,  {
            method: 'GET'
        });
        const data = await response.json();

        return data;
    } catch (error) {
        console.error('Failed to fetch polls:', error);
    }

}

export const pollFullGetRequest = async (pollId) => {

    try{
        const response = await fetch(`${API_URL}/polls/${pollId}`, {
            method: 'GET'
        });
        const data = await response.json();

        return data;
    } catch (error) {
        console.error('Failed to fetch full poll:', error);
    }

}

export const pollCreateRequest = async (pollData, token) => {

    if(token === undefined){
        token = "";
    }
   
    try {
        const response = await fetch(`${API_URL}/polls/create`, {
            method: 'POST',
            headers: {
                'Authorization': token,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(pollData),
          });

        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Failed to create poll:', error);
    }

}

export const pollUpdateRequest = async (pollId, updateData, token) => {

    if(token === undefined){
        token = "";
    }
   
    try {
        const response = await fetch(`${API_URL}/polls/update/${pollId}`, {
            method: 'PATCH',
            headers: {
                'Authorization': token,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(updateData),
          });

        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Failed to fetch polls:', error);
    }

}

export const checkOwnerRequest = async (pollId, token) => {

    if(token === undefined){
        token = "";
    }
   
    try {
        const response = await fetch(`${API_URL}/user/owner-check/${pollId}`, {
            method: 'GET',
            headers: {
                'Authorization': token,
            }
          });

        const data = await response.json();
        return data;

    } catch (error) {
        console.error('Failed to check if user owns the poll.', error);
    }

}

export const voteRequest = async (vote, pollId, token) => {

    if(token === undefined){
        token = "";
    }
   
    try {
        const response = await fetch(`${API_URL}/vote/${pollId}`, {
            method: 'POST',
            headers: {
                'Authorization': token,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(vote),
        });
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Failed proccessing the vote:', error);
    }

}

export const getUserOwnedPollsRequest = async (token) => {

    if(token === undefined){
        token = "";
    }
   
    try {
        const response = await fetch(`${API_URL}/user/polls`, {
            method: 'GET',
            headers: {
                'Authorization': token,
            }
        });

        const data = await response.json();
        return data;

    } catch (error) {
        console.error('Failed to fetch users polls', error);
    }

}

export const getUserTakenPollsRequest = async (token) => {

    if(token === undefined){
        token = "";
    }
   
    try {
        const response = await fetch(`${API_URL}/user/activity`, {
            method: 'GET',
            headers: {
                'Authorization': token,
            }
        });

        const data = await response.json();
        return data;

    } catch (error) {
        console.error('Failed to fetch users polls', error);
    }

}

export const existingVoteCheckRequest = async (pollId, token) => {

    if(token === undefined){
        return [];
    }
   
    try {
        const response = await fetch(`${API_URL}/user/vote/${pollId}`, {
            method: 'GET',
            headers: {
                'Authorization': token,
            }
        });

        const data = await response.json();

        return data;

    } catch (error) {
        console.error('Failed to check for existing choices', error);
    }

}

