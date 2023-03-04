import { useState } from "react";
import { Link } from "react-router-dom";
import { AiOutlineMail } from "react-icons/ai";
import { RiLockPasswordLine } from "react-icons/ri";
import axios from "axios";

function Login() {
  const [inputId, setInputId] = useState("");
  const [inputPw, setInputPw] = useState("");

  const handleInputId = (e) => {
    setInputId(e.target.value);
  };

  const handleInputPw = (e) => {
    setInputPw(e.target.value);
  };

  const onClickLogin = () => {
    console.log("로그인 수행");
    console.log("아이디 : ", inputId);
    console.log("비밀번호 : ", inputPw);

    axios
      .post("/api/login", {
        id: inputId,
        passwd: inputPw,
      })
      .then((res) => {
        console.log(res);
        console.log("res.data.userId :: ", res.data.userId);
        console.log("res.data.msg :: ", res.data.msg);

        // 유효검증
        if (res.data.email === undefined) {
          // id 일치하지 않는 경우 userId = undefined, msg = '입력하신 id 가 일치하지 않습니다.'
          console.log("======================", res.data.msg);
          alert("입력하신 id 가 일치하지 않습니다.");
        } else if (res.data.email === null) {
          // id는 있지만, pw 는 다른 경우 userId = null , msg = undefined
          console.log(
            "======================",
            "입력하신 비밀번호 가 일치하지 않습니다."
          );
          alert("입력하신 비밀번호 가 일치하지 않습니다.");
        } else if (res.data.email === inputId) {
          // id, pw 모두 일치 userId = userId1, msg = undefined
          console.log("======================", "로그인 성공");
          sessionStorage.setItem("user_id", inputId); // sessionStorage에 id를 user_id라는 key 값으로 저장
          sessionStorage.setItem("name", res.data.name); // sessionStorage에 id를 user_id라는 key 값으로 저장
        }

        // 작업 완료 되면 페이지 이동(새로고침)
        document.location.href = "/";
      })
      .catch();
  };

  return (
    <div className="Main">
      <header className="Header">
        <div>로그인</div>
        <div>
          <div>
            <AiOutlineMail />
            <input
              type="id"
              className="form-control"
              placeholder="Enter ID"
              name="input_id"
              value={inputId}
              onChange={handleInputId}
            />
          </div>

          <div>
            <RiLockPasswordLine />
            <input
              type="password"
              className="form-control"
              placeholder="Enter password"
              name="input_pw"
              value={inputPw}
              onChange={handleInputPw}
            />
          </div>
        </div>

        <div>
          <button type="button" onClick={onClickLogin} className="">
            로그인
          </button>
        </div>

        <div>
          <Link to="/signup">
            <button className="">회원가입</button>
          </Link>
        </div>
      </header>
    </div>
  );
}

export default Login;
