const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/ws'
});

$(document).ready(function () {
    // 페이지가 로드되면 채팅방 목록을 가져옵니다.
    fetchChatRooms();

});

$(function () {
    // 채팅방 생성 버튼 클릭 이벤트
    $("#createChatRoom").click(function() {
        const receiverId = 8
        createChatRoom(receiverId);
    });
});

const accessToken = 'eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRlc3QxMjM0Iiwicm9sZSI6IlVTRVIiLCJpZCI6NCwic2lndW5ndSI6MTAwLCJpYXQiOjE3MDY3NDc2NzYsImV4cCI6MTcwNzE3OTY3Nn0.0UtQe8QKEO6KriOAAGD5iJTkmyWIqM0WCCpslvOJWLg'

function createChatRoom(receiverId) {
    $.ajax({
        url: `/api/chat/users/${receiverId}`,
        type: 'POST',
        contentType: 'application/json',
        headers: {
            'Authorization': `Bearer ${accessToken}` // Bearer 토큰을 헤더에 추가
        },
        success: function(response) {
            // 성공 시, 서버로부터 반환된 구독 경로로 구독 시작
            if(response.isSuccess) {
                const chatRoomId = response.result;
                console.log("hi")
                window.location.href = `chat2.html?chatRoomId=${chatRoomId}`;
            } else {
                console.error('Chat room creation failed:', response.message);
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error('Chat room creation failed:', textStatus, errorThrown);
        }
    });
}

function fetchChatRooms() {

    // 채팅방 목록 가져오기 요청
    $.ajax({
        url: "/api/chat/rooms",
        type: "GET",
        contentType: "application/json",
        headers: {
            'Authorization': `Bearer ${accessToken}` // Bearer 토큰을 헤더에 추가
        },
        success: function(response) {
            console.log(response)
            // 채팅방 목록을 화면에 표시
            displayChatRooms(response.result);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error('Failed to fetch chat rooms:', textStatus, errorThrown);
        }
    });
}

function displayChatRooms(rooms) {
    const chatRoomList = $("#chatRoomList");
    chatRoomList.empty(); // 목록을 비우고 새로운 목록으로 채웁니다.

    rooms.forEach(function (chatRoom) {
        // 채팅방 상세 정보를 포함한 HTML 요소 생성 및 추가
        chatRoomList.append(
            `<a href="chat2.html?chatRoomId=${chatRoom.chatRoomId}" class="list-group-item list-group-item-action">
                <div class="chat-room-info">
                    <div class="receiver-profile-image"><img src="${chatRoom.receiverProfileImage}" alt="Profile Image"></div>
                    <div class="chat-info">
                        <div class="receiver-name">${chatRoom.receiverName}</div>
                        <div class="last-message">${chatRoom.lastContent}</div>
                    </div>
                    <div class="update-dt">${chatRoom.updateDt}</div>
                </div>
            </a>`
        );
    });
}
