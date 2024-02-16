import React, { useState } from "react";
import { useForm } from 'react-hook-form'
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup'
// import styled from 'styled-components'

import { PRIMARY_COLOR } from "../../App";

const schema = yup.object().shape({
  name: yup.string()
    .min(2, "이름은 최소 2글자 이상입니다!")
    .max(10, "이름은 최대 10글자입니다!")
    .matches(
      /^[가-힣a-zA-Z][^!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?\s]*$/)
    .required('이름을 입력해주세요'),
  // checkNickname: yup.boolean().required('중복체크해주세요'),
  certifi_email: yup.string()
  .email('이메일형식이 적합하지 않습니다.')
  .required('이메일 인증해주세요'),
  pw: yup.string()
    .max(16, "비밀번호는 최대 16자리입니다!")
    .matches(
      /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]).{8,}[^\s]*$/,
      "알파벳, 숫자, 공백을 제외한 특수문자를 모두 포함한 8자리 이상 입력해주세요"
  )
    // .matches(regexPasswd, '비밀번호를 8~16자로 영문 대소문자, 숫자, 특수기호를 조합해서 사용하세요.')
    .required('비밀번호를 입력해주세요'),
  checkPw: yup
    .string()
    .oneOf([yup.ref('pw'), null], '비밀번호가 일치하지 않습니다')
    .required('비밀번호를 한번 더 입력해주세요'),
})

const SignInForm = () => {
  const [selectedButton, setSelectedButton] = useState(null);
  const [serviceButton, setserviceButton] = useState(null);

  const handleButtonClick = (buttonType) => {
    if (selectedButton === "normal") {
      setserviceButton(null);
    }
    setSelectedButton(buttonType);
  };

  const handleServiceClick = (buttonType) => {
    setserviceButton(buttonType);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    // 여기에 로그인 처리 로직을 추가하세요.
  };

  return (
    <div className="container" style={{ marginTop: "100px" ,marginBottom:"100px"}}>
      <div className="row justify-content-center">
        <div className="col-md-12">
          <form
            onSubmit={handleSubmit}
            className="rounded p-4 border shadow-sm mx-auto"
            style={{ width: "100%", maxWidth: "70%" }}
          >
          <h2 className="mb-4">Sign In</h2>
          <button
            type="button"
            className={`btn ${selectedButton === "normal" ? "btn-primary text-white" : "btn-outline-primary text-dark"} rounded-pill py-3 mx-2`}
            onClick={() => handleButtonClick("normal")}
          >
            일반 가입
          </button>
          <button
            type="button"
            className={`btn ${selectedButton === "company" ? "btn-primary text-white" : "btn-outline-primary text-dark"} rounded-pill py-3 mx-2`}
            onClick={() => handleButtonClick("company")}
          >
            업체 가입
          </button>

          <div className="form-group mb-4">
            <label htmlFor="username">Username</label>
            <input type="text" className="form-control rounded-pill py-4" id="username" placeholder="Enter username" required />
          </div>
          <div className="form-group mb-4">
            <label htmlFor="email">Email</label>
            <input type="email" className="form-control rounded-pill py-4" id="email" placeholder="Enter email" required />
          </div>
          <div className="form-group mb-4">
            <label htmlFor="phoneNumber">Phone Number</label>
            <div className="input-group">
              <input type="tel" className="form-control rounded-pill py-4" id="phoneNumber" placeholder="Enter phone number" required />
              <button type="button" className="btn btn-secondary rounded-pill py-3" style={{ marginLeft: "-1px", backgroundColor: PRIMARY_COLOR }}>Send Code</button>
            </div>
          </div>
          <div className="form-group mb-4">
            <label htmlFor="enterCode">Enter Code</label>
            <div className="input-group">
              <input type="text" className="form-control rounded-pill py-4" id="enterCode" placeholder="Enter code" required />
              <button type="button" className="btn btn-secondary rounded-pill py-3" style={{ marginLeft: "-1px", backgroundColor: PRIMARY_COLOR }}>Verify Code</button>
            </div>
          </div>
          <div className="form-group mb-4">
            <label htmlFor="password">Password</label>
            <input type="password" className="form-control rounded-pill py-4" id="password" placeholder="Password" required />
          </div>
          <div className="form-group mb-4">
            <label htmlFor="confirmPassword">Confirm Password</label>
            <input type="password" className="form-control rounded-pill py-4" id="confirmPassword" placeholder="Confirm password" required />
          </div>
          {selectedButton === "company" && (
            <div className="mt-3">
              <button
                type="button"
                className={`btn ${serviceButton === "clean" ? "btn-primary text-white" : "btn-outline-primary text-dark"} rounded-pill py-3 mx-2`}
                style={{ minWidth: "120px" }}
                onClick={() => handleServiceClick("clean")}
              >
                청소
              </button>
              <button
                type="button"
                className={`btn ${serviceButton === "delivery" ? "btn-primary text-white" : "btn-outline-primary text-dark"} rounded-pill py-3 mx-2`}
                style={{ minWidth: "120px" }}
                onClick={() => handleServiceClick("delivery")}
              >
                용달
              </button>
            </div>
          )}
          <button
            type="submit"
            className="btn btn-primary rounded-pill py-3"
            style={{ width: "100%", marginTop: "5px", marginBottom: "5px", backgroundColor: "#4A3AFF" }}
          >
            Sign In
          </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default SignInForm;
