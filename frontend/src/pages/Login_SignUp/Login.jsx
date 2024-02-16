import React from 'react';
import LoginForm from '../../components/Login_SignUp/LoginForm';
import classes from './Login.module.css';
export default function Login() {
  return (
    <div className={`d-flex justify-content-center align-items-center vh-100 ${classes.loginFormContainer}`}>
      <div className="container">
        <div className="row">
          <div className="col-md-3">
          </div>
          <div className="col-md-6">
            <LoginForm />
          </div>
          <div className="col-md-3">
          </div>
        </div>
      </div>
    </div>
  );
}
