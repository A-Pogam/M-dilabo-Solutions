import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {
  state = {
    clients: []
  };

  async componentDidMount() {
    try {
      const response = await fetch('/patients');
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      const body = await response.json();
      this.setState({ clients: body });
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  }

  render() {
    const { clients } = this.state;
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <div className="App-intro">
            <h2>Patients</h2>
            {clients.map(client =>
              <div key={client.id}>
                {client.name} ({client.email})
              </div>
            )}
          </div>
        </header>
      </div>
    );
  }
}

export default App;
