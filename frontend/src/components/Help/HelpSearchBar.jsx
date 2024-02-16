import { useState } from 'react';
import useStore from '../../store/helpData';
import style from './HelpSearchBar.module.css'


function HelpSearchBar() {
  // 변화감지 변수
  const { setKeyword, fetchData } = useStore();
  const [localKeyword, setLocalKeyword] = useState(''); // 로컬 상태 추가\\

  const handleKeywordChange = (e) => {
    setLocalKeyword(e.target.value)
  }

      // Enter 키를 눌렀을 때 검색을 실행하는 함수
      const handleKeyDown = (e) => {
        if (e.key === 'Enter') {
          setKeyword(localKeyword);
          fetchData();
        }
      };

  return (
    <div className={style.inputbox}>
      <input 
      required="required" 
      type="text"
      value={localKeyword}
      onChange={handleKeywordChange}
      onKeyDown={handleKeyDown}
      />
      <span>검색</span>
      <i></i>
    </div>
  );
}

export default HelpSearchBar;
