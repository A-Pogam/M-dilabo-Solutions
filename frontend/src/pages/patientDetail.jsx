import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

const PatientDetail = () => {
  const { patientId } = useParams();
  const [patient, setPatient] = useState(null);

  useEffect(() => {
    const fetchPatientDetails = async () => {
      try {
        const response = await fetch(`/patients/${patientId}`);
        const data = await response.json();
        setPatient(data);
      } catch (error) {
        console.error('Error fetching patient details:', error);
      }
    };

    fetchPatientDetails();
  }, [patientId]);

  if (!patient) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h2>Patient Details</h2>
      <p>First Name: {patient.firstName}</p>
      <p>Last Name: {patient.lastName}</p>
      <p>Email: {patient.email}</p>
    </div>
  );
};

export default PatientDetail;
