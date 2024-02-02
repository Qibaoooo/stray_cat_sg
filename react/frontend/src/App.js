import "./App.css";
import React, { useLayoutEffect } from "react";
import { Routes, Route } from "react-router-dom";
import MapPage from "pages/mapPage";
import CatDetailsPage from "pages/catDetailsPage";
import LoginPage from "pages/loginPage";
import ListPage from "pages/listPage";

function App() {

  useLayoutEffect(()=>{
    document.body.className = document.body.className + " bg-primary";
  })

  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<MapPage />} />
        <Route path="/map" element={<MapPage />} />
        <Route path="/list" element={<ListPage />} />
        <Route path="/catDetails" element={<CatDetailsPage />} />
        <Route path="/login" element={<LoginPage />} />
      </Routes>
    </div>
  );
}

export default App;
