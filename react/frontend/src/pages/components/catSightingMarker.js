import React, { useState } from "react";
import { Button } from "react-bootstrap";
import "./catSightingMarker.css";

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
            alt="cat"
            src={sighting.imagesURLs[0]}
            style={{ width: "150px", border: "1px solid #ddd" }}
          />
          <p>{sighting.sightingName ? `${sighting.sightingName}` : "unknown cat"}</p>
          <p className="h6">
            <a className="text-secondary" href={`/catDetails?id=${sighting.cat}`}>see details</a>
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
