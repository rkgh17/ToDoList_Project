import logo from "./logo.svg";
import "./App.css";
import { useEffect, useState, useContext } from "react";
import { AuthContext } from "./context/AuthContext";
import axios from "axios";

import { createBrowserRouter, RouterProvider } from "react-router-dom"; //6.6.2
import Main from "./routes/Main";
import Login from "./routes/Login";
import SignUp from "./routes/SignUp";

const router = createBrowserRouter([
  { path: "/", element: <Main /> },
  { path: "/login", element: <Login /> },
  { path: "/signup", element: <SignUp /> },
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
        console.log(res);

        if (res.status === 200) {
          setIsLoggedIn(false);
          window.localStorage.clear();
        }
      })
      .catch((err) => {
        // 로그아웃 수행
        setIsLoggedIn(false);
        window.localStorage.clear();
      });
  };

  return (
    <AuthContext.Provider value={{ isLoggedIn, setIsLoggedIn, logout }}>
      {" "}
      {/* AuthContext.Provider 추가 */}
      <RouterProvider router={router} />
    </AuthContext.Provider>
  );
}

export default App;
