import { motion } from "framer-motion"
import { useNavigate } from "react-router-dom";
export default function UsedProfile({ id, img, title, status, price }) {
    const getStatusColor = () => {

        return status === '판매완료' ? 'gray' : 'green';
    };

    const navigate = useNavigate()
    const handleClick = () => {
        navigate(`/trade/${id}`)
    }
    return <>
        <motion.div key={id} whileHover={{ y: -5, cursor: "pointer" }} onClick={handleClick} className="d-flex p-2 rounded-4 shadow align-items-center gap-3"  >
            <div className="d-flex gap-3">
                <img style={{ width: "4rem", height: "4rem" }} src={img} />
                <div>
                    <div className="d-flex gap-2">
                        <h5>{title}</h5>
                        <h5 style={{ color: getStatusColor() }} >[{status}]</h5>
                    </div>
                    <h5 style={{ fontSize: "1rem" }}>{price.toLocaleString('ko-KR')}원</h5>
                </div>
            </div>
        </motion.div >
    </>
}