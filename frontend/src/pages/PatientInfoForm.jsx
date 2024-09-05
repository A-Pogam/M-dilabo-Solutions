import React from 'react';

const PatientInfoForm = ({ updatedPatient, errors, handleInputChange, handleSubmit }) => {
  return (
    <form onSubmit={handleSubmit} className="patient-info-form">
      <h2>Patient Details</h2>
      <label>
        <strong>First Name:</strong>
        <input
          type="text"
          name="firstName"
          value={updatedPatient.firstName || ''}
          onChange={handleInputChange}
        />
        {errors.firstName && <div className="error">{errors.firstName}</div>}
      </label>
      <label>
        <strong>Last Name:</strong>
        <input
          type="text"
          name="lastName"
          value={updatedPatient.lastName || ''}
          onChange={handleInputChange}
        />
        {errors.lastName && <div className="error">{errors.lastName}</div>}
      </label>
      <label>
        <strong>Date of Birth:</strong>
        <input
          type="date"
          name="dateOfBirth"
          value={updatedPatient.dateOfBirth || ''}
          onChange={handleInputChange}
        />
        {errors.dateOfBirth && <div className="error">{errors.dateOfBirth}</div>}
      </label>
      <label>
        <strong>Gender:</strong>
        <select
          name="gender"
          value={updatedPatient.gender || ''}
          onChange={handleInputChange}
        >
          <option value="">Select</option>
          <option value="Male">Male</option>
          <option value="Female">Female</option>
        </select>
        {errors.gender && <div className="error">{errors.gender}</div>}
      </label>
      <label>
        <strong>Postal Address:</strong>
        <textarea
          name="postalAddress"
          value={updatedPatient.postalAddress || ''}
          onChange={handleInputChange}
        />
      </label>
      <label>
        <strong>Phone Number:</strong>
        <input
          type="tel"
          name="phoneNumber"
          value={updatedPatient.phoneNumber || ''}
          onChange={handleInputChange}
        />
      </label>
      <button type="submit" className="update-button">Update</button>
    </form>
  );
};

export default PatientInfoForm;
