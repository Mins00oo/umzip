import { useEffect, useState } from "react";
import { AnimatePresence, motion } from "framer-motion"
import useStore from "../../store/helpDetailData";
import ListGroup from "react-bootstrap/ListGroup";
import style from "./HelpComments.module.css";
import HelpReview from "./HelpReview"
import moment from 'moment-timezone';
function HelpComments({ toggleModal }) {
  const {
    fetchData,
    loadComment,
    comments,
    loading,
    error,
    sendPostRequest,
    commentChoic,
  } = useStore();
  const [commentText, setCommentText] = useState(""); // 댓글 텍스트 상태
  const [showReviewModal, setShowReviewModal] = useState(false); // 모달 상태 관리
  const [nowWriterId, setNowWriterId] = useState()
  const [status, setStatus] = useState("first")

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
      return serverDate.format('MM.DD');
    } else {
      // 해당사항 없을때
      return serverDate.format('YYYY.MM.DD');
    }
  };

  useEffect(() => {
    loadComment();
  }, [loadComment]);

  const handleCommentChange = (e) => {
    setCommentText(e.target.value); // 입력 값으로 댓글 텍스트 상태 업데이트
  };

  const handleCommentSubmit = async (e) => {
    e.preventDefault();
    try {
      // 댓글 등록 요청을 보냅니다.
      await sendPostRequest(commentText);
      console.log(typeof commentText);
      // 성공적으로 댓글이 등록되면 입력 필드를 초기화합니다.
      setCommentText("");
      // 댓글 리스트를 새로고침합니다.
      await loadComment();
    } catch (error) {
      // 에러 처리를 합니다.
      console.error("댓글 등록 실패:", error);
      alert("댓글을 등록하는 중 오류가 발생했습니다.");
    }
    fetchData();
  };
  const handleClick = (id) => {
    toggleModal(id);
  };
  const handleAdopt = async (commentId, writerName, writerId) => {
    const confirmAdop = window.confirm(`${writerName}님을 채택할까요?`);
    console.log(commentId);
    if (confirmAdop) {
      try {
        // 채택 요청을 보냅니다.
        await commentChoic(commentId);
        // 성공적으로 처리되면 댓글 리스트를 새로고침합니다.
        await loadComment();
        fetchData()
        setNowWriterId(writerId)  // 유저 아이디를 전달
        console.log(nowWriterId)
        const confirmHelpReview = window.confirm(`${writerName} 님에게 후기를 작성할까요?`)
        if(confirmHelpReview) {
          console.log('모달열림')
          setShowReviewModal(true); // 모달 표시
        }
      } catch (error) {
        // 에러 처리를 합니다.
        console.error("채택 처리 실패:", error);
        alert("채택 처리 중 오류가 발생했습니다.");
      }
    }
  };

  // 데이터 로딩 중이면 로딩 인디케이터를 표시합니다.
  if (loading) {
    return <div></div>;
  }

  // 에러가 있으면 에러 메시지를 표시합니다.
  if (error) {
    return <div>Error: {error.message}</div>;
  }

  const content = comments?.result;
  // 데이터가 로드되면, 해당 데이터를 사용하여 무언가를 렌더링합니다.
  if (!content) {
    // 데이터가 비어있으면 메시지를 표시합니다.
    return <div>해당 데이터가 없습니다. </div>;
  }

  return (
    <>
        {/* 후기 모달 */}
        <AnimatePresence>
        {showReviewModal && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}>
            <HelpReview onClose={() => setShowReviewModal(false)} setStatus={setStatus} to={nowWriterId} /> {/* HelpReview에 모달을 닫는 함수를 전달 */}
          </motion.div>
        )}
      </AnimatePresence>
      {/* 댓글 입력 폼 , 조건 충족시 나타남 */}
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0 }}
        style={{ width: "100%" }}
        className="d-flex justify-content-center"
      >
        <div className={style.helps}>
          <div className="col">
            <h4 style={{marginBottom: '20px',marginLeft: '10px',fontWeight:'bold'}}>댓글</h4>
          </div>
          {content.sameMember === false && content.adopted === false && (
            
            <div className="col-12  mb-3">
              <form
                className="d-flex justify-content-center gap-4"
                style={{ width: "100%" }}
                onSubmit={handleCommentSubmit}
              > 
                <input
                  type="text"
                  value={commentText}
                  onChange={handleCommentChange}
                  z
                  style={{ width: "85%", border:"1px solid #d7d7d7"}}
                  className="p-2 rounded-3"
                  placeholder="댓글을 입력하세요"
                />
                <button style={{ width: "13%" }} className={`btn btn-primary ${style.btn} ${style.writeComment}`}>
                  댓글 작성
                </button>
              </form>
            </div>
          )}

          {/* 댓글 리스트 */}
          <ListGroup>
            <div className="d-flex flex-column gap-3">
              {content.commentList.map((item, index) => (
                <ListGroup.Item className="rounded-4" key={index}>
                  <div className="d-flex p-2 row" style={{ display: "flex" }}>
                    <div className="col-2">
                      <div className="d-flex flex-column">
                        <img
                          src={item.writerImageUrl}
                          alt="Writer"
                          className={style.writerImage}
                        />
                        <span>
                          {item.writerName}
                        </span>
                        <div>
                          <span >
                            {formatDate(item.createDt)}
                          </span>
                        </div>
                      </div>
                    </div>
                    <div className="col-8">
                      <div>
                        <span className={style.commentBox}>
                          {item.comment}
                        </span>
                      </div>
                    </div>
                    <div className="col-2 d-flex align-items-center text-lg-end">
                      {content.sameMember && !content.adopted && (
                        <div className={`${style.buttonGroup}`}>
                          <button onClick={() => handleClick(item.writerId)}
                          className={`${style.btn} ${style.chat}`}
                          >
                            채팅하기
                          </button>
                          <button
                            onClick={() =>
                              handleAdopt(item.commentId, item.writerName, item.writerId)
                            }
                            className={`${style.chooseButton} ${style.btnEach} ${style.btn} ${style.selected}`}
                            
                          >
                            채택하기
                          </button>
                        </div>
                      )}
                    </div>
                  </div>
                </ListGroup.Item>
              ))}
            </div>
          </ListGroup>
          {content.commentList.length === 0 && (
            <div
              className="d-flex justify-content-center align-items-center"
              style={{ width: "100%", marginTop: '30px' }}
            >
              <img
                style={{ width: "3rem", height: "3rem" }}
                src="/free-animated-icon-note-6172546.gif"
              />
              <h2 className="m-0" >아직 댓글이 없습니다!</h2>
            </div>
          )}
        </div>
      </motion.div>
    </>
  );
}

export default HelpComments;
