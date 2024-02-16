import axios from 'axios';
import useAuthStore from '../store/store';

const api = axios.create({
  // http://172.30.1.68:8080/api 윤민이 API => 윤민이 게인노트북
  // http://192.168.30.125:8080/api 윤민이 API => 싸피노트북
  // http://192.168.30.206:8080/api 동현
  // http://192.168.30.145:8080/api 민수
  // https://i10e108.p.ssafy.io/api 빌드 주소
  baseURL: 'https://i10e108.p.ssafy.io/api'
});

api.interceptors.request.use((config) => {
  const { token } = useAuthStore.getState();
  if (token) {
    config.headers['Authorization'] = `Bearer ${token}`;
  }
  return config;
});

export const fetchProtectedData = async () => {
  try {
    const response = await api.get('/protected');
    return response.data;
  } catch (error) { console.log(error);}
};

export const refreshToken = async () => {
  try {
    const response = await api.post('/auth/reissu', {
      refreshToken: localStorage.getItem('refreshToken'),
    });
    const { accessToken, refreshToken } = response.data;
    localStorage.setItem('token', `Bearer ${accessToken}`);
    localStorage.setItem('refreshToken', refreshToken);
    return { accessToken, refreshToken };
  } catch (error) { console.log(error);}
};

export { api };
