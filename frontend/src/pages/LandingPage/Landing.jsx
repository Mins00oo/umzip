import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';

import Header from '../../components/Header';
import Chat from '../../components/Chat/Chat';
import { Container, Row, Col } from 'react-bootstrap';
import { motion } from "framer-motion"

function Trade() {
  const navigate = useNavigate()
  const titleFont = {
    fontFamily: 'JalnanGothic',
    src: "url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_231029@1.1/JalnanGothic.woff') format('woff')",
    fontWeight: 'normal',
    fontStyle: 'normal'
  };
  const titleImageStyle = {
    height: '100vh',
    width: '100%',
    objectFit: 'cover',
  };
  const handleClick = () =>{
    navigate("/login")
  }
  return (
    <>
      <Container fluid style={{ backgroundColor: '#eff7ff', width: '100%', height: '100%' }} >
        <Row>
          <Col>
          </Col>
        </Row>
        <Row className="mb-5">

          <Col lg={1}></Col>
          <Col sm={12} lg={12}>
            <div style={{ position: "relative" }}>
              <img src="/titleImage.gif" style={titleImageStyle} />
              <div className='d-flex justify-content-center align-items-center' style={{ position: "absolute", top: "50%", left: "50%", transform: "translate(-50%, -50%)" }}>
                <motion.button
                  whileHover={{ background: 'linear-gradient(to right bottom, #0052D4 , #6FB1FC)' }}
                  className="p-4 btn btn-primary rounded-5 d-flex justify-content-center align-items-center "
                  onClick={handleClick}
                  style={{
                    background: 'linear-gradient(to right bottom, #6FB1FC, #0052D4)',
                    width: '10rem', height: '4rem',
                    border: 'none',
                    color: '#fff', // 텍스트 색상 조정
                    borderRadius: '8px', // 둥근 모서리 조정
                    boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.2)', // 그림자 효과 추가
                  }}
                >
                  <p className="m-0">움집과 함께하기</p>

                </motion.button>
              </div>
            </div>
          </Col>
          <Col lg={1}></Col>
        </Row>

        <Row className="mt-5" >
          <Col lg={1}></Col>
          <Col sm={12} lg={10} >

            <div className='d-flex justify-content-center align-items-center mt-4'>
              <h1 style={titleFont}> ✨ 움집에서만 가능한 혜택 ✨</h1>
            </div>
          </Col>
          <Col lg={1}></Col>


          <Col lg={1}></Col>
          <Col className='p-2' sm={6} lg={3}>
            <div className='p-2' style={{ backgroundColor: '#e3f5ff', border: 'solid 4px #fefefe', borderRadius: '10px' }}>
              <div className='d-flex justify-content-center align-items-center'>
                <h4> 🛒 모호한 가격은 끝!</h4>
              </div>
              <div className='d-flex justify-content-center align-items-center'>
                <div>
                  <p>항상 궁금했던 가격, 이제 계산기로 단번에 비교해요.</p>
                </div>
              </div>
            </div>
          </Col>
          <Col className='p-2' sm={6} lg={3}>
            <div className='p-2' style={{ backgroundColor: '#e3f5ff', border: 'solid 4px #fefefe', borderRadius: '10px' }}>
              <div className='d-flex justify-content-center align-items-center'>
                <h4> 💪 믿음직한 업체 </h4>
              </div>
              <div className='d-flex justify-content-center align-items-center'>
                <div>
                  <p>사업자 등록증이 인증된 업체만을 선정하여 제공해요.</p>
                </div>
              </div>
            </div>
          </Col>
          <Col className='p-2' sm={6} lg={3}>
            <div className='p-2' style={{ backgroundColor: '#e3f5ff', border: 'solid 4px #fefefe', borderRadius: '10px' }}>
              <div className='d-flex justify-content-center align-items-center'>
                <h4> 💎 돈이되는 짐정리 </h4>
              </div>
              <div className='d-flex justify-content-center align-items-center'>
                <div>
                  <p>중고 판매 기능으로, 필요없는 물건을 판매해요.</p>
                </div>
              </div>
            </div>
          </Col>
          <Col lg={2}></Col>

          <Col lg={2}></Col>
          <Col className='p-2' sm={6} lg={3}>
            <div className='p-2' style={{ backgroundColor: '#e3f5ff', border: 'solid 4px #fefefe', borderRadius: '10px' }}>
              <div className='d-flex justify-content-center align-items-center'>
                <h4> 🧔 동네 추천 업체</h4>
              </div>
              <div className='d-flex justify-content-center align-items-center'>
                <div>
                  <p>내 주변의 원하는 기사님들을 골라보세요.</p>
                </div>
              </div>
            </div>
          </Col>
          <Col className='p-2' sm={6} lg={3}>
            <div className='p-2' style={{ backgroundColor: '#e3f5ff', border: 'solid 4px #fefefe', borderRadius: '10px' }}>
              <div className='d-flex justify-content-center align-items-center'>
                <h4> 👓 간편한게 최고야 </h4>
              </div>
              <div className='d-flex justify-content-center align-items-center'>
                <div>
                  <p>대시보드로 한 눈에 모든 정보를 획득해보아요</p>
                </div>
              </div>
            </div>
          </Col>
          <Col className='p-2' sm={6} lg={3}>
            <div className='p-2' style={{ backgroundColor: '#e3f5ff', border: 'solid 4px #fefefe', borderRadius: '10px' }}>
              <div className='d-flex justify-content-center align-items-center'>
                <h4> 💗 따뜻한 이웃간 정</h4>
              </div>
              <div className='d-flex justify-content-center align-items-center'>
                <div>
                  <p>도움을 주고 받으며 나누는 정과 포인트</p>
                </div>
              </div>
            </div>
          </Col>
          <Col lg={1}></Col>
        </Row>

        <Row className='mt-5 mb-5 p-3 pt-5 pd-5' style={{ backgroundColor: '#04001e' }}>
          <Col lg={1}></Col>
          <Col className='mt-5 mb-5' md={6} lg={5} style={{ color: '#fff' }}>
            <div className='d-flex justify-content-center align-items-center pt-4'>
              <h1 style={titleFont}>우리의 화력</h1>
            </div>
            <div className='d-flex justify-content-center align-items-center pt-3'>
              <div>
                <h5>
                  움집 사람들은 경량화된 트럭부터, <br />
                  대형 트럭까지 다양한 수요를 이용할 수 있습니다.  <br />
                  그 과정에서의 고민들을 단축하고  <br />
                  즐거움으로 이끌어 내는 것이 저희의 사명입니다. <br />  <br />
                  강인한 트럭과 움집임들의 땀방울이, <br />
                  헛되지 않게 상호간의 행복으로 남을 수 있길 바랍니다.  <br />

                </h5>
              </div>
            </div>


          </Col>
          <Col className='mt-5 mb-5' md={6} lg={5}>
            <div>
              <iframe
                width="100%"
                height="315"
                src="https://www.youtube.com/embed/jTrqSJ6GiNM?si=KFgMWQTvUXoqq7ED"
                title="YouTube video player"
                frameborder="0"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
                allowfullscreen>

              </iframe>
            </div>
          </Col>
          <Col lg={1}></Col>
        </Row>



        <Row className='p-3 mt-5'>

          <Col sm={12} >
            <div className='d-flex justify-content-center align-items-center'>
              <h1 style={titleFont}> 전문가들이 인정한 움집</h1>
            </div>
          </Col>
          <Col sm={12} > <hr /> </Col>

          <Col xxl={1}></Col>
          <Col className="p-1 mt-2" sm={12} md={12} lg={12} xxl={10} >

            <img src="/prize1.png" style={{ width: '25%' }} />
            <img src="/prize2.png" style={{ width: '25%' }} />
            <img src="/prize3.png" style={{ width: '25%' }} />
            <img src="/prize4.png" style={{ width: '25%' }} />
            <table className='mt-3' style={{ width: '100%' }}>
              <tr>
                <td style={{ width: '25%', textAlign: 'center' }}>
                  <h6>"이만한 사업장 보기 힘들죠"</h6>
                </td>
                <td style={{ width: '25%', textAlign: 'center' }}>
                  <h6>"떠돌이 생활하는 저에게 빛같은 존재"</h6>
                </td>
                <td style={{ width: '25%', textAlign: 'center' }}>
                  <h6>"불타오를 정도의 온정을 느꼈습니다"</h6>
                </td>
                <td style={{ width: '25%', textAlign: 'center' }}>
                  <h6>"여길 거치지 않을 물건이 없을정도?"</h6>
                </td>
              </tr>
            </table>

          </Col>
          <Col xxl={1}></Col>
        </Row>


      </Container>
    </>
  );
}

export default Trade;