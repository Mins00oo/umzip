import { useEffect, useRef, useState } from "react"
import StatusButton from "./Statusbutton"
import { AnimatePresence, motion } from "framer-motion";


export default function DropDown({ reissuing, setReviewId, setServiceId, memberId, setRequestId, Id, setOpenRecommendModal, setChoiceCompanyId, isAll, setRequestList, mappingId, setChatRoom, companyId, toggleModal, requestList, companyName, text, status, img, price }) {
    let newprice = 0
    if (reissuing === 0) { newprice = price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") }
    else { newprice = reissuing.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") }
    let renwerprice = 0
    if (reissuing === 0) { renwerprice = price }
    else { renwerprice = reissuing }

    const handleClick = (event) => {
        event.stopPropagation();
        setChoiceCompanyId(companyId)
        setOpenRecommendModal(true)
    }
    const newstatus = status % 100

    let realstatus = ""

    if (newstatus === 1) { realstatus = "신청중" }
    else if (newstatus === 2) { realstatus = "검토중" }
    else if (newstatus === 3) { realstatus = "예약완료" }
    else if (newstatus === 4) { realstatus = "거절" }
    else if (newstatus === 5) { realstatus = "취소" }
    else if (newstatus === 6) { realstatus = "예약확정" }
    const handlebill = () => {
        // console.log(1111)
        setServiceId({ "requestList": requestList, "Id": Id, "price": renwerprice })
    }
    return (<>
        <motion.div
            onClick={handlebill}
            whileHover={{ cursor: "pointer" }}
            className='rounded-3 p-2 d-flex justify-content-around text-center align-items-center position-relative '
                style={{
                    border: '2px solid',
                    borderImage: 'linear-gradient(to left, #005bea, #ACB6E5)',
                    borderImageSlice: 2, // Stretch the gradient across the entire border
                    minHeight: "8rem",
                    borderRadius: '3rem'
                }}
        >
            <motion.img
                whileHover={{ scale: 1.05, cursor: "pointer" }}
                className="shadow rounded-pill"
                onClick={handleClick}
                src={img}
                style={{ width: 70, height: 70 }}
            />
            <div className="col-2">
                <p className="m-0">업체명 : {companyName} </p>
                <p className="m-0">가격 : {newprice} </p>
            </div>
            <p className="m-0 col-2">{text}</p>
            <p className="m-0 col-2">{realstatus}</p>
            <div className="d-flex gap-1 flex-column align-items-center col-md-2">
                <StatusButton
                    setReviewId={setReviewId}
                    setServiceId={setServiceId}
                    memberId={memberId}
                    price={renwerprice}
                    setRequestId={setRequestId}
                    Id={Id}
                    isAll={isAll}
                    setRequestList={setRequestList}
                    mappingId={mappingId}
                    setChatRoom={setChatRoom}
                    companyId={companyId}
                    toggleModal={toggleModal}
                    requestList={requestList}
                    status={status}
                />
            </div>
        </motion.div>

    </>)
}