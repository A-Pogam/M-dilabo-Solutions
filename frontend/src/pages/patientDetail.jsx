import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import PatientInfoForm from './PatientInfoForm';
import PatientNotes from './patientNotes';
import DiabetesRisk from './DiabetesRisk';
import '../styles/pages/patientDetail.scss';

const PatientDetail = () => {
  const { patientId } = useParams();
  const [patient, setPatient] = useState(null);
  const [updatedPatient, setUpdatedPatient] = useState({});
  const [notes, setNotes] = useState([]);
  const [newNote, setNewNote] = useState('');
  const [diabetesRisk, setDiabetesRisk] = useState('');
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

        const notesResponse = await fetch(`/notes/${patientId}`);
        if (!notesResponse.ok) {
          throw new Error('Network response was not ok for patient notes');
        }
        const notesData = await notesResponse.json();
        setNotes(notesData);

        
        const fetchDiabetesRisk = async () => {
          try {
            const response = await fetch(`/diabetes/${patientId}`);
            if (!response.ok) {
              throw new Error('Failed to fetch diabetes risk');
            }
            const data = await response.text();
            setDiabetesRisk(data);
          } catch (error) {
            console.error('Error fetching diabetes risk:', error);
          }
        };

        await fetchDiabetesRisk();
      } catch (error) {
        console.error('Error fetching patient data:', error);
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
      const response = await fetch(`http://localhost:3000/notes/1`, {
        method: 'POST',
        headers: {
          'Content-Type': 'text/plain',
          'Content-Length': '50000'
        },
        body: newNote,
      });

      if (!response.ok) {
        throw new Error('Failed to add note');
      }

      const responseData = await response.json();
      setNotes((prevNotes) => [...prevNotes, responseData]);
      setNewNote('');
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
      <PatientInfoForm
        updatedPatient={updatedPatient}
        errors={errors}
        handleInputChange={handleInputChange}
        handleSubmit={handleSubmit}
      />
      <PatientNotes
        notes={notes}
        newNote={newNote}
        handleNewNoteChange={handleNewNoteChange}
        addNote={addNote}
      />
      <DiabetesRisk diabetesRisk={diabetesRisk} />
    </div>
  );
};

export default PatientDetail;
