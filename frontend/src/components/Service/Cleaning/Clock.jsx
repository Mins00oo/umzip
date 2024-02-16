// Clock.jsx
import { useState } from "react";
import "./Clock.css"; // 스타일 파일을 불러옵니다.

export default function Clock({setisWhatTime ,toggleClockDropdown}) {

  const handleTimeChange = (time) => {
    setisWhatTime(time)
    toggleClockDropdown(prev =>!prev)
  };

  const generateTimeOptions = () => {
    const options = [];
    for (let i = 0; i <= 12 * 60; i += 60) { // 1시간 간격으로 변경
      const hours = Math.floor(i / 60);
      const minutes = i % 60;
      const formattedTime = `${String(hours).padStart(2, "0")}:${String(minutes).padStart(2, "0")}`;
      options.push(
        <div key={formattedTime} className="time-option" onClick={() => handleTimeChange(formattedTime)}>
          {formattedTime}
        </div>
      );
    }
    return options;
  };

  return (
    <div className="clock-container shadow" style={{height:"20rem", width:"20rem"}}>
      <div className="time-options">{generateTimeOptions()}</div>
    </div>
  );
}
