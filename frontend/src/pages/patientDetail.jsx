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
  const [userRole, setUserRole] = useState(''); 
  const navigate = useNavigate();

  const fetchUserRole = async () => {
    try {
      const response = await fetch('/api/user-role'); 
      if (!response.ok) {
        throw new Error('Failed to fetch user role');
      }
      const data = await response.json();
      setUserRole(data.role); 
    } catch (error) {
      console.error('Error fetching user role:', error);
    }
  };

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

        if (userRole === 'ADMIN') {
          const notesResponse = await fetch(`/notes/${patientId}`);
          if (!notesResponse.ok) {
            throw new Error('Network response was not ok for patient notes');
          }
          const notesData = await notesResponse.json();
          setNotes(notesData);

          const diabetesResponse = await fetch(`/diabetes/${patientId}`);
          if (!diabetesResponse.ok) {
            throw new Error('Failed to fetch diabetes risk');
          }
          const diabetesData = await diabetesResponse.text();
          setDiabetesRisk(diabetesData);
        }
      } catch (error) {
        console.error('Error fetching patient data:', error);
      }
    };

    fetchUserRole();
    fetchPatientData();
  }, [patientId, userRole]);

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
      {userRole === 'ADMIN' && (
        <>
          <PatientNotes
            notes={notes}
            newNote={newNote}
            handleNewNoteChange={handleNewNoteChange}
            addNote={addNote}
          />
          <DiabetesRisk diabetesRisk={diabetesRisk} />
        </>
      )}
    </div>
  );
};

export default PatientDetail;
