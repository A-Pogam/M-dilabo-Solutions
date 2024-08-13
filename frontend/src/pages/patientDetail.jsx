import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import '../styles/pages/patientDetail.scss'; 

const PatientDetail = () => {
  const { patientId } = useParams();
  const [patient, setPatient] = useState(null);
  const [updatedPatient, setUpdatedPatient] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    const fetchPatientDetails = async () => {
      try {
        const response = await fetch(`/patients/${patientId}`);
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        setPatient(data);
        setUpdatedPatient(data);
      } catch (error) {
        console.error('Error fetching patient details:', error);
      }
    };

    fetchPatientDetails();
  }, [patientId]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUpdatedPatient(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch(`/patients/${patientId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedPatient)
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      const data = await response.json();
      setPatient(data);
      navigate('/patients'); // Navigate back to the patient list or details page after update
    } catch (error) {
      console.error('Error updating patient details:', error);
    }
  };

  if (!patient) {
    return <div>Loading...</div>;
  }

  return (
    <div className="patient-detail-container">
      <h2>Patient Details</h2>
      <form onSubmit={handleSubmit} className="patient-info-form">
        <label>
          <strong>First Name:</strong>
          <input
            type="text"
            name="firstName"
            value={updatedPatient.firstName || ''}
            onChange={handleInputChange}
          />
        </label>
        <label>
          <strong>Last Name:</strong>
          <input
            type="text"
            name="lastName"
            value={updatedPatient.lastName || ''}
            onChange={handleInputChange}
          />
        </label>
        <label>
          <strong>Email:</strong>
          <input
            type="email"
            name="email"
            value={updatedPatient.email || ''}
            onChange={handleInputChange}
          />
        </label>
        <label>
          <strong>Date of Birth:</strong>
          <input
            type="date"
            name="dateOfBirth"
            value={updatedPatient.dateOfBirth || ''}
            onChange={handleInputChange}
          />
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
    </div>
  );
};

export default PatientDetail;
