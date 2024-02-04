import React, { useEffect, useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import { Stack } from "react-bootstrap";
import { login } from "./utils/api/apiAuth";
import { getUserinfoFromLocal, setUserinfoLocal } from "./utils/userinfo";
import MyAlert from "./components/myAlert";
import loginBG from "../images/loginBG.png";

function LoginPage() {
  const [username, setUsername] = useState();
  const [password, setPassword] = useState();
  const [showAlert, setShowAlert] = useState(false);
  const [alertMsg, setAlertMsg] = useState();
  const onInputUN = ({ target: { value } }) => setUsername(value);
  const onInputPW = ({ target: { value } }) => setPassword(value);

  const testStyle = {
    fontSize: "1.2rem",
    fontFamily: "Comic Sans MS, cursive",
    fontWeight: "bold",
  };

  useEffect(() => {
    if (getUserinfoFromLocal()) {
      console.log(getUserinfoFromLocal());
      // alr logged in, redirect
      window.location.href = "/map";
    }
  }, []);

  const onFormSubmit = async (e) => {
    e.preventDefault();
    login(username, password)
      .then((response) => {
        if (response.status == 200) {
          console.log(JSON.stringify(response.data.username));
          setUserinfoLocal(response.data);
          window.location.href = "/map";
        } else {
          setAlertMsg(JSON.stringify(response));
          setShowAlert(true);
        }
      })
      .catch((error) => {
        if (error.response.status == 401) {
          setAlertMsg("incorrect username/password.");
          setShowAlert(true);
        } else {
          setAlertMsg("error, please try again.");
          setShowAlert(true);
        }
      });
  };

  return (
    <div>
      <Form className="mx-5" style={{}}>
        <Stack
          style={{
            maxWidth: "300px",
            minHeight: "500px",
            backgroundImage: `url(${loginBG})`,
            backgroundSize: "100% 100%",
          }}
          gap={3}
          className="col-sm-4 mx-auto mt-5 p-4"
        >
          <h5 className="my-4" style={testStyle}>
            login to ur account
          </h5>
          <Form.Group controlId="formGridUsername">
            <Form.Label style={testStyle}>Username</Form.Label>
            <Form.Control
              style={{ opacity: "0.5" }}
              type="text"
              onChange={onInputUN}
              placeholder="Enter Username"
            />
          </Form.Group>

          <Form.Group controlId="formGridPassword">
            <Form.Label style={testStyle}>Password</Form.Label>
            <Form.Control
              style={{ opacity: "0.5" }}
              type="password"
              onChange={onInputPW}
              placeholder="Password"
            />
          </Form.Group>
          <div>
            <Button
              className="opacity-75"
              type="submit"
              onClick={onFormSubmit}
              style={testStyle}
            >
              Login
            </Button>
          </div>
        </Stack>
        <MyAlert
          showAlert={showAlert}
          variant="warning"
          msg1="Login failed, error msg:"
          msg2={alertMsg}
          handleCLose={() => setShowAlert(false)}
          showReturnTo={false}
        ></MyAlert>
      </Form>
    </div>
  );
}

export default LoginPage;
