import React, { useState, useEffect } from 'react';
import axios from 'axios';
const Url_API = "FJuLvLTdlG7GMX22qfC5y3hlNaiB2Myg2OOSUdif"
const Map = ({ start_lat, start_lon, end_lat, end_lon , setCarDistance , setCarTime}) => {
    const [map, setMap] = useState(null);
    const [result, setResult] = useState('');
    const [resultMarkerArr, setResultMarkerArr] = useState([]);
    const [resultdrawArr, setResultDrawArr] = useState([]);
    const startLatFloat = parseFloat(start_lat);
    const startLonFloat = parseFloat(start_lon);
    const endLatFloat = parseFloat(end_lat);
    const endLonFloat = parseFloat(end_lon);
    let chktraffic = [];
    let marker_s = null
    let marker_e = null
    function convertMinutesToHoursAndMinutes(minutes) {
        const hours = Math.floor(minutes / 60);
        const remainingMinutes = (minutes % 60).toFixed(0);
        if(hours ===0) {return `${remainingMinutes}분`}
        else{return `${hours}시간 ${remainingMinutes}분`;}
        
      }
    const initTmap = () => {
        // 1. 지도 띄우기
        const fetchData = async () => {
            try {

                const mapInstance = new Tmapv2.Map("map_div", {
                    center: new Tmapv2.LatLng((startLatFloat + endLatFloat) / 2, (startLonFloat + endLonFloat) / 2),
                    width: "100%",
                    height: "400px",
                    zoom: 7,
                    zoomControl: true,
                    scrollwheel: true,
                });
                marker_s = new Tmapv2.Marker({
                    position: new Tmapv2.LatLng(startLatFloat, startLonFloat),
                    icon: "./Map_start_ping.png",
                    iconSize: new Tmapv2.Size(24, 38),
                    map: mapInstance
                });

                marker_e = new Tmapv2.Marker({
                    position: new Tmapv2.LatLng(endLatFloat, endLonFloat),
                    icon: "./Map_end_ping.png",
                    iconSize: new Tmapv2.Size(24, 38),
                    map: mapInstance
                });
                const response = await axios.post(`https://apis.openapi.sk.com/tmap/routes?version=1&format=json&callback=result&appKey=${Url_API}`, {
                    startX: start_lon,
                    startY: start_lat,
                    endX: end_lon,
                    endY: end_lat,
                    reqCoordType: "WGS84GEO",
                    resCoordType: "EPSG3857",
                    searchOption: "0",
                    trafficInfo: "Y"
                    // 추가적인 데이터 필드들
                });
                const resultData = response.data.features;
                
                setCarDistance(`${(resultData[0].properties.totalDistance/1000).toFixed(2)}km`)
                setCarTime(convertMinutesToHoursAndMinutes(resultData[0].properties.totalTime/60))
                for (const feature of resultData) {
                    const geometry = feature.geometry;
                    if (geometry.type === "LineString") {
                        // 교통 정보도 담음
                        chktraffic.push(geometry.traffic);
                        const sectionInfos = [];
                        const trafficArr = geometry.traffic;

                        for (const coord of geometry.coordinates) {
                            // 경로들의 결과값들을 포인트 객체로 변환 
                            const latlng = new Tmapv2.Point(coord[0], coord[1]);
                            // 포인트 객체를 받아 좌표값으로 변환
                            const convertPoint = new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(latlng);

                            sectionInfos.push(convertPoint);
                        }

                        // drawLine 함수 호출
                        drawLine(sectionInfos, trafficArr, mapInstance);
                    }
                }
            }
            catch (e) {

            }
        }

        // const mapInstance = new Tmapv2.Map("map_div", {
        //     center: new Tmapv2.LatLng(37.49241689559544, 127.03171389453507),
        //     width: "100%",
        //     height: "400px",
        //     zoom: 11,
        //     zoomControl: true,
        //     scrollwheel: true,
        // });
        // setMap(mapInstance);

        // 2. 시작, 도착 심볼찍기

        fetchData()
    };

    // const addComma = (num) => {
    //     const regexp = /\B(?=(\d{3})+(?!\d))/g;
    //     return num.toString().replace(regexp, ',');
    // };

    const addMarkers = (infoObj) => {
        let size = new Tmapv2.Size(24, 38);

        if (infoObj.pointType === "P") {
            size = new Tmapv2.Size(8, 8);
        }

        const marker_p = new Tmapv2.Marker({
            position: new Tmapv2.LatLng(infoObj.lat, infoObj.lng),
            icon: infoObj.markerImage,
            iconSize: size,
            map: map
        });

        setResultMarkerArr(prevArr => [...prevArr, marker_p]);
    };
    const drawLine = (arrPoint, traffic, mapInstance) => {
        let polyline_;
        if (traffic.length !== 0) {
            // 교통정보 혼잡도를 체크
            // strokeColor는 교통 정보상황에 따라 변화
            // traffic: 0-정보없음, 1-원활, 2-서행, 3-지체, 4-정체 (black, green, yellow, orange, red)
            let lineColor = '';

            if (traffic !== "0") {
                if (traffic.length === 0) {
                    // 교통정보가 없으므로 검은색으로 표시
                    lineColor = "#06050D";
                    polyline_ = new Tmapv2.Polyline({
                        path: arrPoint,
                        strokeColor: lineColor,
                        strokeWeight: 6,
                        map: mapInstance
                    });
                    setResultDrawArr(prevArr => [...prevArr, polyline_]);
                } else {
                    if (traffic[0][0] !== 0) {
                        let trafficObject = "";
                        let tInfo = [];

                        for (let z = 0; z < traffic.length; z++) {
                            trafficObject = {
                                startIndex: traffic[z][0],
                                endIndex: traffic[z][1],
                                trafficIndex: traffic[z][2],
                            };
                            tInfo.push(trafficObject);
                        }

                        let noInfomationPoint = [];

                        for (let p = 0; p < tInfo[0].startIndex; p++) {
                            noInfomationPoint.push(arrPoint[p]);
                        }

                        polyline_ = new Tmapv2.Polyline({
                            path: noInfomationPoint,
                            strokeColor: "#06050D",
                            strokeWeight: 6,
                            map: mapInstance
                        });
                        setResultDrawArr(prevArr => [...prevArr, polyline_]);

                        for (let x = 0; x < tInfo.length; x++) {
                            let sectionPoint = [];

                            for (let y = tInfo[x].startIndex; y <= tInfo[x].endIndex; y++) {
                                sectionPoint.push(arrPoint[y]);
                            }

                            if (tInfo[x].trafficIndex === 0) {
                                lineColor = "#06050D";
                            } else if (tInfo[x].trafficIndex === 1) {
                                lineColor = "#61AB25";
                            } else if (tInfo[x].trafficIndex === 2) {
                                lineColor = "#FFFF00";
                            } else if (tInfo[x].trafficIndex === 3) {
                                lineColor = "#E87506";
                            } else if (tInfo[x].trafficIndex === 4) {
                                lineColor = "#D61125";
                            }

                            polyline_ = new Tmapv2.Polyline({
                                path: sectionPoint,
                                strokeColor: lineColor,
                                strokeWeight: 6,
                                map: mapInstance
                            });
                            setResultDrawArr(prevArr => [...prevArr, polyline_]);
                        }
                    } else {
                        let trafficObject = "";
                        let tInfo = [];

                        for (let z = 0; z < traffic.length; z++) {
                            trafficObject = {
                                startIndex: traffic[z][0],
                                endIndex: traffic[z][1],
                                trafficIndex: traffic[z][2],
                            };
                            tInfo.push(trafficObject);
                        }

                        for (let x = 0; x < tInfo.length; x++) {
                            let sectionPoint = [];

                            for (let y = tInfo[x].startIndex; y <= tInfo[x].endIndex; y++) {
                                sectionPoint.push(arrPoint[y]);
                            }

                            if (tInfo[x].trafficIndex === 0) {
                                lineColor = "#06050D";
                            } else if (tInfo[x].trafficIndex === 1) {
                                lineColor = "#61AB25";
                            } else if (tInfo[x].trafficIndex === 2) {
                                lineColor = "#FFFF00";
                            } else if (tInfo[x].trafficIndex === 3) {
                                lineColor = "#E87506";
                            } else if (tInfo[x].trafficIndex === 4) {
                                lineColor = "#D61125";
                            }

                            polyline_ = new Tmapv2.Polyline({
                                path: sectionPoint,
                                strokeColor: lineColor,
                                strokeWeight: 6,
                                map: mapInstance
                            });
                            setResultDrawArr(prevArr => [...prevArr, polyline_]);
                        }
                    }
                }
            }
        } else {
            polyline_ = new Tmapv2.Polyline({
                path: arrPoint,
                strokeColor: "#DD0000",
                strokeWeight: 6,
                map: mapInstance
            });
            setResultDrawArr(prevArr => [...prevArr, polyline_]);
        }
    };


    const resettingMap = () => {
        // 기존 마커는 삭제
        console.log(marker_e)
        marker_s = null;
        marker_e = null;

        if (resultMarkerArr.length > 0) {
            resultMarkerArr.forEach(marker => marker.setMap(null));
            setResultMarkerArr([]);
        }

        if (resultdrawArr.length > 0) {
            resultdrawArr.forEach(draw => draw.setMap(null));
            setResultDrawArr([]);
        }

        chktraffic = [];
        setResult('');
    };

    useEffect(() => {
        initTmap();
    }, []);


    return (
        <div>
            <div id="map_wrap" className="map_wrap">
                <div id="map_div"></div>
            </div>

        </div>
    );
};

export default Map;
