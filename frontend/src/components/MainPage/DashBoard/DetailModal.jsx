import { useEffect, useState } from "react"
import { api } from "../../../services/api"
import { motion } from "framer-motion"
export default function DetailModal({ setServiceId, serviceId }) {
    const [result, setResult] = useState(null)
    const [startTime, setStartTIme] = useState(null)
    const [endTime, setEndTIme] = useState(null)
    const [resTime, setResTIme] = useState(null)
    useEffect(() => { reservationDetail() }, [])
    function formatDateString(originalDateString) {
        const parsedDate = new Date(originalDateString);

        const formattedDate = `${parsedDate.getFullYear()}-${(parsedDate.getMonth() + 1).toString().padStart(2, '0')}-${parsedDate.getDate().toString().padStart(2, '0')} ${parsedDate.getHours().toString().padStart(2, '0')}:${parsedDate.getMinutes().toString().padStart(2, '0')}`;

        return formattedDate;
    }
    const reservationDetail = async () => {
        let service = ""
        console.log(serviceId)
        if (serviceId.requestList === "용달") { service = "delivery" }
        else { service = "clean" }
        console.log(service)
        try {
            const response = await api.get(
                `/${service}/user/reservation/${serviceId.Id}`,

            );
            console.log(response.data.result)
            setResult(response.data.result)
            if (service === "delivery") {
                setStartTIme(formatDateString(response.data.result.startTime))
                setEndTIme(formatDateString(response.data.result.endTime));
            }
            else {
                setResTIme(formatDateString(response.data.result.reservationTime))
            }
            console.log(response)
            return response.data.result
        } catch (error) {
            console.error(error);
        }
    }
    const handleClose = () => {
        setResult(null)
        setServiceId(null)
    }
    return <>
        {result && (
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
                }}>x
                <div style={{
                    position: 'relative',
                    width: '40%',
                    height: "70%",
                    overflow: "auto",
                    backgroundColor: 'white', // 내용의 배경색
                    padding: '3rem',
                    borderRadius: '8px', // 내용의 모서리 둥글게
                }}>
                    {serviceId.requestList === "용달" ? <div><div className="d-flex flex-column p-3 rounded-4 " style={{ backgroundColor: "#EAEBEE" }}>
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
                        <div className="d-flex justify-content-between"><p>추가사항</p><p>{result.movelist}</p></div>
                        <div>
                            <p >첨부사진</p>
                            <div style={{ overflowX: 'auto', whiteSpace: 'nowrap' }}>
                                {result.deliveryImages.map((item, index) => (
                                    <img key={index} style={{ width: "5rem", height: "5rem", marginRight: '5px' }} src={item} alt={`cleanImage-${index}`} />
                                ))}
                            </div>
                        </div>


                    </div>
                        <div className="p-3 rounded-4 mt-3" style={{ border: "blue 1px solid" }}>
                            <div className="d-flex justify-content-between"><p className="m-0">가격</p><p className="m-0">{serviceId.price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}원</p></div>
                        </div></div> :<> <div className="d-flex flex-column p-3 rounded-4 " style={{ backgroundColor: "#EAEBEE" }}>
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
                        </div>
                        <div className="p-3 rounded-4 mt-3" style={{ border: "blue 1px solid" }}>
                            <div className="d-flex justify-content-between"><p className="m-0">가격</p><p className="m-0">{serviceId.price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}원</p></div>
                        </div></> 

                    }




                </div>
            </motion.div>
        )}
    </>
}