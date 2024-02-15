import React, { useState } from "react";
import { Button } from "react-bootstrap";
import "./catSightingMarker.css";

const CatSightingMarker = ({ sighting, isUserPosted }) => {
  const [showInfoWindow, setShowInfoWindow] = useState(false);
  const toggleInfoWindow = () => {
    setShowInfoWindow(!showInfoWindow);
  };
  const closeInfoWindow = () => {
    setShowInfoWindow(false);
  };
  let emojiStyle = {
    margin: 0, // Removes default margin
    lineHeight: "normal", // Adjusts line height to reduce space
    display: "inline-block", // Ensures the emoji is treated as inline-block
  };

  const renderMarker = () => {
    // Define base style
    let markerStyle = {
        padding: "2px 2px 4px 2px",
        display: "inline-block",
        borderRadius: "40%" // Keeping a consistent shape for all markers
    };

    // Define base icon
    let markerIcon = "🐱";

    if (isUserPosted ) {
        // Both conditions are true
        markerStyle = { ...markerStyle, backgroundColor: "purple" }; 
  
    } else if (isUserPosted) {
        // Only isUserPosted is true
        markerStyle = { ...markerStyle, backgroundColor: "red" };
    } 

    // Return the marker with dynamic styles and icon
    return (
        <div style={markerStyle}>
            <h3 style={emojiStyle}>{markerIcon}</h3>
        </div>
    );
};

  return (
    <div style={{ position: "relative" }}>
      <Button
        size="sm"
        style={{ backgroundColor: "transparent", border: "0px" }}
        onClick={toggleInfoWindow}
      >
        {renderMarker()}
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
