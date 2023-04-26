import React from "react";
import styled from "styled-components";

const TodoHeadBlock = styled.div`
  padding-top: 48px;
  padding-left: 32px;
  padding-right: 32px;
  padding-bottom: 24px;
  border-bottom: 1px solid #e9ecef;
  h1 {
    margin: 0;
    font-size: 36px;
    color: #343a40;
  }
  s .day {
    margin-top: 4px;
    color: #868e96;
    font-size: 21px;
  }
  .tasks-left {
    color: #0ac9ff;
    font-size: 18px;
    margin-top: 40px;
    font-weight: bold;
  }
`;

function TodoHead() {
  const today = new Date();
  const dateString = today.toLocaleDateString("ko-KR", {
    year: "numeric",
    month: "long",
    day: "numeric",
  });
  const dayName = today.toLocaleDateString("ko-KR", { weekday: "long" });

  // const week = (num) => {
  //   switch (num) {
  //     case 0:
  //       return "일요일";
  //       break;
  //     case 1:
  //       return "월요일";
  //       break;
  //     case 2:
  //       return "화요일";
  //       break;
  //     case 3:
  //       return "수요일";
  //       break;
  //     case 4:
  //       return "목요일";
  //       break;
  //     case 5:
  //       return "금요일";
  //       break;
  //     case 6:
  //       return "토요일";
  //       break;
  //   }
  // };

  return (
    <TodoHeadBlock>
      <h1>
        {/* {today.getFullYear()}년 {today.getMonth() + 1}월 {today.getDate()}일 */}
        {dateString}
      </h1>
      <div className="day">
        {/* {week(today.getDay())} */}
        {dayName}
      </div>
      <div className="tasks-left">해야 할 일</div>
    </TodoHeadBlock>
  );
}

export default TodoHead;
