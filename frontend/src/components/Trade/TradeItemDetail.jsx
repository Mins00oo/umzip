import { useSubmit, useNavigate } from 'react-router-dom';
import { useEffect, useRef, useState } from 'react';

import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import { Navigation, Pagination } from 'swiper/modules';
import BackButton from '../PublicUse/BackButton';

import classes from './TradeItemDetail.module.css';
import { AnimatePresence, motion } from 'framer-motion';
import useAuthStore from '../../store/store';
import { Client } from '@stomp/stompjs';
import { api } from '../../services/api';
function ReportModal({ onClose }) {
  return (
    <AnimatePresence>
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0 }}
        onClick={onClose}
        style={{
          zIndex: '99',
          position: 'fixed',
          top: 0,
          left: 0,
          width: '100%',
          height: '100%',
          backgroundColor: 'rgba(0, 0, 0, 0.5)', // 배경색 및 투명도 조절
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
        }}
      >
        <motion.div
          initial={{ opacity: 0, y: -20 }}
          animate={{ opacity: 1, y: 0 }}
          exit={{ opacity: 0, y: -20 }}
          onClick={(event) => {
            // 이벤트 전파를 막기 위한 코드
            event.stopPropagation();
          }}
          style={{
            position: 'relative',
            width: '40%',
            backgroundColor: 'white', // 내용의 배경색
            padding: '20px',
            borderRadius: '8px', // 내용의 모서리 둥글게
          }}
        >
          <span onClick={onClose}>&times;</span>
          <h2>신고하기</h2>
          <p>신고 사유를 작성해주세요.</p>
          <button onClick={onClose}>신고 제출</button>
        </motion.div>
      </motion.div>
    </AnimatePresence>
  );
}

function TradeItemDetail({ trade }) {
  // console.log(trade);
  const submit = useSubmit();

  const [chatRoom, setChatRoom] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const stompClientRef = useRef(null);
  const chatContainerRef = useRef();
  const [userId, setUserId] = useState(null);
  const [talkHistory, setTalkHistory] = useState([]);
  const [userinput, setUserInput] = useState('');
  const createTime = new Date(trade.createDt);
  const options = { timeZone: 'Asia/Seoul' };
  const createTimeKST = new Date(createTime.toLocaleString('en-US', options));
  const now = new Date();
  createTimeKST.setHours(createTimeKST.getHours() + 9);
  const diffTime = Math.abs(now - createTimeKST);
  const diffHours = Math.floor(diffTime / (1000 * 60 * 60));
  const diffMinutes = Math.floor(diffTime / (1000 * 60));

  let displayText = '';

  if (diffMinutes < 60) {
    displayText = `${diffMinutes}분 전`;
  } else if (diffHours < 24) {
    displayText = `${diffHours}시간 전`;
  } else {
    const yy = createTimeKST.getFullYear();
    const mm = String(createTimeKST.getMonth() + 1).padStart(2, '0');
    const dd = String(createTimeKST.getDate()).padStart(2, '0');

    displayText = `${yy}-${mm}-${dd}`;
  }

  const [modalShow, setModalShow] = useState(false);

  const showModal = () => {
    setModalShow(true);
  };
  const hideModal = () => setModalShow(false);

  function startDeleteHandler() {
    const proceed = window.confirm('Are you sure?');

    if (proceed) {
      submit(null, { method: 'delete' });
    }
  }

  let navigate = useNavigate();

  const handleClick = () => {
    navigate(`/myprofile/${trade.writerId}`);
  };
  const handleSale = async () => {
    try {
      const response = await api.post('/trade-items/completed-sales', {
        boardId: trade.boardId,
      });
      navigate(-1);
      console.log(response);
    } catch (error) {
      console.error('Error submitting form:', error);
    }
  };

  //========================
  const handleinput = (event) => {
    setUserInput(event.target.value);
  };
  const scrollToBottom = () => {
    // 스크롤 위치를 항상 맨 아래로 조절
    if (chatContainerRef.current) {
      chatContainerRef.current.scrollTop =
        chatContainerRef.current.scrollHeight;
    }
  };
  useEffect(() => {
    scrollToBottom();
  }, [isModalOpen]);

  const toggleModal = async ({ tradeId, memberId }) => {
    console.log(tradeId, memberId);
    setIsModalOpen(true);
    const res = await MakeRoom({ tradeId, memberId });
    const stompClient = socket(res);
    stompClientRef.current = stompClient;
    stompClient.onConnect(stompClient.activate());
  };
  const socket = (res) => {
    const { token } = useAuthStore.getState();
    console.log(res);
    const client = new Client({
      brokerURL: `wss://i10e108.p.ssafy.io/ws?accessToken=${token}`,
      // brokerURL: `ws://192.168.30.145:8080/ws?accessToken=${token}`,
      // 여기에 다른 설정도 추가할 수 있습니다.
      onConnect: (frame) => {
        console.log('Connected: ' + frame);

        client.subscribe(`/topic/user/${token}`, (message) => {
          setUserId((prev) => {
            const updatedHistory = message.body;
            // console.log(message.body);
            return updatedHistory;
          });
        });

        client.subscribe(`/topic/chatroom/${res}`, (message) => {
          console.log(res);
          console.log('Received message: ' + message.body);
          // console.log(talkHistory)
          showReceivedMessage(message.body);
        });
      },

      onStompError: (frame) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
      },
    });

    return client;
  };
  const showReceivedMessage = (message) => {
    try {
      console.log(message);

      const jsonData = JSON.parse(message);
      console.log(jsonData);

      setTalkHistory((prevTalkHistory) => {
        const updatedHistory = [...prevTalkHistory, jsonData];
        // console.log(updatedHistory);
        return updatedHistory;
      });
    } catch (error) {
      console.error('Error parsing received message:', error);
    }
  };

  const sendMessage = (tradeId) => {
    // userinput을 사용하도록 수정
    const { token } = useAuthStore.getState();
    if (userinput && stompClientRef.current.active) {
      console.log(tradeId, chatRoom);
      stompClientRef.current.publish({
        destination: `/app/chat/${chatRoom}`,
        body: JSON.stringify({
          content: userinput,
          type: 'TALK',
          tradeId: tradeId,
        }),
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
    } else {
      console.error('Message is empty or stomp client is not connected.');
    }
  };
  const MakeRoom = async ({ tradeId, memberId }) => {
    // console.log(companyId)
    try {
      const response = await api.post(
        `/chat/trade/${tradeId}/${memberId}`
        // 요청 바디를 올바른 위치에 추가
      );
      setChatRoom(response.data.result);
      // console.log(response.data.result);
      return response.data.result;
    } catch (error) {
      console.error(error);
    }
  };

  const stopSocketCommunication = () => {
    if (stompClientRef.current) {
      stompClientRef.current.deactivate();
      console.log('연결X');
    }
  };

  //=====================

  return (
    <>
      <AnimatePresence>
        {isModalOpen && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            onClick={() => {
              setIsModalOpen(false);
              setTalkHistory([]), setUserInput(''), stopSocketCommunication();
            }}
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
          >
            <div
              style={{
                display: 'flex',
                position: 'relative',
                width: '40%',
                height: '70%',
                backgroundColor: 'white',
                padding: '20px',
                borderRadius: '8px',
              }}
            >
              <div
                onClick={(e) => e.stopPropagation()}
                ref={chatContainerRef}
                style={{
                  width: '100%',
                  display: 'flex',
                  flexDirection: 'column',
                  overflowY: 'auto',
                }}
              >
                {userId &&
                  talkHistory &&
                  talkHistory.map((items, index) => (
                    <div className="d-flex  flex-column">
                      <div
                        className=""
                        style={{
                          alignSelf:
                            userId !== items.senderId
                              ? 'flex-start'
                              : 'flex-end',
                        }}
                      >
                        {userId !== items.senderId ? (
                          <div className="d-flex align-items-center gap-1 justify-content-center">
                            <img
                              src={items.senderProfileImage}
                              style={{ width: '2rem', height: '2rem' }}
                              className="rounded-pill"
                            />
                            <p className="m-0">{items.senderName}</p>
                          </div>
                        ) : (
                          <div className="d-flex align-items-center gap-1 justify-content-center">
                            <p className="m-0">{items.senderName}</p>
                            <img
                              src={items.senderProfileImage}
                              style={{ width: '2rem', height: '2rem' }}
                              className="rounded-pill"
                            />
                          </div>
                        )}
                      </div>
                      <div
                        key={index}
                        style={{
                          maxWidth: '70%',
                          margin: '5px',
                          padding: '10px',
                          borderRadius: '10px',
                          alignSelf:
                            userId !== items.senderId
                              ? 'flex-start'
                              : 'flex-end',
                          background:
                            userId !== items.senderId ? '#e6e6e6' : '#4caf50',

                          color: userId !== items.senderId ? '#000' : '#fff',
                        }}
                      >
                        {items.content}
                      </div>
                    </div>
                  ))}
                <div style={{ marginTop: 'auto' }}>
                  <form
                    className="d-flex justify-content-around"
                    onSubmit={(e) => {
                      e.preventDefault();
                      sendMessage(trade.boardId);
                      setUserInput('');
                    }}
                  >
                    <input
                      value={userinput}
                      className="col-10 border px-3 bg-white shadow-lg rounded-3"
                      type="text"
                      onChange={handleinput}
                    />
                    <button type="submit" className="btn btn-primary rounded-4">
                      <img src="/Paper_Plane.png" />
                    </button>
                  </form>
                </div>
              </div>
            </div>
          </motion.div>
        )}
      </AnimatePresence>
      <BackButton />
      <article className={classes.trade} style={{ marginTop: '4rem' }}>
        <Swiper
          modules={[Navigation, Pagination]}
          spaceBetween={20}
          slidesPerView={1}
          navigation
          pagination={{ clickable: false }} // clickable을 true로 설정하여 페이지네이션 사용
          style={{ width: '100%', height: '500px' }}
        >
          {trade.filePathList.map((img, index) => (
            <SwiperSlide key={index}>
              <img
                src={img}
                alt={`${trade.title} image ${index}`}
                style={{ width: '80%', height: '100%', objectFit: 'cover' }}
              />
            </SwiperSlide>
          ))}
        </Swiper>
        <div style={{ width: '83%', marginLeft: 'auto', marginRight: 'auto' }}>
          <div className={classes.writer}>
            <div className="d-flex gap-2">
              <motion.img
                onClick={handleClick}
                whileHover={{ y: -5, cursor: 'pointer' }}
                className="rounded-pill"
                style={{ width: '3rem', height: '3rem' }}
                src={trade.writerImageUrl}
                alt="랜덤 이미지"
              ></motion.img>
              <div className="text-start">
                <div className="m-0">{trade.writerName}</div>
                <div>{trade.writerAddress}</div>
              </div>
            </div>
            <div className="d-flex gap-2">
              <img
                src="/filled-star.png"
                alt="star"
                className={classes.star}
                style={{ marginTop: '3px', width: 'auto', height: '20px' }}
              />
              <small className="form-text text-dark">
                {trade.writerRating}
              </small>
            </div>
          </div>
        </div>
        <div className={classes.article}>
          <div className={classes.title}>{trade.title}</div>
          <div className={classes.detail}>
            <div className={classes.price}>
              {trade.price.toLocaleString('ko-KR')}원
            </div>
            <div className={classes.date}>{displayText}</div>
          </div>
          <div className={classes.direct}>
            {trade.direct ? '직거래' : '택배배송'}
          </div>
          <div className={classes.content}>
            {trade.content}
            <div className={classes.view}>조회수 : {trade.readCnt}</div>
          </div>
          <div className={classes.report}>
            {trade.writer ? (
              <div>
                {trade.codeSmallId === 301 ? (
                  <menu className={classes.sold}>
                    <button onClick={handleSale}>판매완료</button>
                  </menu>
                ) : null}
              </div>
            ) : (
              <menu className={classes.chat}>
                <button
                  onClick={() =>
                    toggleModal({
                      tradeId: trade.boardId,
                      memberId: trade.writerId,
                    })
                  }
                >
                  채팅
                </button>
              </menu>
            )}
          </div>
        </div>
      </article>
    </>
  );
}

export default TradeItemDetail;
