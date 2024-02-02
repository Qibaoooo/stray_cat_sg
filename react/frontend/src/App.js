import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import React, { useLayoutEffect } from "react";
import { Routes, Route } from "react-router-dom";
import MapPage from "pages/mapPage";
import CatDetailsPage from "pages/catDetailsPage";
import LoginPage from "pages/loginPage";

function App() {

  useLayoutEffect(()=>{
    document.body.className = document.body.className + " bg-primary-subtle";
  })

  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<MapPage />} />
        <Route path="/map" element={<MapPage />} />
        <Route path="/catDetails" element={<CatDetailsPage />} />
        <Route path="/login" element={<LoginPage />} />
      </Routes>
    </div>
  );
}

export default App;
