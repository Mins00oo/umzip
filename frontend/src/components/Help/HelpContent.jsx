import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState  } from 'react';
import style from './HelpContent.module.css';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation, Pagination } from 'swiper/modules';
import { AnimatePresence, motion } from "framer-motion";
import HelpReview from "./HelpReview"
import useStore from '../../store/helpDetailData';
import moment from 'moment-timezone';
import "swiper/css";
import "swiper/css/effect-coverflow";
import "swiper/css/pagination";



function HelpDetail() {
  const { boardId } = useParams();
  const navigate = useNavigate()
  const { setBoardId, fetchData, data, loading, error, pointGive } = useStore();
  const [showReviewModal, setShowReviewModal] = useState(false); // 모달 상태 관리
  const [status, setStatus] = useState("first")

  
  // 도움 받았어요 버튼 클릭시 다시한번 확인하는 alert 코드
  const handlePointGive = () => {
    const confirmGivePoint = window.confirm(`${content.rewardPoint}P를 보내시겠습니까?`);
    if (confirmGivePoint) {
      pointGive(content.boardId);
      const confirmHelpReview = window.confirm(`${content.writerId} 님에게 후기를 작성하시겠습니까?`)
      if (confirmHelpReview) {
        // 오픈 모달 또는 페이지 불러오기
        setShowReviewModal(true); // 모달 표시
      }
    }
  };

  const formatDate = (dateString) => {
    const serverDate = moment(dateString+'Z').tz("Asia/Seoul"); // 유럽시간 포멧
    const now = moment().tz("Asia/Seoul");  // 현재 우리나라시간
  
    if (serverDate.isSame(now, 'day')) {
      // 날짜가 같을때
      const diffHours = now.diff(serverDate, 'hours');
      if (diffHours === 0) {
        return '최근'; // 1시간 안된 글
      } else {
        return `${diffHours} 시간 전`;
      }
    } else if (serverDate.year() === now.year()) {
      // 달이 같을때
      return serverDate.format('MM-DD');
    } else {
      // 해당사항 없을때
      return serverDate.format('YYYY-MM-DD');
    }
  };

  // console.log(boardId)
  useEffect(() => {
    setBoardId(boardId);
    fetchData();
  }, [boardId, setBoardId, fetchData, pointGive]);

  // 데이터 로딩 중이면 로딩 인디케이터를 표시합니다.
  if (loading) {
    return <div>Loading...</div>;
  }

  // 에러가 있으면 에러 메시지를 표시합니다.
  if (error) {
    return <div>Error: {error.message}</div>;
  }

  const content = data?.result;
  // 데이터가 로드되면, 해당 데이터를 사용하여 무언가를 렌더링합니다.
  if (!content) {
    // 데이터가 비어있으면 메시지를 표시합니다.
    return <div>No data found.</div>;
  }
  console.log(content)
  
  // console.log(newDate)
  const handleClick = ()=>{
    navigate(`/myprofile/${content.writerId}`)
  }
  return (
    <>
        {/* 후기 모달 */}
        <AnimatePresence>
        {showReviewModal && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
          >
            <HelpReview onClose={() => setShowReviewModal(false)} setStatus={setStatus} to={content.writerId} /> {/* HelpReview에 모달을 닫는 함수를 전달 */}
          </motion.div>
        )}
      </AnimatePresence>
      <AnimatePresence>
        <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} style={{ marginTop: "4rem" }}>
          <div className={style.helps}>
            <div className="d-flex align-items-center gap-4 justify-content-between">
              <div className="d-flex align-items-center gap-4">
                {content.codeSmallId === 401 && <span style={{ fontSize: "2rem", color: "#f06565", fontWeight: "bold" }} >도와주세요</span>}
                {content.codeSmallId === 402 && <span style={{ fontSize: "2rem", color: "#0077CC", fontWeight: "bold" }}>도와줄게요</span>}
                {content.codeSmallId === 403 && <span style={{ fontSize: "2rem", color: "#0077CC", fontWeight: "bold" }} >도움 받았어요</span>}
                <h4 className="m-0"> {content.boardTitle}</h4>
              </div>
              {!content.sameMember && content.codeSmallId === 402 && <span><button className={`btn btn-success ${style.helpBtn}`} onClick={handlePointGive} >도움 받았어요!</button> 포인트: {content.rewardPoint}P</span> }
            </div>
            
            <div>
              <div className="d-flex align-items-center justify-content-between">
                <div className="d-flex align-items-center gap-2">
                  <span className="m-0"></span>
                  <div className="d-flex align-items-center gap-2">
                    <motion.img  whileHover={{y:-5,cursor:"pointer"}} onClick={handleClick}  className="rounded-pill" style={{ width: "2rem" }} src={content.writerImageUrl} />
                    <span className="m-0">{content.writerName}</span>
                  </div>
                </div>
                <span className="m-0" >{formatDate(content.boardCreateDt)}</span>
              </div>

            </div>
           
            <div>{content.imagePathList && content.imagePathList.length > 0 && ( //사진이 없을 경우엔 나타내지 않음
              <Swiper
                modules={[Navigation, Pagination]}
                slidesPerView={2}
                centeredSlides={true}
                spaceBetween={5}
                navigation
                pagination={{ clickable: true }} // clickable을 true로 설정하여 페이지네이션 사용
                loop={false}
                effect={"coverflow"} // coverflow 효과 설정
                coverflowEffect={{ rotate: 0, stretch: 100, depth: 100, modifier: 1, slideShadows: true }} // coverflow 효과의 설정
                style={{
                  width: '60%', /* 부모 요소의 전체 너비를 차지하도록 설정 */
                  maxWidth: '500px', /* 최대 너비 제한 */
                  // height: '500px', /* 고정 높이 설정 */
                  // margin: 'auto', /* 가운데 정렬 */
                }}
              >
                {content.imagePathList.map((img, index) => (
                  <SwiperSlide key={index}>
                    <img
                      src={img}
                      alt={`상세 이미지 image ${index}`}
                      style={{
                        width: '10rem', // 너비를 700px로 설정
                        height: '10rem', // 높이를 300px로 설정
                        margin: '0 auto', // 가로 중앙 정렬을 위한 margin 설정
                        display: 'flex', // Flex 사용
                        justifyContent: 'center', // 가로 중앙 정렬
                        borderRadius: '8px',
                        alignItems: 'center', // 세로 중앙 정렬
                      }}
                    />
                  </SwiperSlide>
                ))}
              </Swiper>
            )}</div>
             <div style={{marginLeft: '20px'}}>{content.boardContent}</div>
            <div className="d-flex gap-3" style={{ marginLeft: "20px" }}>
              <span>조회수 : {content.readCnt}</span>
              <span>댓글수 : {content.boardCommentCnt}</span>
            </div>
            {/* {content.sameMember === true && (
              <div>
                <button>글 수정</button>
                <button>글 삭제</button>
              </div>
            )} */}

          </div>

        </motion.div>
      </AnimatePresence>
      
    </>
  );
}

export default HelpDetail;
