// API.js
// axios 의 인스턴스를 생성
import axios from 'axios';

const API = axios.create({
	BASE_URL: 'http://localhost:8080/api/users/test/',
    headers: {
      	'Content-Type': 'application/json',
    },
    withCredentials: true,
});

export default API;