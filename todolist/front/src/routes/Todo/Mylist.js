import "../css/MylistCSS.css";
import TodoTemplate from "./TodoTemplate";

import React, { useEffect } from "react";
import { createGlobalStyle } from "styled-components";
import { useContext } from "react";
import { AuthContext } from "../../context/AuthContext";
import { Link } from "react-router-dom";

import TodoHead from "./TodoHead";
import TodoList from "./TodoList";
import TodoCreate from "./TodoCreate";

function Mylist() {
  const { refresh, logout } = useContext(AuthContext);

  useEffect(() => {
    // 페이지 로드 시 토큰 만료시간 검사.
    refresh();
  }, []);

  return (
    // <div className="Mylist">
    <TodoTemplate>
      <TodoHead />
      <TodoList />
      <TodoCreate />
    </TodoTemplate>
    // </div>
  );
}

export default Mylist;