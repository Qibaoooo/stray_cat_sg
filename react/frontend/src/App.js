import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import React from "react";
import { Routes, Route } from "react-router-dom";
import MapPage from "pages/mapPage";
import CatDetailsPage from "pages/catDetailsPage";

function App() {

  return (
    <div className="App bg-primary-subtle">
      <Routes>
        <Route path="/" element={<MapPage />} />
        <Route path="/map" element={<MapPage />} />
        <Route path="/catDetails" element={<CatDetailsPage />} />
      </Routes>
    </div>
  );
}

export default App;
