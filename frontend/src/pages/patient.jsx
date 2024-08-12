import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/pages/patient.scss';

const Patient = () => {
  const [patients, setPatients] = useState([]);
  const [updatedPatient, setUpdatedPatient] = useState({ firstName: '', lastName: '', email: '' });
  const navigate = useNavigate();

  useEffect(() => {
    const fetchPatients = async () => {
      const response = await fetch('/patients');
      const data = await response.json();
      setPatients(data);
    };
    fetchPatients();
  }, []);

  const handleSearch = async (firstName, lastName) => {
    const response = await fetch(`/patients/${firstName}/${lastName}`);
    const patient = await response.json();
    setUpdatedPatient(patient);
    navigate(`/patient/${patient.firstName}-${patient.lastName}`);
  };

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setUpdatedPatient(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  return (
    <div className="App">
      <header className="App-header">
        <h2>Hi Doctor !</h2>
        <h3>Search and update patients</h3>
        <ul>
          {patients.map(patient => (
            <li key={`${patient.firstName}-${patient.lastName}`}>
              {patient.firstName} {patient.lastName} ({patient.email})
            </li>
          ))}
        </ul>

        <h3>Search your patient</h3>
        <form onSubmit={(e) => {
          e.preventDefault();
          handleSearch(updatedPatient.firstName, updatedPatient.lastName);
        }}>
          <input
            name="firstName"
            value={updatedPatient.firstName}
            onChange={handleInputChange}
            placeholder="First Name"
          />
          <input
            name="lastName"
            value={updatedPatient.lastName}
            onChange={handleInputChange}
            placeholder="Last Name"
          />
          <button type="submit">Search</button>
        </form>
      </header>
    </div>
  );
}

export default Patient;
