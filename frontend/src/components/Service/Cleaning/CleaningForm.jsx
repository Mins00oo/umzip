import { useState } from "react";
import { AnimatePresence, motion, stagger, useAnimate } from "framer-motion";
import ReactDatePicker from "react-datepicker";
import Calendar from "./Calendar";
import "react-datepicker/dist/react-datepicker.css";
import Clock from "./Clock";
import { Link, useNavigate } from "react-router-dom";
import CheckButton from "./Check_Button";
import PhotoView from "./PhotoView";
import ProgressBar from "./Progressbar"; // 대소문자 이슈

import Address from "./Address";
import AddButton from "./AddButton";
import axios from "axios";
import Wave from "../../MainPage/DashBoard/Wave";
import { api } from "../../../services/api";
// import { faL } from "@fortawesome/free-solid-svg-icons";





export default function CleaningFrom() {
  const [selectedOption, setSelectedOption] = useState(null);
  const [startDate, setStartDate] = useState(new Date());
  const [isDropdownClockOpen, setDropdownClockOpen] = useState(false);
  const [isWhatTime, setisWhatTime] = useState()
  const [isLoading, setIsLoading] = useState(false)
  const [userinput, setuserinput] = useState("")
  const [isActive, setIsActive] = useState("first")
  const [isTime, setIsTime] = useState("")
  const [selectedFiles, setSelectedFiles] = useState([]);
  const navigate = useNavigate()
  const [activeStep, setActiveStep] = useState(1);
  const totalSteps = 4; // 전체 단계 수에 맞게 수정
  const [whatModal, setWhatModal] = useState(null)
  const [isModalOpen, setIsModalOpen] = useState(false)
  const [option, setOption] = useState({ isGom: false, isOutsideWindow: false, isNewHous: false, isSticker: false })
  const [whereStart, setwhereStart] = useState({})
  const [whereEnd, setwhereEnd] = useState({})
  const [isBalkoni, setIsBalkoni] = useState(null)
  const [isBok, setIsBok] = useState(null)
  const [detailAddress, setDetailAddress] = useState(null)
  const [ispyung, setIsPyung] = useState("m^2")
  const [whatHouse, setWhatHouse] = useState(null)
  const [roomCounts, setRoomCounts] = useState(null)
  const [windowCounts, setWindowCounts] = useState(null)
  const [scope, animate] = useAnimate()
  const [newscope, newanimate] = useAnimate()
  const [calResult, setcalResult] = useState("")
  const [sigungu, setSigungu] = useState("")
  const getToday = (value) => {
    return value.toISOString().split('T')[0];
  };
  const handleOptionChange = (value) => {
    setSelectedOption(value);
  };


  const toggleClockDropdown = () => {
    setDropdownClockOpen((prev) => !prev);
  };

  
  const goToNextForm = async () => {

    if (isActive === "first") {
      if (startDate && selectedOption && isWhatTime) {
        const today = new Date()
        if (getToday(startDate) === getToday(today)) {
          const currentTime = new Date().getHours() * 60 + new Date().getMinutes();
          let targetHour = parseInt(isWhatTime.split(':')[0]) % 12;
          const targetMinute = parseInt(isWhatTime.split(':')[1]);
          const targetOption = isWhatTime.split(' ')[1];

          // selectedOption이 PM이면 targetHour에 12를 더해줌
          if (selectedOption === "PM") {
            targetHour += 12;
          }

          const targetTime = targetHour * 60 + targetMinute;

          // 이미 지나간 시간인 경우
          if (currentTime > targetTime) {
            alert("잘못된 시간입니다!")
            return;
          }
          else {
            setIsActive("second")
            if (activeStep < totalSteps) {
              setActiveStep(activeStep + 1);
            }
          }
        } else {
          setIsActive("second")
          if (activeStep < totalSteps) {
            setActiveStep(activeStep + 1);
          }
        }

      } else {
        animate("#inputcomponent", { x: [-10, 0, 20, 0] }, { type: "spring", duration: 1, delay: stagger(0.05) })


      }
    }
    else if (isActive === "second") {
      if (whereStart && detailAddress && roomCounts && windowCounts && isBalkoni && isBok && whatHouse) {
        // const data = await axios_cal()
        // console.log(data)
        setIsActive("third")
        if (activeStep < totalSteps) {
          setActiveStep(activeStep + 1);
        }
      } newanimate("#secondcomponent", { x: [-10, 0, 20, 0] }, { type: "spring", duration: 1, delay: stagger(0.05) })
    }
    else if (isActive === "third") {
      if (whereStart && detailAddress && roomCounts && windowCounts && isBalkoni && isBok && whatHouse) {
        const data = await axios_cal()
        // console.log(data)
        setIsActive("fourth")
        if (activeStep < totalSteps) {
          setActiveStep(activeStep + 1);
        }
      }
    }
  }
  const axios_CallCle = async () => {

    try {
      const response = await api.post(
        '/clean/user/company-list',
        {
          reservationTime: `${getToday(startDate)} ${isWhatTime}`, // 실제 시작 시간을 올바른 날짜 및 시간 형식으로 교체
          sigungu: sigungu, // 실제 sigungu 값으로 교체 (정수)
          limit:20,
        },

      );
      return response
    }
    catch (e) {

    }
  }

  const hadlesubmit = async () => {
    try {
      const result = await axios_CallCle();
      console.log(result)
      const roomSize = parseInt(whatHouse, 10)
      const roomCount = parseInt(roomCounts, 10)
      const windowCount = parseInt(windowCounts, 10)
      let balconyExistence = ""
      let duplex = ""
      if (isBalkoni === "있음") { balconyExistence = true }
      else { balconyExistence = false }

      if (isBok === "네") { duplex = true }
      else { duplex = false }
      navigate('/recommend', {
        state: {
          type: "청소",
          axios_data: result.data,
          userInput: {
            "imageFileList": [selectedFiles], // 필요한 경우 이미지 파일 배열로 교체
            "price": calResult, // 실제 가격 값으로 교체 (긴 정수)
            "clean": {
              "reservationTime": `${getToday(startDate)} ${isWhatTime}`, // 실제 carId 값으로 교체 (긴 정수)
              "address": whereStart.address, // 실제 시작 시간을 올바른 날짜 및 시간 형식으로 교체
              "addressDetail": detailAddress, // 실제 종료 시간을 올바른 날짜 및 시간 형식으로 교체
              "sigungu": sigungu, // 실제 출발지 값으로 교체 (문자열)
              "roomSize": roomSize,
              "roomCount": roomCount,
              "windowCount": windowCount,
              "balconyExistence": balconyExistence,
              "duplex": duplex,
              "mold": option.isGom,
              "externalWindow": option.isOutsideWindow,
              "houseSyndrome": option.isNewHous,
              "removeSticker": option.isSticker
            }
          }
        }
      });
    } catch (error) {

    }

  }

  const goTobeforeForm = () => {
    if (isActive === "second") { setIsActive("first") }
    else if (isActive === "third") { setIsActive("second") }
    else if (isActive === "fourth") { setIsActive("third") }
    if (activeStep > 1) {
      setActiveStep(activeStep - 1);
    }
  }
  const handleUserInput = (event) => {
    setuserinput(event.target.value);
  };
  const hadleModal = (event) => {
    setWhatModal(event)
    setIsModalOpen(true)
  }
  const handleIsPyung = () => {
    if (ispyung === "m^2") { setIsPyung("평") }
    else { setIsPyung("m^2") }
  }
  const getServiceNameByKey = (key) => {
    const serviceNameMap = {
      isGom: '곰팡이 청소',
      isOutsideWindow: '외부 유리창 청소',
      isNewHous: '세집 증후군 제거',
      isSticker: '스티커&스트지 제거',
      // 다른 서비스에 대한 이름 매핑 추가
    };

    return serviceNameMap[key] || key; // key에 해당하는 값이 없을 경우 key 그대로 반환
  };
  const axios_cal = async () => {

    const roomSize = parseInt(whatHouse, 10)
    const roomCount = parseInt(roomCounts, 10)
    const windowCount = parseInt(windowCounts, 10)

    let balconyExistence = ""
    let duplex = ""
    let mold = ""
    let externalWindow = ""
    let houseSynddrome = ""
    let removeSticker = ""
    if (isBalkoni === "있음") { balconyExistence = true }
    else { balconyExistence = false }

    if (isBok === "네") { duplex = true }
    else { duplex = false }

    setIsLoading(true)
    try {
      const response = await api.post('/clean/user/calculation',
        {
          "reservationTime": `${getToday(startDate)} ${isWhatTime}`,
          "roomSize": roomSize,
          "roomCount": roomCount,
          "windowCount": windowCount,
          "balconyExistence": balconyExistence,
          "duplex": duplex,
          "mold": option.isGom,
          "externalWindow": option.isOutsideWindow,
          "houseSyndrome": option.isNewHous,
          "removeSticker": option.isSticker,
        },
      );
      setcalResult(response.data.result)


      setIsLoading(false)

      return response.data.result
    } catch (error) {
      console.error(error);
      return error
    }

  }
  const handleAddButtonClick = (buttonName) => {
    // Copy the current state
    const updatedOption = { ...option };

    // Toggle the value for the clicked button
    updatedOption[buttonName] = !updatedOption[buttonName];

    // Update the state with the new values
    setOption(updatedOption);
  };

  return (<>
    <div style={{
      position: 'absolute', width: '20%', top: '15%',
      left: '70%',
    }}>
      <ProgressBar steps={totalSteps} activeStep={activeStep} />
    </div>
    <AnimatePresence>
      {isModalOpen && (
        <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} onClick={() => setIsModalOpen(false)}
          style={{
            zIndex: "99",
            position: 'fixed',
            top: 0,
            left: 0,
            width: '100%',
            height: '100%',
            backgroundColor: 'rgba(0, 0, 0, 0.5)', // 배경색 및 투명도 조절
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
          }}>
          <div style={{
            position: 'relative',
            width: '50%',
            backgroundColor: 'white', // 내용의 배경색
            padding: '20px',
            borderRadius: '8px', // 내용의 모서리 둥글게
          }}>
            <Address setSigungu={setSigungu} whatModal={whatModal} setwhereStart={setwhereStart} setwhereEnd={setwhereEnd} setIsModalOpen={setIsModalOpen} />
          </div>
        </motion.div>
      )}
    </AnimatePresence>
    <AnimatePresence>
      {isLoading && (
        <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} onClick={() => setIsModalOpen(false)}
          style={{
            zIndex: "99",
            position: 'fixed',
            top: 0,
            left: 0,
            width: '100%',
            height: '100%',
            backgroundColor: 'rgba(0, 0, 0, 0.5)', // 배경색 및 투명도 조절
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
          }}>
          <div style={{
            position: 'relative',
            width: '40%',
            // backgroundColor: 'white', // 내용의 배경색
            padding: '20px',
            borderRadius: '8px', // 내용의 모서리 둥글게
          }}>
            <div className="d-flex justify-content-center align-items-center gap-4 " style={{ width: "100%" }}>
              <img style={{ width: "20rem", height: "20rem" }} src="./cal.png" />
              {/* <h2 style={{color:"white"}}>계산이 진행중입니다..</h2> */}
              <Wave />
            </div>
          </div>
        </motion.div>
      )}
    </AnimatePresence>
    <AnimatePresence mode="wait">

      {isActive === "first" && <motion.div key="firstForm">

        <motion.div initial={{ opacity: 0.1 }} animate={{ opacity: 1 }} exit={{ opacity: 0.1 }} transition={{ duration: 0.3 }}  >
          <Link to="/dashboard" className="d-flex align-items-center gap-2" style={{
            position: 'absolute',
            top: '10%',
            left: '10%',
            textDecoration: "none",
            // 이미지의 높이를 설정해주세요
            cursor: 'pointer',
          }}><img style={{
            width: '2rem', // 이미지의 너비를 설정해주세요
            height: '2rem',
          }}
            src="/box-arrow-left.png"  // 나가기 버튼 이미지의 경로를 설정해주세요
            alt="Exit Button"

            />
            <motion.p className="m-0">뒤로가기</motion.p>
          </Link>
        </motion.div>

        <motion.h5 initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} transition={{ duration: 0.3 }} onClick={goToNextForm} className="d-flex align-items-center gap-2" style={{
          position: 'absolute',
          top: '85%',
          left: '85%',
          // 이미지의 높이를 설정해주세요
          cursor: 'pointer',
        }}>
          <motion.p className="m-0" style={{ color: "#006EEE" }}>다음으로&rarr;</motion.p>

        </motion.h5>
        <motion.div ref={scope} className="d-flex justify-content-center align-items-center" style={{ width: "100vw", height: "100vh" }} initial={{ opacity: 0, x: -200 }} animate={{ opacity: 1, x: 0 }} exit={{ opacity: 0, x: -200 }} transition={{ duration: 0.3 }} >
          <motion.div style={{ position: 'relative' }} whileHover={{ fontWeight: "bold" }} >
            {/* 나가기 버튼 이미지 */}



          </motion.div>
          <div className="col-12 d-flex flex-column justify-content-center align-items-center">
            <div className="col-6 gap-5 d-flex flex-column" >

              <div className="mb-3 d-flex align-items-center ">
                <div className="col-3">
                  <Calendar setStartDate={setStartDate} startDate={startDate} isTime={isTime} setIsTime={setIsTime} />
                </div>
                <h3 className="m-0 col-6">일</h3>
              </div>

              <div>
                <div className="d-flex gap-5 align-items-center">
                  {/* 첫 번째 라디오 버튼 */}
                  <div className="d-flex gap-3  " id={selectedOption ? "" : "inputcomponent"}>
                    <motion.div
                      whileHover={{ cursor: "pointer", scale: 1.1 }}
                      className={`d-flex gap-3 justify-content-center align-items-center ${selectedOption === 'AM' ? 'checked' : ''}`}
                      value="AM"
                      onClick={() => handleOptionChange('AM')}
                    >
                      <motion.img
                        style={{ width: "1.5rem" }}
                        src={selectedOption === 'AM' ? '/Checkbox_Check.png' : '/Checkbox_Unchecked.png'}
                      />
                      <p className="m-0 fw-bold">AM</p>
                    </motion.div>

                    {/* 두 번째 라디오 버튼 */}
                    <motion.div
                      whileHover={{ cursor: "pointer", scale: 1.1 }}
                      className={`d-flex gap-3 justify-content-center align-items-center ${selectedOption === 'PM' ? 'checked' : ''}`}
                      value="PM"
                      onClick={() => handleOptionChange('PM')}
                    >
                      <motion.img
                        style={{ width: "1.5rem" }}
                        src={selectedOption === 'PM' ? '/Checkbox_Check.png' : '/Checkbox_Unchecked.png'}
                      />
                      <p className="m-0 fw-bold">PM</p>
                    </motion.div>
                  </div>
                  {/* 드롭다운 버튼 */}
                  <div style={{ position: "relative" }} className="d-flex align-items-center gap-4">
                    <button id={isWhatTime ? "" : "inputcomponent"} className="btn btn-primary rounded-5 d-flex  align-items-center gap-2 p-2 " style={{ width: "10rem", height: "4rem" }} onClick={toggleClockDropdown}>
                      <p className="m-0 col-10">{isWhatTime || "시간선택"}</p><img className="col-2" src='/clock.png' style={{ width: "1rem", height: "1rem" }} />
                    </button>
                    <AnimatePresence>
                      {isDropdownClockOpen && (
                        <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }}
                          className="mt-2" style={{ position: "absolute", top: "-200%", left: "120%", zIndex: 1 }}>
                          <Clock setisWhatTime={setisWhatTime} toggleClockDropdown={toggleClockDropdown} />
                        </motion.div>
                      )}
                    </AnimatePresence>
                    <h3 className="m-0">시에 청소가 필요해요! </h3>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </motion.div>
      </motion.div>}
      {isActive === "second" && <motion.div key="secondForm">
        <motion.h5 initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} transition={{ duration: 0.3 }} onClick={goToNextForm} className="d-flex align-items-center gap-2" style={{
          position: 'absolute',
          top: '85%',
          left: '85%',
          // 이미지의 높이를 설정해주세요
          cursor: 'pointer',
        }}>
          <motion.p className="m-0" style={{ color: "#006EEE" }}>다음으로&rarr;</motion.p>

        </motion.h5>

        <motion.h5 initial={{ opacity: 0.1 }} animate={{ opacity: 1 }} exit={{ opacity: 0.1 }} transition={{ duration: 0.3 }} onClick={goTobeforeForm} to="/dashboard" className="d-flex align-items-center gap-2" style={{
          position: 'absolute',
          top: '10%',
          left: '10%',
          // 이미지의 높이를 설정해주세요
          cursor: 'pointer',
        }}><img style={{
          width: '2rem', // 이미지의 너비를 설정해주세요
          height: '2rem',
        }}
          src="/box-arrow-left.png"  // 나가기 버튼 이미지의 경로를 설정해주세요
          alt="Exit Button"

          />
          <motion.p className="m-0" style={{ color: "#006EEE" }}>이전으로</motion.p>
        </motion.h5>
        <div style={{ width: "100%", height: "100%" }}>
          <motion.div ref={newscope} className="d-flex justify-content-center align-items-center" style={{ width: "100vw", height: "100vh" }} initial={{ opacity: 0, x: 200 }} animate={{ opacity: 1, x: 0 }} exit={{ opacity: 0, x: 200 }} transition={{ duration: 0.3 }} >
            <div className="col-8 d-flex gap-3 justify-content-center align-items-center">
              <div className="col-6 d-flex flex-column gap-5 p-3 justify-content-center align-items-center">
                <div id={whereStart.address ? "" : "secondcomponent"} className="d-flex  gap-1 align-items-center " style={{ width: "100%", height: "2rem" }}>
                  <div className="col-2 fw-bold">집주소 : </div>
                  <div onClick={() => hadleModal("start")} className="col-8 shadow rounded-4 fw-bold d-flex justify-content-center align-items-center" style={{ height: "100%" }} >{whereStart.address ? (
                    <p className="m-0">{whereStart.address}</p>
                  ) : (
                    <p className="m-0 text-muted">주소를 입력해주세요!</p>
                  )}</div>
                  <button onClick={() => hadleModal("start")} className="btn-primary btn col-2 d-flex justify-content-center align-items-center" style={{ height: "2rem" }}><p className="m-0">찾기</p></button>
                </div>

                <div className="col-9 d-flex " id={detailAddress ? "" : "secondcomponent"} style={{ width: "100%" }} >
                  <div className="col-2 fw-bold">상세주소 : </div>
                  <input style={{ border: "none" }} className="col-10 shadow fw-bold text-center rounded-4 " placeholder="상세주소를 입력해주세요!" type="text" onChange={(event) => setDetailAddress(event.target.value)} ></input>
                </div>

                <div id={whatHouse ? "" : "secondcomponent"} className="col-9 d-flex " style={{ width: "100%" }} >
                  <div className="col-2 fw-bold">집크기 : </div>
                  <div className="col-12 d-flex gap-2 " >
                    <input style={{ border: "none" }} className="col-8 shadow fw-bold text-center rounded-4 " placeholder="집크기를 입력해주세요!" type="number" onChange={(event) => setWhatHouse(event.target.value)} ></input>
                    <button style={{ height: "2rem" }} onClick={handleIsPyung} className="btn-primary btn col-2 d-flex justify-content-center align-items-center" ><p className="m-0">{ispyung}</p></button>
                  </div>
                </div>
                <div className="d-flex col-12  gap-4">
                  <div id={roomCounts ? "" : "secondcomponent"} className=" d-flex col-6 gap-1 align-items-center"  >
                    <div className=" col-4 fw-bold">방개수 : </div>
                    <input style={{ border: "none", height: "2rem" }} className="col-6 shadow fw-bold text-center rounded-4 " placeholder="방개수" type="number" onChange={(event) => setRoomCounts(event.target.value)}></input>
                    <div className="d-flex  align-items-center fw-bold">개</div>
                  </div>

                  <div id={windowCounts ? "" : "secondcomponent"} className="d-flex col-6 gap-1 align-items-center"  >
                    <div className="col-4 fw-bold">창개수 : </div>
                    <input style={{ border: "none", height: "2rem" }} className="col-6 shadow fw-bold text-center rounded-4 " placeholder="창개수" type="number" onChange={(event) => setWindowCounts(event.target.value)} ></input>
                    <div className="d-flex  align-items-center fw-bold ">개</div>
                  </div>
                </div>

                <div id={isBalkoni ? "" : "secondcomponent"} className="d-flex justify-content-center gap-3 align-items-center  col-12">
                  <p className="m-0 col-3 fw-bold">발코니/베란다</p>
                  <div className="col-3">
                    <CheckButton checkPacking={() => setIsBalkoni("있음")} isActive={isBalkoni === "있음"} name="있음" />
                  </div>
                  <div className="col-3">
                    <CheckButton checkPacking={() => setIsBalkoni("없음")} isActive={isBalkoni === "없음"} name="없음" />
                  </div>
                  <div className="col-3"></div>
                </div>

                <div id={isBok ? "" : "secondcomponent"} className="d-flex justify-content-center gap-3 align-items-center col-12">
                  <p className="m-0 col-3 fw-bold">복층인가요?</p>
                  <div className="col-3">
                    <CheckButton checkPacking={() => setIsBok("네")} isActive={isBok === "네"} name="네" />
                  </div>
                  <div className="col-3">
                    <CheckButton checkPacking={() => setIsBok("아니요")} isActive={isBok === "아니요"} name="아니요" />
                  </div>
                  <div className="col-3"></div>
                </div>

              </div>


            </div>

          </motion.div>
        </div>
      </motion.div>}
      {isActive === "third" &&
        <div className="d-flex justify-content-center align-items-center " style={{ width: "100vw", height: "100vh" }}>
          <motion.h5 initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} transition={{ duration: 0.3 }} onClick={goToNextForm} className="d-flex align-items-center gap-2" style={{
            position: 'absolute',
            top: '85%',
            left: '85%',
            // 이미지의 높이를 설정해주세요
            cursor: 'pointer',
          }}>
            <motion.p className="m-0" style={{ color: "#006EEE" }}>다음으로&rarr;</motion.p>

          </motion.h5>

          <motion.h5 initial={{ opacity: 0.1 }} animate={{ opacity: 1 }} exit={{ opacity: 0.1 }} transition={{ duration: 0.3 }} onClick={goTobeforeForm} to="/dashboard" className="d-flex align-items-center gap-2" style={{
            position: 'absolute',
            top: '10%',
            left: '10%',
            // 이미지의 높이를 설정해주세요
            cursor: 'pointer',
          }}><img style={{
            width: '2rem', // 이미지의 너비를 설정해주세요
            height: '2rem',
          }}
            src="/box-arrow-left.png"  // 나가기 버튼 이미지의 경로를 설정해주세요
            alt="Exit Button"

            />
            <motion.p className="m-0" style={{ color: "#006EEE" }}>이전으로</motion.p>
          </motion.h5>
          <motion.div className="col-4 p-3 gap-4 d-flex flex-column"  initial={{ opacity: 0, x: -200 }} animate={{ opacity: 1, x: 0 }} exit={{ opacity: 0, x: -200 }} transition={{ duration: 0.3 }}>




            <div className="d-flex flex-column  gap-2">
              <p style={{ width: "100%" }} className="m-0 col-3 fw-bold ">필요 추가 서비스를 선택해 주세요</p>
              <div className="d-flex gap-3 align-items-center">
                <div className="col" >
                  <AddButton value="isGom" handleAddButtonClick={handleAddButtonClick} isActive={option.isGom} name="곰팡이 청소" />
                </div>
                <div className="col">
                  <AddButton value="isOutsideWindow" handleAddButtonClick={handleAddButtonClick} isActive={option.isOutsideWindow} name="외부 유리창 청소" />
                </div>
                <div className="col" >
                  <AddButton value="isNewHous" handleAddButtonClick={handleAddButtonClick} isActive={option.isNewHous} name="세집 증후군 제거" />
                </div>
                <div className="col">
                  <AddButton value="isSticker" handleAddButtonClick={handleAddButtonClick} isActive={option.isSticker} name="스티커&스트지 제거" />
                </div>
              </div>

            </div>

            <div className="d-flex justify-content-center gap-2  text-center">
              <p className="m-0 col-3 fw-bold">참고 사진</p>
              <div className="col-9 d-flex">
                <PhotoView selectedFiles={selectedFiles} setSelectedFiles={setSelectedFiles} />
              </div>
            </div>
            <div className="d-flex justify-content-center gap-2  text-center">
              <p className="m-0 col-3 fw-bold">추가사항</p>
              <div className="col-9 d-flex">
                <textarea
                  className="shadow border rounded-3 p-3"
                  value={userinput}
                  onChange={handleUserInput}
                  placeholder="여기에 추가사항을 입력하세요..."
                  rows={4}  // 원하는 행 수로 조절
                  style={{ width: "100%", resize: "none" }}
                />
              </div>
            </div>
          </motion.div>
        </div>}
    {isActive === "fourth" && <div>
      {/* <motion.h5 initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} transition={{ duration: 0.3 }} onClick={goToNextForm} className="d-flex align-items-center gap-2" style={{
          position: 'absolute',
          top: '85%',
          left: '85%',
          // 이미지의 높이를 설정해주세요
          cursor: 'pointer',
        }}>
          <motion.p className="m-0" style={{ color: "#006EEE" }}>다음으로&rarr;</motion.p>

        </motion.h5> */}

      <motion.h5 initial={{ opacity: 0.1 }} animate={{ opacity: 1 }} exit={{ opacity: 0.1 }} transition={{ duration: 0.3 }} onClick={goTobeforeForm} to="/dashboard" className="d-flex align-items-center gap-2" style={{
        position: 'absolute',
        top: '10%',
        left: '10%',
        // 이미지의 높이를 설정해주세요
        cursor: 'pointer',
      }}><img style={{
        width: '2rem', // 이미지의 너비를 설정해주세요
        height: '2rem',
      }}
        src="/box-arrow-left.png"  // 나가기 버튼 이미지의 경로를 설정해주세요
        alt="Exit Button"

        />
        <motion.p className="m-0" style={{ color: "#006EEE" }}>이전으로</motion.p>
      </motion.h5>

      {calResult && <motion.div initial={{ opacity: 0, y: 100 }} animate={{ opacity: 1, y: 0 }} exit={{ opacity: 0, y: 100 }} transition={{ duration: 0.3 }}
        className="col-12 d-flex justify-content-center align-items-center" style={{ width: "100vw", height: "100vh" }}>
        <div className="col-5 p-3 d-flex flex-column ">
          <div style={{ borderBottom: "solid 1px #006EEE" }} className="d-flex flex-column gap-4 p-3">
            <div className="d-flex text-center">
              <p className="m-0 col-4">일시</p>
              <div className="col-8 d-flex  gap-3  justify-content-center">
                <p className="m-0">{isTime}</p>
                <p className="m-0">{isWhatTime}</p>
                <p className="m-0">{selectedOption}</p>
              </div>
            </div>

            <div className="d-flex text-center">
              <p className="m-0 col-4">위치</p>
              <div className="col-8 d-flex  gap-3  justify-content-center">
                <p className="m-0">{whereStart.address}</p>
                <p className="m-0">{detailAddress}</p>
              </div>
            </div>

            <div className="d-flex text-center">
              <p className="m-0 col-4">집크기</p>
              <div className="col-8 d-flex   justify-content-center">
                <p className="m-0">{whatHouse}</p>
                <p className="m-0">{ispyung}</p>
              </div>
            </div>

            <div className="d-flex text-center">
              <p className="m-0 col-4">방/창문 개수</p>
              <div className="col-8 d-flex  gap-5  justify-content-center">
                <div className="d-flex gap-3">
                  <p className="m-0">방 : </p>
                  <p className="m-0">{roomCounts}</p>
                </div>

                <div className="d-flex gap-3">
                  <p className="m-0">창문 : </p>
                  <p className="m-0">{windowCounts}</p>
                </div>
              </div>
            </div>
          </div>

          <div style={{ borderBottom: "solid 1px #006EEE" }} className="d-flex flex-column gap-4 p-3">
            <div className="d-flex text-center">
              <p className="m-0 col-4">발코니/베란다</p>
              <div className="col-8 d-flex  gap-3  justify-content-center">
                <p className="m-0">{isBalkoni}</p>
              </div>
            </div>

            <div className="d-flex text-center">
              <p className="m-0 col-4">복층 여부 </p>
              <div className="col-8 d-flex  gap-3  justify-content-center">
                <p className="m-0">{isBok}</p>
              </div>
            </div>

            <div className="d-flex text-center">
              <p className="m-0 col-4">추가 서비스 </p>
              <div className="col-8 d-flex  gap-3  justify-content-center">
                <p className="m-0">{Object.entries(option)
                  .filter(([key, value]) => value === true)
                  .map(([key]) => {
                    // 여기에서 각 key에 맞는 서비스 이름을 가져오는 함수 또는 매핑을 사용하세요.
                    const serviceName = getServiceNameByKey(key);
                    return serviceName;
                  })
                  .join(', ')}</p>
              </div>
            </div>


            <div className="d-flex text-center">
              <p className="m-0 col-4">가구사진</p>
              {selectedFiles.length !== 0 && <div className="col-8 d-flex  gap-3  justify-content-center shadow " style={{ overflowX: "auto" }}>
                {selectedFiles.map((file, index) => (
                  <div key={index} className="d-flex flex-column justify-content-center align-items-center">
                    <img
                      src={file.previewURL}
                      alt={`선택된 파일 ${index + 1} 미리보기`}
                      style={{ width: "7rem", height: "7rem" }}
                    />
                  </div>
                ))}
              </div>}
            </div>

            <div className="d-flex text-center ">
              <p className="m-0 col-4">추가사항</p>
              <div className="col-8 d-flex  gap-3  justify-content-center">
                {userinput}
              </div>
            </div>
          </div>
          <div className="d-flex align-items-center">
            <p className="m-0 col-4 text-center">예상 가격</p>
            <p className="fw-bold m-0 col-8 text-center " style={{ textDecoration: "underline" }} >{calResult.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}원</p>
          </div>
          <button onClick={hadlesubmit} className="mt-3 btn btn-primary">제출</button>
        </div>





      </motion.div>}
    </div>
    }

  </AnimatePresence >


  </>
  );

}
