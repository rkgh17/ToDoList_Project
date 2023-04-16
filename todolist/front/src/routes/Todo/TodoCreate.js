import React, { useState } from "react";
import styled, { css } from "styled-components";
import { MdAdd } from "react-icons/md";
import axios from "axios";

const CircleButton = styled.button`
  background: #0ac9ff;
  &:hover {
    background: #14d3ff;
  }
  &:active {
    background: #20c997;
  }

  z-index: 5;
  cursor: pointer;
  width: 80px;
  height: 80px;
  display: block;
  align-items: center;
  justify-content: center;
  font-size: 60px;
  position: absolute;
  left: 50%;
  bottom: 0px;
  transform: translate(-50%, 50%);
  color: white;
  border-radius: 50%;
  border: none;
  outline: none;
  display: flex;
  align-items: center;
  justify-content: center;

  transition: 0.125s all ease-in;
  ${(props) =>
    props.open &&
    css`
      background: #ff6b6b;
      &:hover {
        background: #ff8787;
      }
      &:active {
        background: #fa5252;
      }
      transform: translate(-50%, 50%) rotate(45deg);
    `}
`;

const InsertFormPositioner = styled.div`
  width: 100%;
  bottom: 0;
  left: 0;
  position: absolute;
`;

const InsertForm = styled.form`
  background: #f8f9fa;
  padding-left: 32px;
  padding-top: 32px;
  padding-right: 32px;
  padding-bottom: 72px;

  border-bottom-left-radius: 16px;
  border-bottom-right-radius: 16px;
  border-top: 1px solid #e9ecef;
`;

const Input = styled.input`
  margin: 12px;
  padding: 12px;
  border-radius: 4px;
  border: 1px solid #dee2e6;
  width: 100%;
  outline: none;
  font-size: 18px;
  box-sizing: border-box;
`;

const Label = styled.label`
  padding: 15px;
  font-size: 18px;
`;

function TodoCreate() {
  const [open, setOpen] = useState(false);

  const onToggle = () => {
    setOpen(!open);

    if (deadline === undefined) {
      setDeadline(today());
    }
  };

  const [todo, setTodo] = useState();
  const [deadline, setDeadline] = useState();

  const onChangeTodo = (e) => {
    setTodo(e.target.value);
  };
  const onChangeDl = (e) => {
    setDeadline(e.target.value);
  };

  const toDB = () => {
    console.log("db로가자");
    console.log(deadline);
    console.log(typeof deadline);
    alert(todo);
    alert(deadline);

    axios
      .post("/api/createtodo", {})
      .then((res) => {})
      .catch((err) => {});
  };

  const today = () => {
    var date = new Date();
    var year = date.getFullYear();
    var month = ("0" + (1 + date.getMonth())).slice(-2);
    var day = ("0" + date.getDate()).slice(-2);

    return year + "-" + month + "-" + day;
  };

  return (
    <>
      {open && (
        <InsertFormPositioner>
          <InsertForm onSubmit={toDB}>
            <Label>마감일</Label>
            <Input
              type="date"
              defaultValue={deadline}
              onChange={onChangeDl}
              onkeydown="return false"
            />
            <Input
              autoFocus
              placeholder="할 일을 입력 후, Enter 를 누르세요"
              defaultValue={todo}
              onChange={onChangeTodo}
            />
          </InsertForm>
        </InsertFormPositioner>
      )}
      <CircleButton onClick={onToggle} open={open}>
        <MdAdd />
      </CircleButton>
    </>
  );
}

export default TodoCreate;
