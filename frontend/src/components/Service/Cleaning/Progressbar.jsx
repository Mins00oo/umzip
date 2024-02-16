import React from 'react'; 
import './Progressbar.css'; // CSS 파일을 import
import { AnimatePresence, motion } from "framer-motion";

const ProgressBar = ({ steps, activeStep }) => {
    const progressBarWidth = (activeStep / steps) * 100 + '%';

    return (
        <motion.div className="progress-bar-container">
            <motion.div className="progress-bar" style={{ width: progressBarWidth }} />
            <AnimatePresence mode='wait'>
                <motion.div key={activeStep} initial={{ opacity: 0, x: -30 }} animate={{ opacity: 1, x: 0 }} exit={{opacity:0,x:30}} transition={{ duration: 0.3 }}>
                    {activeStep === 1 && "1. 약속할 날짜를 정해주세요!"}
                    {activeStep === 2 && "2. 주소를 정해주세요!"}
                    {activeStep === 3 && "3. 각종 세부사항을 정해주세요!"}
                    {activeStep === 4 && "4. 저희가 계산한 결과입니다!"}
                </motion.div>
            </AnimatePresence>
        </motion.div>
    );
};

export default ProgressBar;
