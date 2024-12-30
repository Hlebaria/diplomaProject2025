import axios from 'axios';

// dev / prod usecase variable injection
const API_URL = (process.env.REACT_APP_BACKEND_URL || $REACT_APP_BACKEND_URL) + '/api';

export const fetchHelloMessage = async () => {
    try {
        const response = await axios.get(`${API_URL}/hello`);
        return response.data;
    } catch (error) {
        console.error('Error fetching the hello message:', error);
        throw error;
    }
};
