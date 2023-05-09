import logo from "./logo.svg";
import "./App.css";
import { useEffect, useState, useContext } from "react";
import { AuthContext } from "./context/AuthContext";
import axios from "axios";
import { createGlobalStyle } from "styled-components";

import { createBrowserRouter, RouterProvider } from "react-router-dom"; //6.6.2
import Main from "./routes/Main";
import Login from "./routes/Login";
import SignUp from "./routes/SignUp";
import Mylist from "./routes/Todo/Mylist";

// styled-components
const GlobalStyle = createGlobalStyle`
  body {
    background: #e9ecef;
  }
`;

const router = createBrowserRouter([
  { path: "/", element: <Main /> },
  { path: "/login", element: <Login /> },
  { path: "/signup", element: <SignUp /> },
  { path: "/mylist", element: <Mylist /> },
]);

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  // 로그인 상태관리
  useEffect(() => {
    const loggedIn = localStorage.getItem("isLoggedIn") === "true";
    setIsLoggedIn(loggedIn);
  }, []);

  // 로그아웃 함수
  const logout = () => {
    // 로그아웃 api get 요청
    axios
      .get("/api/logout", {
        headers: {
          sub: JSON.parse(
            atob(localStorage.getItem("refreshToken").split(".")[1])
          ).sub,
        },
      })
      .then((res) => {
        // console.log(res);

        if (res.status === 200) {
          setIsLoggedIn(false);
          window.localStorage.clear();
          // console.log("로그아웃 - 200");
        }
      })
      .catch((err) => {
        // 로그아웃 수행
        setIsLoggedIn(false);
        window.localStorage.clear();
        // console.log("로그아웃?");
      });
  };

  // refresh 로직
  const refresh = () => {
    // 메인 조건문 - 로그인 되어있는가.
    if (localStorage.getItem("isLoggedIn") === "true") {
      // refresh에 필요한 현재 시간과 각 토큰들의 만료시간
      const now = new Date(); //현재시간
      const tokenExp =
        JSON.parse(atob(localStorage.getItem("accessToken").split(".")[1]))
          .exp * 1000; // 밀리초변환
      const refreshTokenExp =
        JSON.parse(atob(localStorage.getItem("refreshToken").split(".")[1]))
          .exp * 1000; // 밀리초변환

      // 조건1 - access 토큰 체크
      // 현재 시간이 토큰 만료시간보다 작으면 토큰 유효
      if (
        // new Date() > new Date(localStorage.getItem("accessTokenExpiresIn") * 1)
        now.getTime() < tokenExp
      ) {
        // console.log("토큰 유효");
      }
      // 조건 2 - refresh 토큰 체크
      // 현재 시간이 refresh 토큰 만료시간보다 크면 강제로 로그아웃 수행
      else if (now.getTime() > refreshTokenExp) {
        // console.log("refresh토큰 만료. 로그인 재 수행");
        alert("로그인이 만료되었습니다. 다시 로그인 해 주세요.");
        logout();
      }
      // 조건 3 - 토큰 만료
      // 토큰이 만료되었으므로, 백엔드와 통신하여 새 엑세스 토큰 발급
      else {
        console.log("토큰 만료");
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
              // console.log(res.headers.authorization);

              // 엑세스 토큰 새로고침 - 로컬 스토리지 저장
              localStorage.removeItem("accessToken");
              localStorage.setItem(
                "accessToken",
                res.headers.authorization.substr(7)
              ); // Bearer 제거

              // console.log("refresh 성공!");
            }
          })
          .catch((err) => {
            // console.log(err);
            alert("로그인이 만료되었습니다. 다시 로그인 해 주세요.");
            setIsLoggedIn(false);
            window.localStorage.clear();
            window.location.replace("/login");
          });
      }
    } else {
      // 메인 조건문2 : 로그아웃 상태
      window.location.replace("/login");
    }
  };

  return (
    <AuthContext.Provider
      value={{ isLoggedIn, setIsLoggedIn, logout, refresh }}
    >
      {" "}
      {/* AuthContext.Provider 추가 */}
      <GlobalStyle />
      <RouterProvider router={router} />
    </AuthContext.Provider>
  );
}

export default App;
