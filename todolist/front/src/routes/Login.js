import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { AiOutlineMail } from "react-icons/ai";
import { RiLockPasswordLine } from "react-icons/ri";
import axios from "axios";

function Login() {
  const [inputId, setInputId] = useState("");
  const [inputPw, setInputPw] = useState("");

  // id값
  const handleInputId = (e) => {
    setInputId(e.target.value);
  };

  // pw값
  const handleInputPw = (e) => {
    setInputPw(e.target.value);
  };

  const onSubmitLogin = (e) => {
    // 폼 제출 이벤트 취소
    e.preventDefault();

    console.log("로그인 수행");
    console.log("아이디 : ", inputId);
    console.log("비밀번호 : ", inputPw);

    axios
      .post("/api/login", {
        email: inputId,
        password: inputPw,
      })
      .then((res) => {
        console.log(res);
        // console.log("res.data.userId :: ", res.data.userId);
        // console.log("res.data.msg :: ", res.data.msg);
        console.log(res.data.accessToken);
        localStorage.setItem("accessToken", res.data.accessToken);
        // localStorage.setItem('refreshToken', res.data.refreshToken);
        // setIsLoggedIn(true);

        // 작업 완료 되면 페이지 이동(메인화면으로)
        goToMain();
      })
      .catch((err) => {
        console.log(err);
        alert("로그인 중 오류가 발생하였습니다.");
      });
  };

  // 메인화면
  const navigate = useNavigate();
  const goToMain = () => {
    navigate("/");
  };

  return (
    <div className="Main">
      <header className="Header">
        <div>로그인</div>
        <form onSubmit={onSubmitLogin}>
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
            <button type="submit" className="">
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
