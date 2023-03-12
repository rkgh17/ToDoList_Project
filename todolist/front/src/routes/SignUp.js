import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Routes.css";
import axios from "axios";

function SignUp() {
  // 이름, 아이디, 비밀번호
  const [inputUserName, setUserName] = useState("");
  const [inputUserId, setUserId] = useState("");
  const [inputUserPw, setUserPw] = useState("");
  const [inputUserPwCheck, setUserPwCheck] = useState("");

  // 메시지 저장
  const [passwordConfirmMessage, setPasswordConfirmMessage] = useState("");

  // 유효성 검사 (일단 비밀번호만)
  const [isPasswordConfirm, setIsPasswordConfirm] = useState(false);

  // 값 추적
  const handleInputUserId = (e) => {
    setUserId(e.target.value);
  };
  const handleInputUserName = (e) => {
    setUserName(e.target.value);
  };
  const handleInputUserPw = (e) => {
    setUserPw(e.target.value);
  };

  // 비밀번호 검사
  const handleInputUserPwCheck = (e) => {
    setUserPwCheck(e.target.value);
    if (inputUserPw === e.target.value) {
      setPasswordConfirmMessage("비밀번호 일치");
      setIsPasswordConfirm(true);
    } else {
      setPasswordConfirmMessage("비밀번호 불일치");
      setIsPasswordConfirm(false);
    }
  };

  // 회원가입 수행 -> 백으로 전송
  const onClickSignUp = () => {
    console.log("회원가입 수행");
    // console.log("이름 : ", inputUserName);
    // console.log("아이디 : ", inputUserId);
    // console.log("비밀번호 : ", inputUserPw);

    axios
      .post("/api/signup", {
        username: inputUserName,
        nickname: inputUserId,
        password: inputUserPw,
      })
      .then((res) => {
        // console.log(res.data);
        if (res.data == "id중복") {
          alert("id가 중복입니다.");
        } else {
          alert("회원가입 완료");
          goToLogin();
        }
      })
      .catch();
  };

  const navigate = useNavigate();
  const goToLogin = () => {
    navigate("/login");
  };

  return (
    <div className="Main">
      <header className="Header">
        <form>
          <div style={{ display: "flex", flexDirection: "column" }}>
            <div>회원가입</div>

            <div className="formbox">
              <label>Name </label>
              <input
                type="name"
                value={inputUserName}
                onChange={handleInputUserName}
              ></input>
            </div>

            <div className="formbox">
              <label>Email (ID) </label>
              <input
                type="email"
                value={inputUserId}
                onChange={handleInputUserId}
              ></input>
            </div>
            <div className="formbox">
              <label>비밀번호 </label>
              <input
                type="password"
                value={inputUserPw}
                onChange={handleInputUserPw}
                title="비밀번호"
              ></input>
            </div>

            <div className="formbox">
              <label>비밀번호 확인 </label>
              <input
                type="password"
                value={inputUserPwCheck}
                onChange={handleInputUserPwCheck}
              ></input>
              <span
                className={`message ${isPasswordConfirm ? "success" : "error"}`}
              >
                {passwordConfirmMessage}
              </span>
            </div>
          </div>

          <div>
            <button
              type="submit"
              className=""
              onClick={onClickSignUp}
              disabled={!isPasswordConfirm}
            >
              회원가입
            </button>
          </div>
        </form>
      </header>
    </div>
  );
}

export default SignUp;
