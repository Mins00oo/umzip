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

const accessToken = 'eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRlc3QxMjM0Iiwicm9sZSI6IlVTRVIiLCJpZCI6MjYsInNpZ3VuZ3UiOjEsImlhdCI6MTcwNjY4OTk4NCwiZXhwIjoxNzA2NjkzNTg0fQ.otgMV2evMAXMymY3FV9HryUjVysTvMfvhygvxIIFyIg'

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
            destination: `/api/message/chat/${chatRoomId}`,
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
    $.post(`/api/message/chat/${chatRoomId}`, function() {
        // 채팅방 나가기 처리
        window.location.href = '/index.html';
    });

    $.ajax({
        url: `/api/message/chat/${chatRoomId}`,
        type: 'POST',
        contentType: 'application/json',
        headers: {
            'Authorization': `Bearer ${accessToken}` // Bearer 토큰을 헤더에 추가
        },
        success: function(response) {
            // 성공 시, 서버로부터 반환된 구독 경로로 구독 시작
            if(response.isSuccess) {
                window.location.href = '/index.html';
            } else {
                console.error('Chat room creation failed:', response.message);
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error('Chat room creation failed:', textStatus, errorThrown);
        }
    });
}
