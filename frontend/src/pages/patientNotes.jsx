import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

const PatientNotes = () => {
  const { patId } = useParams(); // extract patId from URL
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchNotes = async () => {
      if (!patId) {
        setError(new Error('Patient ID is missing'));
        setLoading(false);
        return;
      }

      try {
        const response = await fetch(`/notes/${patId}`);
        console.log('Response status:', response.status);
        if (!response.ok) {
          throw new Error('Network response was not ok for patient notes');
        }
        const data = await response.json();
        console.log('Fetched data:', data); // Inspect the structure of the received data


        // if data is a table with, at least, one object 
        if (Array.isArray(data) && data.length > 0) {
          // inspect the object inside the table 
          const dataObject = data[0];
          console.log('First object in data:', dataObject);

          // check if notes is a table
          if (Array.isArray(dataObject.notes)) {
          } else {
            throw new Error('Invalid data format: notes should be an array');
          }
        } else {
          throw new Error('Invalid data format: Expected an array with at least one object');
        }
      } catch (error) {
        setError(error);
      } finally {
        setLoading(false);
      }
    };

    fetchNotes();
  }, [patId]);

  if (loading) return <div>Loading notes...</div>;
  if (error) return <div>Error fetching notes: {error.message}</div>;

};

export default PatientNotes;