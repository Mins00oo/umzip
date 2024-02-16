import { AnimatePresence, motion } from "framer-motion"
import { useEffect, useState } from "react"
import { api } from "../../../services/api"
// import AddButton from "./AddBlueButton"
import AddBlueButton from "./AddBlueButton"
import AddRedButton from "./AddRedButton"
import StarRating from "../../Recommend/StarRating"
export default function UsedReview({setStatus, setTradeId, tradeId }) {
    const [tags, setTags] = useState(null)
    const [step, setStep] = useState("first")
    console.log(tradeId)
    useEffect(() => {
        const fetch = async () => {
            const res = await tagDetail()
        }
        fetch()
        // setTradeId({memberId:15 , })
    }, [])
    const [option, setOption] = useState({})
    const [rating, setRating] = useState(5)
    const [isloading, setIsLoading] = useState(false)
    const [userinput, setuserinput] = useState("")
    const tagDetail = async () => {
        let service = "TRADE"

        // console.log(service)
        try {
            const response = await api.get(`/tags/tagType?role=${service}`,

            );
            console.log(response.data)
            setTags(response.data)
            return response.data
        } catch (error) {
            console.error(error);
        }
    }
    const createReview = async () => {
        let service = "TRADE"
        console.log(tradeId)
        const tag = Object.keys(option).map(Number);
        console.log(tag)
        // setIsLoading(true)
        try {
            const response = await api.post(`/reviews/insert`,
                {
                    "to": tradeId,
                    "role": "USER",
                    "reviewType":"TRADE",
                    "score": rating,
                    "tag": tag,
                    "comment": userinput,
                    "point": 50
                }
            );

            console.log(response.data)
            // setTags(response.data)
            return response.data
        } catch (error) {
            console.error(error);
        }
    }
    const [tagList, setTagList] = useState(null)
    const handleClose = () => {
        setTradeId(null)
        setTagList(null)
        setStatus("first")
    }
    const handleAddButtonClick = (buttonName) => {
        // Copy the current state
        const updatedOption = { ...option };

        // Toggle the value for the clicked button
        updatedOption[buttonName] = !updatedOption[buttonName];

        // Count the number of selected options
        const selectedCount = Object.values(updatedOption).filter(value => value).length;

        // Check if the count exceeds 3
        if (selectedCount > 3) {
            // If more than 3 are selected, prevent further selection
            alert("최대 3개만 선택가능합니다")
            return;
        }

        // Filter out the property if it is set to false
        const filteredOption = Object.fromEntries(
            Object.entries(updatedOption).filter(([key, value]) => value !== false)
        );

        // Update the state with the new values
        setOption(filteredOption);
        console.log(filteredOption);
    };
    const goToNextForm = () => {
        if (step === "first") {
            setStep("second")
        }
        else if (step === "second") {
            setIsLoading(true)
            createReview()
            setTimeout(() => {
                setIsLoading(false);
                setTradeId(null)
                setTags(null)
                setStatus("first")
            }, 5000);

        }
    }
    const goToBeforeForm = () => {
        if (step === "second") {
            setStep("first")
        }
    }
    return <>
        {tradeId && tags && !isloading && <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} onClick={handleClose}
            style={{
                zIndex: "99",
                position: 'fixed',
                top: 0,
                left: 0,
                width: '100%',
                height: '100%',
                backgroundColor: 'rgba(0, 0, 0, 0.5)', // 배경색 및 투명도 조절
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
            }}>
            <div onClick={(event) => event.stopPropagation()}
                style={{
                    position: 'relative',
                    width: '50%',
                    height: "80%",

                    backgroundColor: 'white', // 내용의 배경색
                    padding: '3rem',
                    borderRadius: '8px', // 내용의 모서리 둥글게    
                }}>
                <AnimatePresence mode="wait">

                    {step === "first" ? <motion.div key="first" initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} >
                        <div className="text-center mb-3" >
                            <h5>후기를 작성해주세요</h5>
                        </div>
                        <div className="text-center mb-3">
                            최대 3개의 태그를 선택해주세요!
                        </div>
                        <div className="mt-1 d-flex flex-wrap gap-4 justify-content-center align-items-center">
                            {tags.map((item) => (
                                item.tagType === 1
                                    ? <div className="col-3"><AddBlueButton key={item.tagId} name={item.tagName} value={item.tagId} isActive={option[item.tagId]} handleAddButtonClick={handleAddButtonClick} /></div>
                                    : null
                            ))}
                            {tags.map((item) => (
                                item.tagType === 0
                                    ? <div className="col-3"><AddRedButton key={item.tagId} name={item.tagName} value={item.tagId} isActive={option[item.tagId]} handleAddButtonClick={handleAddButtonClick} /></div>
                                    : null
                            ))}
                        </div>
                        <div style={{ position: "absolute", top: "90%", left: "85%" }}>
                            <button onClick={goToNextForm} style={{ width: "5rem", height: "2rem" }} className="btn btn-light p-1 "> <p className="m-0 fw-bold" style={{ fontSize: "0.75rem" }}>다음으로</p></button>
                        </div>
                    </motion.div> : null}


                    {step === "second" ? <motion.div key="second" initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} className="d-flex" style={{ width: "100%", height: "100%" }}>
                        <div style={{ position: "absolute", top: "10%", left: "10%" }}>
                            <button onClick={goToBeforeForm} style={{ width: "5rem", height: "2rem" }} className="btn btn-light p-1 "> <p className="m-0 fw-bold" style={{ fontSize: "0.75rem" }}>이전으로</p></button>
                        </div>
                        <div className="d-flex flex-column justify-content-center align-items-center gap-4" style={{ width: "100%", height: "100%" }}>
                            <div className="d-flex flex-column justify-content-center align-items-center col-12" >
                                <p>평점을 매겨주세요</p>
                                <div class="form-group">
                                    <input
                                        type="range"
                                        class="form-range"
                                        id="rangeInput"
                                        min="0"
                                        max="5"
                                        step="0.5"
                                        value={rating}
                                        onChange={(event) => setRating(parseFloat(event.target.value))}
                                    />

                                </div>
                                <div className="d-flex flex-column justify-content-center align-items-center">
                                    <output className="form-range-value">{rating}</output>
                                    {rating == 0 ? (
                                        <div style={{ height: "1.25rem", width: "7.5em" }}>&nbsp;</div>
                                    ) : (
                                        <StarRating rating={rating} />
                                    )}
                                </div>

                            </div>
                            <div className="d-flex flex-column justify-content-center align-items-center">
                                <p className="m-0">후기를 남겨주세요</p>
                                <textarea
                                    className="shadow border rounded-3 p-3"
                                    value={userinput}
                                    onChange={(event) => setuserinput(event.target.value)}
                                    placeholder="여기에 후기를 적어주세요..."
                                    rows={4}  // 원하는 행 수로 조절
                                    style={{ width: "100%", resize: "none" }}
                                />
                            </div>
                        </div>
                        <div style={{ position: "absolute", top: "90%", left: "85%" }}>
                            <button onClick={goToNextForm} style={{ width: "5rem", height: "2rem" }} className="btn btn-light p-1 "> <p className="m-0 fw-bold" style={{ fontSize: "0.75rem" }}>제출하기</p></button>
                        </div>
                    </motion.div> : null}

                </AnimatePresence>
            </div>
        </motion.div>}
        {isloading && <AnimatePresence>
            {(
                isloading && <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }}
                    style={{
                        zIndex: "99",
                        position: 'fixed',
                        top: 0,
                        left: 0,
                        width: '100%',
                        height: '100%',
                        backgroundColor: 'rgba(0, 0, 0, 0.5)', // 배경색 및 투명도 조절
                        display: 'flex',
                        justifyContent: 'center',
                        alignItems: 'center',
                    }}>
                    <div className="d-flex align-items-center  justify-content-center gap-5" style={{
                        position: 'relative',
                        width: '40%',
                        height: "70%",
                        backgroundColor: 'white', // 내용의 배경색
                        padding: '20px',
                        borderRadius: '8px', // 내용의 모서리 둥글게
                    }}>
                        <div className="d-flex justify-content-center align-items-center gap-5 bg-white " >
                            <img style={{ width: "20rem", height: "20rem" }} src="/free-animated-icon-verified-7920939.gif" />
                            <h3 style={{ color: "black" }}>후기 작성중...</h3>
                            {/* <Wave /> */}
                        </div>

                    </div>
                </motion.div>
            )}
        </AnimatePresence>}
    </>
}