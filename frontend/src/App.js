
import React, { useEffect, useState } from 'react';
import Keycloak from "keycloak-js";
import { Context } from "./Context";
import { fetchHelloMessage } from './services/api';
import Navbar from './components/Navbar'
import HomePage from './components/HomePage/HomePage';
import CreatePage from './components/CreatePage/CreatePage';
import PollPage from './components/PollPage/PollPage';
import UserPage from './components/UserPage/UserPage';
import Footer from './components/Footer';

import {
    BrowserRouter as Router,
    Routes,
    Route,
} from "react-router-dom";

let context = {};

context.kcOptions = {
    url: (window.REACT_APP_KEYCLOAK_URL || 'http://localhost:8080/'),
    realm: (window.REACT_APP_KEYCLOAK_REALM || 'votaroTest'),
    clientId: (window.REACT_APP_KEYCLOAK_CLIENT_ID || 'frontend')
}

context.kc = new Keycloak(context.kcOptions);

const initKeycloak = async () => {

    const authenticated = await context.kc.init({
        checkLoginIframe: false,
        pkceMethod: "S256"
    });

    if (authenticated) {
        const userInfo = await context.kc.loadUserInfo();
        context.user = {
            username: userInfo.preferred_username || '',
            email: userInfo.email || '',
        };
    } else {
        context.user = null;
    }

    return authenticated;
};

await initKeycloak();

export default function App() {

    const [message, setMessage] = useState('');

    useEffect(() => {
        const getMessage = async () => {
            console.log(context.kc.token);
            try {
                const response = await fetchHelloMessage(context.kc.token);
                setMessage(response);
            } catch (error) {
                console.error('Failed to fetch message:', error);
            }
        };

        getMessage();

        const intervalId = setInterval(async () => {
            
            if (context.kc.authenticated) {
                await context.kc.updateToken(60);
            }
        }, 30000);

        return () => {
            clearInterval(intervalId);
        };

    }, []);


    return (
        <Context.Provider value={context}>
            <Router>
            <div>
                <Navbar />
                <Routes>
                    <Route exact path="/" element={<HomePage />} />
                    <Route path="/create" element={<CreatePage />} />
                    <Route path="/poll/:id" element={<PollPage />} />
                    <Route path="/user" element={<UserPage />} />
                </Routes>
                {/* <h1>React Frontend</h1>
                <p>Message from backend: {message}</p> */}
                <Footer />
            </div>
            </Router>
        </Context.Provider>
    );
}

