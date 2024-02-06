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

  return (
    <div style={{ minWidth: "100px", textAlign: "start" }}>
      <div style={divStyles}>
        {window.location.href.includes("/list") && (
          <img src={paw} style={imgStyles}></img>
        )}
        <Button
          className={buttonClass}
          onClick={() => {
            window.location.href = "/list";
          }}
        >
          list view
        </Button>
      </div>
      <div style={divStyles}>
      {window.location.href.includes("/map") && (
          <img src={paw} style={imgStyles}></img>
        )}
        <Button
          className={buttonClass}
          onClick={() => {
            window.location.href = "/map";
          }}
        >
          map view
        </Button>
      </div>
      <div style={divStyles}>
      {window.location.href.includes("/newSighting") && (
          <img src={paw} style={imgStyles}></img>
        )}
        <Button
          className={buttonClass}
          onClick={() => {
            window.location.href = "/newSighting";
          }}
        >
          upload sighting
        </Button>
      </div>
      <div style={divStyles}>
      {window.location.href.includes("/lost") && (
          <img src={paw} style={imgStyles}></img>
        )}
        <Button className={buttonClass}>lost cat</Button>
      </div>
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
    <div style={{}}>
      <Stack style={{ height: "100vh" }}>
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
