import { motion } from "framer-motion"
import { useNavigate } from "react-router-dom";
export default function BuyProfile({ id,img, title, status, price }) {
    const getStatusColor = () => {
        return status === '판매완료' ? 'gray' : 'green';
    };
    const navigate=useNavigate()
    const handleClick=()=>{
        navigate(`/trade/${id}`)
    }
    return <>
        <motion.div onClick={handleClick} whileHover={{ y: -5, cursor: "pointer" }} className="d-flex p-2 rounded-4 shadow align-items-center gap-3" initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }}>
            <div className="d-flex gap-3">
                <img style={{ width: "4rem", height: "4rem" }} src={img} />
                <div>
                    <div className="d-flex gap-2">
                        <h5>{title}</h5>
                        <h5 style={{ color: getStatusColor() }} >[{status}]</h5>
                    </div>
                    <h5 style={{ fontSize: "1rem" }}>{price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}원</h5>
                </div>
            </div>
        </motion.div >
    </>
}