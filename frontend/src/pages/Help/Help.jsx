import HelpList from '../../components/Help/HelpList';
import Header from "../../components/Header";
import Chat from "../../components/Chat/Chat";
import HelpPagination from "../../components/Help/HelpPagination";




function Help() {

  return (
    <>
      <Header />
      <h1>도움 게시판</h1>
      <HelpList />
      {/* <HelpPagination /> */}
      <Chat />
    </>
  );
}
export default Help;