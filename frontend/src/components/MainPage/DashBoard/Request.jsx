import React, { useEffect, useRef, useState } from 'react';
import DropDown from './dropdown';
import { AnimatePresence, motion } from "framer-motion";
import StatusChange from './Status_Change';
import { Client } from "@stomp/stompjs";
import useAuthStore from '../../../store/store';

export default function Requests({ setReviewId, setServiceId, setRequestId, Id, setOpenRecommendModal, setChoiceCompanyId, isAll, setRequestList, requestList, date, orderName, orderNumber, status, list }) {
    // console.log(list)
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
    }, [openModal, talkHistory]);

    const toggleModal = async (res) => {
        setOpenModal(true)
        setChatRoom(res)
        console.log(res)
        const stompClient = socket(res);
        stompClientRef.current = stompClient;
        stompClient.onConnect(
            stompClient.activate()
        )
    }
    const containerVariants = {
        visible: {
            opacity: 1, y: 0,
            transition: {
                staggerChildren: 0.1, // 자식 요소들에 대한 stagger 효과
            },
        },
        hidden: { opacity: 0, y: -20 },
        exit: { opacity: 0, y: 20 },
    };
    const [isDropdownOpen, setDropdownOpen] = useState(false);

    const toggleDropdown = () => {
        setDropdownOpen(!isDropdownOpen);
    };
    const socket = (res) => {
        const { token } = useAuthStore.getState();
        console.log(res)
        const client = new Client({
            brokerURL: `wss://i10e108.p.ssafy.io/ws?accessToken=${token}`,
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
                                    <div className='d-flex  flex-column'>
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
            <motion.div
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                exit={{ opacity: 0 }}
                className=' rounded- mx-5 p-2 d-flex justify-content-around text-center align-items-center position-relative'
                style={{
                    position: "relative",
                    border: '2px solid',
                    borderImage: 'linear-gradient(to left, #005bea, #ACB6E5)',
                    borderImageSlice: 2,
                    // 원하는 정도의 border-radius 값을 설정해보세요
                    minHeight: "6rem",
                }}>
                <h5 className="m-0 col-2">{date}</h5>
                <h5 className="m-0 col-2">{orderName}</h5>
                <h5 className="m-0 col-2">{orderNumber}</h5>
                <div className="m-0 col-2">
                    <StatusChange status={status} />
                </div>
                <div className=" rounded-pill p-1 position-absolute top-5 start-0 translate-middle bg-white " style={{borderBottom:"solid 3px #a1c4fd"}}>
                    {requestList ==="용달" ? <img className=' rounded-pill' style={{width:"2.75rem" ,height:"2.75rem"}} src='/free-icon-fast-delivery-6491506.png'/> :<img className=' rounded-pill' style={{width:"2.75rem" ,height:"2.75rem"}} src='/free-icon-cleaning-tools-2452217.png'/> }
                    
                </div>
                <div className="col-md-2 ">
                    <motion.button
                        type="button"
                        onClick={toggleDropdown}
                        whileHover={{ scale: 1.2 }}
                        style={{ border: 'none', background: 'none', padding: 0 }}
                        animate={{ rotate: isDropdownOpen ? 180 : 0 }} // 이미지가 180도 회전하도록 설정
                        transition={{ duration: 0.3 }}
                    >
                        <img src='/Polygon 3.png' alt='dropdown-icon' />
                    </motion.button>

                    <AnimatePresence>
                        {isDropdownOpen && (
                            <motion.div
                                className="position-absolute top-100 bg-white rounded-3 shadow p-2 d-flex flex-column gap-3"

                                style={{ zIndex: 1, width: '108%', left: "-4%" }}
                                initial="hidden"
                                animate="visible"
                                variants={containerVariants}
                            >
                                {list.map((data, index) => (
                                    <motion.div

                                        className='shadow rounded-3'
                                        style={{ backgroundColor: "white" }}
                                        key={data.companyName}
                                        variants={{ visible: { opacity: 1, y: 0 } }} initial={{ opacity: 0, y: -20 }}
                                        exit={{ opacity: 0, y: 20 }}
                                        whileHover={{ scale: 1.01 }}
                                        transition={{ duration: 0.3 }}
                                    >
                                        <DropDown
                                            reissuing={data.reissuing}
                                            setReviewId={setReviewId}
                                            setServiceId={setServiceId}
                                            memberId={data.memberId}
                                            setRequestId={setRequestId}
                                            Id={Id}
                                            setOpenRecommendModal={setOpenRecommendModal}
                                            setChoiceCompanyId={setChoiceCompanyId}
                                            isAll={isAll}
                                            setRequestList={setRequestList}
                                            mappingId={data.mappingId}
                                            setChatRoom={setChatRoom}
                                            companyId={data.companyId}
                                            toggleModal={toggleModal}
                                            requestList={requestList}
                                            companyName={data.companyName}
                                            price={data.price}
                                            text={data.detail}
                                            status={data.codeSmallId}
                                            img={data.imageUrl}
                                        />
                                    </motion.div>
                                ))}
                            </motion.div>
                        )}
                    </AnimatePresence>
                </div>
            </motion.div>  </>
    );

}
