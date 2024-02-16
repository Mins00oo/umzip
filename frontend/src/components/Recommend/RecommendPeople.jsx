import { useEffect, useRef, useState } from "react";
import { AnimatePresence, motion } from "framer-motion";
import StarRating from "./StarRating";
import RecommendModal from "./RecommendModal";
import { Client } from "@stomp/stompjs";
import useAuthStore from "../../store/store";
import { api } from "../../services/api";
export default function RecommendPeople({ experience, status, memberId, userChoice, setUserChoice, companyId, name, rating, tag, img }) {

    const [imageSrc, setImageSrc] = useState(null);
    if (imageSrc === null) {
        if (status === "용달") { setImageSrc("/truck.png") }
        else { setImageSrc("/clean.png") }
    }

    // else { const [imageSrc, setImageSrc] = useState("/clean.png"); }
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [openModal, setOpenModal] = useState(false) // 챗팅모달
    const [chatRoom, setChatRoom] = useState("")
    const [userinput, setUserInput] = useState("")
    const [talkHistory, setTalkHistory] = useState([])
    const stompClientRef = useRef(null);
    const chatContainerRef = useRef();
    const [userId, setUserId] = useState("")
    const handleinput = (event) => {
        setUserInput(event.target.value);
    };
    const experienceDate = new Date(experience);

    // 현재 날짜를 가져오기
    const currentDate = new Date();

    // 경과 년수 계산
    const yearsOfExperience = currentDate.getFullYear() - experienceDate.getFullYear();

    // 경력에 따른 상 결정
    let imgsrc = "";
    if (yearsOfExperience >= 10) {
        imgsrc = "/free-animated-icon-validation-14183494.gif";
    } else if (yearsOfExperience >= 5) {
        imgsrc = "/free-animated-icon-verified-7920876.gif";
    }
    // console.log(imgsrc)
    const scrollToBottom = () => {
        // 스크롤 위치를 항상 맨 아래로 조절
        if (chatContainerRef.current) {
            chatContainerRef.current.scrollTop = chatContainerRef.current.scrollHeight;
        }
    };
    useEffect(() => {
        scrollToBottom();
    }, [openModal]);
    const handleModal = () => {
        setIsModalOpen((prev) => !prev)
    }
    const toggleModal = async () => {
        setOpenModal(true)
        const res = await MakeRoom()
        const stompClient = socket(res);
        stompClientRef.current = stompClient;
        stompClient.onConnect(
            stompClient.activate()
        )
    }
    const socket = (res) => {
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
            console.log(chatRoom)
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
    const MakeRoom = async () => {

        let service = "";
        if (status === "용달") {
            service = "delivery";
        } else {
            service = "clean";
        }
        // console.log(companyId)
        try {
            const response = await api.post(
                `/chat/${service}/${memberId}`,
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




    const handleClick = (memberId) => {
        if (status === "용달") {
            setImageSrc((prevSrc) =>
                prevSrc === "/truck.png" ? "/checked_truck.png" : "/truck.png"
            );
        } else {
            setImageSrc((prevSrc) =>
                prevSrc === "/clean.png" ? "/checked_clean.png" : "/clean.png"
            );
        }

        setUserChoice((prevChoices) => {
            // 이미 선택된 기업인지 확인
            const isCompanySelected = prevChoices.some(
                (choice) => choice.memberId === memberId
            );

            if (isCompanySelected) {
                // 이미 선택된 기업이면 제거
                return prevChoices.filter((choice) => choice.memberId !== memberId);
            } else {
                // 선택되지 않은 기업이면 추가
                return [...prevChoices, { memberId: memberId }];
            }

        });
    };

    return (
        <>
            <RecommendModal companyId={companyId} isOpen={isModalOpen} closeModal={handleModal} />
            <AnimatePresence>
                {openModal && (
                    <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} onClick={() => { setOpenModal(false); setTalkHistory([]), setUserInput(""), stopSocketCommunication() }}
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
            <motion.div
                style={{ backgroundColor: "#F4F9FF", width: "100%" }}
                className="rounded-4 d-flex  p-3 shadow postion-relative"
                initial={{ opacity: 0 }} // 초기 상태
                animate={{ opacity: 1 }} // 애니메이션 상태
                transition={{ duration: 0.5 }} // 트랜지션 지속 시간
            >
                <div className="d-flex flex-column justify-content-center  align-items-center gap-2 col-md-2">
                    <motion.img
                        className="rounded-pill shadow"
                        src={img}
                        style={{ height: "6rem", width: "6rem" }}
                        alt="Random Image"
                        whileHover={{ scale: 1.1, cursor: "pointer" }}
                        initial={{ opacity: 0 }} // 초기 상태
                        animate={{ opacity: 1 }} // 애니메이션 상태
                        transition={{ duration: 0.5 }} // 트랜지션 지속 시간
                        onClick={handleModal}
                    />
                    <button onClick={toggleModal} type="button" className="btn btn-outline-primary">
                        1대1채팅
                    </button>
                </div>
                <div className="d-flex col-md-8 flex-column justify-content-center gap-3">
                    <div className="gap-3 d-flex justify-content-center ">
                    <div className="m-0  col-md-3 fw-bold d-flex align-items-center"> {imgsrc === "" ? null : <img className="rounded-pill" src={imgsrc} style={{ width: "2.5rem", height: "2.5rem" }} />}<h5 className="m-0">{name}</h5></div>

                        <div className=" col-md-3 d-flex flex-column justify-content-center align-items-center">
                            <StarRating rating={rating} />
                            <p className="fw-bold m-0">{rating}점</p>
                        </div>
                        <h1 className=" col-md-3"></h1>
                    </div>
                    <div className="gap-3 d-flex justify-content-center">
                        {tag && tag.slice(0, 3).map((item, index) => (
                            <div key={index} className="border border-primary rounded-5 d-flex align-items-center justify-content-center p-2 bg-white col-3 text-center">
                                <p className="m-0" style={{fontSize:"0.8rem"}}>{item}</p>
                            </div>
                        ))}


                    </div>
                </div>
                <AnimatePresence>
                    <motion.div
                        key={imageSrc}
                        className="d-flex col-md-2 p-2"
                        onClick={() => handleClick(memberId)}
                        style={{ cursor: "pointer" }}
                        whileHover={{ scale: 1.05 }}
                    >
                        <motion.img
                            src={imageSrc}
                            style={{ height: "100%", width: "100%" }}
                            alt="Truck Image"
                            initial={{ opacity: 0 }} // 초기 상태
                            animate={{ opacity: 1 }} // 애니메이션 상태
                            transition={{ duration: 0.5 }} // 트랜지션 지속 시간
                        />
                    </motion.div>

                </AnimatePresence>
            </motion.div>

        </>
    );
}
