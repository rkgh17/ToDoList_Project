import React from "react";
import { useEffect, useState } from "react";
import styled from "styled-components";
import TodoItem from "./TodoItem";
import axios from "axios";

const TodoListBlock = styled.div`
  flex: 1;
  padding: 20px 32px;
  padding-bottom: 48px;
  overflow-y: auto;
`;

function TodoList() {
  // 받은 데이터 관리
  const [todos, setTodos] = useState([]);

  useEffect(() => {
    if (!localStorage.getItem("refreshToken")) {
      console.log("로그인 필요");
    } else {
      axios
        .post("/api/list", {
          sub: JSON.parse(
            atob(localStorage.getItem("refreshToken").split(".")[1])
          ).sub,
        })
        .then((res) => {
          console.log(res);
          // 받은 데이터 표시하고 -> 각자 고유번호 지정?
          setTodos(res.data);
          // console.log(typeof todos); //object - 각각의 배열 안에 키 - 값
          // console.log(todos[0].listid);
        })
        .catch((err) => {});
    }
  }, []);

  const todoList = todos.map((todo) => (
    <TodoItem key={todo.listid} text={todo.todo} done={todo.isdone} />
  ));

  const listtest = () => {
    // Back과 통신하여 refreshToken속 id를 보내어 id별 todo들을 가져옴
    axios
      .post("/api/list", {
        sub: JSON.parse(
          atob(localStorage.getItem("refreshToken").split(".")[1])
        ).sub,
      })
      .then((res) => {
        console.log(res);
        // 받은 데이터 표시하고 -> 각자 고유번호 지정?
        setTodos(res.data);
        // console.log(typeof todos); //object - 각각의 배열 안에 키 - 값
        // console.log(todos[0].listid);
      })
      .catch((err) => {});
  };

  return (
    <TodoListBlock>
      {/* <TodoItem text={todos[0].todo} done={todos[0].isdone} /> */}
      {todoList}

      {/* <button onClick={listtest}>테스트</button> */}
    </TodoListBlock>
  );
}

export default TodoList;
