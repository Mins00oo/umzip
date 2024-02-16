import { create } from 'zustand';
import { api } from '../services/api';

const useStorePoint = create((set, get) => ({
  data: [],
  pointDetail: [],
  loading: false,
  error: null,
  // 192.168.30.206:8080 동현이 API
  // 172.30.1.68:8080 윤민이 API
  // api: 'http://172.30.1.68:8080/api/',
  // token: 'eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRlc3QxMjM0Iiwicm9sZSI6IlVTRVIiLCJpZCI6NCwic2lndW5ndSI6MTAwLCJpYXQiOjE3MDY4MzY2MTUsImV4cCI6MTcwNzI2ODYxNX0.5jG0Us4yKDSTowVlebRwW1iP03py78fdSdPCcRTzKnk',

  // 매번 다르게 요청하기위한 변수 설정
  page: 1,
  setPage: (page) => set({ page }),

  fetchData: async () => {

    const { page } = get();

    set({ loading: true });
    try {
        // 도메인주소로 할시에는 https로 바꿔줘야함
      const response = await api.get(`/point?page=${page}&size=10`,
      );
      set({ data: response.data, loading: false },
        console.log(response.data));
        console.log(`/point?page=${page}&size=10`)
    } catch (error) {
      set({ error, loading: false });
    }
  },

  pointLoad: async (memberId) => {
    set({ loading: true });
    try {
        // 도메인주소로 할시에는 https로 바꿔줘야함
      const response = await api.get(`/users/${memberId}`,
      );
      set({ pointDetail: response.data, loading: false },
        console.log(response.data));
    } catch (error) {
      set({ error, loading: false });
    }
  },

}));

export default useStorePoint;