import "./css/MylistCSS.css";

import { Link } from "react-router-dom";

function Mylist() {
  return (
    <div className="Mylist">
      <div>
        <h2>나만의 일정 조율하기</h2>
      </div>
      <div>
        <Link to="/">
          <button>메인으로</button>
        </Link>
      </div>
    </div>
  );
}

export default Mylist;
