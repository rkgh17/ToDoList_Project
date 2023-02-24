import logo from "./logo.svg";
import "./App.css";
import { useEffect, useState } from "react";

import { createBrowserRouter, RouterProvider } from "react-router-dom"; //6.6.2
import Main from "./routes/Main";
import Login from "./routes/Login";

const router = createBrowserRouter([
  { path: "/", element: <Main /> },
  { path: "/Login", element: <Login /> },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
