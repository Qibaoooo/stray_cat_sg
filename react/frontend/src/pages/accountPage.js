import React, { useEffect, useState } from "react";
import { Button, Col, Row, Stack } from "react-bootstrap";
import banner from "../images/top_banner.png";
import { RxAvatar } from "react-icons/rx";
import { useSearchParams } from "react-router-dom";
import { getUserById } from "./utils/api/apiUser";
import { clearUserInfoAndRedirectToLogin } from "./utils/userinfo";
import LoginLogoutButton from "./components/loginLogoutButton";

const accountPageButtonClass = "m-auto my-3 border-1 bg-secondary";

const OwnerButton = ({ user }) => {
  return (
    <Button
      className={accountPageButtonClass}
      onClick={() => {
         if (!!user.isOwner) {
           window.location.href = "/newSighting";
         }else{
          window.location.href="/uploadVfcation";
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
    <Button className={accountPageButtonClass}>
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
    getUserById(userId)
      .then((resp) => {
        console.log(resp.data);     
        SetUser({ ...user, ...resp.data });  
    })
      .catch((e) => {
        clearUserInfoAndRedirectToLogin();
      });
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
          <RxAvatar
            className="my-3 bg-primary rounded-circle"
            size={80}
          ></RxAvatar>
          <p>hi, {user.username}</p>
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
