
import style from './HelpPagination.module.css'; // CSS 모듈 임포트
import { useEffect } from "react";
import useStore from '../../store/helpData';
import { motion } from "framer-motion";

function HelpPagination() {
    const { data, page, setPage, codeSmall } = useStore();
    const totalElements = data?.result?.totalElements || 1;
    const totalPages = Math.ceil(totalElements / 10);
    const correctedPage = page;
    const startPage = Math.max(correctedPage - 5, 1);
    const endPage = Math.min(correctedPage + 5, totalPages);


    return (
        <>
            <motion.div initial={{opacity:0, y:10}} animate={{opacity:1, y:0}} exit={{opacity:0, y:10}} className='d-flex justify-content-center' style={{ width: "100%" }}>
                <div className="d-flex gap-3">
                    {Array.from({ length: endPage - startPage + 1 }, (_, i) => startPage + i).map(pageNumber => (
                        <motion.button
                            whileHover={{y:-5}}
                            // 현재 페이지인 경우 특별한 스타일 적용
                            className={`btn ${pageNumber === correctedPage ? style.currentPage : 'btn btn-light'}`}
                            key={pageNumber}
                            onClick={() => setPage(pageNumber)}>
                            {pageNumber}
                        </motion.button>
                    ))}
                </div>
            </motion.div>
        </>
    );
}

export default HelpPagination;