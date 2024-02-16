import React, { useEffect, useState } from 'react';
import Requests from './Request';
import { AnimatePresence, motion } from 'framer-motion';
import { useNavigate } from 'react-router-dom';
import Status from './Status';
import { api } from '../../../services/api';
import useAuthStore from '../../../store/store';
import RecommendModalComponent from '../../Recommend/RecommendModalComponent';
import RecommendModal from '../../Recommend/RecommendModal';
import NewModal from './NewModal';
import DetailModal from './DetailModal';
import MakeReview from './MakeReview';

const MainComponent = () => {
    const [requestAllList, setRequestAllList] = useState([]);
    const [requestDelList, setRequestDelList] = useState([]);
    const [requestCLEList, setRequestCLEList] = useState([]);
    const [requestList, setrequestList] = useState('전체');
    const [pageNumber, setPageNumber] = useState(0);
    const [openRecommendModal, setOpenRecommendModal] = useState(false)
    const itemsPerPage = 5;
    const logout = useAuthStore((state) => state.logout);
    const navigate = useNavigate();
    const [userInfo, setUserInfo] = useState({ name: '', profileImage: '' });
    const [choicecompanyId, setChoiceCompanyId] = useState(null)

    const [requestCheck, setRequestCheck] = useState(true)
    const [requestMappingId, setRequestMappingId] = useState(null)
    const [requestId, setRequestId] = useState(null)
    const [serviceId, setServiceId] = useState(null)
    const [reviewId, setReviewId] = useState(null)
    useEffect(() => {
        const storedUserInfo = localStorage.getItem("userInfo") || sessionStorage.getItem("userInfo");
        // window.scrollTo(0, 0);
        if (storedUserInfo) {
            const parsedInfo = JSON.parse(storedUserInfo);
            console.log(parsedInfo);
            setUserInfo({ name: parsedInfo.name, profileImage: parsedInfo.profileImage, id: parsedInfo.id });
        }
        else {
            navigate("/login")
        }
    }, []);

    const handleLogout = async (event) => {
        event.preventDefault();
        await logout(navigate)
    };

    const CLE_Call = async () => {
        // const { token } = useAuthStore.getState();
        // console.log(token)
        try {
            const response = await api.get('/clean/user/reservation', {});
            setRequestCLEList(response.data.result);
            console.log(response.data.result)
        } catch (error) {
            console.error(error);
        }
    };

    const ALL_Call = async () => {
        try {
            const response = await api.get('/dashboard', {});
            setRequestAllList(response.data.result);
            console.log(response.data)
        } catch (error) {
            console.error(error);
        }
    };

    const DEL_Call = async () => {
        try {
            const response = await api.get('/delivery/user/reservation', {});
            setRequestDelList(response.data.result);
            console.log(response.data.result)
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        DEL_Call();
        CLE_Call();
        ALL_Call();
    }, []);


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

    const pageCount = Math.ceil(
        (requestList === '용달'
            ? requestDelList
            : requestList === '청소'
                ? requestCLEList
                : requestAllList
        ).length / itemsPerPage
    );


    const handlePageClick = (newPageNumber) => {
        setPageNumber(newPageNumber);
    };
    const handleModal = () => {
        setOpenRecommendModal((prev) => !prev)
    }
    const renewer = async () => {
        DEL_Call();
        CLE_Call();
        ALL_Call();
    }
    const goConsulting = () => {
        window.location.href = 'https://mfdo.site/#/consulting';
    }
    const renderPaginationButtons = () => {
        const maxVisibleButtons = 5;
        const maxButtonsBeforeCurrent = Math.floor(maxVisibleButtons / 2);
        const maxButtonsAfterCurrent = Math.ceil(maxVisibleButtons / 2);

        let startPage = Math.max(0, pageNumber - maxButtonsBeforeCurrent);
        let endPage = Math.min(startPage + maxVisibleButtons - 1, pageCount - 1);

        // 만약 endPage가 최대치에 도달했을 때 startPage를 조정
        startPage = Math.max(0, endPage - maxVisibleButtons + 1);
        const variants = {
            visible: { opacity: 1, y: 0 },
            hidden: { opacity: 0, y: 5 },
        };
        return Array.from({ length: endPage - startPage + 1 }, (_, index) => (

            <motion.li initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} key={index} className={`page-item ${pageNumber === startPage + index ? 'active' : ''} d-flex gap-1`}>
                <motion.button
                    whileHover={{ y: -5 }}
                    className={`btn btn-light ${pageNumber === startPage + index ? ' active' : ''}`}
                    key={pageNumber}
                    onClick={() => handlePageClick(startPage + index)}
                >
                    {startPage + index + 1}
                </motion.button>
            </motion.li>

        ));
    };

    return (
        <>
            <AnimatePresence>
                {reviewId && <MakeReview reviewId={reviewId} setReviewId={setReviewId} />}
            </AnimatePresence>
            <AnimatePresence>
                {serviceId && <DetailModal serviceId={serviceId} setServiceId={setServiceId} />}
            </AnimatePresence>
            <AnimatePresence>
                {choicecompanyId && <RecommendModal companyId={choicecompanyId} isOpen={openRecommendModal} closeModal={handleModal} />}
            </AnimatePresence>
            <AnimatePresence>
                {requestId && requestId.price && <NewModal renewer={renewer} id={userInfo.id} setRequestId={setRequestId} requestId={requestId} setRequestMappingId={setRequestMappingId} requestMappingId={requestMappingId} isModalOpen={requestCheck} setIsModalOpen={setRequestCheck} />}
            </AnimatePresence>
            <div className="d-flex justify-content-center align-items-center" style={{ height: '90vh', width: '100%', marginTop: "7rem" }}>
                <motion.div className="col-10">
                    <div className="col-12 px-3">
                        <div className="row my-5" style={{ height: '50%' }}>
                            <motion.div initial={{ opacity: 0, y: 100 }} animate={{ opacity: 1, y: 0 }} exit={{ opacity: 0, y: 100 }} className=" col-2 p-3 gap-3 d-flex flex-column align-items-center justify-content-center text-center" >
                                <img src={userInfo.profileImage} alt="Profile" style={{ maxWidth: '100px', height: 'auto', borderRadius: '50%', objectFit: 'cover' }} />
                                <h5 className="m-0">{userInfo.name}님</h5>
                                <h5 className='m-0'>안녕하세요</h5  >
                                <button
                                    type="button"
                                    onClick={handleLogout}
                                    style={{
                                        color: 'red',
                                        border: 'none',
                                        background: 'none',
                                        padding: '0',
                                        margin: '0',
                                    }}
                                >
                                    로그아웃
                                </button>{/*  */}
                                <div className="d-flex flex-column justify-content-center align-items-center gap-5" style={{ width: '11rem' }}>
                                    <motion.button
                                        type="button"
                                        whileHover={{ background: 'linear-gradient(to right bottom, #ACB6E5, #005bea)', y: -5 }}
                                        className="btn  btn-lg d-flex justify-content-center gap-4 align-items-center"
                                        style={{
                                            background: 'linear-gradient(to right bottom, #005bea, #ACB6E5)',
                                            width: '80%',
                                            border: 'none',
                                            color: '#fff', // 텍스트 색상 조정
                                            borderRadius: '8px', // 둥근 모서리 조정
                                            boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.2)', // 그림자 효과 추가
                                        }}
                                        onClick={() => handlestatus('전체')}
                                    >
                                        <img src="/store.png" alt="" style={{ width: '2rem', height: '2rem' }} /> 전체
                                    </motion.button>
                                    <motion.button
                                        type="button"
                                        whileHover={{ background: 'linear-gradient(to right bottom, #ACB6E5, #005bea)', y: -5 }}
                                        className="btn  btn-lg d-flex justify-content-center gap-4 align-items-center"
                                        style={{
                                            background: 'linear-gradient(to right bottom, #005bea, #ACB6E5)',
                                            width: '80%',
                                            border: 'none',
                                            color: '#fff', // 텍스트 색상 조정
                                            borderRadius: '8px', // 둥근 모서리 조정
                                            boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.2)', // 그림자 효과 추가
                                        }}
                                        onClick={() => handlestatus('청소')}
                                    >
                                        <img style={{ width: '2rem', height: '2rem' }} src="/mop (2) 1.png" alt="" /> 청소
                                    </motion.button>


                                    <motion.button
                                        type="button"
                                        whileHover={{ background: 'linear-gradient(to right bottom, #ACB6E5, #005bea)', y: -5 }}
                                        className="btn  btn-lg d-flex justify-content-center gap-4 align-items-center"
                                        style={{
                                            background: 'linear-gradient(to right bottom, #005bea, #ACB6E5)',
                                            width: '80%',
                                            border: 'none',
                                            color: '#fff', // 텍스트 색상 조정
                                            borderRadius: '8px', // 둥근 모서리 조정
                                            boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.2)', // 그림자 효과 추가
                                        }}
                                        onClick={() => handlestatus('용달')}
                                    >
                                        <img style={{ width: '2rem', height: '2rem' }} src="/truck 1.png" alt="" />
                                        <h5 className="m-0">용달</h5>
                                    </motion.button>

                                    <motion.button onClick={goConsulting} type="button" className="btn btn-light btn-lg" s whileHover="hover" style={{ width: '80%' }}>
                                        상담하기 <img src="/Wavy_Help.png" alt="" />
                                    </motion.button>
                                </div>
                            </motion.div>
                            <motion.div initial={{ opacity: 0, x: 100 }} animate={{ opacity: 1, x: 0 }} exit={{ opacity: 0, x: 100 }} className="col-10 p-5 gap-4 d-flex flex-column ">

                                <div className="d-flex justify-content-between mx-5">
                                    <div className="bg-white shadow rounded-3 p-2  justify-content-center align-items-center ">
                                        <Status />
                                    </div>
                                    <div className="d-flex gap-4">
                                        <div>
                                            <motion.button
                                                type="button"
                                                whileHover={{ background: 'linear-gradient(to right bottom, #ACB6E5, #005bea)' }}
                                                className="btn  d-flex justify-content-center gap-4 align-items-center"
                                                style={{
                                                    background: 'linear-gradient(to right bottom, #005bea, #ACB6E5)',
                                                    // width: '80%',
                                                    border: 'none',
                                                    color: '#fff', // 텍스트 색상 조정
                                                    borderRadius: '8px', // 둥근 모서리 조정
                                                    boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.2)', // 그림자 효과 추가
                                                }}
                                                onClick={hadleDelivery}
                                            >
                                                <p className='m-0'>용달 신청</p>
                                            </motion.button>
                                        </div>

                                        <motion.button
                                            type="button"
                                            whileHover={{ background: 'linear-gradient(to right bottom, #ACB6E5, #005bea)' }}
                                            className="btn  d-flex justify-content-center gap-4 align-items-center"
                                            style={{
                                                background: 'linear-gradient(to right bottom, #005bea, #ACB6E5)',
                                                // width: '80%',
                                                border: 'none',
                                                color: '#fff', // 텍스트 색상 조정
                                                borderRadius: '8px', // 둥근 모서리 조정
                                                boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.2)', // 그림자 효과 추가
                                            }}
                                            onClick={hadleCleaning}
                                        >
                                            <p className='m-0'>청소 신청</p>
                                        </motion.button>

                                    </div>
                                </div>
                                <div className="rounded-3 mx-5 p-2 d-flex justify-content-around align-items-center text-center" style={{ height: "4rem", background: 'linear-gradient(to left, #005bea, #ACB6E5)' }}>
                                    <div className='d-flex col-2 align-items-center justify-content-center gap-2'><h5 style={{ color: "white" }} className="m-0 ">일시</h5></div>
                                    <div className='d-flex col-2 align-items-center justify-content-center gap-2'><h5 style={{ color: "white" }} className="m-0 ">주문명</h5></div>
                                    <div className='d-flex col-2 align-items-center justify-content-center gap-2'><h5 style={{ color: "white" }} className="m-0 ">주문번호</h5></div>
                                    <div className='d-flex col-2 align-items-center justify-content-center gap-2'><h5 style={{ color: "white" }} className="m-0 ">상태</h5></div>
                                    <div className='d-flex col-2 align-items-center justify-content-center gap-2 '><h5 style={{ color: "white" }} className="m-0 ">내가보낸견적서</h5></div>
                                </div>
                                <motion.div style={{ width: '100%', height: '100%' }} className='d-flex flex-column justify-content-between '>
                                    <div className='d-flex flex-column gap-2' style={{ height: "30rem" }}>
                                        {getDisplayedData().length === 0 ? <div style={{ width: "100%", height: "10rem" }} className='d-flex justify-content-center align-items-center gap-3' ><h2 className='m-0'>저희 서비스를 사용해 주세요!</h2><img style={{ width: "4rem" }} src='/free-animated-icon-happy-11175727.gif' /> </div> :
                                            getDisplayedData().map((item, index) => {
                                                if (requestList === "전체") {
                                                    if (item.role === "DELIVER") {
                                                        const originalDate = new Date(item.deliveryReservationDto.createDt);
                                                        let orderDate = new Date(item.deliveryReservationDto.startTime)
                                                        let orderAddress = item.deliveryReservationDto.departure
                                                        const formatDate = `${originalDate.getFullYear()}.${(originalDate.getMonth() + 1).toString().padStart(2, '0')}.${originalDate.getDate().toString().padStart(2, '0')}`;
                                                        const formatoder = `${(orderDate.getMonth() + 1).toString().padStart(2, '0')}${orderDate.getDate().toString().padStart(2, '0')}/${orderAddress.slice(0, 2)}/용달`;
                                                        let orderName = "DLE";


                                                        let orderN = (item.deliveryReservationDto.id * item.deliveryReservationDto.id).toString().padStart(3, '0').slice(0, 3)
                                                        return <Requests setReviewId={setReviewId} setServiceId={setServiceId} setRequestId={setRequestId} Id={item.deliveryReservationDto.id} setOpenRecommendModal={setOpenRecommendModal} setChoiceCompanyId={setChoiceCompanyId} isAll={true} setRequestList={setRequestAllList} requestList="용달" list={item.deliveryReservationDto.list} key={index} date={formatDate} orderName={formatoder} orderNumber={`${orderName}${orderN}`} status={item.deliveryReservationDto.status} />;
                                                    }
                                                    else {

                                                        const originalDate = new Date(item.cleanReservationDto.createDt);
                                                        let orderDate = new Date(item.cleanReservationDto.reservationTime)
                                                        let orderAddress = item.cleanReservationDto.address
                                                        const formatDate = `${originalDate.getFullYear()}.${(originalDate.getMonth() + 1).toString().padStart(2, '0')}.${originalDate.getDate().toString().padStart(2, '0')}`;
                                                        const formatoder = `${(orderDate.getMonth() + 1).toString().padStart(2, '0')}${orderDate.getDate().toString().padStart(2, '0')}/${orderAddress.slice(0, 2)}/청소`;
                                                        let orderName = "CLE";
                                                        let orderN = (item.cleanReservationDto.cleanId * item.cleanReservationDto.cleanId).toString().padStart(3, '0').slice(0, 3);

                                                        return <Requests setReviewId={setReviewId} setServiceId={setServiceId} setRequestId={setRequestId} Id={item.cleanReservationDto.cleanId} setOpenRecommendModal={setOpenRecommendModal} setChoiceCompanyId={setChoiceCompanyId} isAll={true} setRequestList={setRequestAllList} requestList="청소" list={item.cleanReservationDto.list} key={index} date={formatDate} orderName={formatoder} orderNumber={`${orderName}${orderN}`} status={item.cleanReservationDto.status} />;
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
                                                            setReviewId={setReviewId}
                                                            setServiceId={setServiceId}
                                                            setRequestId={setRequestId}
                                                            Id={requestList === "용달" ? item.id : item.cleanId}
                                                            setOpenRecommendModal={setOpenRecommendModal}
                                                            setChoiceCompanyId={setChoiceCompanyId}
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


                                </motion.div>
                                <div className="d-flex justify-content-center" style={{ zIndex: 0 }}>
                                    <ul className="d-flex gap-2 mt-3">
                                        {renderPaginationButtons()}
                                    </ul>
                                </div>
                            </motion.div>

                        </div>
                    </div>
                </motion.div>
            </div>
        </>
    );
};

export default MainComponent;
