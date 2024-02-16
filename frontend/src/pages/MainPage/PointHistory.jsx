import Header from "../../components/Header";
import PointDetails from "../../components/MainPage/Myprofile/PointDetails/PointDetails"
import PointPagination from "../../components/MainPage/Myprofile/PointDetails/PointPagination";
import BackButton from "../../components/PublicUse/BackButton";


export default function PointHistory() {

  return (
  <>
    <Header />
    <BackButton />
    
    <div className="row" style={{marginTop: '50px'}}>
    <PointDetails/>
    </div>
    <div className="row">
      <dir className="col-5"></dir>
      <dir className="col-2">
      <PointPagination />
      </dir>
      <dir className="col-5"></dir>
    
    </div>
    
  </>
  );
}
