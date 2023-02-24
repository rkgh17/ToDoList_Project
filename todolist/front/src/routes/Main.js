import React from "react";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "./Routes.css";
import { FcTodoList } from "react-icons/fc";

function Main() {
  return (
    <div className="Main">
      <header className="Header">
        <h2 style={{ color: "blue" }}>
          <FcTodoList size="180" /> <br />
          To - Do - List
        </h2>
        <Link to="/Login">
          <button className="">로그인</button>
        </Link>
      </header>
    </div>
  );
}

export default Main;
