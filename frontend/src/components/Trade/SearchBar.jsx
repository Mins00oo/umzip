import React, { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';
import classes from './SearchBar.module.css';

function SearchBar({ onSearch }) {
  const [searchTerm, setSearchTerm] = useState('');

  const handleSearchChange = (event) => {
    setSearchTerm(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    onSearch(searchTerm);
  };

  return (
    <div className={classes.search}>
      <form onSubmit={handleSubmit} className={classes.searchBox}>
        <input
          className={classes.searchTxt}
          type="text"
          placeholder="검색어 입력"
          value={searchTerm}
          onChange={handleSearchChange}
        />
        <button className={classes.searchBtn} type="submit">
          <FontAwesomeIcon icon={faMagnifyingGlass} />
        </button>
      </form>
    </div>
  );
}

export default SearchBar;
