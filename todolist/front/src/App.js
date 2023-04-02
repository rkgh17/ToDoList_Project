import logo from "./logo.svg";
import "./App.css";
import { useEffect, useState } from "react";
import { AuthContext } from "./context/AuthContext";

import { createBrowserRouter, RouterProvider } from "react-router-dom"; //6.6.2
import Main from "./routes/Main";
import Login from "./routes/Login";
import SignUp from "./routes/SignUp";

const router = createBrowserRouter([
  { path: "/", element: <Main /> },
  { path: "/login", element: <Login /> },
  { path: "/signup", element: <SignUp /> },
  // { path: "/refresh", element: <Refresh /> },
]);

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const loggedIn = localStorage.getItem("isLoggedIn") === "true";
    setIsLoggedIn(loggedIn);
  }, []);

  return (
    <AuthContext.Provider value={{ isLoggedIn, setIsLoggedIn }}>
      {" "}
      {/* AuthContext.Provider 추가 */}
      <RouterProvider router={router} />
    </AuthContext.Provider>
  );
}

export default App;
