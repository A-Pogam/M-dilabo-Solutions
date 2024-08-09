import React, { Component } from 'react';
import '../styles/pages';

class App extends Component {
  state = {
    patients: [],
    selectedPatient: null,
    updatedPatient: { firstName: '', lastName: '', email: '' }
  };

  async componentDidMount() {
    const response = await fetch('/patients');
    const patients = await response.json();
    this.setState({ patients });
  }

  handleSearch = async (firstName, lastName) => {
    const response = await fetch(`/patients/${firstName}/${lastName}`);
    const patient = await response.json();
    this.setState({ selectedPatient: patient, updatedPatient: patient });
  };

  handleUpdate = async () => {
    const { firstName, lastName, email } = this.state.updatedPatient;
    const response = await fetch(`/patients/${firstName}/${lastName}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ firstName, lastName, email })
    });
    const updatedPatient = await response.json();
    this.setState({ selectedPatient: updatedPatient });
  };

  handleInputChange = (event) => {
    const { name, value } = event.target;
    this.setState({
      updatedPatient: {
        ...this.state.updatedPatient,
        [name]: value
      }
    });
  };

  render() {
    const { patients, selectedPatient, updatedPatient } = this.state;

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

          <h3>Search Patient</h3>
          <form onSubmit={(e) => {
            e.preventDefault();
            this.handleSearch(updatedPatient.firstName, updatedPatient.lastName);
          }}>
            <input
              name="firstName"
              value={updatedPatient.firstName}
              onChange={this.handleInputChange}
              placeholder="First Name"
            />
            <input
              name="lastName"
              value={updatedPatient.lastName}
              onChange={this.handleInputChange}
              placeholder="Last Name"
            />
            <button type="submit">Search</button>
          </form>

          {selectedPatient && (
            <div>
              <h3>Update Patient</h3>
              <form onSubmit={(e) => {
                e.preventDefault();
                this.handleUpdate();
              }}>
                <input
                  name="firstName"
                  value={updatedPatient.firstName}
                  onChange={this.handleInputChange}
                  placeholder="First Name"
                />
                <input
                  name="lastName"
                  value={updatedPatient.lastName}
                  onChange={this.handleInputChange}
                  placeholder="Last Name"
                />
                <input
                  name="email"
                  value={updatedPatient.email}
                  onChange={this.handleInputChange}
                  placeholder="Email"
                />
                <button type="submit">Update</button>
              </form>
            </div>
          )}
        </header>
      </div>
    );
  }
}

export default App;
