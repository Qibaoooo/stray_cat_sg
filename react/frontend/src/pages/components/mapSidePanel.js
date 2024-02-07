import {
  clearUserInfoAndRedirectToLogin,
  getUserinfoFromLocal,
} from "pages/utils/userinfo";
import React, { useEffect, useState } from "react";
import { Button, Stack } from "react-bootstrap";
import paw from "../../images/paw.png";

const ButtonGroups = () => {
  const imgStyles = { width: "30px", marginLeft: "20px" };
  const divStyles = { paddingBottom: "0.5rem" };
  const buttonClass = "mx-3 bg-secondary-subtle";

  const onClickListView = () => {
    window.location.href = "/list";
  };

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
          <div style={divStyles}>
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
    <div >
      <Stack className="my-5" style={{ height: "86vh" }}>
        {username !== "" ? (
          <div>
            <p>hi, {username}</p>
            <Button
              onClick={() => {
                clearUserInfoAndRedirectToLogin();
                window.location.href = "/login";
              }}
            >
              logout
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
              login
            </Button>
          </div>
        )}
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
