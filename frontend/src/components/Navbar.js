import React, { useContext, useState } from 'react';
import './Navbar.css';
import { Context } from '../Context';
import { useNavigate } from "react-router-dom";

export default function Navbar() {

  const [showUserMenu, setShowUserMenu] = useState(false);
  const context = useContext(Context);
  const navigate = useNavigate();

  return (
    <div className="navbar">
      <h1 className="logo"onClick={() => navigate('/')}>Votaro</h1>
      <div className="buttons">
        {context.user ? (
            <>
                <button className="nav-button" onClick={() => navigate('/create')} ><b>Create Poll</b></button>
                <button className="nav-button" onClick={() => setShowUserMenu(true)}><b>{context.user.username}</b></button>
                
                {showUserMenu && (
                  <div className="user-menu">
                    <ul>
                      <li onClick={() => {navigate('/user'); setShowUserMenu(false);}}>My Account</li>
                      <li onClick={() => context.kc.logout()}>Log Out</li>
                    </ul>
                    <button className="close-menu" onClick={() => setShowUserMenu(false)}>Close</button>
                  </div>
                )}
            </>
        ) : (
            <>
                <button className="nav-button" onClick={() => context.kc.login()}><b>Log In</b></button>
                <button className="nav-button" onClick={() => context.kc.register()}><b>Register</b></button>
            </>
        )}
      </div>
    </div>
  );
}
