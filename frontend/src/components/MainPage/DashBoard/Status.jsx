// Status.js

import React from 'react';

export default function Status() {
  return (
    <div className="d-flex justify-content-around align-items-center gap-2 " >
      <div className="d-flex align-items-center gap-2" style={{ fontSize: '2rem' }}>
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" width="1rem" height="1rem">
          <path fill="#45B649" d="M464 256A208 208 0 1 0 48 256a208 208 0 1 0 416 0zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256z"/>
        </svg>
        <h5 className="m-0 fw-bold" style={{ color: "#45B649", marginLeft: '4px',fontSize:"1rem" }}>신청중</h5>
      </div>

      <div className="d-flex align-items-center  gap-2" style={{ fontSize: '14px' }}>
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" width="1rem" height="1rem">
          <path fill="#ffc107" d="M464 256A208 208 0 1 0 48 256a208 208 0 1 0 416 0zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256z"/>
        </svg>
        <h5 className="m-0 fw-bold" style={{ color: "#ffc107", marginLeft: '4px',fontSize:"1rem"  }}>검토중</h5>
      </div>

      <div className="d-flex align-items-center  gap-2 " style={{ fontSize: '14px' }}>
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" width="1rem" height="1rem">
          <path fill="#dc3545" d="M464 256A208 208 0 1 0 48 256a208 208 0 1 0 416 0zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256z"/>
        </svg>
        <h5 className="m-0 fw-bold" style={{ color: "#dc3545", marginLeft: '4px',fontSize:"1rem"  }}>취소,거절</h5>
      </div>

      <div className="d-flex align-items-center  gap-2" style={{ fontSize: '14px' }}>
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" width="1rem" height="1rem">
          <path fill="#006EEE" d="M464 256A208 208 0 1 0 48 256a208 208 0 1 0 416 0zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256z"/>
        </svg>
        <h5 className="m-0 fw-bold" style={{ color: "#006EEE", marginLeft: '4px',fontSize:"1rem"  }}>예약 확정</h5>
      </div>

      <div className="d-flex align-items-center  gap-2" style={{ fontSize: '14px' }}>
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" width="1rem" height="1rem">
          <path fill="#979797" d="M464 256A208 208 0 1 0 48 256a208 208 0 1 0 416 0zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256z"/>
        </svg>
        <h5 className="m-0 fw-bold" style={{ color: "#979797", marginLeft: '4px' ,fontSize:"1rem" }}>완료</h5>
      </div>
    </div>
  );
}
