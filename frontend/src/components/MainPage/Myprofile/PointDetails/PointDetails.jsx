import { useEffect } from "react";
import ListGroup from "react-bootstrap/ListGroup";
import style from "./PointDetails.module.css";
import useStorePoint from "../../../../store/pointData";

function PointDetails() {
  const { fetchData, data, pointLoad, pointDetail } = useStorePoint();
  const storedUserInfo = JSON.parse(
    localStorage.getItem("userInfo") || sessionStorage.getItem("userInfo")
  );

  useEffect(() => {
    fetchData();
    pointLoad(storedUserInfo.id);
  }, [fetchData]);

  const points = data?.result?.pointUsageDtoList ?? [];
  console.log(points);
  const nowPoint = pointDetail.result?.point.toLocaleString();

  return (
    <>
      <div className={style.pointsContainer}>
        <div className="row" style={{ marginBottom: "10px" }}>
          <div className="col-3"></div>
          <div className="col-1 d-flex justify-content-center align-items-center">
            <img src="/starIcon.gif" style={{ width: "100px" }} />
          </div>

          <div className="col-4 d-flex justify-content-center align-items-center">
            <h2 className="text-center">포인트 적립 및 소비 내역</h2>
          </div>

          <div className="col-1 d-flex justify-content-center align-items-center">
            <img src="/starIcon.gif" style={{ width: "100px" }} />
          </div>
          <div className="col-3"></div>
          
        </div>
        
        <div className={`row ${style.main}`}>
            <div className={style.tooltipContainer}>
              <span className={style.tooltip}>현재 포인트</span>
              <span className={style.text}>{nowPoint}P</span>
              <span>
                {nowPoint}P
              </span>
          </div>
        </div>
        <div className={style.head}>
          <span className={style.headDate}>일자</span>
          <span className={style.headContent}>내용</span>
          <span className={style.headPoint}>포인트</span>
        </div>
        <ListGroup>
          {points.map((pointData, index) => (
            <ListGroup.Item className={style.listContainer} key={index}>
              <span className={style.headDate}>{pointData.date}</span>
              <span className={style.headContent}>{pointData.message}</span>
              <span
                className={style.headPoint}
                style={{
                  color: pointData.change < 0 ? "red" : "green",
                  fontWeight: "900",
                }}
              >
                {" "}
                {pointData.change.toLocaleString()}
              </span>
            </ListGroup.Item>
          ))}
        </ListGroup>
        {/* <nav>
        <ul className={style.pagination}>
          {pageNumbers.map(number => (
            <li key={number} className={style.pageItem}>
              <button type="button" onClick={() => paginate(number)} className={style.pageLink}>
                {number}
              </button>
            </li>
          ))}
        </ul>
      </nav> */}
      </div>
    </>
  );
}

export default PointDetails;
