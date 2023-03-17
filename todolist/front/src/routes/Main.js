import React, { useEffect, useState } from "react";
import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { Link } from "react-router-dom";
import "./Routes.css";
import { FcTodoList } from "react-icons/fc";

function Main() {
  // 로그인 상태값
  const { isLoggedIn, setIsLoggedIn } = useContext(AuthContext);

  const [accessToken, setAccessToken] = useState(
    localStorage.getItem("accessToken") || null
  );
  const [refreshToken, setRefreshToken] = useState(
    localStorage.getItem("refreshToken") || null
  );

  useEffect(() => {
    // 페이지 로드 시 localStorage에서 isLoggedIn 값을 가져와 설정
    setIsLoggedIn(JSON.parse(localStorage.getItem("isLoggedIn")) || false);
  }, [setIsLoggedIn]);

  // 로그아웃 함수
  const handleLogout = () => {
    // 토큰 삭제
    setAccessToken(null);
    setRefreshToken(null);

    // 로그인 상태 변경
    setIsLoggedIn(false);
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
  };

  // 토큰 정보
  const tokeninfo = () => {
    console.log("엑세스 토큰 : ", localStorage.getItem("accessToken"));
    console.log("리프레쉬 토큰 : ", localStorage.getItem("refreshToken"));
  };

  return (
    <div className="Main">
      <header className="Header_main">
        <h2 style={{ color: "blue" }}>
          <FcTodoList size="180" /> <br />
          To - Do - List
        </h2>
      </header>

      {isLoggedIn ? (
        <div>
          <button onClick={handleLogout}>로그아웃</button>
          <button onClick={tokeninfo}>토큰정보 확인</button>
        </div>
      ) : (
        <Link to="/login">
          <button className="">로그인</button>
        </Link>
      )}
    </div>
  );
}

export default Main;
