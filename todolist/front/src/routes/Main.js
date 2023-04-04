import React, { useEffect, useState } from "react";
import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { Link } from "react-router-dom";
import "./Routes.css";
import { FcTodoList } from "react-icons/fc";
import axios from "axios";

function Main() {
  // useContext을 사용하여 로그인 상태값관리
  const { isLoggedIn, setIsLoggedIn } = useContext(AuthContext);

  // const [accessToken, setAccessToken] = useState(
  //   localStorage.getItem("accessToken") || null
  // );
  // const [refreshToken, setRefreshToken] = useState(
  //   localStorage.getItem("refreshToken") || null
  // );

  // const [tokenExpiresIn, setTokenExpiresIn] = useState(
  //   localStorage.getItem("accessTokenExpiresIn") || null
  // );

  // const [refreshTokenExpiresIn, setRefreshTokenExpiresIn] = useState(
  //   localStorage.getItem("refreshTokenExpiresIn") || null
  // );

  useEffect(() => {
    // 페이지 로드 시 localStorage에서 isLoggedIn 값을 가져와 설정
    setIsLoggedIn(JSON.parse(localStorage.getItem("isLoggedIn")) || false);
  }, [setIsLoggedIn]);

  // 로그아웃 함수
  const handleLogout = () => {
    // 토큰 삭제
    // setAccessToken(null);
    // setRefreshToken(null);

    // 로컬 스토리지 전체 삭제
    window.localStorage.clear();

    // 로그인 상태 변경
    // localStorage.removeItem("isLoggedIn");
    // localStorage.removeItem("accessToken");
    // localStorage.removeItem("refreshToken");
    window.location.reload();
  };

  // 토큰 정보 함수
  const tokeninfo = () => {
    console.log("엑세스 토큰 : ", localStorage.getItem("accessToken"));
    console.log(
      "엑세스 토큰 만료시간 : ",
      localStorage.getItem("accessTokenExpiresIn") +
        " (" +
        new Date(localStorage.getItem("accessTokenExpiresIn") * 1) +
        ")"
    );
    console.log("리프레쉬 토큰 : ", localStorage.getItem("refreshToken"));
    console.log(
      "리프레쉬 토큰 만료시간 : ",
      localStorage.getItem("refreshTokenExpiresIn") +
        " (" +
        new Date(localStorage.getItem("refreshTokenExpiresIn") * 1) +
        ")"
    );
  };

  // 토큰 페이로드 함수
  const tokenPayload = () => {
    console.log(
      "토큰 페이로드",
      // atob(localStorage.getItem("accessToken").split(".")[1])
      atob(localStorage.getItem("accessToken").split(".")[1]) // 타입은 String
    );
  };

  // 만료시간 테스트 함수 - 현재시간 테스트
  const nowtime = () => {
    const now = new Date();

    console.log("현재 시간 : " + now + " (" + now.getTime() + ")");
    console.log(
      "토큰 만료 시간 : " +
        new Date(localStorage.getItem("accessTokenExpiresIn") * 1) + //자바스크립트 문자열-숫자
        " (" +
        JSON.parse(atob(localStorage.getItem("accessToken").split(".")[1]))
          .exp +
        ")"
      // localStorage.getItem("accessTokenExpiresIn")
    );
    if (
      new Date().getTime() >
      JSON.parse(atob(localStorage.getItem("accessToken").split(".")[1])).exp
    ) {
      console.log("토큰 만료");
    } else {
      console.log("토큰 유효");
    }
  };

  // refresh 테스트 함수
  const refresh = () => {
    // 조건문 - 토큰이 만료
    if (
      // new Date() > new Date(localStorage.getItem("accessTokenExpiresIn") * 1)
      new Date().getTime() >
      JSON.parse(atob(localStorage.getItem("accessToken").split(".")[1])).exp
    ) {
      console.log("토큰 만료");
      axios.get("/api/refresh", {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("accessToken"),
          refreshtoken: localStorage.getItem("refreshToken"),
        },
      });
    } else {
      console.log("토큰 유효");
      axios
        .get("/api/refresh", {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("accessToken"),
            refreshtoken: localStorage.getItem("refreshToken"),
          },
        })
        .then((res) => {
          // refresh성공
          if (res.status === 204) {
            console.log("refresh 성공!");
            console.log(res.headers.authorization);
          }
        })
        .catch((err) => {
          console.log(err);
          alert("refresh 실패!");
        });
    }
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
          <div>
            <button onClick={tokeninfo}>토큰정보 확인</button>
            <button onClick={tokenPayload}>토큰 페이로드 확인</button>
          </div>
          <div>
            <button onClick={nowtime}>현재시간 테스트</button>
          </div>
          <div>
            <button onClick={refresh}>refresh테스트</button>
          </div>
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
