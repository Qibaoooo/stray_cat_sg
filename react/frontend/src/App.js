import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import React from "react";
import { Routes, Route } from "react-router-dom";
import MapPage from "pages/MapPage";
import CatDetailsPage from "pages/CatDetailsPage";

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
