import { forwardRef, useState } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

const Calendar = ({ setStartDate, startDate, isTime, setIsTime }) => {
  const ExampleCustomInput = forwardRef(({ value, onClick }, ref) => (
    <button
      className="btn btn-primary rounded-5 d-flex justify-content-center align-items-center gap-2 p-2"
      style={{ width: '10rem', height: '4rem' }}
      onClick={onClick}
      ref={ref}
    >
      {value}
      <img src="/calendar-frame.png" style={{ width: '1rem', height: '1rem' }} />
    </button>
  ));

  const handleDateChange = (date) => {
    // Update the startDate state
    setStartDate(date);

    // Extract the time from the formatted date string
    const timeIncluded = date && date.getHours() > 0;

    // Update the isTime state
    setIsTime(`${date.getFullYear()}년 ${date.getMonth()+1}월 ${date.getDate()}일`)
  };

  return (
    <>
      <DatePicker
        dateFormat="yyyy.MM.dd"
        minDate={new Date()}
        selected={startDate}
        onChange={handleDateChange}
        customInput={<ExampleCustomInput />}
      />
    </>
  );
};

export default Calendar;
