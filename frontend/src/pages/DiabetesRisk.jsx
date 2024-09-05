import React from 'react';

const DiabetesRisk = ({ diabetesRisk }) => {
  return (
    <div className="notes-section">
      <h2>Diabetes Risk</h2>
      {diabetesRisk ? (
        <p>Risk Level: {diabetesRisk}</p>
      ) : (
        <p>No diabetes risk data available.</p>
      )}
    </div>
  );
};

export default DiabetesRisk;
