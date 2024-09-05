import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import '../styles/pages/patientDetail.scss';

const PatientDetail = () => {
  const { patientId } = useParams();
  const [patient, setPatient] = useState(null);
  const [updatedPatient, setUpdatedPatient] = useState({});
  const [notes, setNotes] = useState([]);
  const [newNote, setNewNote] = useState('');
  const [diabetesRisk, setDiabetesRisk] = useState(''); // Nouvel état pour stocker le risque de diabète
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    const fetchPatientData = async () => {
      try {
        const response = await fetch(`/patients/${patientId}`);
        if (!response.ok) {
          throw new Error('Network response was not ok for patient details');
        }
        const data = await response.json();
        setPatient(data);
        setUpdatedPatient(data);

        // Fetch patient notes
        const notesResponse = await fetch(`/notes/${patientId}`);
        if (!notesResponse.ok) {
          throw new Error('Network response was not ok for patient notes');
        }
        const notesData = await notesResponse.json();
        setNotes(notesData);

        // Fetch diabetes risk
        const fetchDiabetesRisk = async () => {
          try {
            const response = await fetch(`/diabetes/${patientId}`);
            if (!response.ok) {
              throw new Error('Failed to fetch diabetes risk');
            }
            const data = await response.text();  // Assure-toi que c'est du texte
            console.log('Diabetes Risk Response:', data); // Log pour vérifier la réponse
            setDiabetesRisk(data);
          } catch (error) {
            console.error('Error fetching diabetes risk:', error);
          }
        };

        await fetchDiabetesRisk();  // Appelle la fonction ici

      } catch (error) {
        console.error('Error fetching patient data:', error);  // Ajoute le bloc catch manquant ici
      }
    };

    fetchPatientData();
}, [patientId]);


  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUpdatedPatient((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleNewNoteChange = (e) => {
    setNewNote(e.target.value);
  };

  const addNote = async () => {
    if (!newNote.trim()) return;

    try {
      const response = await fetch(`/notes/${patientId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'text/plain',
        },
        body: newNote, // Update: Send new note as JSON object
      });

      if (!response.ok) {
        throw new Error('Failed to add note');
      }

      const responseData = await response.json();
      setNotes((prevNotes) => [...prevNotes, responseData]); // Assumes response returns the saved Note object
      setNewNote(''); // Clear the input field
    } catch (error) {
      console.error('Error adding note:', error);
    }
  };

  const validateForm = () => {
    const newErrors = {};
    if (!updatedPatient.firstName) newErrors.firstName = 'First name is mandatory';
    if (!updatedPatient.lastName) newErrors.lastName = 'Last name is mandatory';
    if (!updatedPatient.dateOfBirth) newErrors.dateOfBirth = 'Date of birth is mandatory';
    if (!updatedPatient.gender) newErrors.gender = 'Gender is mandatory';
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    try {
      const response = await fetch(`/patients/${patientId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedPatient),
      });

      if (!response.ok) {
        const errorData = await response.json();
        console.error('Error updating patient details:', errorData);
        setErrors(errorData.reduce((acc, curr) => ({ ...acc, [curr.field]: curr.defaultMessage }), {}));
        return;
      }

      const data = await response.json();
      setPatient(data);
      navigate('/patients');
    } catch (error) {
      console.error('Error updating patient details:', error);
    }
  };

  if (!patient) {
    return <div>Loading...</div>;
  }

  return (
    <div className="patient-detail-container">
      <form onSubmit={handleSubmit} className="patient-info-form">
        <h2>Patient Details</h2>
        <label>
          <strong>First Name:</strong>
          <input
            type="text"
            name="firstName"
            value={updatedPatient.firstName || ''}
            onChange={handleInputChange}
          />
          {errors.firstName && <div className="error">{errors.firstName}</div>}
        </label>
        <label>
          <strong>Last Name:</strong>
          <input
            type="text"
            name="lastName"
            value={updatedPatient.lastName || ''}
            onChange={handleInputChange}
          />
          {errors.lastName && <div className="error">{errors.lastName}</div>}
        </label>
        <label>
          <strong>Date of Birth:</strong>
          <input
            type="date"
            name="dateOfBirth"
            value={updatedPatient.dateOfBirth || ''}
            onChange={handleInputChange}
          />
          {errors.dateOfBirth && <div className="error">{errors.dateOfBirth}</div>}
        </label>
        <label>
          <strong>Gender:</strong>
          <select
            name="gender"
            value={updatedPatient.gender || ''}
            onChange={handleInputChange}
          >
            <option value="">Select</option>
            <option value="Male">Male</option>
            <option value="Female">Female</option>
          </select>
          {errors.gender && <div className="error">{errors.gender}</div>}
        </label>
        <label>
          <strong>Postal Address:</strong>
          <textarea
            name="postalAddress"
            value={updatedPatient.postalAddress || ''}
            onChange={handleInputChange}
          />
        </label>
        <label>
          <strong>Phone Number:</strong>
          <input
            type="tel"
            name="phoneNumber"
            value={updatedPatient.phoneNumber || ''}
            onChange={handleInputChange}
          />
        </label>
        <button type="submit" className="update-button">Update</button>
      </form>

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

      <div className="notes-section">
        <h2>Diabetes Risk</h2>
        {diabetesRisk && (
          <p>Risk Level: {diabetesRisk}</p>
        )}
      </div>
    </div>
  );
};

export default PatientDetail;