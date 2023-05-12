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
  // 데이터 관리 - object들로 이루어진 배열
  const [todos, setTodos] = useState([]);

  // 할일 토글
  const updateTodo = (id, isdone) => {
    // console.log("props 전달 완료");
    setTodos((prevTodos) =>
      prevTodos.map((todo) => {
        if (todo.listid === id) {
          return {
            ...todo,
            isdone,
          };
        }
        return todo;
      })
    );
  };

  // 맵 로딩시 백으로부터 각자 고유id로 할일을 찾는 쿼리 전송
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
          // console.log(res);
          // 받은 데이터 표시하고 -> 각자 고유번호 지정?
          setTodos(res.data);
          // console.log(todos);
          // console.log(typeof todos); //object - 각각의 배열 안에 키 - 값
          // console.log(todos[0].listid);
        })
        .catch((err) => {
          // console.log("Todo List Error");
        });
    }
  }, []);

  const todoList = todos.map((todo) => (
    <TodoItem
      key={todo.listid}
      id={todo.listid}
      text={todo.todo}
      done={todo.isdone}
      updateTodo={updateTodo}
    />
  ));

  return (
    <TodoListBlock>
      {/* 할일이 없을 때, 표시해줌 */}
      {/* <TodoItem text={todos[0].todo} done={todos[0].isdone} /> */}
      {/* {todos.length === 0 ? <div>할 일을 입력해주세요.</div> : { todoList }} */}
      {todoList}
      {/* <button onClick={listtest}>테스트</button> */}
    </TodoListBlock>
  );
}

export default TodoList;
