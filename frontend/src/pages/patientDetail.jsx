import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import '../styles/pages/patientDetail.scss'; 

const PatientDetail = () => {
  const { patientId } = useParams();
  const [patient, setPatient] = useState(null);

  useEffect(() => {
    const fetchPatientDetails = async () => {
      try {
        const response = await fetch(`/patients/${patientId}`);
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        setPatient(data);
      } catch (error) {
        console.error('Error fetching patient details:', error);
      }
    };

    fetchPatientDetails();
  }, [patientId]);

  if (!patient) {
    return <div className="error-message">Loading...</div>;
  }

  return (
    <div className="patient-detail-container">
      <h2>Patient Details</h2>
      <div className="patient-info">
        <p><strong>First Name:</strong> {patient.firstName}</p>
        <p><strong>Last Name:</strong> {patient.lastName}</p>
        <p><strong>Email:</strong> {patient.email}</p>
        <p><strong>Date of Birth:</strong> {patient.dateOfBirth}</p>
        <p><strong>Gender:</strong> {patient.gender}</p>
        <p><strong>Postal Address:</strong> {patient.postalAddress}</p>
        <p><strong>Phone Number:</strong> {patient.phoneNumber}</p>
      </div>
    </div>
  );
};

export default PatientDetail;
