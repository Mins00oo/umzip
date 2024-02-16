import { useState } from "react";

import { AnimatePresence, motion } from "framer-motion"
// import ReviewToMeProfile from "./ReviewToPeopleProfile";
import ReviewToPeopleProfile from "./ReviewToPeopleProfile";
import { api } from "../../../../services/api";

const ITEMS_PER_PAGE = 3;
const MAX_DISPLAY_PAGES = 5

export default function ReviewToPeopleView({ reviewToPeopleTotalPages, reviewToPeopleList, setReviewToPeopleList, id }) {
    const axios_ReviewToPeople = async (pageNumber) => {
        // console.log(pageNumber)
        try {
            const response = await api.post(
                `/reviews/myWrite`,
                {
                    memberId: id,
                    role: "USER",
                    offset: pageNumber-1,
                    limit: 3,
                }
            );
            // console.log(response)
            setReviewToPeopleList(response.data.reviews)
            // setReviewToPeopleToTalPages(response.data.board_cnt)
            // setReviewToMeList()
            return response
        }
        catch (e) {

        }
    }
    const [currentPage, setCurrentPage] = useState(1);

    // const currentItems = sellList.slice(indexOfFirstItem, indexOfLastItem);
    const paginate = (pageNumber) => {
        setCurrentPage(pageNumber)
        axios_ReviewToPeople(pageNumber)
    };
    const startPage = Math.max(1, currentPage - Math.floor(MAX_DISPLAY_PAGES / 2));
    const endPage = Math.min(
        startPage + MAX_DISPLAY_PAGES - 1,
        Math.ceil(reviewToPeopleTotalPages / ITEMS_PER_PAGE)
    );
    const pageButtons = Array.from({ length: endPage - startPage + 1 }, (_, index) => startPage + index);
    return <>
        {reviewToPeopleList &&
            <div className="d-flex col-12 flex-column p-3 justify-content-between gap-3" style={{ height: "100%" }}>
                <div className="d-flex flex-column   " >
                    <div className="mb-3 d-flex gap-2 align-items-center" style={{ borderBottom: "1px solid " }}><img style={{ width: "3rem", height: "3rem" }} src="/free-animated-icon-gift-13399189.gif" /><h3 className="m-0">보낸 후기</h3></div>
                    <AnimatePresence mode="wait">
                        <motion.div initial={{ opacity: 0, x: 50 }}
                            animate={{ opacity: 1, x: 0 }}
                            exit={{ opacity: 0, x: 50 }} className="d-flex flex-column gap-4" >
                            {reviewToPeopleList.map((item, index) => (
                                <ReviewToPeopleProfile createDt={item.createDt} img={item.memberImageUrl} id={item.id} tagType={item.tagType} tag={item.tag} key={index} name={item.memberName} rating={item.score} review={item.content} />
                            ))}
                            {reviewToPeopleList.length === 0 && <div className="d-flex gap-3 justify-content-center align-items-center mt-5"><p className="m-0">아직 리뷰글이 없습니다!</p><img style={{ width: "3rem", height: "3rem" }} src="/free-animated-icon-note-6172546.gif" /></div>}
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
        }
    </>
}