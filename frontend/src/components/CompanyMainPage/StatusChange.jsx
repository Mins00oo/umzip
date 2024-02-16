import PropTypes from 'prop-types';


export default function StatusChange({ status }) {

    // PropTypes를 사용하여 props 유형 검증
    StatusChange.propTypes = {
      status: PropTypes.number, // 'codeSmall' prop이 문자열이어야 함을 선언
    };

  const newstatus = status % 100;
  // console.log(newstatus)
  let color = null;
  if (newstatus === 1) {
    color = "#45B649";
  } else if (newstatus === 2) {
    color = "#ffc107";
  } else if (newstatus === 4) {
    color = "#dc3545";
  } else if (newstatus === 5) {
    color = "#dc3545";
  } else if (newstatus === 3) {
    color = "#006EEE";
  } else if (newstatus === 6) {
    color = "#979797";
  }
  // console.log(color)
  return (
    <>
      <svg
        xmlns="http://www.w3.org/2000/svg"
        viewBox="0 0 512 512"
        width="20"
        height="20"
      >
        <path
          fill={color}
          d="M464 256A208 208 0 1 0 48 256a208 208 0 1 0 416 0zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256z"
        />
      </svg>
    </>
  );
}
