import PropTypes from 'prop-types';
import useReplyStore from '../../store/replyStore'
import companyDeliveryReservation from '../../store/companyDeliveryReservation'
import companyCleanReservation from '../../store/companyCleanReservation'
import { useState } from 'react';
import style from './ReplyModal.module.css'

function ReplyModal({ role, onClose, mappingId, reissuing }) {
    const submitReplyStore = useReplyStore((state) => state.submitReplyStore);
    const [operReissuing, setReissuing] = useState(reissuing)
    const { fetchDataDelivery } = companyDeliveryReservation();
    const { fetchDataClean } = companyCleanReservation();

    const handleSubmit = async (event) => {
      event.preventDefault();
      const reissuing = operReissuing; // input으로부터의 값은 문자열이므로, 숫자로 변환
      const detail = event.target.message.value;

      await submitReplyStore(role, mappingId, reissuing, detail); // Zustand store의 함수 호출
      // 화면 즉각 반영을 위한 코드
      fetchDataDelivery()
      fetchDataClean()
      onClose(); // 모달 닫기
    };

  return (
    <div className={style.modalContent}>
    <div style={{ /* 모달 스타일 */ }}>
      <h2>견적 제안</h2>
      <form onSubmit={handleSubmit}>
        {/* 견적 가격과 메시지 입력 필드 */}
        <div>
          <label>견적 가격:</label>
          <input name="estimate" type="number" value={operReissuing} onChange={(e) => setReissuing(parseFloat(e.target.value))} required />      
        </div>
        <div>
          <label>메시지:</label>
          <textarea name="message" required></textarea>
        </div>
        <button type="submit">제출</button>
        <button type="button" onClick={onClose}>취소</button>
      </form>
    </div>
    </div>
  );
}
  
  // PropTypes를 사용하여 props 유형 검증
  ReplyModal.propTypes = {
    role: PropTypes.string.isRequired,
    onClose: PropTypes.func.isRequired,
    mappingId: PropTypes.number.isRequired,
    reissuing: PropTypes.number // price의 유형을 string으로 가정, 필요에 따라 수정 가능
};
  
  export default ReplyModal;