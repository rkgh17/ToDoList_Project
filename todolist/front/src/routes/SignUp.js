import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Routes.css";
import axios from "axios";

function SignUp() {
  // 이름, 아이디, 비밀번호
  const [inputUserNickName, setUserName] = useState("");
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
  const handleInputUserNickName = (e) => {
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
    // console.log("회원가입 수행");
    // console.log("이름 : ", inputUserNickName);
    // console.log("아이디 : ", inputUserId);
    // console.log("비밀번호 : ", inputUserPw);

    axios
      .post("/api/signup", {
        nickname: inputUserNickName,
        email: inputUserId,
        password: inputUserPw,
      })
      .then((res) => {
        console.log(res.data);
        alert("회원가입이 완료되었습니다.");
        goToLogin();
        // if (res.data.success) {
        //   alert("회원가입이 완료되었습니다.");
        //   goToLogin();
        // }
      })
      .catch((err) => {
        // console.log(err.response.data);
        if (err.response.status === 409) {
          alert("중복된 Email입니다.");
        } else {
          alert("회원가입 중 오류가 발생하였습니다.");
        }
      });
  };

  const navigate = useNavigate();
  const goToLogin = () => {
    navigate("/login");
  };

  return (
    <div className="Main">
      <header className="Header">
        <form>
          <div>
            {/* <div style={{ display: "flex", flexDirection: "column" }}> */}
            <div>회원가입</div>

            <div className="formbox">
              <label>닉네임 </label>
              <input
                type="name"
                placeholder="닉네임을 입력하세요"
                value={inputUserNickName}
                onChange={handleInputUserNickName}
              ></input>
            </div>

            <div className="formbox">
              <label>이메일 (ID) </label>
              <input
                type="email"
                placeholder="이메일을 입력하세요"
                value={inputUserId}
                onChange={handleInputUserId}
              ></input>
            </div>
            <div className="formbox">
              <label>비밀번호 </label>
              <input
                type="password"
                placeholder="비밀번호를 입력하세요"
                value={inputUserPw}
                onChange={handleInputUserPw}
                title="비밀번호"
              ></input>
            </div>

            <div className="formbox">
              <label>비밀번호 확인 </label>
              <input
                type="password"
                placeholder="비밀번호를 입력하세요"
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
              className="Main_Button"
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
