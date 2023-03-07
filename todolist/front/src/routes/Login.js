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
        // console.log("res.data.userId :: ", res.data.userId);
        // console.log("res.data.msg :: ", res.data.msg);

        // 작업 완료 되면 페이지 이동(새로고침)
        document.location.href = "/";
      })
      .catch();
  };

  return (
    <div className="Main">
      <header className="Header">
        <div>로그인</div>
        <form>
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
            <button type="submit" onClick={onClickLogin} className="">
              로그인
            </button>
          </div>
        </form>
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
