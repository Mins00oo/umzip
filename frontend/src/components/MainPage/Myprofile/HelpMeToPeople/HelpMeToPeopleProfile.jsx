
import { motion } from "framer-motion"
import moment from "moment-timezone";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom"
export default function HelpMeToPeopleProfile({ createDt, codeSmallName, id, writerName, title, point }) {
    const [timeDifference, setTimeDifference] = useState('');
    const truncatedTitle = title.length > 20
    ? title.slice(0, 20) + '...'
    : title
    const createTime = new Date(createDt);
    const now = new Date();
    const diffTime = Math.abs(now - createTime);
    const diffHours = Math.floor(diffTime / (1000 * 60 * 60));
  
    // Formatting the date part as YY-MM-DD
    const yy = createTime.getFullYear().toString();
    const mm = ('0' + (createTime.getMonth() + 1)).slice(-2); // Adding 1 because months are 0-indexed
    const dd = ('0' + createTime.getDate()).slice(-2);

    const navigate = useNavigate()
    const handleClick = () => {
        navigate(`/helpdetail/${id}`)
    }
    return <>
        <motion.div
            onClick={handleClick}
            whileHover={{ y: -5, cursor: "pointer" }}
            className="d-flex p-2 rounded-4 shadow align-items-center gap-3" initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }}>
            <div style={{ width: "100%" }} className="p-2" >
                <div className="d-flex gap-2 justify-content-between" >

                    <div className="d-flex gap-1 m-0">
                        <h5 className="m-0">{truncatedTitle}</h5>
                        <h5 className="m-0" style={{ color: "blue" }}>[{codeSmallName}]</h5>
                    </div>
                    <h5  >{writerName}</h5>
                </div>
                <div className="d-flex justify-content-between">
                    <h5 className="m-0" style={{ fontSize: "1rem" }}>{point}P</h5>
                    <h5 className="m-0 mute" style={{ fontSize: "1rem" }}>{diffHours < 24 ? `${diffHours} 시간 전` : `${yy}-${mm}-${dd}`}</h5>
                </div>
            </div>
        </motion.div >
    </>
}