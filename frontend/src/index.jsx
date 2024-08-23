import React from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import Error from './pages/error';
import Home from './pages/home';
import Patient from './pages/patient';
import PatientDetail from './pages/patientDetail';
import PatientNotes from './pages/patientNotes';


const container = document.getElementById('root');
const root = createRoot(container);
root.render(
  <React.StrictMode>
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="*" element={<Error />} />
        <Route path="/patients" element={<Patient />} />
        <Route path="/patients/:patientId" element={<PatientDetail />} /> 
        <Route path="/patients/:firstName/:lastName" element={<PatientDetail />} />
        <Route path="/notes/:patId" element={<PatientNotes />} />
      </Routes>
    </Router>
  </React.StrictMode>
);
