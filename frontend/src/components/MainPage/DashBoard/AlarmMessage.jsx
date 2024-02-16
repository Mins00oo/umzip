import "./AlarmMessage.css"
import React from 'react';
import { motion } from 'framer-motion';

const AlarmMessage = ({profileImg, message,status,date}) => {
  const containerVariants = {
    hidden: { opacity: 0, y:20 },
    visible: { opacity: 1, y:0 },
  };

  const createTime = new Date(date);
  const now = new Date();
  const diffTime = Math.abs(now - createTime);
  const diffHours = Math.floor(diffTime / (1000 * 60 * 60));

  // Formatting the date part as YY-MM-DD
  const yy = createTime.getFullYear().toString();
  const mm = ('0' + (createTime.getMonth() + 1)).slice(-2); // Adding 1 because months are 0-indexed
  const dd = ('0' + createTime.getDate()).slice(-2);

  return (
    <>
      <motion.div
        initial="hidden"
        animate="visible"
        whileHover={{y:-10}}
        variants={containerVariants}
        transition={{ duration: 0.3 }}
        style={{ backgroundColor: "#EAEBEE" }}
        className="shadow rounded-5 p-3 d-flex align-items-center gap-5 position-relative"
      >
        <img className="col-2 shadow-sm" style={{ height: "3rem", width: "3rem", borderRadius: '50%', objectFit: 'cover' }} src={profileImg} alt="Profile Image" />
        <p className="m-0 col-7">{message}</p>
        <p className="m-0 col-2 text-end">{diffHours < 24 ? `${diffHours} 시간 전` : `${yy}-${mm}-${dd}`}</p>
        {/* 빨간색 원 */}
        {!status&& <motion.div
          className="red-circle"
        ></motion.div>}
      </motion.div>
    </>
  );
};

export default AlarmMessage;
