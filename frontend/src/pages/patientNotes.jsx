import React from 'react';

const PatientNotes = ({ notes, newNote, handleNewNoteChange, addNote }) => {
  return (
    <div className="notes-section">
      <h2>Patient Notes</h2>
      {notes.length > 0 ? (
        <ul>
          {notes.map((note, index) => (
            <li key={index}>{note.notes}</li>
          ))}
        </ul>
      ) : (
        <p>No notes available for this patient.</p>
      )}
      <input
        value={newNote}
        onChange={handleNewNoteChange}
        placeholder="Write your new note here..."
      />
      <button type="button" onClick={addNote} className="update-button">Add Note</button>
    </div>
  );
};

export default PatientNotes;
