import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/pages/patient.scss';

const Patient = () => {
  const [patients, setPatients] = useState([]);
  const [search, setSearch] = useState({ firstName: '', lastName: '' });
  const navigate = useNavigate();

  useEffect(() => {
    const fetchPatients = async () => {
      try {
        const response = await fetch('/patients');
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        setPatients(data);
      } catch (error) {
        console.error('Error fetching patients:', error);
      }
    };

    fetchPatients();
  }, []);

  const handleSearch = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch(`/patients/search?firstName=${search.firstName}&lastName=${search.lastName}`);
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      const patient = await response.json();
      if (patient && patient.id) {
        navigate(`/patients/${patient.id}`);
      } else {
        console.log('Patient not found');
      }
    } catch (error) {
      console.error('Error fetching patient:', error);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setSearch(prevState => ({
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
        <form onSubmit={handleSearch}>
          <input
            name="firstName"
            value={search.firstName}
            onChange={handleInputChange}
            placeholder="First Name"
          />
          <input
            name="lastName"
            value={search.lastName}
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
                        onClick={() => navigate(`/patients/${patient.id}`)}
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
