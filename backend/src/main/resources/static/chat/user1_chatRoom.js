const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/ws'
});

$(function () {
    const chatRoomId = getChatRoomIdFromUrl();

    fetchMyMessages(chatRoomId);

    if (chatRoomId) {
        connectToChatRoom(chatRoomId);
    }

    // 메시지 전송 이벤트
    $("#messageForm").submit(function(event) {
        event.preventDefault();
        sendMessage(chatRoomId);
    });

    // 채팅방 나가기 버튼 클릭 이벤트
    $("#leaveChatRoom").clk(function() {
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

const accessToken = 'eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRlc3QxMjM0Iiwicm9sZSI6IlVTRVIiLCJpZCI6MjYsInNpZ3VuZ3UiOjEsImlhdCI6MTcwNjc2NjA1OCwiZXhwIjoxNzA3MTk4MDU4fQ.JxgiXOYDr_HdMgQQM_xl6qsAD3WPHmsGaJcJHEdxX74'

function fetchMyMessages(chatRoomId) {
    $.ajax({
        url: `/api/chat/rooms/${chatRoomId}`,
        type: "GET",
        headers: {
            'Authorization': `Bearer ${accessToken}`
        },
        success: function (response) {
            response.result.forEach(function (message) {
                displayMessage(message);
            });
        },
        error: function (error) {
            console.error("Failed to fetch messages", error);
        }
    });
}

const currentUserId = 26

function displayMessage(message) {
    const messageElement = $("<div>").addClass("message");
    const senderNameElement = $("<div>").addClass("sender-name").text(message.senderName);
    const contentElement = $("<div>").addClass("content").text(message.content);
    const timeElement = $("<div>").addClass("time").text(message.sendTime);
    const senderProfileImageElement = $("<img>").addClass("sender-profile-image").attr("src", message.senderProfileImage);

    // 현재 사용자의 메시지인지 확인하여 스타일 적용
    if (message.senderId.toString() === currentUserId.toString()) {
        messageElement.addClass("my-message");
    } else {
        messageElement.addClass("their-message");
    }

    messageElement.append(senderProfileImageElement, senderNameElement, contentElement, timeElement);
    $("#messages").append(messageElement);
}

function connectToChatRoom(chatRoomId) {
    // WebSocket 연결 및 채팅방 구독
    stompClient.activate();

    stompClient.onConnect = function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe(`/topic/chatroom/${chatRoomId}`, function (message) {
            console.log(message + "보내줄게~")
            console.log(message)
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

function showReceivedMessage(messageData) {
    const message = JSON.parse(messageData);
    console.log(message + "여기 보여줌")
    // 서버로부터 받은 메시지 데이터를 JSON 객체로 파싱

    const messageElement = $("<div>").addClass("message");
    const senderNameElement = $("<div>").addClass("sender-name").text(message.senderName);
    const contentElement = $("<div>").addClass("content").text(message.content);
    const timeElement = $("<div>").addClass("time").text(message.sendTime);
    const senderProfileImageElement = $("<img>").addClass("sender-profile-image").attr("src", message.senderProfileImage);

    console.log(message.senderId + " " + currentUserId.toString())
    // 현재 사용자의 메시지인지 확인하여 스타일 적용
    if (message.senderId === currentUserId.toString()) {
        messageElement.addClass("my-message");
    } else {
        messageElement.addClass("their-message");
    }
    // 프로필 이미지, 발신자 이름, 메시지 내용, 시간을 메시지 요소에 추가
    messageElement.append(senderProfileImageElement, senderNameElement, contentElement, timeElement);
    $("#messages").append(messageElement);
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
