import React, { useEffect, useState } from 'react';

const PatientNotes = ({ patientId }) => {
  const [notes, setNotes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchNotes = async () => {
      try {
        const response = await fetch(`/notes/${patientId}`);
        if (!response.ok) {
          throw new Error('Network response was not ok for patient notes');
        }
        const data = await response.json();
        setNotes(data);
      } catch (error) {
        setError(error);
        console.error('Error fetching patient notes:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchNotes();
  }, [patientId]);

  if (loading) return <div>Loading notes...</div>;
  if (error) return <div>Error fetching notes: {error.message}</div>;

  return (
    <div className="notes-section">
      <h3>Patient Notes</h3>
      {notes.length > 0 ? (
        <ul>
          {notes.map((note, index) => (
            <li key={index}>{note}</li>
          ))}
        </ul>
      ) : (
        <p>No notes available for this patient.</p>
      )}
    </div>
  );
};

export default PatientNotes;
