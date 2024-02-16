import { AnimatePresence } from "framer-motion";
import CompanyMain from '../../components/CompanyMainPage/CompanyMain';
import Header from "../../components/Header";
import Chat from "../../components/Chat/Chat";




function CompanyDsahBord() {
        return (
        <>
            <Header />
            {/* <h1>업체 대시보드</h1> */}
            <AnimatePresence>
            <CompanyMain />
            </AnimatePresence>
            <Chat />
        </>
        );
}
export default CompanyDsahBord;