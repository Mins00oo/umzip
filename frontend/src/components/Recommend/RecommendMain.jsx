import React, { useState } from "react";
import RecommendPeople from "./RecommendPeople";
import { AnimatePresence, motion } from "framer-motion"
import { useLocation, useNavigate } from "react-router-dom";
import "./RecommendMain.css"
import axios from "axios";
import { api } from "../../services/api";
import useAuthStore from "../../store/store";


export default function RecommendMain() {
    const navigate = useNavigate()
    const itemsPerPage = 5;
    const [visibleItems, setVisibleItems] = useState(itemsPerPage);
    const location = useLocation();
    const status = location.state.type
    // location.state에서 데이터를 추출합니다.

    const data = location.state.axios_data.result
    console.log(location.state)
    // console.log(location.state)
    // console.log(location.state.userInput)
    const userInput = location.state.userInput
    const [userChoice, setUserChoice] = useState([])
    const [isLoading, setIsLoading] = useState(false)
    const handleLoadMore = () => {
        setVisibleItems((prev) => prev + itemsPerPage);
    };
    const handlesubmit = () => {
        // console.log(status);

        if (userChoice.length === 0) { alert("최소 한 기사님은 선택해주세요") }
        else {
            if (status === "용달") {
                axios_DEL_reservation(userChoice);
            } else {
                axios_CLE_reservation(userChoice);
            }
            const { token } = useAuthStore.getState();
            // console.log(token)
            // 로딩 상태 변경
            setIsLoading(true);

            // 3초 후에 isLoading을 다시 false로 변경
            setTimeout(() => {
                setIsLoading(false);
                navigate("/dashboard");
            }, 3000);
            // 필요에 따라 이동 처리
        }

    };

    const axios_DEL_reservation = async (memberId) => {

        const delivery = {
            "carId": userInput.delivery.carId, // 실제 carId 값으로 교체 (긴 정수)
            "startTime": userInput.delivery.startTime, // 실제 시작 시간을 올바른 날짜 및 시간 형식으로 교체
            "endTime": userInput.delivery.endTime, // 실제 종료 시간을 올바른 날짜 및 시간 형식으로 교체
            "departure": userInput.delivery.departure, // 실제 출발지 값으로 교체 (문자열)
            "departureDetail": userInput.delivery.departureDetail, // 실제 출발지 상세정보 값으로 교체 (문자열)
            "destination": userInput.delivery.destination, // 실제 도착지 값으로 교체 (문자열)
            "destinationDetail": userInput.delivery.destinationDetail, // 실제 도착지 상세정보 값으로 교체 (문자열)
            "packaging": userInput.delivery.packaging, // 실제 값으로 교체 (부울)
            "move": userInput.delivery.move, // 실제 값으로 교체 (부울)
            "elevator": userInput.delivery.elevator, // 실제 값으로 교체 (부울)
            "parking": userInput.delivery.parking, // 실제 값으로 교체 (부울)
            "movelist": userInput.delivery.moveList, // 실제 이사 목록 값으로 교체 (문자열)
            "sigungu": userInput.delivery.sigungu // 실제 sigungu 값으로 교체 (긴 정수)
        }
        console.log(delivery)
        const price = userInput.price
        console.log(memberId)
        const companys = memberId
        // console.log(companys)
        console.log(userInput.imageFileList)
        const selectedFiles = userInput.imageFileList[0]
        const formData = new FormData()
        formData.append("delivery", new Blob([JSON.stringify(delivery)], { type: "application/json" }));
        formData.append("price", new Blob([JSON.stringify(price)], { type: "application/json" }));
        formData.append("companys", new Blob([JSON.stringify(companys)], { type: "application/json" }));

        // 이미지 파일들은 별도로 추가
        selectedFiles.forEach((fileObj) => {
            // 파일 객체를 개별적으로 추가
            formData.append('imageFileList', fileObj.file);
        });

        console.log(formData)
        try {
            const response = await api.post(
                '/delivery/user/reservation',
                formData,
            );

            console.log(response)
            return response
        } catch (error) {
            console.error(error);
            return error
        }
    }
    const axios_CLE_reservation = async (memberId) => {

        const clean = {
            "reservationTime": userInput.clean.reservationTime, // 실제 carId 값으로 교체 (긴 정수)
            "address": userInput.clean.address, // 실제 시작 시간을 올바른 날짜 및 시간 형식으로 교체
            "addressDetail": userInput.clean.addressDetail, // 실제 종료 시간을 올바른 날짜 및 시간 형식으로 교체
            "sigungu": userInput.clean.sigungu, // 실제 출발지 값으로 교체 (문자열)
            "roomSize": userInput.clean.roomSize,
            "roomCount": userInput.clean.roomCount,
            "windowCount": userInput.clean.windowCount,
            "balconyExistence": userInput.clean.balconyExistence,
            "duplex": userInput.clean.duplex,
            "mold": userInput.clean.mold,
            "externalWindow": userInput.clean.externalWindow,
            "houseSyndrome": userInput.clean.houseSyndrome,
            "removeSticker": userInput.clean.removeSticker
        }
        const price = userInput.price
        const companys = memberId
        console.log(companys)
        const selectedFiles = userInput.imageFileList[0]
        const formData = new FormData()
        formData.append("clean", new Blob([JSON.stringify(clean)], { type: "application/json" }));
        formData.append("price", new Blob([JSON.stringify(price)], { type: "application/json" }));
        formData.append("companys", new Blob([JSON.stringify(companys)], { type: "application/json" }));

        // 이미지 파일들은 별도로 추가
        selectedFiles.forEach((fileObj) => {
            // 파일 객체를 개별적으로 추가
            formData.append('imageFileList', fileObj.file);
          });
      
        try {
            const response = await api.post(
                '/clean/user/reservation',
                formData,
            );


            console.log(response)
            return response
        } catch (error) {
            console.error(error);
            return error
        }
    }   


    const containerVariants = {
        visible: {
            transition: {
                staggerChildren: 0.1, // 자식 요소들에 대한 stagger 효과
            },
        },
    };
    return (<>
        <AnimatePresence>
            {(isLoading&&
           <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }}
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
                    <div className="d-flex align-items-center  justify-content-center gap-5" style={{
                        position: 'relative',
                        width: '50%',
                        height: "60%",
                        backgroundColor: 'white', // 내용의 배경색
                        padding: '20px',
                        borderRadius: '8px', // 내용의 모서리 둥글게
                    }}>
                        {status === "용달" && <div className="d-flex justify-content-center align-items-center gap-5 bg-white " >
                            <img style={{ width: "20rem", height: "20rem" }} src="./free-animated-icon-delivery-truck-11933537.gif" />
                            <h3 style={{ color: "black" }}>배달 준비중...</h3>
                            {/* <Wave /> */}
                        </div>}
                        {status === "청소" && <div className="d-flex justify-content-center align-items-center gap-5 bg-white " >
                            <img style={{ width: "20rem", height: "20rem" }} src="./free-animated-icon-cleaning-13275288.gif" />
                            <h3 style={{ color: "black" }}>청소 준비중...</h3>
                            {/* <Wave /> */}
                        </div>}
                    </div>
                </motion.div>
            )}
        </AnimatePresence>
        <div className="d-flex justify-content-center p-5 flex-column align-items-center gap-5 removescroll" >
            <div className="d-flex justify-content-center flex-column align-items-center col-md-8 gap-3" style={{ marginTop: "5rem" }}>
                <h3>이런 기사님은 어떠세요?</h3>
                <small className="form-text text-muted">
                    견적을 전달할 기사님들을 선택후 "견적 전달"을 눌러주세요.
                </small>
                <small className="form-text text-muted">
                    기사님의 프로필을 클릭하면 상세정보로 이동합니다
                </small>
                <motion.div
                    initial="hidden"
                    animate="visible"
                    variants={containerVariants}
                    style={{ width: "100%" }}
                    className="d-flex flex-column gap-3"
                >
                    {data && data.slice(0, visibleItems).map((item, index) => (
                        <motion.div key={index} variants={{ visible: { opacity: 1, y: 0 } }} initial={{ opacity: 0, y: 20 }}>
                            {status === "용달 " ? <RecommendPeople experience={item.experience} memberId={item.memberId} status={status} userChoice={userChoice} setUserChoice={setUserChoice} companyId={item.companyId} tag={item.topTagList} name={item.companyName} rating={item.score} img={item.imageUrl} /> :
                                <RecommendPeople experience={item.experience} memberId={item.memberId} status={status} userChoice={userChoice} setUserChoice={setUserChoice} companyId={item.companyId} tag={item.topTagList} name={item.companyName} rating={item.score} img={item.imageUrl} />}
                        </motion.div>
                    ))}
                </motion.div>
                {visibleItems < data.length && (
                    <motion.button
                        type="button"
                        className="btn btn-light d-flex justify-content-center align-items-center gap-2 shadow"
                        onClick={handleLoadMore}
                        whileHover={{ y: -5 }} // Adjust the value based on your preference
                    >
                        <div className="d-flex align-items-center  justify-content-center rounded-circle bg-black text-white" style={{ width: "1rem", height: "1rem" }}>
                            +
                        </div>
                        <p className="m-0">더보기</p>
                    </motion.button>
                )}
                <button onClick={handlesubmit} className="btn btn-primary">견적 전달</button>
            </div>
        </div>
    </>
    );
}
