import React, { useEffect, useLayoutEffect } from "react";
import { Routes, Route } from "react-router-dom";
import MapPage from "pages/mapPage";
import CatDetailsPage from "pages/catDetailsPage";
import LoginPage from "pages/loginPage";
import ListPage from "pages/listPage";
import UploadSighting from "pages/uploadSightingPage";
import LostCat from "pages/lostCat";

const RedirectToMapPage = () => {
  useEffect(() => {
    window.location.href = "/map"
  });

  return null;
};

function App() {
  useLayoutEffect(() => {
    document.body.className = document.body.className + " bg-primary";
  });

  return (
    <div style={{ fontFamily: "Comic Sans MS, cursive", textAlign:"center" }}>
      <Routes>
        <Route path="/" element={<RedirectToMapPage />} />
        <Route path="/map" element={<MapPage />} />
        <Route path="/list" element={<ListPage />} />
        <Route path="/catDetails" element={<CatDetailsPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/newSighting" element={<UploadSighting />} />
        <Route path="/lost" element={<LostCat />} />
      </Routes>
    </div>
  );
}

export default App;
