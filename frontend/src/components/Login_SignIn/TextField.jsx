import { css } from "@emotion/react";
import React from "react";

const TextField = ({
  text,
  name,
  inputType = "text",
  register,
  errorMsg,
  ...others
}) => {
  console.log("in TextField", others);
  return (
    <div css={[fieldWrapper]}>
      <label htmlFor={name}>{text}</label>
      <input type={inputType} {...register(name)} css={[inputStyle]} />
      {errorMsg && <span css={[errorMsgStyle]}>{errorMsg}</span>}
    </div>
  );
};

export default TextField;

const fieldWrapper = css`
  padding: 10px;
  margin: 10px;
  padding: 10px;
  display: flex;
  align-items: flex-start;
  flex-direction: column;
  text-align: left;
`;

const inputStyle = css`
  outline: none;
  padding: 10px 0px;
  width: 100%;
  border: none;
  border-bottom: 1px solid #ddd;
  margin-bottom: 5px;
`;

const errorMsgStyle = css`
  font-size: 12px;
  color: #f00;
  line-height: 1.4;
`;