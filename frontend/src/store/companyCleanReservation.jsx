import { create } from 'zustand';
import { api } from '../services/api';

const useStoreClean = create((set) => ({
  data: [],
  detail: [],
  loading: false,
  error: null,

  fetchDataClean: async () => {

    set({ loading: true });
    try {
        // 도메인주소로 할시에는 https로 바꿔줘야함
      const response = await api.get(`/clean/company/reservation`,
      );
      set({ data: response.data, loading: false },
        console.log(response.data)
        );
    } catch (error) {
      set({ error, loading: false }
        );
    }
  },
}));

export default useStoreClean;