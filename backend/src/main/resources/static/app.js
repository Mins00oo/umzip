const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/ws'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/chatroom/1', (greeting) => {
        console.log(greeting.body)
        showGreeting(greeting.body);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    console.log("connecting")
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    console.log($("#name").val())
    console.log("sending")
    stompClient.publish({
        destination: "/api/message/chat/1",
        body: JSON.stringify({'content': $("#name").val()})
    });
}

function showGreeting(message) {
    console.log("showing")
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());

    // 채팅방 생성 버튼 클릭 이벤트 처리
    $("#createChatRoom").click(() => {
        const receiverId = $("#receiverId").val();
        createAndSubscribeChatRoom(receiverId);
    });

    // 메시지 전송 버튼 클릭 이벤트 처리 (채팅방 ID 필요)
    $("#send").click(() => {
        const chatRoomId = 1;
        sendName(chatRoomId);
    });
});
const accessToken = 'eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRlc3QxMjM0Iiwicm9sZSI6IlVTRVIiLCJpZCI6MjYsInNpZ3VuZ3UiOjEsImlhdCI6MTcwNjY2MTU5NiwiZXhwIjoxNzA2NjY1MTk2fQ.HgOW__xZaNsT6gchMAe0GgGjlWxsbPwgZQsm0j8UFpo'

// 채팅방 생성 및 구독
function createAndSubscribeChatRoom(receiverId) {
    // 채팅방 생성 요청
    // receiverId는 2로 잠시 고정
    $.ajax({
        type: "POST",
        url: `/api/chat/user/2`,
        headers: {
            "Authorization" : `Bearer ${accessToken}`
        },
        success: function(response) {
            console.log(response)
            // 채팅방 생성 성공 시 구독
            subscribeChatRoom(response.result);
        },
        error: function(error) {
            console.error('Chat room creation failed:', error);
        }
    });
}

function subscribeChatRoom(subscriptionPath) {
    stompClient.subscribe(subscriptionPath, (message) => {
        console.log(message.body);
        showGreeting(message.body);
    });
    console.log(`Subscribed to chat room: ${subscriptionPath}`);
}