import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { useState } from "react";
import Button from "react-bootstrap/Button";

function App() {
  const BACKEND_IP = process.env.REACT_APP_BACKEND_IP
    ? process.env.REACT_APP_BACKEND_IP
    : "localhost";
  const BACKEND_PORT = process.env.REACT_APP_BACKEND_PORT
    ? process.env.REACT_APP_BACKEND_PORT
    : "80";

  const [randInt, SetRandInt] = useState(0);

  const onClickRoll = () => {
    fetch(`http://${BACKEND_IP}:${BACKEND_PORT}/roll`)
      .then((response) => response.text())
      .then((data) => {
        console.log(data);
        SetRandInt(data);
      })
      .catch((error) => console.error(error));
  };

  return (
    <div className="App">
      <header className="App-header">
        <h4>Demo Page</h4>
        <br></br>
        <Button onClick={onClickRoll} variant="secondary">
          Roll
        </Button>
        <br></br>
        <p>{randInt}</p>
      </header>
    </div>
  );
}

export default App;
