
import { useState } from "react";
import UsedProfile from "../SellProduct/UsedProfile";
import { AnimatePresence, motion } from "framer-motion"
import HelpMeToPeopleProfile from "./HelpMeToPeopleProfile";
import { api } from "../../../../services/api";

const ITEMS_PER_PAGE = 5;
const MAX_DISPLAY_PAGES=5
export default function HelpMeToPeopleView({id, setHelpYouList, helpYouList, helpYouTotalPages }) {
    const [currentPage, setCurrentPage] = useState(1);

    const indexOfLastItem = currentPage * ITEMS_PER_PAGE;
    const indexOfFirstItem = indexOfLastItem - ITEMS_PER_PAGE;
    const paginate = (pageNumber) => {
        setCurrentPage(pageNumber)
        axios_HelpYou(pageNumber)
    };
    const startPage = Math.max(1, currentPage - Math.floor(MAX_DISPLAY_PAGES / 2));
    const endPage = Math.min(
        startPage + MAX_DISPLAY_PAGES - 1,
        Math.ceil(helpYouTotalPages / ITEMS_PER_PAGE)
    );
    const pageButtons = Array.from({ length: endPage - startPage + 1 }, (_, index) => startPage + index);
    const axios_HelpYou = async (pageNumber) => {

        try {
            const response = await api.get(
                `/helps/profiles/help-you?memberId=${id}&page=${pageNumber}&size=5`,

            );
            // console.log(response.data.result)
            setHelpYouList(response.data.result.content)
            // setHelpYouTotalPages(response.data.result.totalElements)
            return response
        }
        catch (e) {

        }
    }

    return <>
        <div className="d-flex col-12 flex-column p-3 justify-content-between gap-3" style={{ height: "100%" }}>
            <div className="d-flex flex-column   " >
            <div className="mb-3 d-flex gap-2 align-items-center" style={{ borderBottom: "1px solid " }}><img style={{width:"3rem" ,height:"3rem"}} src="/free-animated-icon-kindness-9534941.gif"/><h3 className="m-0">도움 내역</h3></div>
                <AnimatePresence mode="wait">
                    <motion.div initial={{ opacity: 0, x: 50 }}
                        animate={{ opacity: 1, x: 0 }}
                        exit={{ opacity: 0, x: 50 }}  className="d-flex flex-column gap-4" >
                        {helpYouList.map((item, index) => (
                            <HelpMeToPeopleProfile createDt={item.createDt} writerName={item.writerName} codeSmallName={item.codeSmallName} id={item.boardId} key={index} title={item.title} point={item.point} />
                        ))}
                        {helpYouList.length===0 && <div className="d-flex gap-3 justify-content-center align-items-center mt-5"><p className="m-0">아직 도움글이 없습니다!</p><img style={{width:"3rem",height:"3rem"}} src="/free-animated-icon-note-6172546.gif"/></div>}
                        
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
    </>
}