import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import React from "react";
import { useState } from "react";
import { Routes, Route } from "react-router-dom";
import MapPage from "pages/MapPage";

function App() {
  const BACKEND_IP = process.env.REACT_APP_BACKEND_IP
    ? process.env.REACT_APP_BACKEND_IP
    : "http://localhost:8080";

  const [randInt, SetRandInt] = useState(0);

  const onClickRoll = () => {
    fetch(`${BACKEND_IP}/roll`)
      .then((response) => response.text())
      .then((data) => {
        console.log(data);
        SetRandInt(parseInt(data));
      })
      .catch((error) => console.error(error));
  };

  return (
    <div className="App bg-primary-subtle">
      <Routes>
        <Route path="/" element={<MapPage />} />
        <Route path="/map" element={<MapPage />} />
      </Routes>
    </div>
  );
}

export default App;
