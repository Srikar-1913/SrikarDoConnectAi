import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import 'bootstrap/dist/css/bootstrap.min.css'; // Bootstrap for UI styling

// Create root element for React app
const root = ReactDOM.createRoot(document.getElementById('root'));

// Render main App component
root.render(

  <React.StrictMode>  {/* Helps identify potential issues in development */}
  
      <App />         {/* Main application component */}

  </React.StrictMode>

);

// Function to measure app performance (optional)
reportWebVitals();