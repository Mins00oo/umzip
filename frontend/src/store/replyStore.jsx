import { create } from 'zustand';
import { api } from '../services/api';

const useStore = create((set, get) => ({
  data: [],
  loading: false,
  error: null,
  fetchData: async (mappingId, price, detail) => {

    const quotation = {        
        mappingId: mappingId,
        price : price,
        detail: detail
}

    set({ loading: true });
    try {
        // 도메인주소로 할시에는 https로 바꿔줘야함
      const response = await api.put(`/delivery/company/quotation`, quotation
      );
      set({ data: response.data, loading: false },
        console.log(response.data));
    } catch (error) {
      set({ error, loading: false });
    }
  }
}));

export default useStore;