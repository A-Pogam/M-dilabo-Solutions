import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/pages/error.scss';

const Error = () => {
  return (
    <div className='error'>
            <h1>404</h1>
            <p>Oupsi ! The page you are looking for does not exist</p>
      <ul>
            <li>
              <Link className="back-home" to="/">
                Go back to Home
              </Link>
            </li>
      </ul>
    </div>
  );
};

export default Error;