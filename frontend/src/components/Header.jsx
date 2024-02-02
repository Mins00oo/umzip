import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { motion } from 'framer-motion';

const Header = () => {
  const [scrollY, setScrollY] = useState(0);

  const handleScroll = () => {
    setScrollY(window.scrollY);
  };

  useEffect(() => {
    window.addEventListener('scroll', handleScroll);

    // Clean up the event listener when the component unmounts
    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, []);

  const navItemVariants = {
    hover: {
      translateY: -3,
      fontWeight: 'bold',
      textDecoration: 'underline',
    },
  };

  return (
    <motion.div
      style={{
        backgroundColor: '#fff',
        position: 'fixed',
        width: '100%',
        zIndex: '1000',
        top: '0',
        boxShadow: scrollY > 0 ? '0px 8px 12px rgba(0, 0, 0, 0.1)' : 'none',
        transition: 'box-shadow 0.5s ease',
      }}
    >
      <div className="">
        <div className="navbar navbar-expand-lg navbar-light" style={{height:"6vh"}}>
          <Link className="navbar-brand" to="/">
            로고
          </Link>
          <div className="collapse navbar-collapse justify-content-end" id="navbarNav">
            <ul className="navbar-nav d-flex justify-content-center align-items-center">
              <motion.li whileHover={navItemVariants.hover} className="nav-item">
                <Link className="nav-link mx-3 px-3" to="/dashboard">
                  대시보드
                </Link>
              </motion.li >
              <motion.li whileHover={{ translateY: -3 ,fontWeight:"bold", textDecoration: "underline"}}  className="nav-item">
                <Link className="nav-link mx-3 px-3 d-flex align-items-center" to="/trade">
                  <img style={{ width: 17, height: 17 }} className='mb-1' src='/iconoir.png' alt='' />
                  <p className='m-0'>중고</p>
                </Link>
                </motion.li >
              <motion.li whileHover={{ translateY: -3 ,fontWeight:"bold", textDecoration: "underline"}}  className="nav-item">
                <Link className="nav-link mx-3 px-3 d-flex align-items-center" to="/help">
                  <img style={{ width: 17, height: 17 }} className='mb-1' src='/iconoir.png' alt='' />
                  도움
                </Link>
                </motion.li >
              <motion.li whileHover={{ translateY: -3 ,fontWeight:"bolder", textDecoration: "underline"}}  className="nav-item ">
                <Link className="nav-link mx-3 px-3" to="/Alarm">
                  알림
                </Link>
                </motion.li >
              <motion.li whileHover={{ translateY: -3 ,fontWeight:"bold", textDecoration: "underline"}}  className="nav-item">
                <Link className="nav-link mx-3 px-3" to="/myprofile">
                  프로필
                </Link>
                </motion.li >
                </ul>
          </div>
        </div>
      </div>
    </motion.div>
  );
};

export default Header;
