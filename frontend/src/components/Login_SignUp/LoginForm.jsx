import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import useAuthStore from '../../store/store';
import classes from './LoginForm.module.css';
import {motion} from "framer-motion"
const LoginForm = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  const login = useAuthStore((state) => state.login);
  const isLoading = useAuthStore((state) => state.isLoading);
  const error = useAuthStore((state) => state.error);
  const [rememberMe, setRememberMe] = useState(false);

  const handleSubmit = async (event) => {
    event.preventDefault();
    await login(email, password, navigate, rememberMe);
  };

  return (
    <motion.div initial={{opacity:0}}  animate={{opacity:1}}   className={`container mt-5 `} >
      <div className="row justify-content-center">
        <div className="col-md-10">
          <form
            onSubmit={handleSubmit}
            className={`rounded p-4 border shadow-sm mx-auto ${classes.backColor}`}
            style={{ width: '100%' }}
          >
            <div className="col"  style={{ marginLeft: '60px', marginBottom: '40px',marginTop:'10px' }}>
              <div >
               <img src="login-house.gif" className={`${classes.imgStyle}`} alt="GIF" />
              </div>
              <div>
              <h2 className="mb-4"> 움집에 오신걸 환영합니다!</h2>
              </div>
            </div>
          
            <div className="form-group mb-4">
              <label htmlFor="email" className={`${classes.labelMargin}`}>이메일</label>
              <input
                type="email"
                value={email}
                onChange={(event) => setEmail(event.target.value)}
                className={`form-control rounded-pill py-4 ${classes.inputWithPadding} ${classes.topMargin}`}
                id="email"
                placeholder="이메일을 입력해주세요"
                required
              />
              <small className={`form-text text-muted `} style={{marginLeft: "20px"}}>
                이메일의 전체 형식을 입력해주세요 !
              </small>
            </div>

            <div className="form-group mb-4">
              <label htmlFor="password" className={`${classes.labelMargin}`}>비밀번호</label>
              <input
                type="password"
                value={password}
                onChange={(event) => setPassword(event.target.value)}
                className={`form-control rounded-pill py-4  ${classes.inputWithPadding}`}
                id="password"
                placeholder="비밀번호를 입력해주세요 "
                required
              />
            </div>

            <div className={`form-group form-check mb-4 ${classes.rightAligned}`}>
              <input
                type="checkbox"
                className="form-check-input"
                id="rememberMe"
                checked={rememberMe}
                onChange={(event) => setRememberMe(event.target.checked)}
              />
              <label className={`form-check-label `} 
              style={{marginRight: "20px"
            }}
              htmlFor="rememberMe">
                로그인 정보 저장
              </label>
            </div>

            <button
              type="submit"
              disabled={isLoading}
              className="btn btn-primary rounded-pill py-3"
              style={{
                width: '100%',
                marginTop: '5px',
                marginBottom: '5px',
                backgroundColor: '#40A2D8',
                border: '#40A2D8',
                fontSize: "15pt"
              }}
            >
              로그인
            </button>
            <div
              style={{
                margin: '15px 0',
                textAlign: 'center',
                fontSize: '16px',
                color: '#666666',
              }}
            >
              아직 회원이 아니신가요? 
              <Link
                to="/signup"
                style={{
                  color: '#2681d9',
                  textDecoration: 'none',
                  hover: {
                    textDecoration: 'underline',
                  },
                }}
              >
                회원가입
              </Link>
            </div>
          </form>
          {error && <p>Error: {error}</p>}
          {isLoading && <p>Loading...</p>}
        </div>
      </div>
    </motion.div>
  );
};

export default LoginForm;
