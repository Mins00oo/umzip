import React, { useState } from "react";
import { AnimatePresence, motion } from "framer-motion";
import "./PhotoView.css";
const PhotoView = ({selectedFiles,setSelectedFiles}) => {
  

  const handleFileChange = (event) => {
    const files = event.target.files;

    if (files.length > 0) {
      const updatedFiles = [...selectedFiles];

      for (const file of files) {
        const reader = new FileReader();
        reader.onload = () => {
          updatedFiles.push({
            file: file,
            previewURL: reader.result,
          });

          // 선택된 파일들을 상태에 업데이트
          setSelectedFiles([...updatedFiles]);
        };
        reader.readAsDataURL(file);
      }
    }
  };

  const removeFile = (index) => {
    const updatedFiles = [...selectedFiles];
    updatedFiles.splice(index, 1);

    // 선택된 파일들을 상태에서 제거
    setSelectedFiles([...updatedFiles]);
  };

  return (
    <div className="d-flex photoview-container" style={{ overflowX: "auto", width: "100%" }}>
      <input
        type="file"
        accept="image/*"
        id="file"
        onChange={handleFileChange}
        multiple
        style={{ display: "none" }}
      />

      {/* 선택된 파일들의 미리보기 */}
      <div className="d-flex  gap-2 shadow align-items-center  rounded-4 border solid p-2" style={{height:"10rem"}}>
        <AnimatePresence>
        {selectedFiles.map((selectedFile, index) => (
          <motion.div  
          className="d-flex flex-column justify-content-center align-items-center" key={index}>
            <img
              src={selectedFile.previewURL}
              alt={`선택된 파일 ${index + 1} 미리보기`}
              style={{ width: "7rem", height: "7rem" }}
            />
            <button className="d-flex justify-content-center align-items-center btn btn-outline-primary " style={{ width: "4rem", height: "2rem" }} onClick={() => removeFile(index)}>
              <p className="m-0">삭제</p>
            </button>
          </motion.div>
        ))}
        </AnimatePresence>
        <label htmlFor="file" style={{ display: "block" }}>
          <motion.img whileHover={{ cursor: "pointer", y: -5 }} style={{ width: "6rem", height: "6rem" }} src="/file-earmark-plus.png" />
        </label>
      </div>
    </div>
  );
};

export default PhotoView;
