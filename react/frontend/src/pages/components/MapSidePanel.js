import { getUserinfoFromLocal, setUserinfoLocal } from "pages/utils/userinfo";
import React, { useEffect, useState } from "react";
import { Button, Stack } from "react-bootstrap";

const MapSidePanel = () => {
  const [username, SetUsername] = useState("");

  useEffect(() => {
    SetUsername(getUserinfoFromLocal().username);
  }, []);

  return (
    <div>
      <Stack>
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
      </Stack>
    </div>
  );
};

export default MapSidePanel;
