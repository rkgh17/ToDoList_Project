import React from "react";
import styled, { css } from "styled-components";
import { MdDone, MdDelete } from "react-icons/md";
import axios from "axios";

const Remove = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  color: #dee2e6;
  font-size: 24px;
  cursor: pointer;
  &:hover {
    color: #ff6b6b;
  }
  display: none;
`;

const TodoItemBlock = styled.div`
  display: flex;
  align-items: center;
  padding-top: 12px;
  padding-bottom: 12px;
  &:hover {
    ${Remove} {
      display: initial;
    }
  }
`;

const CheckCircle = styled.div`
  width: 32px;
  height: 32px;
  border-radius: 16px;
  border: 1px solid #ced4da;
  font-size: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 20px;
  cursor: pointer;
  ${(props) =>
    props.done &&
    css`
      border: 1px solid #0ac9ff;
      color: #0ac9ff;
    `}
`;

const Text = styled.div`
  flex: 1;
  font-size: 21px;
  color: #495057;
  ${(props) =>
    props.done &&
    css`
      color: #ced4da;
    `}
`;

function TodoItem({ id, done, text, updateTodo }) {
  // 할일 토글
  const onToggle = () => {
    // console.log("클릭한 todo의 id는 " + id);
    if (!done) {
      // 할일 완료
      // updateTodo(id, !done);

      axios
        .post("/api/donetodo", {
          listid: id,
          state: done,
        })
        .then((res) => {
          // console.log("투두 체크 완료");
          // console.log(res.status);

          // 서버와 통신 완료
          if (res.status === 200) {
            updateTodo(id, !done);
          }
        })
        .catch((err) => {
          console.log("체크 에러");
        });
    } else {
      // 할일 완료 취소
      if (window.confirm("정말 취소하시겠습니까?")) {
        // updateTodo(id, !done);
        axios
          .post("/api/donetodo", {
            listid: id,
            state: done,
          })
          .then((res) => {
            // 서버와 통신 완료
            if (res.status === 200) {
              updateTodo(id, !done);
            }
          })
          .catch((err) => {
            console.log("취소 에러");
          });
      } else {
      }
    }
  };

  // 할일 삭제
  const onDelete = () => {
    // console.log("삭제처리 예정");

    axios
      .post("/api/deltodo", {
        listid: id,
      })
      .then((res) => {
        if (res.status === 200) {
          alert("삭제 완료");
          window.location.reload();
        }
      })
      .catch((err) => {
        console.log("삭제 에러");
      });
  };

  return (
    <TodoItemBlock>
      <CheckCircle done={done} onClick={onToggle}>
        {done && <MdDone />}
      </CheckCircle>
      <Text done={done}>{text}</Text>
      <Remove onClick={onDelete}>
        <MdDelete />
      </Remove>
    </TodoItemBlock>
  );
}

export default TodoItem;
