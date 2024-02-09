import { clearUserInfoAndReload } from "pages/utils/userinfo";
import React from "react";
import { Button } from "react-bootstrap";

const LoginLogoutButton = ({ type }) => {
  const loginButtonClass = "m-auto my-3 bg-secondary-subtle border-1";

  return (
    <Button
      className={loginButtonClass}
      size="sm"
      style={{ display: "block" }}
      onClick={() => {
        if (type === "login") {
            window.location.href = "/login"
        } else {
            clearUserInfoAndReload();
        }
      }}
    >
      <u>
        {type === "login" ? "login" : "logout"}
      </u>
    </Button>
  );
};

export default LoginLogoutButton;
