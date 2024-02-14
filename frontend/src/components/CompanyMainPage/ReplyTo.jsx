import PropTypes from 'prop-types';
import ReplyModal from './ReplyModal';
import companyDeliveryReservation from '../../store/companyDeliveryReservation'
import companyCleanReservation from '../../store/companyCleanReservation'
import useReplyStore from '../../store/replyStore'
import chatToCompanyStore from '../../store/chatToCompanyStore'
import styles from './ReplyTo.module.css'
import { useState } from 'react';
import { api } from '../../services/api';



function ReplyTo({ chatModal, role, status, mappingId, reissuing, memberId }) {
  // console.log(memberId)
  const [showModal, setShowModal] = useState(false);
  const { fetchDataDelivery } = companyDeliveryReservation();
  const { fetchDataClean } = companyCleanReservation();
  const { makeChatRoom } = chatToCompanyStore()


  const handleOpenModal = () => setShowModal(true);
  const handleCloseModal = () => setShowModal(false);
  const rejectionReservation = useReplyStore((state) => state.rejectionReservation);

  const code = status % 100;
  const MakeRoom = async () => {

    console.log(role)
    const numericRoomNumber = parseInt(memberId, 10);
    try {
      const response = await api.post(
        `/chat/${role}/${numericRoomNumber}`,
        // 요청 바디를 올바른 위치에 추가
        {},  // 만약 바디가 있다면 여기에 추가하세요.

      );
      // setChatRoom(response.data.result)
      // console.log(response)
      return response.data.result
    } catch (error) {
      console.error(error);
    }
  };
  const rejectionHandle = async () => {
    await rejectionReservation(role, mappingId); // Zustand store의 함수 호출
    // 화면 즉각 반영을 위한 코드
    fetchDataDelivery()
    fetchDataClean()
  };

  const chatStart = async () => {
    const res = await MakeRoom()
    // console.log(res)
    chatModal(res)
  }

  return (
    <div>
      {/* 견적제안과 거절 버튼 */}
      {code === 1 && (
        <>
          {/* <button onClick={handleOpenModal}>견적제안</button> */}
          <div onClick={handleOpenModal} className={styles.uiverseCheck}>
            <span className={styles.tooltipCheck}>재견적 보내기</span>
            <span>O</span>
          </div>
          {/* <button onClick={() => rejectionHandle(role, mappingId)}>거절</button> */}
          <div
            onClick={() => rejectionHandle(role, mappingId)}
            className={styles.uiverseCancel}>
            <span className={styles.tooltipCancel}>요청 거절하기</span>
            <span>X</span>
          </div>
          {showModal && (
            <ReplyModal
              role={role}
              onClose={handleCloseModal}
              mappingId={mappingId}
              reissuing={reissuing}
            />
          )}
        </>
      )}

      {/* 취소 버튼 */}
      {code === 2 && (
        <button className='btn btn-danger' onClick={() => rejectionHandle(role, mappingId)}>취소</button>
      )}

      {/* 1대1 채팅 버튼 */}
      {code === 3 && (
        <button className='btn btn-primary' onClick={() => chatStart()}>1대1 채팅</button>
      )}

      {/* 제안 거절시 */}
      {code === 4 && <h5>거절한 제안</h5>}

      {/* 제안 취소시 */}
      {code === 5 && <h5>취소된 제안</h5>}
    </div>
  );
}
// PropTypes를 사용하여 props 유형 검증
ReplyTo.propTypes = {
  role: PropTypes.string,
  status: PropTypes.number, // 'codeSmall' prop이 문자열이어야 함을 선언
  mappingId: PropTypes.number,
  reissuing: PropTypes.number,
  memberId: PropTypes.string,
};

export default ReplyTo;