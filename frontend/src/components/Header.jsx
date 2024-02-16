import React, { useEffect, useState } from "react";
import { NavLink, useLocation } from "react-router-dom";
import { motion } from "framer-motion";
import useAuthStore from "../store/store";
import "./Header.css";

const Header = () => {
  const [scrollY, setScrollY] = useState(0);
  const [id, setId] = useState(null);
  const [profileImage, setProfileImage] = useState("");
  const [checkWho, setWho] = useState(null);
  const userInfo = JSON.parse(
    localStorage.getItem("userInfo") ||
      sessionStorage.getItem("userInfo") ||
      "{}"
  );

  const location = useLocation();

  const handleScroll = () => {
    setScrollY(window.scrollY);
  };

  useEffect(() => {
    window.addEventListener("scroll", handleScroll);

    const storedUserInfo =
      localStorage.getItem("userInfo") || sessionStorage.getItem("userInfo");
    console.log(storedUserInfo);
    if (storedUserInfo) {
      const { profileImage, who, id } = JSON.parse(storedUserInfo);
      setProfileImage(profileImage || "");
      setWho(who || "");
      setId(id || "");
    }

    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  const navItemVariants = {
    hover: {
      translateY: -3,
      fontWeight: "bold",
      textDecoration: "underline",
    },
  };
  //대시보드
  const dashBoardChangeImage =() =>{
    const imgElement = document.getElementById('dashBoard')
    imgElement.src = '/dashboardIcon.gif'; // 호버
    imgElement.style.width = '1.7rem'; // 너비 조정
    imgElement.style.height = 'auto'; // 높이 자동 조정
  }
  const dashBoardResetImage = ()=> {
    const imgElement = document.getElementById('dashBoard');
    imgElement.src = '/dashboardIcon-static.png'; // 호버 out
    imgElement.style.width = '1.5rem'; // 너비 조정
    imgElement.style.height = 'auto'; // 높이 자동 조정
  }
  //중고
  const tradeChangeImage =() =>{
    const imgElement = document.getElementById('trade')
    imgElement.src = '/saleIcon2.gif'; // 호버
    imgElement.style.width = '1.7rem'; // 너비 조정
    imgElement.style.height = 'auto'; // 높이 자동 조정
  }
  const tradeResetImage = ()=> {
    const imgElement = document.getElementById('trade')
    imgElement.src = '/saleIcon2-static.png'; // 호버 out
    imgElement.style.width = '1.5rem'; // 너비 조정
    imgElement.style.height = 'auto'; // 높이 자동 조정
  }
  //도움
  const helpChangeImage =() =>{
    const imgElement = document.getElementById('help')
    imgElement.src = '/careIcon.gif';
    imgElement.style.width = '1.7rem'; // 너비 조정
    imgElement.style.height = 'auto'; // 높이 자동 조정
  }
  const helpResetImage = ()=> {
    const imgElement = document.getElementById('help')
    imgElement.src = '/careIcon-static.png';
    imgElement.style.width = '1.5rem'; // 너비 조정
    imgElement.style.height = 'auto'; // 높이 자동 조정
  }
  //알람
  const alarmChangeImage =() =>{
    const imgElement = document.getElementById('alarm')
    imgElement.src = '/alarmIcon.gif'
    imgElement.style.width = '1.7rem'; // 너비 조정
    imgElement.style.height = 'auto'; // 높이 자동 조정
  }
  const alarmResetImage = ()=> {
    const imgElement = document.getElementById('alarm')
    imgElement.src = '/alarmIcon-static.png';
    imgElement.style.width = '1.5rem'; // 너비 조정
    imgElement.style.height = 'auto'; // 높이 자동 조정
  }
  return (  
    <motion.div
      style={{
        backgroundColor: "#fff",
        position: "fixed",
        width: "100%",
        zIndex: "1000",
        top: "0",
        boxShadow: scrollY > 0 ? "0px 8px 12px rgba(0, 0, 0, 0.1)" : "none",
        transition: "box-shadow 0.5s ease",
      }}
    >
      <div className="">
        <div
          className="navbar navbar-expand-lg navbar-light"
          style={{ height: "8vh" }}
        >
          <NavLink
            className="navbar-brand"
            to={checkWho === 1 ? "/dashboard" : "/dashbordcompany"}
          >
            <div style={{ display: "flex", alignItems: "center" }}>
              <img
                src="/umzipLogo.png"
                alt=""
                style={{ width: "60px", height: "auto", marginLeft: "10px" }}
              />
              <span style={{ marginLeft: "20px" }}>내 손안의 작은 이사 : 움집</span>
            </div>
          </NavLink>
          <div
            className="collapse navbar-collapse justify-content-end"
            id="navbarNav"
          >
            <ul className="navbar-nav d-flex justify-content-center align-items-center">
              <motion.li
                whileHover={navItemVariants.hover}
                className="nav-item"
              >
                <NavLink
                  className="nav-link mx-3 px-3"
                  to={checkWho === 1 ? "/dashboard" : "/dashbordcompany"}
                  activeclassname="active" // 이 부분이 추가되었습니다
                >
                  <div className="d-flex align-items-center gap-1" onMouseOver={dashBoardChangeImage} onMouseOut={dashBoardResetImage}>
                    <img 
                    
                      id ="dashBoard"
                      style={{ width: "1.5rem", height: "auto", marginRight: "5px"}}
                      src='/dashboardIcon-static.png'
                    />
                    <p className="m-0">대시보드</p>
                  </div>
                </NavLink>
              </motion.li>
              {checkWho === 1 && (
                <>
                  <motion.li
                    whileHover={navItemVariants.hover}
                    className="nav-item"
                  >
                    <NavLink
                      className="nav-link mx-3 px-3 d-flex align-items-center"
                      to="/trade"
                      activeclassname="active" // 이 부분이 추가되었습니다
                    >
                      <div className="d-flex align-items-center gap-1"  onMouseOver={tradeChangeImage} onMouseOut={tradeResetImage}>
                        <img
                          id ="trade"
                          style={{ width: "1.5rem", height: "auto", marginRight: "5px"}}
                          className=""
                          src='/saleIcon2-static.png'
                          alt=""
                        />
                        <p className="m-0">중고</p>
                      </div>
                    </NavLink>
                  </motion.li>
                  <motion.li
                    whileHover={navItemVariants.hover}
                    className="nav-item"
                  >
                    <NavLink
                      className="nav-link mx-3 px-3 d-flex align-items-center"
                      to="/help"
                      activeclassname="active" // 이 부분이 추가되었습니다
                    >
                      <div className="d-flex align-items-center gap-1" onMouseOver={helpChangeImage} onMouseOut={helpResetImage}>
                        <img
                        id ="help"
                        style={{ width: "1.5rem", height: "auto", marginRight: "5px"}}
                          className=""
                          src='/careIcon-static.png'
                          alt=""
                        />
                        <p className="m-0">도움</p>
                      </div>
                    </NavLink>
                  </motion.li>
                </>
              )}
              <motion.li
                whileHover={navItemVariants.hover}
                className="nav-item"
              >
                <NavLink
                  className="nav-link mx-3 px-3"
                  to="/Alarm"
                  activeclassname="active" // 이 부분이 추가되었습니다
                >
                  <div className="d-flex align-items-center gap-1" onMouseOver={alarmChangeImage} onMouseOut={alarmResetImage}>
                    <img
                    id ="alarm"
                      src= '/alarmIcon-static.png'
                      style={{ width: "1.4rem", height: "auto", marginRight: "5px"}}
                    />
                    <p className="m-0">알림</p>
                  </div>
                </NavLink>
              </motion.li>
              <motion.li
                whileHover={navItemVariants.hover}
                className="nav-item"
              >
                <NavLink
                  className="nav-link mx-3 px-3"
                  to={`/myprofile/${id}`}
                  activeclassname="active" // 이 부분이 추가되었습니다
                >
                  <img
                    className="shadow"
                    src={profileImage}
                    alt="Profile"
                    style={{
                      maxWidth: "30px",
                      height: "auto",
                      borderRadius: "50%",
                      objectFit: "cover",
                      marginRight: "7px"
                    }}
                  />
                  프로필
                </NavLink>
              </motion.li>
            </ul>
          </div>
        </div>
      </div>
    </motion.div>
  );
};

export default Header;
