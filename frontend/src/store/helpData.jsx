import { create } from 'zustand';
import { api } from '../services/api';

const useStore = create((set, get) => ({
  data: [],
  loading: false,
  error: null,
  // 192.168.30.206:8080 동현이 API
  // 172.30.1.68:8080 윤민이 API
  // api: 'http://172.30.1.68:8080/api/',
  // token: 'eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRlc3QxMjM0Iiwicm9sZSI6IlVTRVIiLCJpZCI6NCwic2lndW5ndSI6MTAwLCJpYXQiOjE3MDY4MzY2MTUsImV4cCI6MTcwNzI2ODYxNX0.5jG0Us4yKDSTowVlebRwW1iP03py78fdSdPCcRTzKnk',

  // 매번 다르게 요청하기위한 변수 설정
  keyword: '', // 검색 키워드 상태
  setKeyword: (keyword) => set({ keyword }), // 검색 키워드를 업데이트하는 함수

  codeSmall: 0,
  setcodeSmall: (codeSmall) => set({ codeSmall }),

  page: 1,
  setPage: (page) => set({ page }),

  fetchData: async () => {

    const { keyword, codeSmall, page } = get();

    set({ loading: true });
    try {
        // 도메인주소로 할시에는 https로 바꿔줘야함
      const response = await api.get(`/helps?code-small=${codeSmall}&keyword=${keyword}&page=${page}&size=10`,
      // {headers: {
      //   // Authorization:`Bearer eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRlc3QxMjM0Iiwicm9sZSI6IlVTRVIiLCJpZCI6NCwic2lndW5ndSI6MTAwLCJpYXQiOjE3MDY4MzY2MTUsImV4cCI6MTcwNzI2ODYxNX0.5jG0Us4yKDSTowVlebRwW1iP03py78fdSdPCcRTzKnk`
      // }}
      );
      set({ data: response.data, loading: false },
        console.log(response.data));
    } catch (error) {
      set({ error, loading: false });
    }
  }
}));

export default useStore;