import { useEffect } from "react"
import bookingDetailStore from "../../store/bookingDetailStore"
import style from './BookingDetails.module.css';



export default function BookingDetails({ memberId, nowId, role, price, reissuing, name }) {

    const { deliveryDetail, data } = bookingDetailStore()

    useEffect(() => {
        deliveryDetail(nowId, role)
    }, [deliveryDetail, nowId, role])

    // console.log(data.result);
    const estimate = data?.result || [];
    console.log(data)
    return (
        <div className="rounded-3 p-3" style={{ width: "80%", backgroundColor: "#EAEBEE"   ,overflow:"auto"}}>
            {role === 'delivery' ? (<>
                {/* <div className="d-flex flex-column">
                <div className="d-flex justify-content-between "><p className="m-0">이름</p><p className="m-0">{name}</p></div>
                <div className="d-flex justify-content-between "><p className="m-0">견적 가격</p><p className="m-0">{price}</p></div>
                <div className="d-flex justify-content-between "><p className="m-0">재견적 가격</p><p className="m-0">{reissuing}</p></div>
                <div className="d-flex justify-content-between "><p className="m-0">작업 일시</p><p className="m-0"> {estimate.startTime}</p></div>
                <div className="d-flex justify-content-between "><p className="m-0">예상 종료일시</p><p className="m-0">{estimate.endTime}</p></div>
                <div className="d-flex justify-content-between "><p className="m-0">요청 차량</p><p className="m-0">{estimate.carName}</p></div>
                <div className="d-flex justify-content-between "><p className="m-0">출발 지역</p><p className="m-0"> {estimate.departure} / {estimate.departureDetail}</p></div>
                <div className="d-flex justify-content-between "><p className="m-0">도착 지역</p><p className="m-0">{estimate.destination} / {estimate.destinationDetail}</p></div>
                <div className="d-flex justify-content-between "><p className="m-0">포장여부</p><p className="m-0">{estimate.packaging}</p></div>
                <div className="d-flex justify-content-between "><p className="m-0">탑승여부</p><p className="m-0">{estimate.move}</p></div>
                <div className="d-flex justify-content-between "><p className="m-0">주차여부</p><p className="m-0">{estimate.parking}</p></div>
                <div className="d-flex justify-content-between "><p className="m-0">짐 목록</p><p className="m-0">{estimate.movelist}</p></div>
                <div className="d-flex justify-content-between "><p className="m-0">탑승여부</p><p className="m-0">{estimate.move}</p></div>
                <div className="d-flex justify-content-between "><p className="m-0">재요청 가격</p><p className="m-0">{estimate.reissuing}</p></div>
                <div className="d-flex justify-content-between "><p className="m-0">사진</p><p className="m-0">{estimate.deliveryImages}</p></div>
            </div> */}
                {estimate.length !== 0 && <div className="rounded-3 p-3" style={{ width: "100%", backgroundColor: "#EAEBEE" }}><div className="d-flex flex-column p-3 rounded-4 " >
                    <div className="d-flex justify-content-between"><p className="">차량 </p><p className="">{estimate.carName}</p></div>
                    <div className="d-flex justify-content-between"><p className="">시작 시간</p><p className="">{estimate.startTime}</p></div>
                    <div className="d-flex justify-content-between"><p className="">끝나는 시간 </p><p className="">{estimate.endTime}</p></div>
                    <div className="d-flex justify-content-between"><p className="">출발지 주소</p><p className="">{estimate.departure}</p></div>
                    <div className="d-flex justify-content-between"><p className="">출발지 상세주소</p><p className="">{estimate.departureDetail}</p></div>
                    <div className="d-flex justify-content-between"><p className="">도착지 주소</p><p className="">{estimate.destination}</p></div>
                    <div className="d-flex justify-content-between"><p className="">도착지 상세주소</p><p className="">{estimate.destinationDetail}</p></div>
                    <div className="d-flex justify-content-between"><p className="">포장여부</p><p className="">{estimate.packaging === true ? "포장" : "미포장"}</p></div>
                    <div className="d-flex justify-content-between"><p className="">탑승 여부</p><p className="">{estimate.move === true ? "탑승" : "미탑승"}</p></div>
                    <div className="d-flex justify-content-between"><p className="">엘레베이터</p><p className="">{estimate.elevator === true ? "있음" : "없음"}</p></div>
                    <div className="d-flex justify-content-between"><p className="">주차장</p><p className="">{estimate.parking === true ? "있음" : "없음"}</p></div>
                    <div className="d-flex justify-content-between"><p className="">추가사항</p><p className="">{estimate.moveList}</p></div>

                    <div className="d-flex">
                        <p >첨부사진</p>
                        <div style={{ overflowX: 'auto', whiteSpace: 'nowrap' }}>
                            {estimate.deliveryImages.map((item, index) => (
                                <img key={index} style={{ width: "5rem", height: "5rem", marginRight: '5px' }} src={item} alt={`cleanImage-${index}`} />
                            ))}
                        </div>
                    </div>
                </div>
                    <div className="p-3 rounded-4 mt-3" style={{ border: "blue 1px solid" }}>
                        <div className="d-flex justify-content-between"><p className="m-0">가격</p><p className="m-0">{price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}원</p></div>
                    </div>
                </div>
                }</>
            ) : (
                <>
                    {/* 청소 관련 정보를 렌더링합니다. */}
                    <div>신청자 이름 : {name}</div>
                    <div>견적 가격 : {price}</div>
                    <div>재견적 가격 : {reissuing}</div>
                    <div>청소 진행일 : {estimate.reservationTime}</div>
                    <div>장소 : {estimate.address} / {estimate.addressDetail}</div>
                    <div>방 크기 : {estimate.roomSize}</div>
                    <div>방 갯수 : {estimate.roomCount}</div>
                    <div>창 갯수 : {estimate.windowCount}</div>

                    <div>발코니 유무 : {estimate.balconyExistence}</div>
                    <div>복층 유무 : {estimate.duplexRoom}</div>
                    <div>외부 창 청소 : {estimate.externalWindow}</div>
                    <div>새집 증후군 제거 : {estimate.houseSyndrome}</div>
                    <div>스티커 제거 : {estimate.removeSticker}</div>


                    {/* 여기에 필요한 나머지 clean 관련 렌더링을 추가합니다. */}
                </>
            )}
        </div>

        //     "move": false,
        //     "elevator": true,
        //     "parking": true,
        //     "movelist": "짐3짐2짐1",
        //     "deliveryImages": []


        /* { 청소 API
            "isSuccess": Boolean,
            "message": String,
            "code": INT,
            "result": {
                "id": Long,
                "reservationTime": LocalDateTime,
                "roomSize": Int,
                "roomCount": Int,
                "balconyExistence": Boolean,
                "windowCount": Int,
                "duplexRoom": Boolean,
                "mold": Boolean,
                "externalWindow": Boolean,
                "houseSyndrome": Boolean,
                "removeSticker": Boolean,
                "sigungu": Int,
                "address": String,
                "addressDetail": String,
                "cleanImages": [
                    String
                ]
            }
        } */
    )
}