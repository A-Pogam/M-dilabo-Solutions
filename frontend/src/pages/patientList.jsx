import React, { useEffect, useState } from 'react';

const PatientList = () => {
  const [patients, setPatients] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetch('/patient/patients/')  
      .then(response => response.json())
      .then(data => setPatients(data))
      .catch(error => setError(error));
  }, []);

  if (error) return <div>Error: {error.message}</div>;

  return (
    <div>
      <h1>Patient List</h1>
      <ul>
        {patients.map(patient => (
          <li key={patient.id}>
            {patient.firstName} {patient.lastName}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default PatientList;
