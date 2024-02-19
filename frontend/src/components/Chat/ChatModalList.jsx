import React, { useEffect, useRef, useState } from 'react';
import styles from './ChatModalList.module.css';
import { AnimatePresence, motion } from "framer-motion"
import axios from 'axios';
import { Client } from "@stomp/stompjs";
import { api } from '../../services/api';
import useAuthStore from '../../store/store';
import { useNavigate } from 'react-router-dom';
import UsedReview from '../MainPage/DashBoard/UsedReview';



export default function ChatModalList({ toggleDropdown, setChatList, name, chat, date, img, chatroomId, receiverId, unReadCount }) {
  const [openModal, setOpenModal] = useState(false);
  const [talkHistory, setTalkHistory] = useState([]);
  const chatContainerRef = useRef();
  const [userinput, setuserinput] = useState("");
  const [unReadChat, setunReadChat] = useState(unReadCount)
  const stompClientRef = useRef(null);
  const [userId, setUserId] = useState(null)
  const [tradeChat, setTradeChat] = useState(null)
  const navigate = useNavigate()
  const [status, setStatus] = useState("first")
  const [tradeId, setTradeId] = useState(null)
  const [userinfo,setUserInfo] = useState(null)
  const scrollToBottom = () => {
    // 스크롤 위치를 항상 맨 아래로 조절
    if (chatContainerRef.current) {
      chatContainerRef.current.scrollTop = chatContainerRef.current.scrollHeight;
    }
  };
  const truncatedTitle = chat.length > 9
  ? chat.slice(0, 9) + '...'
  : chat
  useEffect(() => {
    scrollToBottom();
  }, [talkHistory, openModal]);
  useEffect(() => {
    const storedUserInfo = localStorage.getItem("userInfo") || sessionStorage.getItem("userInfo");
    if (storedUserInfo) {
        const parsedInfo = JSON.parse(storedUserInfo);
        console.log(parsedInfo);
        setUserInfo({ name: parsedInfo.name, profileImage: parsedInfo.profileImage, id: parsedInfo.id });
    }
    else {
        navigate("/login")
    }
}, []);

  const handleinput = (event) => {
    setuserinput(event.target.value);
  };

  const toggleModal = async () => {
    setOpenModal(true);

    setunReadChat(0)
    // 모달이 열릴 때만 대화 내용을 불러옴
    if (!talkHistory.length) {

      const stompClient = socket();
      console.log(stompClient)
      const talk = await axios_detailChat();
      setTalkHistory(talk)
      stompClientRef.current = stompClient;
      // stompClient.onConnect(stompClient.activate());
      // console.log(stompClient)

      stompClient.onConnect(
        stompClient.activate()
      )
      // stompClient.activate() 호출 후에 구독을 시도
      // stompClient.activate().then(() => {

      // });
    }
  };

  const socket = () => {
    const { token } = useAuthStore.getState();
    const client = new Client({
      brokerURL: `wss://umzip.com/ws?accessToken=${token}`,
      // brokerURL: `ws://192.168.30.145:8080/ws?accessToken=${token}`,

      // 여기에 다른 설정도 추가할 수 있습니다.
      onConnect: (frame) => {
        console.log('Connected: ' + frame);

        client.subscribe(`/topic/user/${token}`, (message) => {
          console.log(message.body)

          setUserId((prev) => {
            const updatedHistory = message.body
            // console.log(updatedHistory);
            return updatedHistory;
          })
        });

        client.subscribe(`/topic/chatroom/${chatroomId}`, (message) => {
          console.log('Received message: ' + message.body);
          showReceivedMessage(message.body);
        });
      },

      onStompError: (frame) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
      }
    });

    return client;
  };




  const axios_detailChat = async () => {


    try {
      const response = await api.get(`/chat/rooms/${chatroomId}`, {
      });
      console.log(response.data.result)
      setTradeChat(response.data.result.tradeItem)
      return response.data.result.chatMessages
        // console.log(response.data.result)
        // console.dir(stompClient.subscribe)
        ;
    } catch (error) {
      console.error(error);
    }
  };

  const showReceivedMessage = (message) => {
    try {
      // console.log(message)

      const jsonData = JSON.parse(message);
      console.log(jsonData);

      setTalkHistory((prevTalkHistory) => {
        const updatedHistory = [...prevTalkHistory, jsonData];
        // console.log(updatedHistory);
        return updatedHistory;

      })

    } catch (error) {
      console.error('Error parsing received message:', error);
    }
  };
  const stopSocketCommunication = () => {
    if (stompClientRef.current) {

      stompClientRef.current.deactivate();
      console.log("연결X")
    }
   
  };
  const LeaveChat = () => {
    const { token } = useAuthStore.getState();

    const shouldLeave = window.confirm('정말로 채팅을 나가시겠습니까?');

    if (shouldLeave) {
      if (stompClientRef.current.active) {
        console.log('채팅창 나가기');
        stompClientRef.current.publish({
          destination: `/app/chat/${chatroomId}`,
          body: JSON.stringify({
            content: "asdf",
            type: 'LEAVE'
          }),
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
        setuserinput(""); // 입력 필드를 비웁니다.
        stopSocketCommunication();
        setOpenModal(false);
        setTalkHistory([]);
        toggleDropdown()
        Chat_Call()
      } else {
        console.error('못나갔음 ;;');
      }
    }
  };
  const Chat_Call = async () => {

    try {
      const response = await api.get('/chat/rooms', {

      });
      setChatList(response.data.result)
    } catch (error) {
      console.error(error);
    }
  };
  const sendMessage = () => {
    // userinput을 사용하도록 수정
    const { token } = useAuthStore.getState();
    if (userinput && stompClientRef.current.active) {
      console.log('메시지 보낸다');
      stompClientRef.current.publish({
        destination: `/app/chat/${chatroomId}`,
        body: JSON.stringify({
          content: userinput,
          type: 'TALK'
        }),
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      setuserinput(""); // 입력 필드를 비웁니다.

    } else {
      console.error('Message is empty or stomp client is not connected.');
    }
  };
  const confirmBuy = async () => {


    try {
      console.log(tradeChat.tradeId)
      const response = await api.post(`/trade-items/completed-buys`, {
        boardId: tradeChat.tradeId
      });
      console.log(response)
      // setTradeChat(response.data.result.tradeItem)
      return response.data.isSuccess
        // console.log(response.data.result)
        // console.dir(stompClient.subscribe)
        ;
    } catch (error) {
      console.error(error);
    }
  };
  const handleBuy = async (e) => {
    e.stopPropagation()
    const userConfirmed = window.confirm("구매를 확정하시겠습니까?");
    if(userConfirmed){
      const answer = await confirmBuy()
      setTradeId(tradeChat.memberId)
      if(answer){
        stopSocketCommunication(), setOpenModal(false); setTalkHistory([]); setuserinput("")
  
        setStatus("second")
      }
      else{
        alert("이미 구매완료된 중고글 입니다!")
      }
    }
    
    // setOpenModal(false)
  }
  return (
    <>
      <AnimatePresence>
        {openModal && status === "first" && (
          <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} onClick={() => { stopSocketCommunication(), setOpenModal(false); setTalkHistory([]); setuserinput("") }}
            style={{
              zIndex: "99",
              position: 'fixed',
              top: 0,
              left: 0,
              width: '100%',
              height: '100%',
              backgroundColor: 'rgba(0, 0, 0, 0.5)',
              display: 'flex',
              justifyContent: 'center',
              alignItems: 'center',
            }}>
            <div style={{
              display: "flex",
              position: 'relative',
              width: '50rem',
              height: "70%",
              backgroundColor: 'white',
              padding: '20px',
              borderRadius: '8px',
            }}>
              <div
                onClick={(e) => e.stopPropagation()}
                ref={chatContainerRef}
                style={{
                  width: "100%",
                  display: "flex",
                  flexDirection: "column",
                  overflowY: "auto"
                }}
              >{tradeChat && <motion.div onClick={() => navigate(`/trade/${tradeChat.tradeId}`)} whileHover={{ y: -5, cursor: "pointer", backgroundColor: "#EAEBEE" }} className='d-flex gap-2 p-2 rounded-3 ' style={{ position: "fixed", top: "", width: "48rem", height: "6rem", backgroundColor: "white" }}>
                <img style={{ width: "5rem", height: "5rem" }} src={tradeChat.tradeItemImage} />
                <div className='d-flex flex-column gap-3' style={{ width: "100%" }}>
                  <div className='d-flex  justify-content-between'>
                    <h5 className='m-0 '>제목 : {tradeChat.tradeItemTitle}</h5>
                    {tradeChat.memberId !==userinfo.id && <button onClick={handleBuy} className='btn btn-light'>구매확정</button>}
                    
                  </div>
                  <h5 className='m-0 '>가격 : {tradeChat.tradeItemPrice}원</h5>
                  {/* <p className='m-0 '>제목 : {tradeChat.tradeItemTitle}</p> */}
                </div>
              </motion.div>}

                <div style={{ marginTop: tradeChat === null ? "0" : "6rem" }}>
                  {userId && talkHistory && talkHistory.map((items, index) => (
                    <motion.div initial={{ opacity: 0, y: 5 }}
                      animate={{ opacity: 1, y: 0 }}
                      exit={{ opacity: 0, y: 5 }} className='d-flex  flex-column'>
                      <div className='' style={{ alignSelf: userId !== items.senderId ? "flex-start" : "flex-end", }}>
                        {userId !== items.senderId ? <div className='d-flex align-items-center gap-1 justify-content-center'><img src={items.senderProfileImage} style={{ width: "2rem", height: "2rem" }} className='rounded-pill' />
                          <p className='m-0'>{items.senderName}</p></div> : <div className='d-flex align-items-center gap-1 justify-content-center'><p className='m-0'>{items.senderName}</p><img src={items.senderProfileImage} style={{ width: "2rem", height: "2rem" }} className='rounded-pill' />
                        </div>}
                      </div>
                      <motion.div

                        key={index}
                        style={{

                          maxWidth: "70%",
                          margin: "5px",
                          padding: "10px",
                          borderRadius: "10px",
                          alignSelf: userId !== items.senderId ? "flex-start" : "flex-end",
                          background: userId !== items.senderId ? "#e6e6e6" : "#4caf50",

                          color: userId !== items.senderId ? "#000" : "#fff",
                        }}
                      >
                        {items.content}
                      </motion.div>
                    </motion.div>
                  ))}
                </div>
                <div style={{ marginTop: "auto" }}>
                  <form className='d-flex justify-content-around' onSubmit={(e) => { e.preventDefault(); sendMessage(); setuserinput(''); }}>
                    <input value={userinput} className='col-10 border px-3 bg-white shadow-lg rounded-3' type='text' onChange={handleinput} />
                    <button type="submit" className='btn btn-primary rounded-4'><img style={{ width: "1.5rem", height: "1.5rem" }} src='/Paper_Plane.png' /></button>
                    <button onClick={LeaveChat} className='btn btn-danger rounded-4'><img style={{ width: "1.5rem", height: "1.5rem" }} src='/message.png' /></button>
                  </form>
                </div>

              </div>
            </div>

          </motion.div>
        )}
      </AnimatePresence>

      <motion.button onClick={toggleModal} className={styles['chat-button']}>
        <div className='d-flex flex-row p-2 justify-content-between m-1'>
          <div className='d-flex gap-2'>
            <img
              style={{ width: "3rem", height: "3rem" }}
              src={img}
              alt='랜덤 이미지'
            ></img>
            <div className='text-start'>
              <p className='m-0'>{name}</p>
              <p className='m-0'>{truncatedTitle}</p>
            </div>
          </div>
          <div className='d-= position-relative p-2'>
            <small className="form-text text-muted">{date}</small>
            {unReadChat !== 0 ? <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
              {unReadChat}
            </span> : null}

          </div>
        </div>
      </motion.button>
      {status === "second" && <UsedReview setStatus={setStatus} tradeId={tradeId} setTradeId={setTradeId} />}
    </>
  );
}