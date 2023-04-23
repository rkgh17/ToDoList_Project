import React from "react";
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
  // Back과 통신하여 refreshToken속 id를 보내어 id별 todo들을 가져옴
  const listtest = () => {
    axios
      .post("/api/list", {
        sub: JSON.parse(
          atob(localStorage.getItem("refreshToken").split(".")[1])
        ).sub,
      })
      .then((res) => {
        console.log(res);
        // 받은 데이터 표시하고 -> 각자 고유번호 지정?
      })
      .catch((err) => {});
  };

  return (
    <TodoListBlock>
      <TodoItem text="A" done={true} />
      <TodoItem text="컴포넌트 스타일링 하기" done={true} />
      <TodoItem text="Context 만들기" done={false} />
      <TodoItem text="기능 구현하기" done={false} />
      <button onClick={listtest}>테스트</button>
    </TodoListBlock>
  );
}

export default TodoList;
