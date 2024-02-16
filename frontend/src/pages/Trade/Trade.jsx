import { useNavigate, useLocation } from "react-router-dom";
import { useEffect, useState } from "react";

import TradesList from "../../components/Trade/TradesList";
import SearchBar from "../../components/Trade/SearchBar";
import Header from "../../components/Header";
import Chat from "../../components/Chat/Chat";
import classes from "./Trade.module.css";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCirclePlus } from "@fortawesome/free-solid-svg-icons";

import { api } from "../../services/api";

function Trade() {
  const navigate = useNavigate();
  const [trades, setTrades] = useState([]);
  const [page, setPage] = useState(1);
  const pageSize = 12;
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(false);
  const [hasMore, setHasMore] = useState(true);

  const getTrades = async () => {
    setLoading(true);
    try {
      const queryString = `keyword=${searchTerm}&page=${page}&size=${pageSize}`;
      const response = await api.get(`/trade-items?${queryString}`);
      const newTrades = response.data.result || [];

      setTrades((prevTrades) => [...prevTrades, ...newTrades]);
      setHasMore(newTrades.length === pageSize);
    } catch (error) {
      console.error("Failed to fetch trades", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    getTrades();
  }, [searchTerm, page]);

  const handleLoadMore = () => {
    if (!loading && hasMore) {
      setPage((prevPage) => prevPage + 1);
    }
  };

  const handleSearch = (term) => {
    setTrades([]);
    setPage(1);
    setSearchTerm(term);
  };

  return (
    <>
      <Header />
      <Chat />
      <article style={{ width: "90%", margin: "0 auto", marginTop: "8rem" }}>
        <div className={`${classes.head} row`}>
          <div
            className="col-10"
            style={{ display: "flex", alignItems: "center" }}
          >
            <h1 style={{ marginLeft: "20px" }}>중고 거래</h1>
            <img
              src="/saleIcon2.gif"
              style={{ width: "4rem", height: "4rem", marginLeft: "7px" }}
            />
          </div>
          <div
            className={`${classes.search} col-2 d-flex flex-column justify-content-end`}
          >
            <SearchBar onSearch={handleSearch} />
          </div>
          <hr
            style={{
              margin: "0 auto",
              width: "98%",
              border: "0",
              borderTop: "3px solid #b6d8f8",
              marginTop: '30px'
            }}
          />
        </div>

        <div className="row"></div>
        {trades.length > 0 ? (
          <>
            <TradesList trades={trades} />
            {hasMore && (
              <div className={classes.more}>
                <button
                  onClick={handleLoadMore}
                  className={classes.moreBtn}
                  disabled={loading}
                >
                  <FontAwesomeIcon
                    icon={faCirclePlus}
                    style={{ paddingRight: "10px" }}
                  />
                  더보기
                </button>
              </div>
            )}
          </>
        ) : (
          <div style={{ marginLeft: "10%" }}>검색결과가 없습니다.</div>
        )}
        <div className={classes.writing}>
          <button
            onClick={() => navigate("/tradewriting")}
            className={classes.writingBtn}
          >
            글 쓰기
          </button>
        </div>
      </article>
    </>
  );
}

export default Trade;
