import { create } from 'zustand';
import { api } from '../services/api';

const replyStore = create((set) => ({
  data: [],
  loading: false,
  error: null,
  submitReplyStore: async (role, mappingId, reissuing, detail) => {
    const quotation = {        
        mappingId: mappingId,
        reissuing : reissuing,
        detail: detail
}

    set({ loading: true });
    try {
        // 도메인주소로 할시에는 https로 바꿔줘야함
        console.log(role)
      const response = await api.put(`/${role}/company/quotation`, quotation
      );
      set({ data: response.data, loading: false },
        console.log(response.data));
    } catch (error) {
      set({ error, loading: false });
    }
  },

  rejectionReservation: async (role, mappingId) => {
    const rejectionData = {        
        mappingId: mappingId,
}

    set({ loading: true });
    try {
        // 도메인주소로 할시에는 https로 바꿔줘야함
      const response = await api.put(`/${role}/company/rejection`, rejectionData
      );
      set({ data: response.data, loading: false },
        console.log(response.data));
    } catch (error) {
      set({ error, loading: false });
    }
  },

  
}));

export default replyStore;