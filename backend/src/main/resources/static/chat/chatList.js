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
        const receiverId = 3
        createChatRoom(receiverId);
    });
});

const accessToken = 'eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRlc3QxMjM0Iiwicm9sZSI6IlVTRVIiLCJpZCI6NSwic2lndW5ndSI6MSwiaWF0IjoxNzA2NzI4NTk1LCJleHAiOjE3MDcxNjA1OTV9.9Y5Oimq0b7FhQawTz75lcJrqRrO-awb-QAtO1TEW0D0'

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
                window.location.href = `chat.html?chatRoomId=${chatRoomId}`;
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
            `<a href="chat.html?chatRoomId=${chatRoom.chatRoomId}" class="list-group-item list-group-item-action">
                <div class="chat-room-info">
                    <div class="receiver-id">${chatRoom.chatRoomId}</div>
                    <div class="receiver-name">${chatRoom.receiverName}</div>
                    <div class="receiver-profile-image"><img src="${chatRoom.receiverProfileImage}" alt="Profile Image"></div>
                    <div class="update-dt">${chatRoom.updateDt}</div>
                </div>
            </a>`
        );
    });
}
