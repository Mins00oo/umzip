import { motion } from "framer-motion";

import "./Wave.css"



export default function Wave() {
  const text = "계산이 진행중입니다!!";

  const containerVariant = {
    visible: {
      opacity: 1,
      transition: { staggerChildren: 0.05, delayChildren: 0.05 },
    },
    hidden: {
      opacity: 0,
    },
  };
  
  const letterVariant = {
    visible: {
      opacity: 1,
      y: 0,
      transition: {
        type: "spring",
        damping: 15,
        stiffness: 400,
      },
    },
    hidden: {
      opacity: 0,
      y: 20,
      transition: {
        type: "spring",
        damping: 15,
        stiffness: 400,
      },
    },
  };
  return (
    <motion.h1 className="m-0" whileInView="visible" initial="hidden" variants={containerVariant}>
      {Array.from(text).map((letter, index) => (
        <motion.span className="styledletter m-0" key={index} variants={letterVariant}>
          {letter === " " ? "\u00A0" : letter}
        </motion.span>
      ))}
    </motion.h1>
  );
}
