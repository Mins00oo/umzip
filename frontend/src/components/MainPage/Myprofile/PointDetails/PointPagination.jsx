import style from './PointPagination.module.css'
import useStorePoint from '../../../../store/pointData';
import { useEffect } from "react";


function PointPagination() {
    const { fetchData, data, page, setPage } = useStorePoint(); // 현재 페이지와 페이지 변경 함수, 총 페이지 수를 가져옴
    const totalPages = data?.result?.totalPage || 1;
    console.log(data?.result)
    const pages = Array.from({ length: totalPages }, (_, i) => i + 1);
    
    useEffect(() => {
      fetchData();
    }, [ fetchData, page ]);

    
    return (
    <>
        <div className={style.pagination}>
  {page > 1 && (
    <button 
    className={style.arrow}
    onClick={() => setPage(page - 5 > 0 ? page - 5 : 1)}> {'<'} </button>
  )}

  {pages.slice((Math.ceil(page / 5) - 1) * 5, (Math.ceil(page / 5) * 5)).map((pageNumber) => (
    <button
      key={pageNumber}
      className={page === pageNumber ? style.active : style.unActive}
      onClick={() => setPage(pageNumber)}
    >
      {pageNumber}
    </button>
  ))}

  {page < totalPages && (
    <button 
    className={style.arrow}
    onClick={() => setPage(page + 5 < totalPages ? page + 5 : totalPages)}> {'>'} </button>
  )}
</div>

    </>
    );
  }

  export default PointPagination;