import { useState } from "react";
import { AnimatePresence, motion } from "framer-motion"
import BuyProfile from "./BuyProfile";
import { api } from "../../../../services/api";
// const DUMMY_DATA = [
//     { title: "p1", price: 1, status: "판매중" },
//     { title: "p2", price: 2, status: "판매중" },
//     { title: "p3", price: 3, status: "판매완료" },
//     { title: "p4", price: 4, status: "판매중" },
//     { title: "p5", price: 5, status: "판매중" },
//     { title: "p6", price: 6, status: "판매완료" },
//     { title: "p8", price: 7, status: "판매완료" },
//     { title: "p7", price: 8, status: "판매중" },
// ]
const ITEMS_PER_PAGE = 5;
const MAX_DISPLAY_PAGES = 5
export default function BuyView({ id, setBuyList, buyTotalPages, buyList }) {
    // console.log(buyTotalPages)
    const axios_BuyList = async (pageNumber) => {
        const number = pageNumber

        try {
            const response = await api.get(
                `/trade-items/profiles/buy?memberId=${id}&page=${number}&size=5`,

            );
            console.log(response)
            setBuyList(response.data.result.content)
            // console.log(response.data.result.totalElements)
            // buyTotalPages = response.data.result.totalElements
            return response
        }
        catch (e) {

        }
    }
    const [currentPage, setCurrentPage] = useState(1);

    const indexOfLastItem = currentPage * ITEMS_PER_PAGE;
    const indexOfFirstItem = indexOfLastItem - ITEMS_PER_PAGE;
    // const currentItems = DUMMY_DATA.slice(indexOfFirstItem, indexOfLastItem);
    const paginate = async (pageNumber) => {
        setCurrentPage(pageNumber)
        // console.log(pageNumber)
        axios_BuyList(pageNumber)
    };
    const startPage = Math.max(1, currentPage - Math.floor(MAX_DISPLAY_PAGES / 2));
    const endPage = Math.min(
        startPage + MAX_DISPLAY_PAGES - 1,
        Math.ceil(buyTotalPages / ITEMS_PER_PAGE)
    );
    const pageButtons = Array.from({ length: endPage - startPage + 1 }, (_, index) => startPage + index);
    return <>
        <div className="d-flex col-12 flex-column p-3 justify-content-between gap-3" style={{ height: "100%" }}>
            <div className="d-flex flex-column   " >

                <div className="mb-3 d-flex gap-2 align-items-center" style={{ borderBottom: "1px solid " }}><img style={{ width: "3rem", height: "3rem" }} src="/free-animated-icon-buy-8797955.gif" /><h3 className="m-0">구매 물품</h3></div>
                <AnimatePresence mode="wait">
                    <motion.div initial={{ opacity: 0, x: 50 }}
                        animate={{ opacity: 1, x: 0 }}
                        exit={{ opacity: 0, x: 50 }} className="d-flex flex-column gap-4" >
                        {buyList.map((item, index) => (
                            <BuyProfile id={item.boardId} key={index} title={item.title} price={item.price} status={item.codeSmallName} img={item.thumbnailPath} />
                        ))}
                        {buyList.length === 0 && <div className="d-flex gap-3 justify-content-center align-items-center mt-5"><p className="m-0">아직 구매글이 없습니다!</p><img style={{ width: "3rem", height: "3rem" }} src="/free-animated-icon-note-6172546.gif" /></div>}
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