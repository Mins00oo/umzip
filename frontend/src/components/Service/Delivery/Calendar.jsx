import { forwardRef, useState } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import {motion} from "framer-motion"
const Calendar = ({ setStartDate, startDate }) => {
  const ExampleCustomInput = forwardRef(({ value, onClick }, ref) => (
    <motion.button
      type="button"
      whileHover={{ background: 'linear-gradient(to right bottom, #0052D4 , #6FB1FC)' }}
      className="btn  d-flex justify-content-center gap-4 align-items-center rounded-5"
      onClick={onClick}
      ref={ref}
      style={{
        background: 'linear-gradient(to right bottom, #6FB1FC, #0052D4)',
        width: '10rem', height: '4rem',
        border: 'none',
        color: '#fff', // 텍스트 색상 조정
        borderRadius: '8px', // 둥근 모서리 조정
        boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.2)', // 그림자 효과 추가
      }}
    >
      {value}
      <img src="/calendar-frame.png" style={{ width: '1rem', height: '1rem' }} />
    </motion.button>
  ));

  const handleDateChange = (date) => {
    // Update the startDate state
    // console.log(date)
    setStartDate(date);
    // Extract the time from the formatted date string
    // const timeIncluded = date && date.getHours() > 0;

    // Update the isTime state
    // setIsTime(`${date.getFullYear()}년 ${date.getMonth()+1}월 ${date.getDate()}일`)
    // console.log(isTime)
  };

  return (
    <>
      <DatePicker
        dateFormat="yyyy.MM.dd"
        minDate={new Date()}
        selected={startDate}
        onChange={handleDateChange}
        customInput={<ExampleCustomInput />}
        style={{ width: '100rem' }}
      />
    </>
  );
};

export default Calendar;
