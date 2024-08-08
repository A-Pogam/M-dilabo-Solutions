import React from 'react'
import ReactDOM from 'react-dom/client';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'

import Home from './pages/home'
import patient from './pages/patient'
import Error from './pages/error'


const container = document.getElementById('root')
const root = createRoot(container)
root.render(
  <React.StrictMode>
    <Router>
      <Header></Header>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/patient/:id" element={<Patient />} />
        <Route path="*" element={<Error />} />
      </Routes>
      <Footer></Footer>
    </Router>
  </React.StrictMode>
)