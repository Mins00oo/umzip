import { create } from 'zustand';
import { api } from '../services/api';

const useStoreEstimate = create((set) => ({
  data: [],
  loading: false,
  error: null,

  deliveryDetail: async (NowId, role) => {
    set({ loading: true });
    try {
      // 도메인주소로 할시에는 https로 바꿔줘야함
      const response = await api.get(`/${role}/company/reservation/${NowId}`);
      set(
        { data: response.data, loading: false },
        console.log(response.data),
        console.log(NowId),
        console.log(typeof(NowId)),
        console.log(role)
      );
    } catch (error) {
      set({ error, loading: false });
    }
  },
}));

export default useStoreEstimate;