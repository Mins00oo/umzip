import { create } from 'zustand';
import { api } from '../services/api';



const useStoreChatToCompany = create((set) => ({
  data: [],
  roomNumber:[],
  loading: false,
  error: null,

  makeChatRoom: async ( role, memberId ) => {
    set({ loading: true });
    try {
      // 여기서 생성되는 result는 채팅방 번호
      const response = await api.post(`/chat/${role}/${memberId}`);
      set(
        { roomNumber: response.data.result, loading: false },
        console.log(response),
        // console.log(memberId),
        // console.log(typeof(memberId)),
        console.log(role)
      );
    } catch (error) {
      set({ error, loading: false });
    }
  },
}
))

export default useStoreChatToCompany;