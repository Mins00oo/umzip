import { Link } from 'react-router-dom';

import classes from './TradeItem.module.css';
import styled from '@emotion/styled';

function TradeItem({ trade }) {
  return (
    <Link to={`/trade/${trade.boardId}`}>
      <div className={classes.item}>
        <img className={`${classes.thumbnailImg}`}src={trade.thumbnailPath} alt={trade.title} />
        <div className={classes.content}>
          <div>{trade.title}</div>
          <address>{trade.address}</address>
          <p className={classes.price}>{trade.price.toLocaleString('ko-KR')}원</p>
        </div>
      </div>
    </Link>
  );
}

export default TradeItem;
