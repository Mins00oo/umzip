import { useParams } from "react-router-dom";
import HelpContent from '../../components/Help/HelpContent';
import HelpComments from '../../components/Help/HelpComments';
import Header from "../../components/Header";
import BackButton from "../../components/PublicUse/BackButton";
import { useEffect, useRef, useState } from "react";
import useAuthStore from "../../store/store";
import { Client } from "@stomp/stompjs";
import { AnimatePresence, motion } from "framer-motion";
import { api } from "../../services/api";
import Chat from "../../components/Chat/Chat";

export default function HelpDetail() {
    console.log('디테일페이지이동')
    // 받아온 아이디 디테일 페이지로 이동
    let { boardId } = useParams();
    boardId = parseInt(boardId);
    // console.log(boardId)
    const [chatRoom, setChatRoom] = useState(null)
    const [isModalOpen, setIsModalOpen] = useState(false)
    const stompClientRef = useRef(null);
    const chatContainerRef = useRef();
    const [userId, setUserId] = useState(null)
    const [talkHistory, setTalkHistory] = useState([])
    const [userinput, setUserInput] = useState("");
    // const [memberId,setMemberId] = useState(null)
    const handleinput = (event) => {
        setUserInput(event.target.value);
    };
    const scrollToBottom = () => {
        // 스크롤 위치를 항상 맨 아래로 조절
        if (chatContainerRef.current) {
            chatContainerRef.current.scrollTop = chatContainerRef.current.scrollHeight;
        }
    };
    useEffect(() => {
        scrollToBottom();
    }, [isModalOpen]);

    const handleModal = () => {
        setIsModalOpen((prev) => !prev)
    }
    const toggleModal = async (memberId) => {
        console.log(memberId)
        setIsModalOpen(true)
        const res = await MakeRoom( memberId )
        const stompClient = await socket(res);
        stompClientRef.current = stompClient;
        stompClient.onConnect(
            stompClient.activate()
        )
    }
    const socket = async(res) => {
        const { token } = useAuthStore.getState();
        console.log(res)
        const client = new Client({
            brokerURL: `wss://i10e108.p.ssafy.io/ws?accessToken=${token}`,
            // brokerURL: `ws://192.168.30.145:8080/ws?accessToken=${token}`,
            // 여기에 다른 설정도 추가할 수 있습니다.
            onConnect: (frame) => {
                console.log('Connected: ' + frame);

                client.subscribe(`/topic/user/${token}`, (message) => {

                    setUserId((prev) => {
                        const updatedHistory = message.body
                        // console.log(message.body);
                        return updatedHistory;
                    })
                });

                client.subscribe(`/topic/chatroom/${res}`, (message) => {
                    console.log(res)
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
            console.log(message)

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
            // console.log(tradeId, chatRoom)
            stompClientRef.current.publish({
                destination: `/app/chat/${chatRoom}`,
                body: JSON.stringify({
                    content: userinput,
                    type: 'TALK',
                }),
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
        } else {
            console.error('Message is empty or stomp client is not connected.');
        }
    };
    const MakeRoom = async ( memberId ) => {


        console.log(memberId)
        try {
            const response = await api.post(
                `/chat/users/${memberId}`,
                // 요청 바디를 올바른 위치에 추가
            );
            setChatRoom(response.data.result)
            // console.log(response.data.result);
            return response.data.result
        } catch (error) {
            console.error(error);
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
            <Header />
            <Chat/>
            <BackButton />
            <AnimatePresence>
                {(isModalOpen &&
                    <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} onClick={() => { setIsModalOpen(false); setTalkHistory([]), setUserInput(""), stopSocketCommunication() }}
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
                                    <form className='d-flex justify-content-around' onSubmit={(e) => { e.preventDefault(); sendMessage(); setUserInput(''); }}>
                                        <input value={userinput} className='col-10 border px-3 bg-white shadow-lg rounded-3' type='text' onChange={handleinput} />
                                        <button type="submit" className='btn btn-primary rounded-4'><img src='/Paper_Plane.png' /></button>
                                    </form>
                                </div>

                            </div>
                        </div>

                    </motion.div>
                )}
            </AnimatePresence>
            <HelpContent />
            <div>
                <HelpComments toggleModal={toggleModal} />
            </div>
        </>
    );
}