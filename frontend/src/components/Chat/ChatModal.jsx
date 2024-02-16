import React, { useEffect, useState } from 'react';
import { AnimatePresence, motion } from 'framer-motion';
import ChatModalList from './ChatModalList';
import styles from './ChatModal.module.css'; // Import the CSS module
import axios from 'axios';
import { api } from '../../services/api';



const ChatModal = ({toggleDropdown}) => {
  useEffect(() => {
    Chat_Call();
  }, []);
  const [openModal, setOpenModal] = useState()
  const [ChatList, setChatList] = useState()
  const Chat_Call = async () => {

    try {
      const response = await api.get('/chat/rooms', {

      });
      setChatList(response.data.result)
      console.log(response.data.result)
    } catch (error) {
      console.error(error);
    }
  };
  return (
    <AnimatePresence>
      <motion.div
        className={`${styles.chatModalContainer} ${styles.shadow} ${styles.chatModalContent}  shadow-lg p-2 rounded-4`}
        variants={{
          visible: { transition: { staggerChildren: 0.1 } }
        }}
      >
        <div className={`${styles.chatModalHeader} d-flex`}>
          <h3>Chat</h3>
        </div>
        {ChatList && ChatList.map((data) => (
          <motion.div
            key={data.chatRoomId}
            className={`${styles.chatListItem}`}
            variants={{
              hidden: { opacity: 0, y: 30 },
              visible: { opacity: 1, y: 0 }
            }}
            exit={{ opacity: 1, y: 30 }}
            transition={{ type: 'tween' }}
            onClick={() => setOpenModal(true)}
          >
            <ChatModalList
            toggleDropdown={toggleDropdown}
            setChatList={setChatList}
              unReadCount={data.unReadCount}
              img={data.receiverProfileImage}
              name={data.receiverName}
              chat={data.lastContent}
              date={data.updateDt}
              chatroomId={data.chatRoomId}
              receiverId={data.receiverId}
            />

          </motion.div>
        ))}

      </motion.div>
    </AnimatePresence>
  );
};

export default ChatModal;
