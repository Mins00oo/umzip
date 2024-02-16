import { AnimatePresence, motion } from "framer-motion";

export default function AddButton({ name, isActive, value, handleAddButtonClick }) {
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
      {isActive ? (
        <motion.button
          onClick={() => handleAddButtonClick(value)}
          variants={buttonVariants}
          initial="inactive"
          animate="active"
          exit="inactive"
          whileHover={{ y: -5 }}
          className="shadow rounded-4"
          style={{
            width: "100%",
            borderColor: "#1042C0",
            borderWidth: "2px",
            borderStyle: "solid",
            height: "3.5rem",
          }}
        >
          {name}
        </motion.button>
      ) : (
        <motion.button
          onClick={() => handleAddButtonClick(value)}
          variants={buttonVariants}
          initial="inactive"
          animate="inactive"
          exit="active"
          whileHover={{ y: -5 }}
          className="shadow rounded-4"
          style={{
            width: "100%",
            borderColor: "#1042C0",
            borderWidth: "2px",
            borderStyle: "solid",
            height: "3.5rem",
          }}
        >
          {name}
        </motion.button>
      )}
    </AnimatePresence>
  );
}
