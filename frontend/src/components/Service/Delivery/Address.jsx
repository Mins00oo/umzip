import axios from 'axios';
import DaumPostcode from 'react-daum-postcode';

const apiKey = '1e72bab546a7e14fc79fcd64e5163474';

export default function Address({ whatModal, setwhereStart, setwhereEnd, setIsModalOpen ,setSigungu}) {
    const onCompletePost = async (data) => {
        
        const address = data.address;

        try {
            const response = await axios.get(`https://dapi.kakao.com/v2/local/search/address.json?query=${encodeURIComponent(address)}`, {
                headers: {
                    'Authorization': `KakaoAK ${apiKey}`,
                },
            });

            const location = response.data.documents[0];
            const latitude = location.y;
            const longitude = location.x;
            // console.log(`위도: ${latitude}, 경도: ${longitude}`);

            if (whatModal === "start") {
                console.log(data.sigunguCode)
                setSigungu(data.sigunguCode)
                setwhereStart({ address: address, lat: latitude, lon: longitude })
            }
            else { setwhereEnd({ address: address, lat: latitude, lon: longitude }) }

        } catch (error) {
            console.error('Geocoding 실패', error);
        }
        setIsModalOpen(false);
    };

    return (
        <div>
            <DaumPostcode onComplete={onCompletePost} />
        </div>
    );
}
