import React, { useState } from "react";
import { Button, Form, Stack } from "react-bootstrap";
import MyAlert from "./components/myAlert";
import loginBG from "../images/loginBG.png";
import { register } from "./utils/api/apiAuth";

const RegisterPage = () => {
  const [username, setUsername] = useState();
  const [password, setPassword] = useState();
  const [passwordConfirm, setPasswordConfirm] = useState();
  const [showAlert, setShowAlert] = useState(false);
  const [alertMsg, setAlertMsg] = useState();
  const onInputUN = ({ target: { value } }) => setUsername(value);
  const onInputPW = ({ target: { value } }) => setPassword(value);
  const onInputPWConfirm = ({ target: { value } }) => setPasswordConfirm(value);

  const testStyle = {
    fontSize: "1.2rem",
    fontFamily: "Comic Sans MS, cursive",
    fontWeight: "bold",
  };

  const onFormSubmit = async (e) => {
    e.preventDefault();

    if (password !== passwordConfirm) {
        setAlertMsg("please make sure passwords entered are the same");
        setShowAlert(true);
    }
    register(username, password)
    .then((response) => {
      if (response.status == 200) {
        // console.log(JSON.stringify(response.data.username));
        alert(response.data)
        window.location.href = "/login";
      } else {
        setAlertMsg(JSON.stringify(response));
        setShowAlert(true);
      }
    })
    .catch((error) => {
        setAlertMsg(JSON.stringify(error));
        setShowAlert(true);
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
            register new user
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

          <Form.Group controlId="formGridPasswordConfirm">
            <Form.Control
              style={{ opacity: "0.5" }}
              type="password"
              onChange={onInputPWConfirm}
              placeholder="Confirm password"
            />
          </Form.Group>
          <div>
            <Button
              className="opacity-75"
              type="submit"
              onClick={onFormSubmit}
              style={testStyle}
            >
              Submit
            </Button>
          </div>
        </Stack>
        <MyAlert
          showAlert={showAlert}
          variant="warning"
          msg1="Register failed, error msg:"
          msg2={alertMsg}
          handleCLose={() => setShowAlert(false)}
          showReturnTo={false}
        ></MyAlert>
      </Form>
    </div>
  );
};

export default RegisterPage;
