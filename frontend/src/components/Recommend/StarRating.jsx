import { useState } from 'react';
import { motion } from 'framer-motion';
import styles from './StarRating.module.css';

export default function StarRating({ rating }) {
  const baseFontSize = 16; // Assuming the base font size is 16px
  const integerPart = Math.floor(rating);
  const decimalPart = rating - integerPart;

  const stars = Array.from({ length: 5 }, (_, index) => {
    if (index < integerPart) {
      return <motion.div key={index} className={`${styles.star} ${styles.filled}`} animate={{ opacity: 1 }} />;
    }

    if (index === integerPart && decimalPart > 0) {
      const decimalWidth = decimalPart * 1.25; // 20px / 16px = 1.25rem (decimalWidth in rem)
      return (
        <motion.div
          key={index}
          className={`${styles.star} ${styles.filled}`}
          style={{ clipPath: `polygon(0 0, ${decimalWidth}rem 0, ${decimalWidth}rem 100%, 0 100%)` }}
        />
      );
    }

    // Render nothing for empty stars when index is greater than or equal to integerPart
    return null;
  });

  return <div className={styles.starRating}>{stars}</div>;
}
