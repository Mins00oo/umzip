// RecommendModal.js
import { motion, AnimatePresence } from "framer-motion";
import React from "react";
import RecommendModalComponent from "./RecommendModalComponent";

const RecommendModal = ({companyId, isOpen, closeModal }) => {
  const modalStyle = {
    height: "85%",
    width: "78%",
    zIndex: '999',
    position: 'fixed',
    top: '55%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    overflowY: "auto",  // 여기에 스타일 추가
  };

  return (
    <AnimatePresence>
      {isOpen && (
        <motion.div
          key="modal"
          style={modalStyle}
          className="d-flex justify-content-center align-items-center shadow-lg rounded-5 bg-white"
          initial={{ opacity: 0.1 }}
          animate={{ opacity: 1 }}
          exit={{ opacity: 0.1 }}
          transition={{ duration: 0.3 }}
        >
          <motion.div
            style={{ height: "100%", width: "100%" }}
            className="bg-white p-3 rounded-4 position-relative"
          >
            {/* 모달 내용 */}
            <RecommendModalComponent companyId={companyId} />
            {/* 모달 닫기 버튼 */}
            <button
              onClick={closeModal}
              className="position-absolute top-0 end-0 m-4 btn-close"
              aria-label="Close"
            ></button>
          </motion.div>
        </motion.div>
      )}
    </AnimatePresence>
  );
};

export default RecommendModal;
