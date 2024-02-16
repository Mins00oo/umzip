import { AnimatePresence, motion } from "framer-motion"
import { useEffect, useState } from "react"
import { api } from "../../../services/api";
// import { renewer } from "./Statusbutton";
export default function NewModal({ renewer, id, setRequestId, requestId }) {
    const [result, setResult] = useState(null)
    const [point, setPoint] = useState(0)
    const [myProfile, setMyprofile] = useState(null)
    const [startTime, setStartTIme] = useState(null)
    const [endTime, setEndTIme] = useState(null)
    const [resTime, setResTIme] = useState(null)
    const [userInfo, setUserInfo] = useState({ name: '', profileImage: '' });
    const [isLoading, setIsLoading] = useState(false)
    // const [Id, setId] = useState(null)
    useEffect(() => {
        const fetchData = async () => {
            const storedUserInfo = localStorage.getItem("userInfo") || sessionStorage.getItem("userInfo");
            if (storedUserInfo) {
                try {
                    const parsedInfo = JSON.parse(storedUserInfo);
                    console.log(parsedInfo.id);

                    if (parsedInfo && parsedInfo.id) {
                        setUserInfo({ name: parsedInfo.name, profileImage: parsedInfo.profileImage, id: parsedInfo.id });
                        await axios_myprofile(parsedInfo.id); // id가 사용 가능할 때만 호출
                    }
                } catch (error) {
                    console.error('Error parsing user info:', error);
                }
            }

            const res = await reservationDetail();
        };

        fetchData();
    }, []);


    const axios_myprofile = async (id) => {
        console.log(id)
        try {
            const response = await api.get(
                `/users/${id}`,
            );
            console.log(response.data.result)
            setMyprofile(response.data.result)

            return response
        }
        catch (e) {

        }
    }

    const reservationDetail = async () => {
        let service = ""
        console.log(requestId)
        if (requestId.requestList === "용달") { service = "delivery" }
        else { service = "clean" }
        
        try {
            const response = await api.get(
                `/${service}/user/reservation/${requestId.Id}`,

            );
            console.log(response.data.result)
            setResult(response.data.result)
            if (service === "delivery") {
                setStartTIme(new Date(response.data.result.startTime).toISOString().split('T')[0])
                setEndTIme(new Date(response.data.result.endTime).toISOString().split('T')[0]);
            }
            else {
                setResTIme(new Date(response.data.result.reservationTime).toISOString().split('T')[0])
            }
            console.log(response)
            return response.data.result
        } catch (error) {
            console.error(error);
        }
    }
    const MakeConfirm = async () => {
        let service = "";
        if (requestId.requestList === "용달") {
            service = "delivery";
        } else {
            service = "clean";
        }
    
        // Display a confirmation dialog
        const userConfirmed = window.confirm("확정하시겠습니까?");
        console.log(requestId)
        // Check user's response
        if (userConfirmed) {
            setIsLoading(true);
            console.log()
            try {
                const response = await api.post(
                    `/${service}/user/reservation-complete`,
                    {
                        "mappingId": requestId.mappingId,
                        "point": point,
                    }
                );
    
                console.log(response);
                setTimeout(() => {
                    setIsLoading(false);
                    setMyprofile(null);
                setRequestId(null);
                renewer();  
                }, 5000);
                
                // You can add further actions after a successful API call if needed
    
                // setTimeout 내부에서 호출되도록 이동
                
            } catch (error) {
                console.error(error);
                // Handle error if the API call fails
                setIsLoading(false); // 에러 발생 시에도 isLoading을 false로 설정
            }
        } else {
            // Handle the case where the user did not confirm
            console.log("User did not confirm. Operation aborted.");
        }
    };
    
    const handleClose = (e) => {
        // 모달 외부의 영역을 클릭한 경우에만 닫기
        if (e.target === e.currentTarget) {
            setRequestId(null);
            setMyprofile(null)
        }
    }
    const handleInput = (e) => {
        const inputValue = e.target.value;
        const maxValue = Math.min(myProfile.point, requestId.price / 10);

        if (inputValue > maxValue) {
            // 최대값을 초과하는 경우 혹은 다른 유효성 검사를 수행할 수 있습니다.
            // 여기서는 입력값을 최대값으로 설정합니다.
            setPoint(maxValue);
        } else {
            // 유효한 경우에는 입력값을 설정합니다.
            setPoint(inputValue);
        }
        if (inputValue < 0) { setPoint(0) }
    };
    return <>
    <AnimatePresence>
        {!isLoading && myProfile && result && requestId && (
            <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} onClick={handleClose}
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
                    height: "70%",
                    overflow: "auto",
                    backgroundColor: 'white', // 내용의 배경색
                    padding: '3rem',
                    borderRadius: '8px', // 내용의 모서리 둥글게
                }}>
                    {requestId.requestList === "용달" ? <div>
                        <div className="d-flex justify-content-between"><p>차량 </p><p>{result.carName}</p></div>
                        <div className="d-flex justify-content-between"><p>시작 시간</p><p>{startTime}</p></div>
                        <div className="d-flex justify-content-between"><p>끝나는 시간 </p><p>{endTime}</p></div>
                        <div className="d-flex justify-content-between"><p>출발지 주소</p><p>{result.departure}</p></div>
                        <div className="d-flex justify-content-between"><p>출발지 상세주소</p><p>{result.departureDetail}</p></div>
                        <div className="d-flex justify-content-between"><p>도착지 주소</p><p>{result.destination}</p></div>
                        <div className="d-flex justify-content-between"><p>도착지 상세주소</p><p>{result.destinationDetail}</p></div>
                        <div className="d-flex justify-content-between"><p>포장여부</p><p>{result.packaging === true ? "포장" : "미포장"}</p></div>
                        <div className="d-flex justify-content-between"><p>탑승 여부</p><p>{result.move === true ? "탑승" : "미탑승"}</p></div>
                        <div className="d-flex justify-content-between"><p>엘레베이터</p><p>{result.elevator === true ? "있음" : "없음"}</p></div>
                        <div className="d-flex justify-content-between"><p>주차장</p><p>{result.parking === true ? "있음" : "없음"}</p></div>
                        <p>추가사항 : {result.moveList}  </p>
                        <div>
                            <p >첨부사진</p>
                            <div style={{ overflowX: 'auto', whiteSpace: 'nowrap' }}>
                                {result.deliveryImages.map((item, index) => (
                                    <img key={index} style={{ width: "5rem", height: "5rem", marginRight: '5px' }} src={item} alt={`cleanImage-${index}`} />
                                ))}
                            </div>
                        </div>
                        <div className="p-3 rounded-4" style={{ border: "blue 1px solid" }}>
                            <div className="d-flex justify-content-between"><p className="m-0">가격</p><p className="m-0">{requestId.price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}</p></div>
                            <div className="d-flex justify-content-between"><p className="m-0">보유포인트</p><p className="m-0">{myProfile.point.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}</p></div>
                            <div style={{ borderBottom: '1px solid blue' }} className="d-flex justify-content-between"><p style={{ fontSize: "0.75rem", }}>사용포인트</p><div><input value={point} onChange={handleInput} className="text-end  m-0" type="number"></input>P</div></div>
                            <div className="d-flex justify-content-between"><p className="m-0">최종가격</p><p className="m-0">{(requestId.price - point).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}</p></div>
                            <div className="d-flex"><button onClick={MakeConfirm} className="btn btn-primary p-1" style={{ marginLeft: "auto", height: "2rem" }} ><p className="m-0" style={{ fontSize: "0.75rem" }}>예약확정</p></button></div>
                        </div>
                    </div> : <div className="d-flex flex-column p-3">
                        <div className="d-flex justify-content-between"><p>예약시간 </p><p>{resTime}</p></div>
                        <div className="d-flex justify-content-between"><p>집크기 </p><p>{result.roomSize}</p></div>
                        <div className="d-flex justify-content-between"><p>방개수 </p><p>{result.roomCount}</p></div>
                        <div className="d-flex justify-content-between"><p>창문개수 </p><p>{result.windowCount}</p></div>
                        <div className="d-flex justify-content-between"><p>발코니/베란다 </p><p>{result.balconyExistence === true ? "있음" : "없음"}</p></div>
                        <div className="d-flex justify-content-between"><p>복층여부 </p><p>{result.duplexRoom === true ? "있음" : "없음"}</p></div>
                        <div className="d-flex justify-content-between"><p>곰팡이 청소 </p><p>{result.mold === true ? "있음" : "없음"}</p></div>
                        <div className="d-flex justify-content-between"><p>외부 유리창 청소 </p><p>{result.externalWindow === true ? "있음" : "없음"}</p></div>
                        <div className="d-flex justify-content-between"><p>세집 증후군 제거 </p><p>{result.houseSyndrome === true ? "있음" : "없음"}</p></div>
                        <div className="d-flex justify-content-between"><p>스티커&스트지 제거 </p><p>{result.removeSticker === true ? "있음" : "없음"}</p></div>
                        <div className="d-flex justify-content-between"><p>주소 </p><p>{result.address}</p></div>
                        <div className="d-flex justify-content-between"><p>상세주소 </p><p>{result.addressDetail}</p></div>
                        <div>
                            <p className="m-0">첨부사진</p>
                            <div style={{ overflowX: 'auto', whiteSpace: 'nowrap', marginBottom: "1rem" }}>
                                {result.cleanImages.map((item, index) => (
                                    <img key={index} style={{ width: "5rem", height: "5rem", marginRight: '5px' }} src={item} alt={`cleanImage-${index}`} />
                                ))}
                            </div>
                        </div>
                        <div className="p-3 rounded-4" style={{ border: "blue 1px solid" }}>
                            <div className="d-flex justify-content-between"><p className="m-0">가격</p><p className="m-0">{requestId.price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}</p></div>
                            <div className="d-flex justify-content-between"><p className="m-0">보유포인트</p><p className="m-0">{myProfile.point.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}</p></div>
                            <div style={{ borderBottom: '1px solid blue' }} className="d-flex justify-content-between"><p style={{ fontSize: "0.75rem", }}>사용포인트</p><div><input value={point} onChange={handleInput} className="text-end  m-0" type="number"></input>P</div></div>
                            <div className="d-flex justify-content-between"><p className="m-0">최종가격</p><p className="m-0">{(requestId.price - point).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}</p></div>
                            <div className="d-flex"><button onClick={MakeConfirm} className="btn btn-primary p-1" style={{ marginLeft: "auto", height: "2rem" }} ><p className="m-0" style={{ fontSize: "0.75rem" }}>예약확정</p></button></div>
                        </div>

                    </div>}




                </div>
            </motion.div>
        )}
        </AnimatePresence>
        {isLoading && <AnimatePresence>
            {(
                isLoading && <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }}
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
                        width: '40%',
                        height: "40%",
                        backgroundColor: 'white', // 내용의 배경색
                        padding: '20px',
                        borderRadius: '8px', // 내용의 모서리 둥글게
                    }}>
                        <div className="d-flex justify-content-center align-items-center gap-5 bg-white " >
                            <img style={{ width: "20rem", height: "20rem" }} src="/free-animated-icon-verified-7920939.gif" />
                            <h3 style={{ color: "black" }}>예약 진행중...</h3>
                            {/* <Wave /> */}
                        </div>
         
                    </div>
                </motion.div>
            )}
        </AnimatePresence>}
    </>

}