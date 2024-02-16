import { AnimatePresence, motion } from "framer-motion";

export default function AddRedButton({ name, isActive, value, handleAddButtonClick }) {
  const buttonVariants = {
    active: {
      backgroundColor: "#f92a76",
      color: "#fff",
    },
    inactive: {
      backgroundColor: "#fff",
      color: "#f92a76",
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
            borderColor: "#f92a76",
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
            borderColor: "#f92a76",
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
