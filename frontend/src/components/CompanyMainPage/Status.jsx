import styles from './Status.module.css'

export default function Status({ handleFilterChange }) {
  return (
    <div className={styles.statusContainer}>
      <div onClick={() => handleFilterChange(null)} className={`${styles.statusOption} ${styles.statusText}`}>
        <span></span>
        <svg className={styles.statusIcon}
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 512 512"
        >
          <path
            fill="#000000"
            d="M464 256A208 208 0 1 0 48 256a208 208 0 1 0 416 0zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256z"
          />
        </svg>
        <h5 className={styles.statusTitle} style={{ color: "#000000" }}>상태 전체</h5>
      </div>
      <div onClick={() => handleFilterChange(1)} className={`${styles.statusOption} ${styles.statusText}`}>
        <span></span>
        <svg className={styles.statusIcon}
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 512 512"
        >
          <path
            fill="#45B649"
            d="M464 256A208 208 0 1 0 48 256a208 208 0 1 0 416 0zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256z"
          />
        </svg>
        <h5
          className={styles.statusTitle}
          style={{ color: "#45B649" }}
        >
          새로운 신청
        </h5>
      </div>
      <div onClick={() => handleFilterChange(2)} className={`${styles.statusOption} ${styles.statusText}`}>
        <span></span>
        <svg className={styles.statusIcon}
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 512 512"
        >
          <path
            fill="#ffc107"
            d="M464 256A208 208 0 1 0 48 256a208 208 0 1 0 416 0zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256z"
          />
        </svg>
        <h5
          className={styles.statusTitle}
          style={{ color: "#ffc107" }}
        >
          제안중
        </h5>
      </div>
      <div onClick={() => handleFilterChange(4, 5)} className={`${styles.statusOption} ${styles.statusText}`}>
        <span></span>
        <svg className={styles.statusIcon}
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 512 512"
        >
          <path
            fill="#dc3545"
            d="M464 256A208 208 0 1 0 48 256a208 208 0 1 0 416 0zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256z"
          />
        </svg>
        <h5
          className={styles.statusTitle}
          style={{ color: "#dc3545" }}
        >
          취소,거절
        </h5>
      </div>
      <div onClick={() => handleFilterChange(3)} className={`${styles.statusOption} ${styles.statusText}`}>
        <span></span>
        <svg className={styles.statusIcon}
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 512 512"
        >
          <path
            fill="#006EEE"
            d="M464 256A208 208 0 1 0 48 256a208 208 0 1 0 416 0zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256z"
          />
        </svg>
        <h5
          className={styles.statusTitle}
          style={{ color: "#006EEE" }}
        >
          예약 확정
        </h5>
      </div>
      <div onClick={() => handleFilterChange(6)} className={`${styles.statusOption} ${styles.statusText}`}>
        <span></span>
        <svg className={styles.statusIcon}
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 512 512"
        >
          <path
            fill="#979797"
            d="M464 256A208 208 0 1 0 48 256a208 208 0 1 0 416 0zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256z"
          />
        </svg>
        <h5
          className={styles.statusTitle}
          style={{ color: "#979797" }}
        >
          완료
        </h5>
      </div>
    </div>
  );
}
