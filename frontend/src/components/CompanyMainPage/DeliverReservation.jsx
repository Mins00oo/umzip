import { useEffect, useState } from 'react';
import companyDeliveryReservation from '../../store/companyDeliveryReservation'
import { AnimatePresence, motion } from "framer-motion";
import BookingDetails from './BookingDetails';
import Status from './Status';
import StatusChange from './StatusChange';
import ReplyTo from './ReplyTo';

function DeliverReservation({ chatModal }) {
  const { fetchDataDelivery, data } = companyDeliveryReservation();
  const [currentPage, setCurrentPage] = useState(1); // 현재 페이지
  const [visibleItems, setVisibleItems] = useState([]); // 현재 화면에 보여줄 아이템 목록
  const [filterStatus, setFilterStatus] = useState(null); // 필터링할 상태 코드
  const itemsPerPage = 5; // 보여줄 갯수
  const reservationList = data?.result || [];
  
  // 견적서 모달
  const [modalShow, setModalShow] = useState(new Map());
  const toggleModal = (deliveryId) => {
    setModalShow((prev) =>
      new Map(prev).set(deliveryId, !prev.get(deliveryId))
    );
  };

  // 새로운 마우스 오버 상태 관리
  const [hoverState, setHoverState] = useState(new Map());

  // 마우스 오버 이벤트 핸들러
  const handleMouseEnter = (id) => {
    setHoverState((prev) => new Map(prev).set(id, true));
  };

  const handleMouseLeave = (id) => {
    setHoverState((prev) => new Map(prev).set(id, false));
  };

  useEffect(() => {
    fetchDataDelivery();
  }, [fetchDataDelivery]);

  useEffect(() => {
    let filteredData = data?.result || [];
    if (filterStatus === 1) {
      filteredData = filteredData.filter(
        (item) => item.codeSmallId % 100 === 1
      );
    }
    if (filterStatus === 2) {
      filteredData = filteredData.filter(
        (item) => item.codeSmallId % 100 === 2
      );
    }
    if (filterStatus === 3) {
      // 예약 확정 상태일 때 시간순 정렬
      filteredData = filteredData.filter(
        (item) => item.codeSmallId % 100 === 3
      );
      filteredData.sort(
        (a, b) => new Date(a.startTime) - new Date(b.startTime)
      );
    }
    if (filterStatus === (4 || 5)) {
      filteredData = filteredData.filter(
        (item) => item.codeSmallId % 100 === (4 || 5)
      );
    }
    if (filterStatus === 6) {
      filteredData = filteredData.filter(
        (item) => item.codeSmallId % 100 === 6
      );
    }
    const firstItemIndex = (currentPage - 1) * itemsPerPage;
    // Slice the data based on pagination
    setVisibleItems(filteredData.slice(firstItemIndex, firstItemIndex + itemsPerPage));
  }, [data, currentPage, filterStatus, itemsPerPage]);

  const handleFilterChange = (status) => {
    setFilterStatus(status);
    setCurrentPage(1);
  };

  const goToPage = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  // 페이지 계산
  const totalPages = Math.ceil((filterStatus ? visibleItems.length + 1 : reservationList.length + 1) / itemsPerPage);

  const pageNumbers = [];
  for (let i = 1; i <= totalPages; i++) {
    pageNumbers.push(i);
  }

  return (
    <>
      <div className="col-md-10 p-5 gap-4 d-flex flex-column">
        <div
          className="d-flex justify-content-between mx-5"
          style={{
            position: 'sticky',
            top: 0,
            background: 'white',
          }}
        >
          <div className="bg-white shadow rounded-3 p-2  justify-content-center align-items-center ">
            <Status handleFilterChange={handleFilterChange} />
          </div>
        </div>
        <div style={{ maxHeight: 'calc(100vh - 150px)' }}>
          <div
            className=" rounded-3 mx-5 p-2 d-flex justify-content-around align-items-center text-center mb-2"
            style={{ background: '#D9E4FF' }}
          >
            <h5 className="m-0 col-md-2">견적서</h5>
            <h5 className="m-0 col-md-2">작업 일시</h5>
            <h5 className="m-0 col-md-2">현재 견적</h5>
            <h5 className="m-0 col-md-2">주문번호/고객명</h5>
            <h5 className="m-0 col-md-2">상태</h5>
            <h5 className="m-0  col-md-2">응답</h5>
          </div>
          <motion.div className='d-flex flex-column justify-content-between gap-2 ' style={{ width: '100%', minHeight: '10rem' }}>
            {visibleItems.map((item, index) => (

              <motion.div
                key={index}
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                exit={{ opacity: 0 }}
                className="rounded-3 mx-5 p-2 d-flex justify-content-around text-center align-items-center position-relative"
                style={{
                  border: '1px solid #006EEE',
                  minHeight: '6rem',
                }}
              >
                <h5 className="m-0 col-md-2">
                  <img
                    onMouseEnter={() => handleMouseEnter(item.deliveryId)}
                    onMouseLeave={() => handleMouseLeave(item.deliveryId)}
                    onClick={() => toggleModal(item.deliveryId)}
                    style={
                      hoverState.get(item.deliveryId)
                        ? {
                          width: '4rem',
                          height: '4rem',
                          cursor: 'pointer',
                          transition: 'width 1s ease, height 1s ease',
                        }
                        : { width: '2rem', height: '2rem', cursor: 'pointer' }
                    } // cursor 추가
                    src={
                      hoverState.get(item.deliveryId)
                        ? '/detailHover.gif'
                        : '/detail.png'
                    }
                    alt="Detail"
                  />
                </h5>
                <h5 className="m-0 col-md-2">{item.startTime}</h5>
                <h5 className="m-0 col-md-2">
                  {item.reissuing
                    ? item.reissuing
                      .toString()
                      .replace(/\B(?=(\d{3})+(?!\d))/g, ',')
                    : item.price
                      .toString()
                      .replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
                </h5>
                <h5 className="m-0 col-md-2">
                  {item.deliveryId}/{item.memberName}/{item.mappingId}
                </h5>
                <div className="m-0 col-md-2">
                  <StatusChange status={item.codeSmallId} />
                </div>
                <h5 className="m-0 col-md-2">
                  <ReplyTo
                    chatModal={chatModal}
                    role="delivery"
                    memberId={String(item.memberId)}
                    status={item.codeSmallId}
                    mappingId={item.mappingId}
                    reissuing={item.reissuing ? item.reissuing : item.price}
                  />
                </h5>
                <AnimatePresence>
                  {modalShow.get(item.deliveryId) && (
                    <motion.div
                      initial={{ y: -20, opacity: 0 }}
                      animate={{ y: 0, opacity: 1 }}
                      exit={{ y: 20, opacity: 0 }}
                      transition={{ duration: 0.2 }}
                      style={{
                        zIndex: '99',
                        position: 'fixed',
                        top: 0,
                        left: 0,
                        width: '100%',
                        height: '100%',
                        backgroundColor: 'rgba(0, 0, 0, 0.5)',
                        display: 'flex',
                        justifyContent: 'center',
                        alignItems: 'center',
                      }}
                      onClick={() => toggleModal(item.deliveryId)} // 모달 외부 클릭 시 모달 닫기
                    >
                      {/* 모달 내용 클릭 시 이벤트 버블링 방지 */}
                      <div className='d-flex justify-content-center' style={{ width: "50%", height: "70%" }} onClick={(e) => e.stopPropagation()}>
                        <BookingDetails
                          nowId={item.deliveryId}
                          role={'delivery'}
                          price={item.price}
                          reissuing={item.reissuing}
                          name={item.memberName}
                          memberId={item.memberId}
                        />
                      </div>
                    </motion.div>
                  )}
                </AnimatePresence>
              </motion.div>
            ))}
            <div className="pagination gap-2 d-flex align-items-center justify-content-center mt-3">
              {pageNumbers.map(number => (
                <button
                  key={number}
                  onClick={() => goToPage(number)}
                  className={currentPage === number ? 'active btn btn-light' : 'btn btn-light'}
                >
                  {number}
                </button>
              ))}
            </div>

          </motion.div>
        </div>
      </div>
    </>
  );
}

export default DeliverReservation;
