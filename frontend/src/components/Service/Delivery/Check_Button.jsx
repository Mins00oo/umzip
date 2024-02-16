import { AnimatePresence, motion } from "framer-motion";

export default function CheckButton({ name, checkPacking, isActive }) {
  const buttonVariants = {
    active: {
      backgroundColor: "#1042C0",
      color: "#fff",
    },
    inactive: {
      backgroundColor: "#fff",
      color: "#1042C0",
    },
  };

  return (
    <AnimatePresence>
      <motion.button
        onClick={checkPacking}
        variants={buttonVariants}
        initial="inactive"
        animate={isActive ? "active" : "inactive"}
        exit="inactive"
        whileHover={{ y: -5 }}
        className="shadow rounded-4"
        style={{
          width: "100%",
          borderColor: "#1042C0",
          borderWidth: "2px",
          borderStyle: "solid",
        }}
      >
        {name}
      </motion.button>
    </AnimatePresence>
  );
}
