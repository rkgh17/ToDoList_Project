import React from "react";
import { useEffect, useState } from "react";

import "./Routes.css";
import { FcTodoList } from "react-icons/fc";

function Main() {
  return (
    <div className="Main">
      <header className="Header">
        <h2 style={{ color: "blue" }}>
          <FcTodoList size="180" /> <br />
          체계적인 생활
        </h2>
        <button className="">무료로 시작하기</button>
      </header>
    </div>
  );
}

export default Main;
