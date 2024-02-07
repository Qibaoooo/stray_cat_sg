import {
  clearUserInfoAndReload,
  getUserinfoFromLocal,
} from "pages/utils/userinfo";
import React, { useEffect, useState } from "react";
import { Button, Stack } from "react-bootstrap";
import paw from "../../images/paw.png";
import { RxAvatar } from "react-icons/rx";

const UserSection = ({ username }) => {
  return (
    <div>
      {username !== "" ? (
        <div>
          <RxAvatar className="my-3" size={50} ></RxAvatar>
          <p>hi, {username}</p>
          <Button
            onClick={() => {
              clearUserInfoAndReload()
            }}
          >
            <u>logout</u>
          </Button>
        </div>
      ) : (
        <div>
          <p>please login</p>
          <Button
            onClick={() => {
              window.location.href = "/login";
            }}
          >
            <u>login</u>
          </Button>
        </div>
      )}
    </div>
  );
};

const ButtonGroups = () => {
  const imgStyles = { width: "30px", marginLeft: "20px" };
  const divStyles = { paddingBottom: "0.5rem" };
  const buttonClass = "mx-3 bg-secondary-subtle";

  const URLPathList = ["/list", "/map", "/newSighting", "/lost"];
  const ButtonTexts = {
    "/list": "list view",
    "/map": "map view",
    "/newSighting": "upload sighting",
    "/lost": "lost cat",
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

const SocialMediaIcons = () => {
  return <p>SocialMediaIcons</p>;
};

const MapSidePanel = () => {
  const [username, SetUsername] = useState("");

  useEffect(() => {
    if (getUserinfoFromLocal() == null) {
      return;
    }
    SetUsername(getUserinfoFromLocal().username);
  }, []);

  return (
    <div>
      <Stack className="my-5" style={{ height: "86vh" }}>
        <UserSection username={username}></UserSection>
        {/* spacer */}
        <div className="my-auto"></div>
        <div className="p-2">
          <ButtonGroups />
        </div>
        <div className="my-auto"></div>
        <SocialMediaIcons />
      </Stack>
    </div>
  );
};

export default MapSidePanel;
