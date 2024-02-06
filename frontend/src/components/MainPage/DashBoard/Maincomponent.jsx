import React, { useEffect, useState } from 'react';
import Requests from './Request';
import { motion } from 'framer-motion';
import { useNavigate } from 'react-router-dom';
import Status from './Status';
import { api } from '../../../services/api';

const MainComponent = () => {
    const [requestAllList, setRequestAllList] = useState([]);
    const [requestDelList, setRequestDelList] = useState([]);
    const [requestCLEList, setRequestCLEList] = useState([]);
    const [requestList, setrequestList] = useState('전체');
    const [pageNumber, setPageNumber] = useState(0);
    const itemsPerPage = 5;

    const CLE_Call = async () => {
        try {
            const response = await api.get('/clean/user/reservation', {});
            setRequestCLEList(response.data.result);
        } catch (error) {
            console.error(error);
        }
    };

    const ALL_Call = async () => {
        try {
            const response = await api.get('/dashboard', {});
            setRequestAllList(response.data.result);
        } catch (error) {
            console.error(error);
        }
    };

    const DEL_Call = async () => {
        try {
            const response = await api.get('/delivery/user/reservation', {});
            setRequestDelList(response.data.result);
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        DEL_Call();
        CLE_Call();
        ALL_Call();
    }, []);

    const navigate = useNavigate();
    const hadleDelivery = () => {
        navigate('/requestdelivery');
    };

    const hadleCleaning = () => {
        navigate('/requestcleaning');
    };

    const handlestatus = (target) => {
        setrequestList(target);
        setPageNumber(0);
    };

    const getDisplayedData = () => {
        switch (requestList) {
            case '용달':
                return requestDelList.slice(pageNumber * itemsPerPage, (pageNumber + 1) * itemsPerPage);
            case '청소':
                return requestCLEList.slice(pageNumber * itemsPerPage, (pageNumber + 1) * itemsPerPage);
            default:
                return requestAllList.slice(pageNumber * itemsPerPage, (pageNumber + 1) * itemsPerPage);
        }
    };

    const pageCount = Math.ceil((requestList === '용달' ? requestDelList : requestCLEList).length / itemsPerPage);

    const handlePageClick = (newPageNumber) => {
        setPageNumber(newPageNumber);
    };

    return (
        <div className="d-flex justify-content-center align-items-center" style={{ height: '100vh', width: '100%' }}>
            <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} transition={{ duration: 0.5 }} className="col-10">
                <div className="col-12 px-3">
                    <div className="row my-5" style={{ height: '80%' }}>
                        <div className="col-2 p-3 gap-5 d-flex flex-column align-items-center justify-content-center text-center border-dark-subtle border-end">
                            <h3 className="mt-5">OOO님</h3>
                            <h3>안녕하세요</h3>
                            <div className="d-flex flex-column justify-content-center gap-5" style={{ width: '11rem' }}>
                                <motion.button
                                    type="button"
                                    className="btn btn-primary btn-lg  d-flex justify-content-center gap-4 align-items-center"
                                    style={{ width: '10rem' }}
                                    whileHover="hover"
                                    onClick={() => handlestatus('전체')}
                                >
                                    <img src="/store.png" alt="" style={{ width: '2rem', height: '2rem' }} /> 전체
                                </motion.button>
                                <motion.button
                                    type="button"
                                    className="btn btn-primary btn-lg  d-flex justify-content-center gap-4 align-items-center"
                                    style={{ width: '10rem' }}
                                    whileHover="hover"
                                    onClick={() => handlestatus('청소')}
                                >
                                    <img style={{ width: '2rem', height: '2rem' }} src="/mop (2) 1.png" alt="" /> 청소
                                </motion.button>
                                <motion.button
                                    type="button"
                                    className="btn btn-primary btn-lg  d-flex justify-content-center gap-4 align-items-center"
                                    style={{ width: '10rem' }}
                                    whileHover="hover"
                                    onClick={() => handlestatus('용달')}
                                >
                                    <img style={{ width: '2rem', height: '2rem' }} src="/truck 1.png" alt="" />
                                    <h5 className="m-0">용달</h5>
                                </motion.button>
                                <motion.button type="button" className="btn btn-light btn-lg" s whileHover="hover" style={{ width: '10rem' }}>
                                    상담하기 <img src="/Wavy_Help.png" alt="" />
                                </motion.button>
                            </div>
                        </div>
                        <div className="col-10 p-5 gap-4 d-flex flex-column" style={{ height: "50rem" }}>
                            <div className="d-flex justify-content-between mx-5">
                                <div className="bg-white shadow rounded-3 p-2  justify-content-center align-items-center ">
                                    <Status />
                                </div>
                                <div className="d-flex gap-4">
                                    <button onClick={hadleDelivery} type="button" className="btn btn-outline-primary rounded-5 shadow-5">
                                        용달 신청
                                    </button>
                                    <button onClick={hadleCleaning} type="button" className="btn btn-outline-primary rounded-5 shadow-5">
                                        청소 신청
                                    </button>
                                </div>
                            </div>
                            <div className="rounded-3 mx-5 p-2 d-flex justify-content-around align-items-center text-center" style={{ background: '#D9E4FF' }}>
                                <h5 className="m-0 col-md-2">일시</h5>
                                <h5 className="m-0 col-md-2">주문명</h5>
                                <h5 className="m-0 col-md-2">주문번호</h5>
                                <h5 className="m-0 col-md-2">상태</h5>
                                <h5 className="m-0  col-md-2">내가 보낸견적서</h5>
                            </div>
                            <motion.div style={{ width: '100%', height: '100%' }} className='d-flex flex-column justify-content-between '>
                                <div>
                                    {getDisplayedData().length === 0 ? <div style={{width:"100%",height:"10rem"}} className='d-flex justify-content-center align-items-center gap-3' ><h2 className='m-0'>저희 서비스를 사용해 주세요!</h2><img style={{width:"4rem"}} src='./free-animated-icon-happy-11175727.gif'/> </div> : getDisplayedData().map((item, index) => {
                                        if (requestList === "전체") {
                                            if (item.role === "DELIVER") {
                                                const originalDate = new Date(item.deliveryReservationDto.createDt);
                                                let orderDate = new Date(item.deliveryReservationDto.startTime)
                                                let orderAddress = item.deliveryReservationDto.departure
                                                const formatDate = `${originalDate.getFullYear()}.${(originalDate.getMonth() + 1).toString().padStart(2, '0')}.${originalDate.getDate().toString().padStart(2, '0')}`;
                                                const formatoder = `${(orderDate.getMonth() + 1).toString().padStart(2, '0')}${orderDate.getDate().toString().padStart(2, '0')}/${orderAddress.slice(0, 2)}/용달`;
                                                let orderName = "DLE";

  const handleLogout = async (event) => {
    event.preventDefault();
    await logout(navigate);
  };

                                                let orderN = (item.deliveryReservationDto.id * item.deliveryReservationDto.id).toString().padStart(3, '0').slice(0, 3)
                                                return <Requests isAll={true} setRequestList={setRequestAllList} requestList="용달" list={item.deliveryReservationDto.list} key={index} date={formatDate} orderName={formatoder} orderNumber={`${orderName}${orderN}`} status={item.deliveryReservationDto.status} />;
                                            }
                                            else {

                                                const originalDate = new Date(item.cleanReservationDto.createDt);
                                                let orderDate = new Date(item.cleanReservationDto.reservationTime)
                                                let orderAddress = item.cleanReservationDto.address
                                                const formatDate = `${originalDate.getFullYear()}.${(originalDate.getMonth() + 1).toString().padStart(2, '0')}.${originalDate.getDate().toString().padStart(2, '0')}`;
                                                const formatoder = `${(orderDate.getMonth() + 1).toString().padStart(2, '0')}${orderDate.getDate().toString().padStart(2, '0')}/${orderAddress.slice(0, 2)}/청소`;
                                                let orderName = "CLE";
                                                let orderN = (item.cleanReservationDto.cleanId * item.cleanReservationDto.cleanId).toString().padStart(3, '0').slice(0, 3);

                                                return <Requests isAll={true} setRequestList={setRequestAllList} requestList="청소" list={item.cleanReservationDto.list} key={index} date={formatDate} orderName={formatoder} orderNumber={`${orderName}${orderN}`} status={item.cleanReservationDto.status} />;
                                            }
                                        }
                                        else {
                                            const originalDate = new Date(item.createDt);
                                            let orderDate = new Date();
                                            let orderAddress = '';

                                            if (requestList === '용달') {
                                                orderDate = new Date(item.startTime);
                                                orderAddress = item.departure;
                                            } else if (requestList === '청소') {
                                                orderDate = new Date(item.reservationTime);
                                                orderAddress = item.address;
                                            }

                                            const formatDate = `${originalDate.getFullYear()}.${(originalDate.getMonth() + 1).toString().padStart(2, '0')}.${originalDate.getDate().toString().padStart(2, '0')}`;
                                            const formatoder = `${(orderDate.getMonth() + 1).toString().padStart(2, '0')}${orderDate.getDate().toString().padStart(2, '0')}/${orderAddress.slice(0, 2)}/${requestList}`;
                                            let orderName = requestList === '용달' ? 'DEL' : 'CLE';
                                            let orderN = (requestList === '용달' ? item.id * item.id : item.cleanId * item.cleanId).toString().padStart(3, '0').slice(0, 3);

                                            return (
                                                <Requests
                                                    setRequestList={requestList === "용달" ? setRequestDelList : setRequestCLEList}
                                                    requestList={requestList}
                                                    isAll={false}
                                                    list={item.list}
                                                    key={index}
                                                    date={formatDate}
                                                    orderName={formatoder}
                                                    orderNumber={`${orderName}${orderN}`}
                                                    status={requestList === '용달' ? item.status : item.status}
                                                />
                                            );
                                        }
                                    })}

                                </div>
                                <div className="d-flex justify-content-center" style={{ zIndex: 0 }}>
                                    <ul className="pagination">
                                        {Array.from({ length: pageCount }, (_, index) => (
                                            <li key={index} className={`page-item ${pageNumber === index ? 'active' : ''}`}>
                                                <button className="page-link" onClick={() => handlePageClick(index)}>{index + 1}</button>
                                            </li>
                                        ))}
                                    </ul>
                                </div>

                            </motion.div>
                        </div>
                    </div>
                </div>
                <div className="d-flex gap-4">
                  <button
                    onClick={hadleDelivery}
                    type="button"
                    className="btn btn-outline-primary rounded-5 shadow-5 "
                  >
                    용달 신청
                  </button>
                  <button
                    onClick={hadleCleaning}
                    type="button"
                    className="btn btn-outline-primary rounded-5 shadow-5"
                  >
                    청소 신청
                  </button>
                </div>
              </div>
              <div
                className=" rounded-3 mx-5 p-2 d-flex justify-content-around align-items-center text-center"
                style={{ background: '#D9E4FF' }}
              >
                <h5 className="m-0 col-md-2">일시</h5>
                <h5 className="m-0 col-md-2">주문명</h5>
                <h5 className="m-0 col-md-2">주문번호</h5>
                <h5 className="m-0 col-md-2">상태</h5>
                <h5 className="m-0  col-md-2">내가 보낸견적서</h5>
              </div>
              <motion.div style={{ width: '100%', minHeight: '10rem' }}>
                {(requestList === '용달' ? requestDelList : requestCLEList).map(
                  (item, index) => {
                    const originalDate = new Date(item.createDt);
                    let orderDate = '';
                    {
                      requestList === '용달'
                        ? (orderDate = new Date(item.startTime))
                        : (orderDate = new Date(item.reservationTime));
                    }
                    let orderAddress = '';
                    {
                      requestList === '용달'
                        ? (orderAddress = item.departure)
                        : (orderAddress = item.address);
                    }
                    const formatDate = `${originalDate.getFullYear()}.${(
                      originalDate.getMonth() + 1
                    )
                      .toString()
                      .padStart(2, '0')}.${originalDate
                      .getDate()
                      .toString()
                      .padStart(2, '0')}`;
                    const formatoder = `${(orderDate.getMonth() + 1)
                      .toString()
                      .padStart(2, '0')}${orderDate
                      .getDate()
                      .toString()
                      .padStart(2, '0')}/${orderAddress}/${requestList}`;
                    let orderName = '';
                    if (requestList === '용달') {
                      orderName = 'DEL';
                    } else {
                      orderName = 'CLE';
                    }
                    let orderN = '';
                    {
                      requestList === '용달'
                        ? (orderN = (item.id * item.id)
                            .toString()
                            .padStart(3, '0'))
                        : (orderN = (item.cleanId * item.cleanId)
                            .toString()
                            .padStart(3, '0'));
                    }

                    // requests 컴포넌트 반환
                    return (
                      <Requests
                        requestList={requestList}
                        list={item.list}
                        key={index}
                        date={formatDate}
                        orderName={formatoder}
                        orderNumber={`${orderName}${orderN}`}
                        status={item.status}
                      />
                    );
                  }
                )}
              </motion.div>
            </div>
          </div>
        </div>
      </motion.div>
    </div>
  );
};

export default MainComponent;
