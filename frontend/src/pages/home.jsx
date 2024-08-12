import React from 'react';
import '../styles/pages/home.scss';

function Home() {
  return (
    <div className="home-container"> 
      {/* Header Section */}
      <header className="header">
        <div className="header-logo">Médilabo Solutions</div>
        <nav className="header-nav">
          <a href="/login" className="login-link">Login</a>
        </nav>
      </header>

      {/* Hero Section */}
      <section className="hero">
        <h1>Welcome to Médilabo Solutions</h1>
        <p>Your trusted partner in healthcare management</p>
        <button className="cta-button">Get Started</button>
      </section>

      {/* Features Section */}
      <section className="features">
        <div className="feature">
          <h2>Patient Management</h2>
          <p>Efficiently manage patient records with our secure platform.</p>
        </div>
        <div className="feature">
          <h2>Medical Reports</h2>
          <p>Generate and access detailed medical reports anytime, anywhere.</p>
        </div>
      </section>

      {/* Footer Section */}
      <footer className="footer">
        <p>&copy; 2024 Médilabo Solutions. All rights reserved.</p>
      </footer>
    </div>
  );
}

export default Home;
