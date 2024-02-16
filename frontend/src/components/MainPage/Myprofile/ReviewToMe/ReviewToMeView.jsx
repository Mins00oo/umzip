import { useState } from "react";

import { AnimatePresence, motion } from "framer-motion"
import ReviewToMeProfile from "./ReviewToMeProfile";
import { api } from "../../../../services/api";
// const DUMMY_DATA = [
//     { name: "p1", rating: 3.5,review:"이" },
//     { name: "p2", rating: 4.8,review:"놈" },
//     { name: "p3", rating: 5,review:"의" },
//     { name: "p4", rating: 4.5,review:"미" },
//     { name: "p5", rating: 1,review:"친" },
//     { name: "p6", rating: 1.3,review:"개" },
//     { name: "p8", rating: 2.5,review:"최대글 길이 확인용 확인용 확인용" },
//     { name: "p7", rating: 3,review:"최대글 길이 확인용 확인용 확인용" },
// ]
const ITEMS_PER_PAGE = 3;
const MAX_DISPLAY_PAGES = 5
export default function ReviewToMeView({ renew, id, setReviewToMeList, reviewToMeList, reviewToMeTotalPages }) {
    const [currentPage, setCurrentPage] = useState(1);

    const axios_ReviewToMe = async (pageNumber) => {
        console.log(pageNumber-1)
        const num = pageNumber-1
        try { 
            const response = await api.post(
                `/reviews/myReceive`,
                {
                    memberId: id,
                    role: "USER",
                    offset: num,
                    limit: 3,
                    // point: myprofile.point,
                }
            );
            // console.log(response)
            // setHelpMeList(response.data.result.content)
            // setHelpMeTotalPages(response.data.result.totalElements)
            setReviewToMeList(response.data.reviews)
            return response
        }
        catch (e) {

        }
    }
    const paginate = (pageNumber) => {
        setCurrentPage(pageNumber)
        axios_ReviewToMe(pageNumber)
    };
    const startPage = Math.max(1, currentPage - Math.floor(MAX_DISPLAY_PAGES / 2));
    const endPage = Math.min(
        startPage + MAX_DISPLAY_PAGES - 1,
        Math.ceil(reviewToMeTotalPages / ITEMS_PER_PAGE)
    );
    const pageButtons = Array.from({ length: endPage - startPage + 1 }, (_, index) => startPage + index);
    return <>{reviewToMeList &&
        <div className="d-flex col-12 flex-column p-3 justify-content-between gap-3" style={{ height: "100%" }}>
            <div className="d-flex flex-column   " >
                <div className="mb-3 d-flex gap-2 align-items-center" style={{ borderBottom: "1px solid " }}><img style={{ width: "3rem", height: "3rem" }} src="/free-animated-icon-valentines-day-14118612.gif" /><h3 className="m-0">받은 후기</h3></div>
                <AnimatePresence mode="wait">
                    <motion.div initial={{ opacity: 0, x: 50 }}
                        animate={{ opacity: 1, x: 0 }}
                        exit={{ opacity: 0, x: 50 }} className="d-flex flex-column gap-4" >
                        {reviewToMeList.map((item,index) => (
                            <ReviewToMeProfile memberId={item.memberId} key={index} renew={renew} createDt={item.createDt} img={item.memberImageUrl} id={item.id} tag={item.tag} tagType={item.tagType}  name={item.memberName} rating={item.score} review={item.content} />
                        ))}
                        {reviewToMeList.length === 0 && <div className="d-flex gap-3 justify-content-center align-items-center mt-5"><p className="m-0">아직 리뷰글이 없습니다!</p><img style={{ width: "3rem", height: "3rem" }} src="/free-animated-icon-note-6172546.gif" /></div>}
                    </motion.div>
                </AnimatePresence>
            </div>
            <div className="pagination d-flex justify-content-center gap-3">
                <div className="pagination d-flex justify-content-center gap-3">
                    {pageButtons.map((pageNumber) => (
                        <motion.button
                            whileHover={{ y: -5 }}
                            className={`btn btn-light${pageNumber === currentPage ? ' active' : ''}`}
                            key={pageNumber}
                            onClick={() => paginate(pageNumber)}
                        >
                            {pageNumber}
                        </motion.button>
                    ))}
                </div>
            </div>
        </div>
    }</>
}