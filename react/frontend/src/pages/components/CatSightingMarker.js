import React, { useState } from "react";
import { Button } from "react-bootstrap";
import "./CatSightingMarker.css";

const CatSightingMarker = ({ sighting }) => {
  const [showInfoWindow, setShowInfoWindow] = useState(false);
  const toggleInfoWindow = () => {
    setShowInfoWindow(!showInfoWindow);
  };

  const closeInfoWindow = () => {
    setShowInfoWindow(false);
  };

  return (
    <div style={{ position: "relative" }}>
      <Button
        size="sm"
        style={{ backgroundColor: "transparent", border: "0px" }}
        onClick={toggleInfoWindow}
      >
        <h3>🐱</h3>
      </Button>

      {showInfoWindow && (
        <div className="info-window">
          <h4>{sighting.text}</h4>
          <img
            src={sighting.imagesURLs[0]}
            style={{ width: "150px", border: "1px solid #ddd" }}
          />
          <p>{sighting.cat ? `cat id ${sighting.cat}` : "unknown cat"}</p>
          <p>
            <a href={`/catDetails?id=${sighting.cat}`}>see details</a>
          </p>
          <button className="close-button" onClick={closeInfoWindow}>
            &times;
          </button>
        </div>
      )}
    </div>
  );
};

export default CatSightingMarker;
