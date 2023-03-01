import React from "react";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "./Routes.css";
import { FcTodoList } from "react-icons/fc";

function Main() {
  return (
    <div className="Main">
      <header className="Header_main">
        <h2 style={{ color: "blue" }}>
          <FcTodoList size="180" /> <br />
          To - Do - List
        </h2>
      </header>

      <Link to="/login">
        <button className="">로그인</button>
      </Link>
    </div>
  );
}

export default Main;
