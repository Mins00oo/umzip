import React, { useEffect, useState } from 'react';
import AlarmMessage from './AlarmMessage';
import { motion } from 'framer-motion';
import { api } from '../../../services/api';

export default function AlarmComponent() {
  const [name, setName] = useState(null);
  const [alarms, setAlarms] = useState([]);
  const [offset, setOffset] = useState(0);
  const [loading, setLoading] = useState(false);
  const [hasMore, setHasMore] = useState(true);
  const limit = 5;

  useEffect(() => {
    const storedUserInfo =
      localStorage.getItem('userInfo') || sessionStorage.getItem('userInfo');
    if (storedUserInfo) {
      const { name } = JSON.parse(storedUserInfo);
      setName(name || '');
    }
  }, []);

  const Alarm_Call = async () => {
    setLoading(true);
    try {
      const queryString = `limit=${limit}&offset=${offset}`;
      const response = await api.get(`/alarm?${queryString}`);
      const newAlarms = response.data || [];
      console.log(response)
      setAlarms((prevAlarms) => [...prevAlarms, ...newAlarms]);
      setHasMore(newAlarms.length === limit);

    } catch (error) {
      console.error('Failed to fetch alarms', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    Alarm_Call();
  }, [offset]);

  const loadMoreAlarms = () => {
    if (!loading && hasMore) {
      setOffset((prevOffset) => prevOffset + 1);
    }
  };

  return (
    <>
      <div
        className="col-12 d-flex justify-content-center "
        style={{ marginTop: '7rem', marginBottom: '10rem' }}
      >
        <div className="col-6 justify-content-center align-items-center">
          <p className="fw-bold">{name} 님이 받은 알람</p>
          <div className="d-flex flex-column gap-3">
            {alarms.length > 0 ? (
              <>
                {alarms.slice(0, limit * (offset + 1)).map((data, index) => (
                  <AlarmMessage
                    key={index}
                    profileImg={data.imgPath}
                    date={data.createDt}
                    message={data.content}
                    status={data.read}
                  />
                ))}
              </>
            ) : (
              <p>알람이 없습니다.</p>
            )}
          </div>
          {hasMore && (
            <div
              style={{ width: '100%' }}
              className="d-flex justify-content-center mt-4"
            >
              <motion.button
                className="btn btn-light shadow"
                onClick={loadMoreAlarms}
                style={{ width: '6rem' }}
                whileHover={{ y: -5 }}
              >
                <div className="d-flex justify-content-center align-items-center gap-1">
                  <div
                    className="d-flex align-items-center justify-content-center rounded-circle bg-black text-white"
                    style={{ width: '1rem', height: '1rem' }}
                  >
                    +
                  </div>
                  <p className="m-0">더보기</p>
                </div>
              </motion.button>
            </div>
          )}
        </div>
      </div>
    </>
  );
}
