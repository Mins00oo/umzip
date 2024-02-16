import Chat from "../../components/Chat/Chat";
import Header from "../../components/Header";
import UserProfile from "../../components/MainPage/Myprofile/UserProfile";
import { motion } from "framer-motion"
export default function MyProfile() {
    return <>
        <Header />
        <Chat />
        <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} transition={{ duration: 0.3 }}
            style={{ marginTop: "5%", height: "80Vh" }} className="d-flex col-12 justify-content-center ">
            <UserProfile />
        </motion.div>
    </>
}