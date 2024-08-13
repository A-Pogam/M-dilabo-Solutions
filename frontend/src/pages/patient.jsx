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
    navigate(`/patients/${patient.firstName}-${patient.lastName}`);
  };

  const handleUpdatePatient = (patientId) => {
    navigate(`/patients/${patientId}`);
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
        <h2>Hi Doctor ! ⚕️</h2>
        <h3>Search and update your patients 👩🏼‍💻</h3>

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

        <div className="patient-container">
          <div className="patient-header">
            <h2>Your Patients</h2>
          </div>

          <section className="patient-body">
            <table className="patient-table">
              <thead>
                <tr>
                  <th>First Name</th>
                  <th>Last Name</th>
                  <th>Date of Birth</th>
                  <th>Gender</th>
                  <th>Postal Address</th>
                  <th>Phone Number</th>
                  <th>Update</th>
                </tr>
              </thead>
              <tbody>
                {patients.map(patient => (
                  <tr key={patient.id}>
                    <td>{patient.firstName}</td>
                    <td>{patient.lastName}</td>
                    <td>{patient.dateOfBirth}</td>
                    <td>{patient.gender}</td>
                    <td>{patient.postalAddress}</td>
                    <td>{patient.phoneNumber}</td>
                    <td>
                      <button
                        onClick={() => handleUpdatePatient(patient.id)}
                        className="update-button"
                      >
                        Update
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </section>
        </div>

    
      </header>
    </div>
  );
}

export default Patient;
