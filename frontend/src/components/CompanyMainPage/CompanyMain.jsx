import { useEffect, useRef, useState } from 'react';
import { AnimatePresence, motion } from "framer-motion"
import useAuthStore from '../../store/store';
import DeliverReservation from "./DeliverReservation"
import CleanReservation from "./CleanReservation"
import { useNavigate } from 'react-router-dom';
import { Client } from "@stomp/stompjs";
import chatToCompanyStore from '../../store/chatToCompanyStore'

const CompanyMain = () => {
  // const [requestList, setrequestList] = useState("용달")
  const { makeChatRoom } = chatToCompanyStore()
  const [chatRoom, setChatRoom] = useState("")
    const scrollToBottom = () => {
        // 스크롤 위치를 항상 맨 아래로 조절
        if (chatContainerRef.current) {
            chatContainerRef.current.scrollTop = chatContainerRef.current.scrollHeight;
        }
    };
    const [userId, setUserId] = useState("")
    const [talkHistory, setTalkHistory] = useState([])
    const chatContainerRef = useRef();
    const [userinput, setuserinput] = useState("");
    const [openModal, setOpenModal] = useState(false)
    const stompClientRef = useRef(null);

    const handleinput = (event) => {
        setuserinput(event.target.value);
    };
    useEffect(() => {
        scrollToBottom();
    }, [openModal, talkHistory ]);

  const buttonVariants = {
    hover: {
      scale: 1.05,
      transition: {
        duration: 0.3,
      },
    },
  };
  const userData = JSON.parse(localStorage.getItem('userInfo') || sessionStorage.getItem('userInfo'))
  // console.log(userData)

  // 현재 role상태에 따라 버튼 활성화를 위한 변수
  const userRole = userData.roleList
  // console.log(userRole)
  const [roleBtn, setRoleBtn] = useState(userRole[0])
  const logout = useAuthStore((state) => state.logout);

  const navigate = useNavigate();
  const { authChange } = useAuthStore();


  const handleDeliveryClick = async (role, authNum) => {
    if (!userRole.includes(role)) {
      alert('사업자 등록을 해주세요!!');
      return; 
    }
    
    try {
      await authChange(authNum);
      // Then set role
      setRoleBtn(role);
    } catch (error) {
      console.error('오 이런...:', error);
    }
};


  const handleLogout = async (event) => {
    event.preventDefault();
    await logout(navigate)
  };

  const socket = (res) => {
    const { token } = useAuthStore.getState();
    console.log(res)
    const client = new Client({
      brokerURL: `wss://umzip.com/ws?accessToken=${token}`,
      // brokerURL: `ws://192.168.30.125:8080/ws?accessToken=${token}`,
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

        client.subscribe(`/topic/chatroom/${res}`, (message) => {
          console.log('Received message: ' + message.body);
          // console.log(talkHistory)
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

  const sendMessage = () => {
    // userinput을 사용하도록 수정
    const { token } = useAuthStore.getState();
    if (userinput && stompClientRef.current.active) {
      // console.log('메시지 보낸다');
      stompClientRef.current.publish({
        destination: `/app/chat/${chatRoom}`,
        body: JSON.stringify({
          content: userinput,
          type: 'TALK'
        }),
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
    } else {
      console.error('Message is empty or stomp client is not connected.');
    }
  };
  const stopSocketCommunication = () => {
    if (stompClientRef.current) {

      stompClientRef.current.deactivate();
      console.log("연결X")
    }
  };

  // 권한변경 함수 넣어주기
  const chatModal = async (res) => {
    setOpenModal(true)
    setChatRoom(res)
    console.log(res)
    const stompClient = socket(res);
    stompClientRef.current = stompClient;
    stompClient.onConnect(
        stompClient.activate()
    )
}
  return (
    <>
      <AnimatePresence>
        {openModal && (
          <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} onClick={() => { setOpenModal(false); setTalkHistory([]); setuserinput(''); stopSocketCommunication() }}
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
              width: '40%',
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
              >
                {userId && talkHistory && talkHistory.map((items, index) => (
                  <div key={index} className='d-flex  flex-column'>
                    <div className='' style={{ alignSelf: userId !== items.senderId ? "flex-start" : "flex-end", }}>
                      {userId !== items.senderId ? <div className='d-flex align-items-center gap-1 justify-content-center'><img src={items.senderProfileImage} style={{ width: "2rem", height: "2rem" }} className='rounded-pill' />
                        <p className='m-0'>{items.senderName}</p></div> : <div className='d-flex align-items-center gap-1 justify-content-center'><p className='m-0'>{items.senderName}</p><img src={items.senderProfileImage} style={{ width: "2rem", height: "2rem" }} className='rounded-pill' />
                      </div>}
                    </div>
                    <div
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
                    </div>
                  </div>
                ))}
                <div style={{ marginTop: "auto" }}>
                  <form className='d-flex justify-content-around' onSubmit={(e) => { e.preventDefault(); sendMessage(); setuserinput(''); }}>
                    <input value={userinput} className='col-10 border px-3 bg-white shadow-lg rounded-3' type='text' onChange={handleinput} />
                    <button type="submit" className='btn btn-primary rounded-4'><img src='./Paper_Plane.png' /></button>
                  </form>
                </div>

              </div>
            </div>

          </motion.div>
        )}
      </AnimatePresence>
      <div
        className="d-flex  justify-content-center align-items-center"
        style={{ height: "100vh", width: "100%" }}
      >
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          exit={{ opacity: 0 }}
          transition={{ duration: 0.5 }}
          className="col-10 mt-5"
        >
          <div className="col-12 px-3" style={{marginTop:"10rem"}}>
            <div className="row my-5" style={{ height: "50%"  }}>
              <div className="col-2 p-3 gap-3 d-flex flex-column align-items-center justify-content-center text-center border-dark-subtle border-end">
                <img
                  src={userData.profileImage}
                  alt="Profile"
                  style={{
                    maxWidth: "100px",
                    height: "auto",
                    borderRadius: "50%",
                    objectFit: "cover",
                  }}
                />
                {/* 좌측 컬럼 */}
                <h3 className="mt-5">{userData.name}님</h3>
                <h3>안녕하세요</h3>
                <button
                  type="button"
                  onClick={handleLogout}
                  style={{
                    color: "red",
                    border: "none",
                    background: "none",
                    padding: "0",
                    margin: "0",
                  }}
                >
                  로그아웃
                </button>
                <div
                  className="d-flex flex-column justify-content-center gap-5"
                  style={{ width: "11rem" }}
                >

                  <motion.button
                    type="button"
                    className={`btn btn-lg d-flex justify-content-center gap-4 align-items-center ${roleBtn === "DELIVER" ? "btn-primary" : "btn-secondary"
                      }`}
                    variants={buttonVariants}
                    whileHover="hover"
                    style={{ width: "10rem" }}
                    onClick={() => handleDeliveryClick("DELIVER", 1)}
                  >
                    <img
                      style={{ width: "2rem", height: "2rem" }}
                      src="/truck 1.png"
                      alt=""
                    />
                    <h5 className="m-0">용달</h5>
                  </motion.button>

                  <motion.button
                    type="button"
                    className={`btn btn-lg d-flex justify-content-center gap-4 align-items-center ${roleBtn === "CLEAN" ? "btn-primary" : "btn-secondary"
                      }`}
                    variants={buttonVariants}
                    style={{ width: "10rem" }}
                    whileHover="hover"
                    onClick={() => handleDeliveryClick("CLEAN", -1)}
                  >
                    <img
                      style={{ width: "2rem", height: "2rem" }}
                      src="/mop (2) 1.png"
                      alt=""
                    />
                    청소
                  </motion.button>

                  <motion.button
                    type="button"
                    className="btn btn-light btn-lg"
                    s
                    variants={buttonVariants}
                    whileHover="hover"
                    style={{ width: "10rem" }}
                  >
                    상담하기 <img src="/Wavy_Help.png" alt="" />
                  </motion.button>
                </div>
              </div>
              {roleBtn === "DELIVER" ? <DeliverReservation  chatModal={chatModal} /> : roleBtn === "CLEAN" ? <CleanReservation chatModal={chatModal} /> : null}
            </div>
          </div>
        </motion.div>
      </div>
    </>
  );
};

export default CompanyMain;
