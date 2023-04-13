/**
 * 앱 전역에서 인증 관련 정보를 관리함.
 */

import { createContext, useState } from "react";

export const AuthContext = createContext();

const AuthContextProvider = ({ children }) => {
  // const [isLoggedIn, setIsLoggedIn] = useState(
  //   JSON.parse(localStorage.getItem("isLoggedIn")) || false
  // );

  // const login = () => {};

  const logout = () => {
    console.log("로그아웃");
  };

  const contextValue = {
    //   isLoggedIn,
    //   setIsLoggedIn,
    //   login,
    logout,
  };

  return (
    <AuthContext.Provider value={contextValue}>{children}</AuthContext.Provider>
  );
};

export default AuthContextProvider;
