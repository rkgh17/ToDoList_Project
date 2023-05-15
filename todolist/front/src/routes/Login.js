import { useState, useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import { AiOutlineMail } from "react-icons/ai";
import { RiLockPasswordLine } from "react-icons/ri";
import { AuthContext } from "../context/AuthContext";
import axios from "axios";
import "./Routes.css";

function Login() {
  // 로그인 상태관리
  const { setIsLoggedIn } = useContext(AuthContext);

  // 토큰관리
  const saveToken = (accessToken, refreshToken) => {
    localStorage.setItem("accessToken", accessToken);
    localStorage.setItem("refreshToken", refreshToken);
  };

  // 엑세스 타임 관리
  const ExpiresIn = (accessTokenExpiresIn, refreshTokenExpiresIn) => {
    localStorage.setItem("accessTokenExpiresIn", accessTokenExpiresIn);
    localStorage.setItem("refreshTokenExpiresIn", refreshTokenExpiresIn);
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

    // console.log("로그인 수행");
    // console.log("아이디 : ", inputId);
    // console.log("비밀번호 : ", inputPw);

    axios
      .post("/api/login", {
        email: inputId,
        password: inputPw,
      })
      .then((res) => {
        // console.log(res);
        // console.log("res.data.userId :: ", res.data.userId);
        // console.log("res.data.msg :: ", res.data.msg);
        // console.log(res.data.accessToken);

        // 로그인 성공
        if (res.status === 200) {
          // 토큰 저장
          saveToken(res.data.accessToken, res.data.refreshToken);

          // 만료시간 저장
          ExpiresIn(res.data.tokenExpiresIn, res.data.refreshTokenExpiresIn);

          // 로그인 상태 저장 - useContext
          setIsLoggedIn(true);

          localStorage.setItem("isLoggedIn", true);

          alert("로그인 완료");

          goToMain();
        }
      })
      .catch((err) => {
        // console.log(err);

        if (err.status === 401) {
          alert("등록되지 않은 아이디 입니다.");
        } else {
          alert("로그인 중 오류가 발생하였습니다.");
        }
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
          <div className="input_box_wrapper">
            <div className="input_box">
              <AiOutlineMail />
              <input
                type="id"
                className="form-control"
                placeholder="Enter Email"
                name="input_id"
                value={inputId}
                onChange={handleInputId}
              />
            </div>

            <div className="input_box">
              <RiLockPasswordLine />
              <input
                type="password"
                className="form-control"
                placeholder="Enter Password"
                name="input_pw"
                value={inputPw}
                onChange={handleInputPw}
              />
            </div>
          </div>
        </form>
        <div>
          <div>
            <button type="submit" className="Main_Button">
              로그인
            </button>
          </div>
          <Link to="/signup">
            <button className="Main_Button">회원가입</button>
          </Link>
        </div>
      </header>
    </div>
  );
}

export default Login;
