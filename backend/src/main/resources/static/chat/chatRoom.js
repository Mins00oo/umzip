const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/ws'
});

$(function () {
    const chatRoomId = getChatRoomIdFromUrl();

    if (chatRoomId) {
        connectToChatRoom(chatRoomId);
    }

    // 메시지 전송 이벤트
    $("#messageForm").submit(function(event) {
        event.preventDefault();
        sendMessage(chatRoomId);
    });

    // 채팅방 나가기 버튼 클릭 이벤트
    $("#leaveChatRoom").click(function() {
        leaveChatRoom(chatRoomId);
    });
});

$(document).ready(function () {
    // 페이지가 로드되면 채팅방 목록을 가져옵니다.
    const chatRoomId= getChatRoomIdFromUrl();

    connectToChatRoom(chatRoomId);

});

function getChatRoomIdFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('chatRoomId');
}

const accessToken = 'eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRlc3QxMjM0Iiwicm9sZSI6IlVTRVIiLCJpZCI6NSwic2lndW5ndSI6MSwiaWF0IjoxNzA2NzI0NDg0LCJleHAiOjE3MDcxNTY0ODR9.MJe1fypLv-W2mAgEHmoU5o5tgXL3ha_cRSp3CeXPGAk'

function connectToChatRoom(chatRoomId) {
    // WebSocket 연결 및 채팅방 구독
    stompClient.activate();

    stompClient.onConnect = function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe(`/topic/chatroom/${chatRoomId}`, function (message) {
            showReceivedMessage(message.body);
        });
    };

    stompClient.onStompError = function (frame) {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
    };
}

function sendMessage(chatRoomId) {
    console.log(chatRoomId)
    const messageContent = $("#messageInput").val();
    if(messageContent && stompClient.active) { // 메시지 내용이 있고, stompClient가 활성화 상태인지 확인
        console.log('메시지 보낸다')
        stompClient.publish({
            destination: `/app/chat/${chatRoomId}`,
            body: JSON.stringify({
                content: messageContent,
                type: 'TALK'
            }),
            headers: {
                'Authorization': `Bearer ${accessToken}` // 필요한 경우 헤더 추가
            }
        });
        $("#messageInput").val(""); // 입력 필드를 비웁니다.
    } else {
        console.error('Message is empty or stomp client is not connected.');
    }
}

function showReceivedMessage(message) {
    $("#messages").append(`<div>${message}</div>`);
}

function leaveChatRoom(chatRoomId) {
    console.log('채팅방 나간다')
    stompClient.publish({
        destination: `/app/chat/${chatRoomId}`,
        body: JSON.stringify({
            type: 'LEAVE'
        }),
        headers: {
            'Authorization': `Bearer ${accessToken}` // 필요한 경우 헤더 추가
        }
    });
    window.location.href = 'index.html';
}
