import { motion, useScroll } from "framer-motion"
import StarRating from "../../../Recommend/StarRating"
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import moment from "moment-timezone";
export default function ReviewToMeProfile({memberId,renew, tagType, createDt, img, id, tag, name, rating, review }) {
    // console.log(createDt)
    const truncatedTitle = review.length > 40
    ? review.slice(0, 40) + '...'
    : review
    const navigate = useNavigate()
    const [time, setTime] = useState(null)
    const tags = tag.map((tagName, index) => ({
        tagName,
        tagType: tagType[index],
    }));
    const createTime = new Date(createDt);
    const now = new Date();
    const diffTime = Math.abs(now - createTime);
    const diffHours = Math.floor(diffTime / (1000 * 60 * 60));
  
    // Formatting the date part as YY-MM-DD
    const yy = createTime.getFullYear().toString();
    const mm = ('0' + (createTime.getMonth() + 1)).slice(-2); // Adding 1 because months are 0-indexed
    const dd = ('0' + createTime.getDate()).slice(-2);
    const handleClick = () => {
        
        navigate(`/myprofile/${memberId}`)
        renew()
    }
    return <>
        <motion.div whileHover={{ y: -5, cursor: "pointer" }} onClick={handleClick} className="d-flex p-2 rounded-4 shadow align-items-center gap-3" initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} style={{ width: "100%" }}>
            <div className="p-2 d-flex flex-column gap-3 " style={{ width: "100%" }}>
                <div className="d-flex" style={{ width: "100%" }}>
                    <div className="d-flex flex-column align-items-center" style={{ marginRight: "1rem" }} >
                        <img className="rounded-pill" style={{ width: "4rem", height: "4rem" }} src={img} />
                        <p className="m-0 fw-bold">{name}</p>
                    </div>
                    <div style={{ width: "100%" }} className="d-flex flex-column  justify-content-around">
                        <div className="d-flex  justify-content-between" style={{ width: "100%" }}>

                            <div className="d-flex gap-3 col-6 ">

                                <StarRating rating={rating} />
                                {rating}점
                            </div>
                            <div className="col-6 d-flex" ><p style={{ marginLeft: "auto", marginBottom: "0" }}>{diffHours < 24 ? `${diffHours} 시간 전` : `${yy}-${mm}-${dd}`}</p></div>

                        </div>
                        <div style={{ width: "100%", height: "2rem" }} className="d-flex gap-3  ">
                            {tags.map((item, index) => (
                                item.tagType === 1 ? (
                                    <div key={index} className="d-flex border border-primary rounded-5 col-3 bg-white align-items-center justify-content-center" style={{ fontSize: "0.75rem" }}>
                                        {item.tagName}
                                    </div>
                                ) : (
                                    <div key={index} className="d-flex border border-danger rounded-5 col-3 bg-white align-items-center justify-content-center" style={{ fontSize: "0.75rem" }}>
                                        {item.tagName}
                                    </div>
                                )
                            ))}
                        </div>
                    </div>

                </div>
                <div className="col-12  p-2 rounded-3" style={{ backgroundColor: "#ECECEC" }}>
                    {truncatedTitle}
                </div>
            </div>
        </motion.div >
    </>
}