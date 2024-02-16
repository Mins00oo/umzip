import { useState, useRef } from 'react';
import { motion } from 'framer-motion';
import DaumPostcode from 'react-daum-postcode';
import axios from 'axios';

import classes from './CompanySignUpForm.module.css';
import { PRIMARY_COLOR } from '../../App';

const CompanySignUpForm = ({ companyType, onCompanyDataSubmit }) => {
  const [companyName, setCompanyName] = useState('');
  const [businessNumber, setBusinessNumber] = useState('');
  const [startDate, setStartDate] = useState('');
  const [introduction, setIntroduction] = useState('');
  const [ceo, setCeo] = useState('');
  const [address, setAddress] = useState('');
  const [addressDetail, setAddressDetail] = useState('');
  const addressDetailRef = useRef(null);
  const [sigungu, setSigungu] = useState('');
  const [isBusinessNumberVerified, setIsBusinessNumberVerified] =
    useState(false);
  const [uploadedImage, setUploadedImage] = useState({
    file: null,
    previewURL: '/blank-profile.png',
  });
  const [deliveryCertificate, setDeliveryCertificate] = useState('');
  const fileInput = useRef(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const onCompletePost = (data) => {
    let fullAddress = data.roadAddress || data.jibunAddress;
    let extraAddress = '';
    setSigungu(data.sigunguCode);

    if (
      data.userSelectedType === 'R' &&
      data.bname &&
      /[동|로|가]$/g.test(data.bname)
    ) {
      extraAddress += data.bname;
    }
    if (data.buildingName && data.apartment === 'Y') {
      extraAddress += extraAddress
        ? ', ' + data.buildingName
        : data.buildingName;
    }
    fullAddress += extraAddress ? ` (${extraAddress})` : '';

    setIsModalOpen(false);
    setAddress(fullAddress + extraAddress);

    if (addressDetailRef.current) {
      addressDetailRef.current.focus();
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    if (!isBusinessNumberVerified) {
      alert('사업자 인증을 완료해주시기 바랍니다.');
      return;
    }

    const formData = {
      companyName,
      companyType,
      businessNumber,
      startDate,
      introduction,
      ceo,
      address,
      addressDetail,
      sigungu,
      ...(companyType === 1
        ? { deliveryImgUrl: uploadedImage.file, deliveryCertificate }
        : { cleanImgUrl: uploadedImage.file }),
      ...(companyType === 1 && { deliveryCertificate }),
    };
    onCompanyDataSubmit(formData);
  };

  const handleAddressChange = (event) => {
    setAddressDetail(event.target.value);
  };

  const onChangeImage = (event) => {
    const file = event.target.files[0];

    if (file) {
      setUploadedImage({
        file: file,
        previewURL: URL.createObjectURL(file),
      });
    } else {
      setUploadedImage({
        file: null,
        previewURL: '/blank-profile.png',
      });
      return;
    }
  };

  const handleBusinessNumberVerification = async () => {
    try {
      const response = await axios.post(
        'https://i10e108.p.ssafy.io/api/auth/business-code',
        { businessNumber, startDate, personName: ceo }
      );

      if (response.data.isSuccess) {
        alert('인증되었습니다.');
        setIsBusinessNumberVerified(true);
      } else {
        alert(response.data.message);
        setIsBusinessNumberVerified(false);
      }
    } catch (error) {
      console.error('인증 코드 전송 에러:', error);
      alert('인증 코드 전송 중 오류가 발생했습니다.');
      setIsBusinessNumberVerified(false);
    }
  };

  const handleDeliveryCertificateChange = (event) => {
    if (event.target.files[0]) {
      setDeliveryCertificate(event.target.files[0]);
    } else {
      setDeliveryCertificate('');
    }
  };

  return (
    <>
      {isModalOpen && (
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          exit={{ opacity: 0 }}
          onClick={() => setIsModalOpen(false)}
          style={{
            position: 'fixed',
            zIndex: '1',
            top: 0,
            left: 0,
            width: '100%',
            height: '100%',
            backgroundColor: 'rgba(0, 0, 0, 0.5)', // 배경색 및 투명도 조절
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
          }}
        >
          <div
            style={{
              position: 'relative',
              width: '40%',
              backgroundColor: 'white', // 내용의 배경색
              padding: '20px',
              borderRadius: '8px', // 내용의 모서리 둥글게
            }}
          >
            <DaumPostcode onComplete={onCompletePost} />
          </div>
        </motion.div>
      )}
      <div
        className="container rounded p-4 border shadow-sm mx-auto"
        style={{ marginTop: '50px', marginBottom: '50px' }}
      >
        <div className="row justify-content-center">
          <div className="col-md-12">
            <h2
              className="mb-4"
              style={{ textAlign: 'center', fontWeight: '800' }}
            >
              업체 등록
            </h2>
            <div className={`form-group mb-4 ${classes.inputStyling}`}>
              <label htmlFor="companyName">업체 이름</label>
              <input
                id="companyName"
                className="form-control rounded-pill py-4"
                type="text"
                value={companyName}
                onChange={(event) => setCompanyName(event.target.value)}
                placeholder="업체 이름"
                required
              />
            </div>
            <div
              htmlFor="uploadedImage"
              style={{
                textAlign: 'center',
                fontWeight: 'bold',
                fontSize: '1.2em',
              }}
            >
              업체 대표 사진
            </div>
            <div
              style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '150px',
                margin: '15px',
              }}
            >
              <img
                src={uploadedImage.previewURL}
                alt="Uploaded"
                style={{
                  width: '120px',
                  height: '120px',
                  objectFit: 'cover',
                }}
                onClick={() => {
                  fileInput.current && fileInput.current.click();
                }}
              />
              <input
                type="file"
                style={{ display: 'none' }}
                accept="image/jpg,impge/png,image/jpeg"
                name="profile_img"
                onChange={onChangeImage}
                ref={fileInput}
              />
            </div>
            <div className={`form-group mb-4 ${classes.inputStyling}`}>
              <label htmlFor="introduction">소개글</label>
              <input
                id="introduction"
                className="form-control rounded-pill py-4"
                type="text"
                value={introduction}
                onChange={(event) => setIntroduction(event.target.value)}
                placeholder="소개글을 작성해주세요."
                required
              />
            </div>

            <div className={`form-group mb-4 ${classes.inputStyling}`}>
              <label htmlFor="startDate">서비스 시작년도</label>
              <input
                id="startDate"
                className="form-control rounded-pill py-4"
                type="text"
                value={startDate}
                onChange={(event) => setStartDate(event.target.value)}
                placeholder="20010101"
                required
              />
            </div>
            <div className={`form-group mb-4 ${classes.inputStyling}`}>
              <label htmlFor="ceo">사업자명</label>
              <input
                id="ceo"
                className="form-control rounded-pill py-4"
                type="text"
                value={ceo}
                onChange={(event) => setCeo(event.target.value)}
                placeholder="사업자명"
                required
              />
            </div>
            <div className={`form-group mb-4 ${classes.inputStyling}`}>
              <label htmlFor="address">업체주소</label>
              <input
                id="address"
                className="form-control rounded-pill py-4"
                type="text"
                onClick={() => setIsModalOpen(true)}
                readOnly
                placeholder={address ? address : '기본주소'}
                required
              />
            </div>
            <div className={`form-group mb-4 ${classes.inputStyling}`}>
              <label htmlFor="addressDetail">상세주소</label>
              <input
                id="addressDetail"
                ref={addressDetailRef}
                className="form-control rounded-pill py-4"
                type="text"
                placeholder={addressDetail ? addressDetail : '상세주소'}
                onChange={handleAddressChange}
                value={addressDetail}
                required
              />
            </div>
            <div className={`form-group mb-4 ${classes.inputStyling}`}>
              <label htmlFor="businessNumber">사업자 등록 번호</label>
              <div className="input-group" style={{ zIndex: '0' }}>
                <input
                  id="businessNumber"
                  className="form-control rounded-pill py-4"
                  type="text"
                  placeholder="사업자 등록 번호"
                  value={businessNumber}
                  onChange={(event) => setBusinessNumber(event.target.value)}
                  required
                />
                <button
                  type="button"
                  className="btn btn-secondary rounded-pill py-3"
                  onClick={handleBusinessNumberVerification}
                  style={{
                    marginLeft: '-1px',
                    backgroundColor: '#40A2D8',
                    border: '#40A2D8',
                  }}
                >
                  인증
                </button>{' '}
              </div>
            </div>
            {companyType === 1 && (
              <div className={`form-group mb-4 ${classes.inputStyling}`}>
                <label htmlFor="deliveryCertificate">
                  화물운송종사자 자격증
                </label>
                <input
                  id="deliveryCertificate"
                  className="form-control rounded-pill py-4"
                  type="file"
                  onChange={handleDeliveryCertificateChange}
                  required
                />
              </div>
            )}
            <button
              type="submit"
              className="btn btn-primary"
              onClick={handleSubmit}
              disabled={!isBusinessNumberVerified}
              style={{ float: 'right', fontSize: '1.1em', padding: '8px 16px' }}
            >
              작성 완료
            </button>
          </div>
        </div>
      </div>
    </>
  );
};

export default CompanySignUpForm;
