import { Link, useNavigate } from "react-router-dom";
import { useEffect } from "react";
import ListGroup from "react-bootstrap/ListGroup";
import style from "./HelpList.module.css";
import HelpSearchBar from "./HelpSearchBar";
import HelpCategorySelect from "./HelpCategorySelect";
import useStore from "../../store/helpData";
import usePointStore from '../../store/pointData'
import { motion } from "framer-motion";
import HelpPagination from "./HelpPagination";
import moment from 'moment-timezone';



function HelpList() {
  const { data, loading, error, fetchData, page } = useStore();
  const { pointLoad, pointDetail } = usePointStore()
  const storedUserInfo = JSON.parse(localStorage.getItem("userInfo") || sessionStorage.getItem("userInfo"))
  const navigate = useNavigate();

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


  function navigateHandler() {
    if (nowPoint < 50) { // 지금 포인트가 50보다 많은지 확인
      alert("최소 50포인트 이상 있어야 해요.");
      return; 
    }
    
    navigate("/helpwriting");
  }

  useEffect(() => {
    fetchData(); // 컴포넌트 마운트 시 데이터를 가져옵니다.
    pointLoad(storedUserInfo.id);
  }, [fetchData, page]); // fetchData가 변경될 때마다 호출됩니다.

  // 데이터 로딩 중이면 로딩 인디케이터를 표시합니다.
  if (loading) {
    return <div>Loading...</div>;
  }

  // 에러가 있으면 에러 메시지를 표시합니다.
  if (error) {
    return <div>Error: {error.message}</div>;
  }

  const content = data?.result?.content;
  const nowPoint = pointDetail.result?.point
  // 데이터가 로드되면, 해당 데이터를 사용하여 무언가를 렌더링합니다.
  if (!content) {
    // 데이터가 비어있으면 메시지를 표시합니다.
    return <div>No data found.</div>;
  }
  console.log(content)

  return (
    <motion.div
      initial={{ opacity: 0, y: 10 }}
      animate={{ opacity: 1, y: 0 }}
      exit={{ opacity: 0, y: 10 }}
      className={style.helps}
    >
      <div className={style.logoContainer} style={{marginTop:'50px'}}>
        <span className="d-flex align-items-center gap-1">
          <span className={style.logo}>도움</span>
          <span style={{ fontSize: "20px" }}>을 나눠요</span>
          <img
            style={{ width: "4rem", height: "auto", marginLeft: "15px" }}
            className=""
            src="/free-animated-icon-care-11688516.gif"
            alt=""
          />
        </span>
        <HelpSearchBar />
      </div>
      <div className="row" style={{ marginLeft: "0px" }}>
        <HelpCategorySelect />
      </div>

      <div className={style.head}>
        <span className={style.headType}>글 종류</span>
        <span className={style.headTitle}>제목(댓글)</span>
        <span className={style.headPoint}>포인트</span>
        <span className={style.headDate}>날짜</span>
        <span className={style.headUserName}>작성자</span>
        <span className={style.headView}>조회수</span>
      </div>
      <ul className={style.list}>
        <ListGroup>
          {content.map((helps) => (
            <ListGroup.Item className={style.listGrop} key={helps.id}>
              <Link
                style={{ textDecoration: "none", color: "black" }}
                to={`/helpdetail/${helps.id}`}
              >
                <motion.div
                  whileHover={{
                    fontWeight: "bold",
                    textDecoration: "underline",
                  }}
                  className={style.content}
                >
                  {helps.codeSmallId === 401 && (
                    <span style={{ color: "#f06565", fontWeight: 'bold' }} className={style.headType}>
                      도와주세요
                    </span>
                  )}
                  {helps.codeSmallId === 402 && (
                    <span
                      style={{ color: "#4A3AFF", fontWeight: 'bold'  }}
                      className={style.headType}
                    >
                      도와줄게요
                    </span>
                  )}
                  {helps.codeSmallId === 403 && (
                    <span style={{ color: "gray", fontWeight: 'bold'  }} className={style.headType}>
                      도움 받았어요
                    </span>
                  )}
                  <span className={style.headTitle}>
                    {helps.title}
                    {`(${helps.commentCnt})`}
                  </span>
                  <span className={style.headPoint}>{helps.rewardPoint}P</span>
                  <span className={style.headDate}>
          {formatDate(helps.createDt)}
        </span>
                  <span className={style.headUserName}>{helps.writerName}</span>
                  <span className={style.headView}>{helps.readCnt}</span>
                </motion.div>
              </Link>
            </ListGroup.Item>
          ))}
          {content.length === 0 && (
            <div
              className="mt-5 gap-3 d-flex justify-content-center align-items-center"
              style={{ width: "100%" }}
            >
              <img
                style={{ width: "3rem", height: "3rem" }}
                src="/free-animated-icon-note-6172546.gif"
              />
              <h4 className="m-0">아직 글이 없습니다!</h4>
            </div>
          )}
        </ListGroup>
      </ul>
      <div className="d-flex justify-content-end">
        <button
          className="btn btn-primary"
          style={{ backgroundColor: "#78cad5",
                    border: 0,
         }}
          onClick={navigateHandler}
        >
          글쓰기
        </button>
      </div>

      <div
        className="m-4 d-flex justify-content-center"
        style={{ width: "100%" }}
      >
        <HelpPagination />
      </div>
    </motion.div>
  );
}
export default HelpList;
