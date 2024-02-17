import React, { useEffect, useState } from "react";
import { Button, Col, Row, Stack } from "react-bootstrap";
import banner from "../images/top_banner.png";
import { RxAvatar } from "react-icons/rx";
import { useSearchParams } from "react-router-dom";
import { getUserById } from "./utils/api/apiUser";
import {
  clearUserInfoAndRedirectToLogin,
  getUserinfoFromLocal,
} from "./utils/userinfo";
import LoginLogoutButton from "./components/loginLogoutButton";
import avatar from "../images/avatar.png";

const accountPageButtonClass = "m-auto my-3 border-1 bg-secondary";

const OwnerButton = ({ user }) => {
  return (
    <Button
      className={accountPageButtonClass}
      onClick={() => {
        if (!!user.isOwner) {
          window.location.href = "/newSighting";
        } else {
          window.location.href = "/uploadVerification";
        }
      }}
    >
      {!!user.isOwner ? (
        <u>Search For Lost Cat</u>
      ) : (
        <u>Apply to be an owner</u>
      )}
    </Button>
  );
};
const EditButton = ({ user }) => {
  return (
    <Button className={accountPageButtonClass} onClick={()=>{
      window.location.href = "/editProfile";
    }}>
      <u>Edit Personal Profile</u>
    </Button>
  );
};

const AccountPage = () => {
  const [searchParams] = useSearchParams();
  const userId = searchParams.get("user");

  const [user, SetUser] = useState({
    username: "",
    id: 0,
  });

  useEffect(() => {
    const _user = getUserinfoFromLocal();
    if (!_user) {
      window.location.href = "/"
    }
    _user.isOwner = (_user.role === "ROLE_owner")
    SetUser(_user);
  }, []);

  return (
    <Row className="g-0">
      <Col
        style={{
          backgroundImage: `url(${banner})`,
          backgroundRepeat: "no-repeat",
          backgroundSize: "contain",
        }}
        xs={6}
        className="m-auto"
      >
        <div style={{ marginTop: "50px" }}>
        <img src={avatar} alt="avatar" style={{ height: "80px", width: "80px" ,borderRadius:"40px"}} />
          <p>
            <em>{user.username}</em>
          </p>
          <p>
            your role is:
            <em>{user.role}</em>
          </p>
        </div>
        <Stack direction="horizontal">
          <OwnerButton user={user}></OwnerButton>
          <EditButton user={user}></EditButton>
        </Stack>
        <LoginLogoutButton type="logout"></LoginLogoutButton>
      </Col>
    </Row>
  );
};

export default AccountPage;
