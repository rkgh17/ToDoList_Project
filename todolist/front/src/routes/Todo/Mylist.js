import "../css/MylistCSS.css";
import TodoTemplate from "./TodoTemplate";

import React from "react";
import { createGlobalStyle } from "styled-components";
import { Link } from "react-router-dom";

function Mylist() {
  return (
    <div className="Mylist">
      <TodoTemplate>
        <div>
          <h2 style={{ color: "blue" }}>TO DO</h2>
        </div>
        <div></div>
        <div>
          <Link to="/">
            <button>메인으로</button>
          </Link>
        </div>
      </TodoTemplate>
    </div>
  );
}

export default Mylist;
