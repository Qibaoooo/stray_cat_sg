import { getUserinfoFromLocal, setUserinfoLocal } from "pages/utils/userinfo";
import React, { useEffect, useState } from "react";
import { Button, Row, Stack } from "react-bootstrap";
import paw from "../../images/paw.png";

const ButtonGroups = () => {
  const imgStyles = { width: "30px", marginLeft:"20px"}
  const divStyles = {paddingBottom:"0.5rem"}
  const buttonClass = "mx-3 bg-secondary-subtle"
  return (
    <div style={{minWidth:"100px", textAlign:"start"}}>
      <div style={divStyles}>
        <img src={paw} style={imgStyles}></img>
        <Button className={buttonClass}>list view</Button>
      </div>
      <div style={divStyles}>
        <img src={paw} style={imgStyles}></img>
        <Button className={buttonClass}>upload sighting</Button>
      </div>
      <div style={divStyles}>
        <img src={paw} style={imgStyles}></img>
        <Button className={buttonClass}>lost cat</Button>
      </div>
    </div>
  );
};

const SocialMediaIcons = ()=>{
  return (
    <p>SocialMediaIcons</p>
  )
}

const MapSidePanel = () => {
  const [username, SetUsername] = useState("");

  useEffect(() => {
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
                setUserinfoLocal("");
                window.location.reload();
              }}
            >
              logout
            </Button>
          </div>
        ) : (
          <div>
            <p>please login</p>
            <Button>login</Button>
          </div>
        )}
        {/* spacer */}
        <div className="my-auto"></div>
        <div className="p-2">
          <ButtonGroups />
        </div>
        <div className="my-auto"></div>
        <SocialMediaIcons/>
      </Stack>
    </div>
  );
};

export default MapSidePanel;
