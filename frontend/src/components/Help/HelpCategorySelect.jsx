import useStore from '../../store/helpData';
import classes from './HelpCategorySelect.module.css';

function HelpCategorySelect() {
  const { setcodeSmall, fetchData, codeSmall } = useStore();

  const handleChange = (e) => {
    // value가 문자열로 오기 때문에 숫자로 변환해줘야 합니다. => 체크박스를 띄우기 위함
    const newCodeSmall = Number(e.target.value);
    setcodeSmall(newCodeSmall);
    fetchData();
  }

  return (
  <>
   <div className={`${classes.select} align-items-end`} >
      <input 
        type="radio" 
        id="all" 
        name="category" 
        value={0} 
        checked={codeSmall === 0} // 현재 선택된 값과 비교
        onChange={handleChange}
      />
      <label htmlFor="all">전체</label>
      
      <input 
        type="radio" 
        id="needHelp" 
        name="category" 
        value={401} 
        checked={codeSmall === 401} // 현재 선택된 값과 비교
        onChange={handleChange}
      />
      <label htmlFor="needHelp">도와주세요</label>
      
      <input 
        type="radio" 
        id="willHelp" 
        name="category" 
        value={402} 
        checked={codeSmall === 402} // 현재 선택된 값과 비교
        onChange={handleChange}
      />
      <label htmlFor="willHelp">도와줄게요</label>
    </div>
  </>
  );
}
export default HelpCategorySelect;