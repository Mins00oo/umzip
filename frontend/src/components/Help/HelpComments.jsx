import { useEffect,useState } from 'react';
import useStore from '../../store/helpDetailData';
import ListGroup from 'react-bootstrap/ListGroup';
import style from './HelpComments.module.css';

function HelpComments({toggleModal}) {
  const { loadComment, comments, loading, error, sendPostRequest, commentChoic } = useStore();
  const [commentText, setCommentText] = useState(''); // 댓글 텍스트 상태
  const formatTimeAgo = (dateString) => {
    const currentDate = new Date();
    const itemDate = new Date(dateString);
  
    const timeDifference = currentDate - itemDate;
    const hoursDifference = Math.floor(timeDifference / (1000 * 60 * 60));
  
    if (hoursDifference < 24) {
      // 24시간 이내의 경우 시간으로 표시
      const formattedTime = `${hoursDifference}시간 전`;
      return formattedTime;
    } else {
      // 24시간 이후의 경우 날짜로 표시
      const year = itemDate.getFullYear();
      const month = itemDate.getMonth() + 1; // 월은 0부터 시작하므로 +1
      const day = itemDate.getDate();
      const formattedDate = `${year}-${month < 10 ? '0' : ''}${month}-${day < 10 ? '0' : ''}${day}`;
      return formattedDate;
    }
  };
  useEffect(() => {
    loadComment();
  }, [loadComment]);

    const handleCommentChange = (e) => {
    setCommentText(e.target.value); // 입력 값으로 댓글 텍스트 상태 업데이트
  };

  const handleCommentSubmit = async (e) => {
    e.preventDefault()
    try {
      // 댓글 등록 요청을 보냅니다.
      await sendPostRequest(commentText);
      console.log(typeof(commentText))
      // 성공적으로 댓글이 등록되면 입력 필드를 초기화합니다.
      setCommentText('');
      // 댓글 리스트를 새로고침합니다.
      await loadComment();
    } catch (error) {
      // 에러 처리를 합니다.
      console.error('댓글 등록 실패:', error);
      alert('댓글을 등록하는 중 오류가 발생했습니다.');
    }
  };
  const handleClick=(id)=>{
    toggleModal(id)
  }
  const handleAdopt = async (writerId, writerName) => {
    const confirmAdop = window.confirm(`${writerName}님을 채택할까요?`);
    if (confirmAdop) {
    try {
      // 채택 요청을 보냅니다.
      await commentChoic(writerId);
      // 성공적으로 처리되면 댓글 리스트를 새로고침합니다.
      await loadComment();
    } catch (error) {
      // 에러 처리를 합니다.
      console.error('채택 처리 실패:', error);
      alert('채택 처리 중 오류가 발생했습니다.');
    }
  }
  };


    // 데이터 로딩 중이면 로딩 인디케이터를 표시합니다.
    if (loading) {
      return <div>Loading...</div>;
    }
  
    // 에러가 있으면 에러 메시지를 표시합니다.
    if (error) {
      return <div>Error: {error.message}</div>;
    }
    
    const content = comments?.result
    // 데이터가 로드되면, 해당 데이터를 사용하여 무언가를 렌더링합니다.
    if (!content) {
      // 데이터가 비어있으면 메시지를 표시합니다.
      return <div>No data found.</div>;
    }
  
  return (
    <>
    {/* 댓글 입력 폼 , 조건 충족시 나타남 */}
    <div style={{width:"100%"}} className='d-flex justify-content-center'>
      <div style={{width:"60%"}}>
    { content.sameMember === false && content.adopted === false && <div className="col-12">
      <form className='d-flex' onSubmit={handleCommentSubmit}>
        <input
          type="text"
          value={commentText}
          onChange={handleCommentChange}
          className={style.commentInput}
          placeholder="댓글을 입력하세요"
        />
        <button onClick={handleCommentSubmit} className={style.commentSubmit}>
          댓글 달기
        </button>
        </form>
      </div>}

      {/* 댓글 리스트 */}
      <ListGroup>
        {content.commentList.map((item) => (
          <ListGroup.Item className={style.listGrop} key={item.id}>
              <div className="d-flex gap-3">
                <div className='d-flex flex-column'>
              <img src={item.writerImageUrl} alt="Writer" className={style.writerImage} />
                <span className={style.headPoint}>{item.writerName}</span>
                </div>
                <div>
                <span className={style.headUserName}>{item.comment}</span>
                </div>
                <div style={{marginLeft:"auto"}}>
                <span className={style.headDate}>{formatTimeAgo(item.createDt)}</span>
                </div>
                {content.sameMember && !content.adopted && (
                <div className={style.buttonGroup}>
                  <button onClick={()=>handleClick(item.writerId)}>
                    채팅하기
                  </button>
                  <button onClick={() => handleAdopt(item.writerId, item.writerName)} className={style.chooseButton}>
                    채택하기
                  </button>
                </div>
              )}
              </div>
          </ListGroup.Item>
        ))}
        </ListGroup>
        </div>
        </div>
    </>
  );
}

export default HelpComments;

// // 기존코드 => 댓글 채택하기 시 오류 확인하고 변경
// import { useEffect,useState } from 'react';
// import useStore from '../../store/helpDetailData';
// import ListGroup from 'react-bootstrap/ListGroup';
// import style from './HelpComments.module.css';

// function HelpComments() {
//   const { loadComment, comments, loading, error, sendPostRequest, commentChoic } = useStore();
//   const [commentText, setCommentText] = useState(''); // 댓글 텍스트 상태

//   useEffect(() => {
//     loadComment();
//   }, [loadComment]);

//     const handleCommentChange = (e) => {
//     setCommentText(e.target.value); // 입력 값으로 댓글 텍스트 상태 업데이트
//   };

//   const handleCommentSubmit = async () => {
//     try {
//       // 댓글 등록 요청을 보냅니다.
//       await sendPostRequest(commentText);
//       console.log(typeof(commentText))
//       // 성공적으로 댓글이 등록되면 입력 필드를 초기화합니다.
//       setCommentText('');
//       // 댓글 리스트를 새로고침합니다.
//       await loadComment();
//     } catch (error) {
//       // 에러 처리를 합니다.
//       console.error('댓글 등록 실패:', error);
//       alert('댓글을 등록하는 중 오류가 발생했습니다.');
//     }
//   };

//   const handleChooseComment = async (writerId) => {
//     try {
//       // 채택 요청을 보냅니다.
//       await commentChoic(writerId);
//       // 성공적으로 처리되면 댓글 리스트를 새로고침합니다.
//       await loadComment();
//     } catch (error) {
//       // 에러 처리를 합니다.
//       console.error('채택 처리 실패:', error);
//       alert('채택 처리 중 오류가 발생했습니다.');
//     }
//   };


//     // 데이터 로딩 중이면 로딩 인디케이터를 표시합니다.
//     if (loading) {
//       return <div>Loading...</div>;
//     }
  
//     // 에러가 있으면 에러 메시지를 표시합니다.
//     if (error) {
//       return <div>Error: {error.message}</div>;
//     }
    
//     const content = comments?.result
//     // 데이터가 로드되면, 해당 데이터를 사용하여 무언가를 렌더링합니다.
//     if (!content) {
//       // 데이터가 비어있으면 메시지를 표시합니다.
//       return <div>No data found.</div>;
//     }

//   return (
//     <>
//     {/* 댓글 입력 폼 , 조건 충족시 나타남 */}
//     { content.sameMember === false && content.adopted === false && <div className={style.commentForm}>
//         <input
//           type="text"
//           value={commentText}
//           onChange={handleCommentChange}
//           className={style.commentInput}
//           placeholder="댓글을 입력하세요"
//         />
//         <button onClick={handleCommentSubmit} className={style.commentSubmit}>
//           댓글 달기
//         </button>
//       </div>}

//       {/* 댓글 리스트 */}
//       <ListGroup>
//         {content.commentList.map((item) => (
//           <ListGroup.Item className={style.listGrop} key={item.id}>
//               <div className={style.content}>
//               <img src={item.writerImageUrl} alt="Writer" className={style.writerImage} />
//                 <span className={style.headPoint}>{item.writerName}P</span>
//                 <span className={style.headDate}>{item.createDt}</span>
//                 <span className={style.headUserName}>{item.comment}</span>
//                 {content.sameMember && !content.adopted && (
//                 <div className={style.buttonGroup}>
//                   <button>
//                     채팅하기
//                   </button>
//                   <button onClick={() => handleChooseComment(item.writerId)} className={style.chooseButton}>
//                     채택하기
//                   </button>
//                 </div>
//               )}
//               </div>
//           </ListGroup.Item>
//         ))}
//         </ListGroup>
//     </>
//   );
// }

// export default HelpComments;