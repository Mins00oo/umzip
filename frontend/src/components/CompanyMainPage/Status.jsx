// Status.js

export default function Status({handleFilterChange}) {
  return (
    <div className="d-flex justify-content-around align-items-center h-100" >
      <div onClick={() => handleFilterChange(1)} className="d-flex align-items-center gap-2" style={{ fontSize: '14px' }}>
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" width="20" height="20">
          <path fill="#8FFF00" d="M464 256A208 208 0 1 0 48 256a208 208 0 1 0 416 0zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256z"/>
        </svg>
        <h5 className="m-0 fw-bold" style={{ color: "#8FFF00", marginLeft: '4px' }}>새로운 신청</h5>
      </div>

      <div onClick={() => handleFilterChange(2)} className="d-flex align-items-center  gap-2" style={{ fontSize: '14px' }}>
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" width="20" height="20">
          <path fill="#ffc107" d="M464 256A208 208 0 1 0 48 256a208 208 0 1 0 416 0zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256z"/>
        </svg>
        <h5 className="m-0 fw-bold" style={{ color: "#ffc107", marginLeft: '4px' }}>제안중</h5>
      </div>

      <div onClick={() => handleFilterChange(4)} className="d-flex align-items-center  gap-2 " style={{ fontSize: '14px' }}>
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" width="20" height="20">
          <path fill="#dc3545" d="M464 256A208 208 0 1 0 48 256a208 208 0 1 0 416 0zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256z"/>
        </svg>
        <h5 className="m-0 fw-bold" style={{ color: "#dc3545", marginLeft: '4px' }}>취소,거절</h5>
      </div>

      <div onClick={() => handleFilterChange(3)} className="d-flex align-items-center  gap-2" style={{ fontSize: '14px' }}>
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" width="20" height="20">
          <path fill="#006EEE" d="M464 256A208 208 0 1 0 48 256a208 208 0 1 0 416 0zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256z"/>
        </svg>
        <h5 className="m-0 fw-bold" style={{ color: "#006EEE", marginLeft: '4px' }}>예약 확정</h5>
      </div>

      <div onClick={() => handleFilterChange(6)} className="d-flex align-items-center  gap-2" style={{ fontSize: '14px' }}>
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" width="20" height="20">
          <path fill="#979797" d="M464 256A208 208 0 1 0 48 256a208 208 0 1 0 416 0zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256z"/>
        </svg>
        <h5 className="m-0 fw-bold" style={{ color: "#979797", marginLeft: '4px' }}>완료</h5>
      </div>
    </div>
  );
}
