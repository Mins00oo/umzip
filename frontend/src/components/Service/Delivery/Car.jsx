// Car.jsx
import "./Car.css";
import { motion } from "framer-motion";

export default function Car({ data, setisWhatCar, toggleCarDropdown ,setCarID}) {
  const handleCar = (info) => {
    setisWhatCar(info.name);
    setCarID(info.carId)
    // console.log(info.carId)
    toggleCarDropdown();
  };

  return (
    <>
      <div className="car-container shadow">
        <div className="car-options">
          {data.map((info) => (
            <motion.div
              key={info.name}
              onClick={() => handleCar(info)}
              className="car-option"
              whileHover={{ cursor: "pointer"  }}
            >
              {info.name}
              <motion.div className="car-description-container" >
                {/* <img  style={{width:"20rem", height:"20rem" }} src={info.name === "다마스" ? "/damas.jpg" :""} />
                <img  style={{width:"20rem", height:"20rem" }} src={info.name === "라보" ? "/labo.jpg" :""} />
                <img  style={{width:"20rem", height:"20rem" }} src={info.name === "1톤 트럭" ? "/1톤 트럭.jpg" :""} />
                <img  style={{width:"20rem", height:"20rem" }} src={info.name === "1톤 리프트 트럭" ? "/1톤 리프트 트럭.jpg" :""} />
                <img  style={{width:"20rem", height:"20rem" }} src={info.name === "1톤 냉장/냉동 탑차" ? "/1톤냉장냉동탑차.jpg" :""} /> */}
                 <p className="m-2">{info.description} </p>
                  
              </motion.div>
            </motion.div>
            
          ))}
        </div>
      </div>
    </>
  );
}
