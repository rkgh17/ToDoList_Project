function SignUp() {
  return (
    <div className="Main">
      <header className="Header">
        <div>회원가입</div>
        <div style={{ display: "flex", flexDirection: "column" }}>
          <label>Email </label>
          <input type="email"></input>
          <label>Name</label>
          <input type="name"></input>
          <label>비밀번호</label>
          <input type="password"></input>
          <label>비밀번호 확인</label>
          <input type="password"></input>
        </div>
      </header>
    </div>
  );
}

export default SignUp;
