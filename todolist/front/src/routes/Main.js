import React, { useEffect, useState } from "react";
import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { Link, useNavigate } from "react-router-dom";
import "./Routes.css";
import { FcTodoList } from "react-icons/fc";
import axios from "axios";

function Main() {
  // useContext을 사용하여 로그인 상태값관리
  const { isLoggedIn, setIsLoggedIn, logout, refresh } =
    useContext(AuthContext);

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

  useEffect(() => {
    // 페이지 로드 시 토큰 만료시간 검사.
    refresh();
  }, []);

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

  // 현재시간 테스트 - 만료시간 테스트 함수
  const nowtime = () => {
    const now = new Date(); //현재시간
    const tokenExp =
      JSON.parse(atob(localStorage.getItem("accessToken").split(".")[1])).exp *
      1000; // 밀리초변환
    const refreshTokenExp =
      JSON.parse(atob(localStorage.getItem("refreshToken").split(".")[1])).exp *
      1000;

    console.log("현재 시간 : " + now + " (" + now.getTime() + ")");
    console.log(
      "토큰 만료 시간 : " +
        new Date(tokenExp) + //자바스크립트 문자열-숫자
        " (" +
        tokenExp +
        ")"
      // localStorage.getItem("accessTokenExpiresIn")
    );
    console.log(
      "리프레쉬 토큰 만료 시간 : " +
        new Date(refreshTokenExp) +
        " (" +
        refreshTokenExp +
        ")"
    );

    if (now.getTime() > tokenExp) {
      console.log("토큰 만료");
    } else {
      console.log("토큰 유효");
    }
  };

  // refresh 함수
  // const refresh = () => {
  //   const now = new Date(); //현재시간
  //   const tokenExp =
  //     JSON.parse(atob(localStorage.getItem("accessToken").split(".")[1])).exp *
  //     1000; // 밀리초변환
  //   const refreshTokenExp =
  //     JSON.parse(atob(localStorage.getItem("refreshToken").split(".")[1])).exp *
  //     1000; // 밀리초변환

  //   // 조건1 - access 토큰 체크
  //   if (
  //     // new Date() > new Date(localStorage.getItem("accessTokenExpiresIn") * 1)
  //     now.getTime() < tokenExp
  //   ) {
  //     console.log("토큰 유효");
  //   }
  //   // 조건 2 - refresh 토큰 체크
  //   else if (now.getTime() > refreshTokenExp) {
  //     console.log("refresh토큰 만료. 로그인 재 수행");
  //     logout();
  //   }
  //   // 조건 3 - 토큰 만료
  //   else {
  //     console.log("토큰 만료");
  //     axios
  //       .get("/api/refresh", {
  //         headers: {
  //           Authorization: "Bearer " + localStorage.getItem("accessToken"),
  //           refreshtoken: localStorage.getItem("refreshToken"),
  //         },
  //       })
  //       .then((res) => {
  //         // refresh성공
  //         if (res.status === 204) {
  //           console.log("refresh 성공!");
  //           // console.log(res.headers.authorization);

  //           // 엑세스 토큰 새로고침 - 로컬 스토리지 저장
  //           localStorage.removeItem("accessToken");
  //           localStorage.setItem(
  //             "accessToken",
  //             res.headers.authorization.substr(7)
  //           ); // Bearer 제거
  //         }
  //       })
  //       .catch((err) => {
  //         console.log(err);
  //         alert("refresh 실패.");
  //         setIsLoggedIn(false);
  //         window.localStorage.clear();
  //         navigate("/login");
  //       });
  //   }
  // };

  // 리프레쉬 로그아웃 테스트
  // const refreshlogout = () => {
  //   console.log("리프레쉬 로그아웃 수행");
  //   axios
  //     .get("/api/logout", {
  //       headers: {
  //         sub: JSON.parse(
  //           atob(localStorage.getItem("refreshToken").split(".")[1])
  //         ).sub,
  //       },
  //     })
  //     .then((res) => {
  //       console.log(res);
  //       if (res.status === 200) {
  //         setIsLoggedIn(false);
  //         window.localStorage.clear();
  //         navigate("/login");
  //       }
  //     })
  //     .catch((err) => {
  //       // 로그아웃 수행
  //       setIsLoggedIn(false);
  //       window.localStorage.clear();
  //       navigate("/login");
  //     });
  // };

  // 네비게이터
  // const navigate = useNavigate();

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
          <div>
            <Link to="mylist">
              <button>리스트로 이동하기</button>
            </Link>
          </div>
          <button onClick={logout}>로그아웃</button>
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
          {/* <div>
            <button onClick={refreshlogout}>리프레쉬 로그아웃 테스트</button>
          </div> */}
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
