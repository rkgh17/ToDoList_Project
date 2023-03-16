import { useState, useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import { AiOutlineMail } from "react-icons/ai";
import { RiLockPasswordLine } from "react-icons/ri";
import { AuthContext } from "../context/AuthContext";
import axios from "axios";

function Login() {
  // 로그인 상태관리
  const { setIsLoggedIn } = useContext(AuthContext);

  // 토큰관리
  const saveAccessToken = (accessToken, refreshToken) => {
    localStorage.setItem("accessToken", accessToken);
    localStorage.setItem("refreshToken", refreshToken);
  };

  // id, pw 입력 관리
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
        // console.log(res.data.accessToken);

        // 로그인 성공
        if (res.status === 200) {
          // 토큰 저장
          saveAccessToken(res.data.accessToken, res.data.refreshToken);

          // 로그인 상태 저장 - useContext
          setIsLoggedIn(true);

          localStorage.setItem("isLoggedIn", true);

          alert("로그인 완료");

          goToMain();
        }
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
