import {
  clearUserInfoAndReload,
  getUserRole,
  getUserinfoFromLocal,
} from "pages/utils/userinfo";
import React, { useEffect, useState } from "react";
import { Button, Stack } from "react-bootstrap";
import paw from "../../images/paw.png";
import banner from "../../images/top_banner.png";
import { RxAvatar } from "react-icons/rx";
import LoginLogoutButton from "./loginLogoutButton";
import avatar from "../../images/avatar.png";

const UserSection = ({ user }) => {
  return (
    <div>
      {!!user ? (
        <div className="m-3">
          <Button
            className="btn border-0 m-auto bg-secondary"
            style={{ display: "block" }}
            onClick={() => {
              window.location.href = `/account?user=${user.id}`;
            }}
          >
            <img src={avatar} alt="avatar" style={{ height: "80px", width: "80px" ,borderRadius:"40px"}} />
            <p>hi, {user.username}</p>
          </Button>
          <LoginLogoutButton type="logout"></LoginLogoutButton>
        </div>
      ) : (
        <div>
          <Button
            className="btn border-0 mx-auto bg-secondary"
            style={{ display: "block", marginTop: "40px" }}
          >
            <p>please log in</p>
          </Button>
          <LoginLogoutButton type="login"></LoginLogoutButton>
        </div>
      )}
    </div>
  );
};

const ButtonGroups = () => {
  const imgStyles = { width: "30px", marginLeft: "20px" };
  const divStyles = { paddingBottom: "0.5rem" };
  const buttonClass = "mx-3 bg-secondary-subtle";
  const [URLPathList, SetURLPathList] = useState([])

  const PublicURLPathList = [
    "/list",
    "/map",
    "/newSighting",
    "/lost"
  ];

  const AdminURLPathList = [
    "/approval",
    "/grouping",
  ]

  useEffect(()=>{
    let _urlList = [...PublicURLPathList]
    if (getUserRole() === "ROLE_admin") {
      _urlList = [..._urlList, ...AdminURLPathList]
    }
    SetURLPathList(_urlList)
  },[])

  const ButtonTexts = {
    "/list": "list view",
    "/map": "map view",
    "/newSighting": "upload sighting",
    "/lost": "lost cat",
    "/approval": "pending approvals",
    "/grouping": "group cat sightings",
  };

  return (
    <div style={{ minWidth: "100px", textAlign: "start" }}>
      {URLPathList.map((url, index, array) => {
        return (
          <div style={divStyles} key={index}>
            {window.location.href.includes(url) && (
              <img src={paw} style={imgStyles}></img>
            )}
            <Button
              className={buttonClass}
              onClick={() => {
                window.location.href = url;
              }}
            >
              {ButtonTexts[url]}
            </Button>
          </div>
        );
      })}
    </div>
  );
};

const MapSidePanel = () => {
  const [user, SetUser] = useState("");

  useEffect(() => {
    if (getUserinfoFromLocal() == null) {
      return;
    }
    SetUser(getUserinfoFromLocal());
  }, []);

  return (
    <div>
      <Stack
        className="py-5 bg-secondary"
        style={{
          height: "100vh",
          backgroundImage: `url(${banner})`,
          backgroundRepeat: "no-repeat",
          backgroundSize: "contain",
        }}
      >
        <UserSection user={user}></UserSection>
        {/* spacer */}
        <div className="my-auto"></div>
        <div className="p-2">
          <ButtonGroups />
        </div>
        <div className="my-auto"></div>
        {getUserRole() === "ROLE_admin" && 
        <Button className="btn border-0 bg-secondary" href="/analytics">
          <p>Go to Analytics</p>
        </Button>
        }
        
      </Stack>
    </div>
  );
};

export default MapSidePanel;
