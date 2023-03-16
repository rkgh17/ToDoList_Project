import React, { useEffect } from "react";
// import { useEffect, useState } from "react";
import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { Link } from "react-router-dom";
import "./Routes.css";
import { FcTodoList } from "react-icons/fc";

function Main() {
  const { isLoggedIn, setIsLoggedIn } = useContext(AuthContext);

  useEffect(() => {
    // 페이지 로드 시 localStorage에서 isLoggedIn 값을 가져와 설정
    setIsLoggedIn(JSON.parse(localStorage.getItem("isLoggedIn")) || false);
  }, [setIsLoggedIn]);

  return (
    <div className="Main">
      <header className="Header_main">
        <h2 style={{ color: "blue" }}>
          <FcTodoList size="180" /> <br />
          To - Do - List
        </h2>
      </header>

      {isLoggedIn ? (
        <button>로그아웃</button>
      ) : (
        <Link to="/login">
          <button className="">로그인</button>
        </Link>
      )}
    </div>
  );
}

export default Main;
