import { useEffect, useState } from "react";
import style from "./HelpWrite.module.css";
import useStore from "../../store/helpDetailData";
import usePointStore from '../../store/pointData'
import { useNavigate } from "react-router-dom";
import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation, Pagination } from "swiper/modules";
import { AnimatePresence, motion } from "framer-motion";

function HelpWrite() {
  const { pointLoad, pointDetail } = usePointStore()
  const storedUserInfo = JSON.parse(localStorage.getItem("userInfo") || sessionStorage.getItem("userInfo"))
  const { sendPostBulletin } = useStore();
  const navigate = useNavigate();
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  // 여러장의 사진을 보여주기 위한 state
  const [showImages, setShowImages] = useState([]);
  const [showNow, setShowNow] = useState([]);
  //  401: 도와줘요, 402: 도와줄게요, 403: 도와줬어요
  const [codeSmall, setCodeSmall] = useState(401);
  const [point, setPoint] = useState(50);

  // 이미지 미리보기를 위한 상대경로
  const handleAddImages = (e) => {
    const imageLists = e.target.files;
    if (imageLists.length + showImages.length > 10) {
      alert("이미지는 최대 10개까지 업로드 가능합니다.");
      return;
    }

    const imageUrlLists = [...showNow];
    for (let i = 0; i < imageLists.length; i++) {
      const imageUrl = URL.createObjectURL(imageLists[i]);
      imageUrlLists.push(imageUrl);
    }

    // 보여줄 이미지들 => URL
    setShowNow(imageUrlLists);
    setShowImages([...showImages, ...imageLists]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // 입력값이 있는지 확인
    if (!title) {
      alert("제목을 입력해주세요");
      return; // 함수를 여기서 종료하여 데이터 전송을 중지합니다.
    }

    if (!content) {
      alert("내용을 입력해주세요");
      return; // 함수를 여기서 종료하여 데이터 전송을 중지합니다.
    }

    // 서버로 보낼 묶음
    const boardHelp = {
      codeSmallId: codeSmall,
      title: title,
      content: content,
      point: point,
    };

    const Bulletin = new FormData();
    // 게시글 정보를 JSON 문자열로 변환하여 Bulletin 추가
    Bulletin.append(
      "boardHelp",
      new Blob([JSON.stringify(boardHelp)], { type: "application/json" })
    );

    // 이미지 파일 추가
    showImages.forEach((file) => {
      Bulletin.append("imageFileList", file);
    });
    console.log(showImages);

    // useStore의 sendPostBulletin 함수를 호출하여 FormData 전송
    try {
      await sendPostBulletin(Bulletin); // 비동기 호출
      navigate("/help"); // 성공 시 페이지 이동
    } catch (error) {
      console.error("게시글 등록 실패:", error);
      alert("게시글 등록에 실패했습니다.");
    }
  };

  // X버튼 클릭 시 이미지 삭제하는 함수
  const handleDeleteImage = (id) => {
    setShowImages(showImages.filter((_, index) => index !== id));
    setShowNow(showNow.filter((_, index) => index !== id));
    console.log(showImages);
    console.log(showNow);
  };
  // 페이지 로드하면서 포인트 정보 불러오기
  useEffect(() => {
    pointLoad(storedUserInfo.id);
  }, [])
  const nowPoint = pointDetail.result?.point


  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      exit={{ opacity: 0 }}
      transition={{ duration: 0.3 }}
      style={{ width: "100%", height: "100%", marginTop: "4rem" }}
      className=" d-flex align-items-center flex-column justify-content-cetner"
    >
      <div className="d-flex gap-2 " style={{ width: "60%" }}>
        <h2 className="d-flex flex-content-start">도움 글쓰기</h2>
        <img
          style={{ width: "2rem", height: "2rem" }}
          src="/free-animated-icon-pen-12747616.gif"
        />
      </div>
      <form
        className="d-flex flex-column justify-content-cetner gap-3"
        style={{ width: "60%" }}
        onSubmit={handleSubmit}
      >
        <div className="row">
          {/* 카테고리선택 */}
          <div className="col-6">
            <div
              className={`d-flex gap-3 align-items-center col-3 ${style.select} ${style.category}`}
            >
              <div className="d-flex gap-1">
                <input
                  type="radio"
                  id="needHelp"
                  name="codeSmall"
                  value={401}
                  checked={codeSmall === 401}
                  onChange={() => setCodeSmall(401)}
                />
                <label htmlFor="needHelp">도와줘요</label>
              </div>
              <div className="d-flex gap-1">
                <input
                  type="radio"
                  id="willHelp"
                  name="codeSmall"
                  value={402}
                  checked={codeSmall === 402}
                  onChange={() => setCodeSmall(402)}
                />
                <label htmlFor="willHelp">도와줄게요</label>
              </div>
            </div>
          </div>
          <div className="col-6">
            {/* 포인트 입력 */}
            <div
              className="d-flex justify-content-end align-items-center gap-2"
              style={{ height: "100%" }}
            >
              {/* 오른쪽으로 붙이고 세로에서 가운데로 정렬 */}
              <div className="d-flex flex-column align-items-center">
                <label htmlFor="pointRange">포인트 설정: {point}P</label>
                <input
                  type="range"
                  className={`${style.rangeCss}`}
                  id="pointRange"
                  min={50}
                  max={500 < nowPoint ? 500 : nowPoint}
                  step={50}
                  value={point}
                  style={{ width: '200px', accentColor: '#6ec8e0',border:0 ,backgroundColor: "white"}} 
                  onChange={(e) => setPoint(Number(e.target.value))}
                />
              </div>
            </div>
          </div>
        </div>

        <div className="d-flex justify-content-between">
          <div className="d-flex col-12 gap-2">
            <input
              className="p-1 rounded-3 border form-control"
              style={{ border: "solid 1px ", borderColor: "gray" }}
              id="title"
              type="text"
              value={title}
              placeholder="  제목"
              onChange={(e) => setTitle(e.target.value)}
            />
          </div>
        </div>

        {/* 제목 입력 */}

        {/* 콘텐트 입력 */}
        {/* <div>
          <label htmlFor="content">본문:</label>

          <textarea
            className='rounded-4 p-2 border form-control'
            style={{ width: "100%", height: "20rem" }}
            id="content"
            
            
          />
        </div> */}
        <div>
          <div className="form-floating">
            <textarea
              className="form-control"
              onChange={(e) => setContent(e.target.value)}
              value={content}
              placeholder="Leave a comment here"
              id="content"
              style={{ width: "100%", height: "20rem" }}
            ></textarea>
            <label htmlFor="content">본문</label>
          </div>
        </div>

        {/* 이미지 업로드 */}
        <div>
          <label htmlFor="imageUploa">이미지 업로드 :      </label>    
          <input onChange={handleAddImages} id="image" type="file" multiple />
        </div>
        <AnimatePresence>
          {showNow.length !== 0 && (
            <motion.div
              initial={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              exit={{ opacity: 0 }}
              transition={{ duration: 0.3 }}
            >
              <Swiper
                modules={[Navigation, Pagination]}
                spaceBetween={20}
                slidesPerView={3}
                navigation
                pagination={{ clickable: false }}
                style={{ width: "40rem", height: "12rem" }}
              >
                <AnimatePresence>
                  {showNow.map((image, id) => (
                    <SwiperSlide key={id}>
                      <div className="d-flex flex-column  justify-content-center align-items-center">
                        <motion.img
                          initial={{ opacity: 0 }}
                          animate={{ opacity: 1 }}
                          exit={{ opacity: 0 }}
                          transition={{ duration: 0.3 }}
                          src={image}
                          alt={`image-${id}`}
                          style={{
                            width: "10rem",
                            height: "10rem",
                            objectFit: "cover",
                          }}
                        />
                        <button
                          type="button"
                          className="btn btn-primary d-flex align-items-center"
                          onClick={() => handleDeleteImage(id)}
                        >
                          <p className="m-0" style={{ fontSize: "0.75rem" }}>
                            X
                          </p>
                        </button>
                      </div>
                    </SwiperSlide>
                  ))}
                </AnimatePresence>
              </Swiper>{" "}
            </motion.div>
          )}
        </AnimatePresence>

        {/* 보내기버튼 */}
        <div style={{ textAlign: 'right',marginBottom: '30px' }}>
          <button type="submit" className={`btn btn-primary mt-4 ${style.btnWrite}`}>
            글 등록
          </button>
        </div>
      </form>

      {/* 이미지 미리보기 툴 */}
    </motion.div>
  );
}

export default HelpWrite;
