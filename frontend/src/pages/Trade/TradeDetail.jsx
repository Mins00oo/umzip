import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

import TradeItemDetail from '../../components/Trade/TradeItemDetail';
import Header from '../../components/Header';
import Chat from '../../components/Chat/Chat';

import { api } from '../../services/api';

function TradeDetail() {
  const { tradeId } = useParams();
  const [loading, setLoading] = useState(true);
  const [trade, setTrade] = useState({});

  const getTrade = async () => {
    const response = await api.get(`/trade-items/detail/${tradeId}`);
    setTrade(response.data.result);
    setLoading(false);
  };

  useEffect(() => {
    getTrade();
  }, [tradeId]);

  return (
    <>
      <Header />
      <Chat />
      <div>
        {loading ? (
          <h2>loading...</h2>
        ) : (
          <div>{trade && <TradeItemDetail trade={trade} />}</div>
        )}
      </div>
    </>
  );
}

export default TradeDetail;
