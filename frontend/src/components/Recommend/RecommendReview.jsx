import { motion, AnimatePresence } from "framer-motion"
import StarRating from "./StarRating"
export default function RecommendReview({ tag, img, name, date, rating, text }) {
    return <>
        <motion.div
            style={{ backgroundColor: "#F4F9FF", width: "100%" }}
            className="rounded-4 d-flex  p-3 shadow postion-relative"
            initial={{ opacity: 0 }} // 초기 상태
            animate={{ opacity: 1 }} // 애니메이션 상태
            transition={{ duration: 0.5 }} // 트랜지션 지속 시간
        >
            <div style={{marginRight:"1rem"}} className="d-flex flex-column justify-content-around  align-items-center gap-2 col-2">
                <motion.img
                    className="rounded-pill shadow"
                    src={img}
                    style={{ height: "4rem", width: "4rem" }}
                    alt="Random Image"
                    initial={{ opacity: 0 }} // 초기 상태
                    animate={{ opacity: 1 }} // 애니메이션 상태
                    transition={{ duration: 0.5 }} // 트랜지션 지속 시간
                />
                <div className="d-flex align-items center gap-2 justify-content-center" >
                    <StarRating rating={rating} />
                    {rating}
                </div>
            </div>
            <div className="d-flex col-10 flex-column justify-content-center gap-2">
                <div className="gap-3 d-flex justify-content-between ">
                    <h5 className="m-0 ">{name}</h5>
                    <p style={{marginBottom:0,marginRight:"2rem"}} className="fs-12">{date}</p>
                </div>
                <div className="gap-3 d-flex justify-content-start col-12">
                    {tag.map((item, index) => (
                        item.tagType === 1 ?
                            (
                                <div key={index} className=" border border-primary rounded-5 justify-content-center bg-white text-center shadow p-2" style={{ width: "9rem" }}>
                                    <p className="m-0" style={{ fontSize: "0.75rem" }}>{item.tagName}</p>
                                </div>
                            ) : (
                                <div key={index} className="border border-danger rounded-5 justify-content-center bg-white text-center shadow p-2" style={{ width: "9rem" }}>
                                    <p className="m-0" style={{ fontSize: "0.75rem" }}>{item.tagName}</p>
                                </div>
                            )
                    ))}


                </div>
                <div>
                    {text}
                </div>
            </div>
            <AnimatePresence>


            </AnimatePresence>
        </motion.div>
    </>
}