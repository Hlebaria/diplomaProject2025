import React, { useState } from 'react';
import './SettingsBox.css';

export default function SettingsBox(
{
  caption, 
  setCaption, 
  description, 
  setDescription,
  publicity,
  setPublicity,
  platformOnly,
  setPlatformOnly,
  closeTime,
  setCloseTime,
  createPoll
})
{

  return (
    <div className="settings-box">
      <h2>Settings</h2>
      <label>
        Caption:
        <textarea
          className='settings-textarea'
          type="text"
          value={caption}
          onChange={(e) => setCaption(e.target.value)}
        />
      </label>
      <label>
        Description:
        <textarea
          className='settings-textarea'
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        />
      </label>
      <div className="clickable-options">
        <p>Visibility: {publicity ? 'Public' : 'Private'}</p>
        <label className="switch">
            <input type="checkbox" checked={publicity} onChange={() => setPublicity(!publicity)}/>
        </label>
        <p>Voters: {platformOnly ? 'Account Only' : 'Everyone (Prone To Duplicate Votes!)'}</p>
        <label className="switch">
            <input type="checkbox" checked={platformOnly} onChange={() => setPlatformOnly(!platformOnly)}/>
        </label>
      </div>
      <br/>
      <div>
        <p>When would you like to automatically close the poll?</p>
        <input
          type="datetime-local"
          id="timestamp"
          name="timestamp"
          value={closeTime}
          onChange={(e) => setCloseTime(e.target.value)}
        />
      </div>
      <button className="create-button" onClick={createPoll}>Create</button>
    </div>
  );
}
