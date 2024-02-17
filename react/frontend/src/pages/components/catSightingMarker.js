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
  
  const flagged = sighting.isFlagged;
  console.log(flagged);
  let emojiStyle = {
    margin: 0, 
    lineHeight: "normal", 
    display: "inline-block", 
  };

  const renderMarker = () => {
    let markerStyle = {
        padding: "2px 2px 4px 2px",
        display: "inline-block",
        borderRadius: "40%" 
    };

    let outerCircleStyle = {
      display: "inline-block",
      borderRadius: "50%",
      padding: "2px", // This creates the "outer circle" effect
    };

    
    let markerIcon = "🐱";

    if (isUserPosted && flagged) {
      // Apply red background for isUserPosted and add an outer circle for flagged
      markerStyle.backgroundColor = "red";
      outerCircleStyle.borderColor = "yellow";
      outerCircleStyle.borderWidth = "4px"; // Thickness of the outer circle
      outerCircleStyle.borderStyle = "solid";
    } else if (isUserPosted) {
      // Only isUserPosted is true, so just red background
      markerStyle.backgroundColor = "red";
    } else if (flagged) {
      // Only flagged is true, so just yellow background
      markerStyle.backgroundColor = "yellow";
    } else {
      // Default case, no specific styles
      markerStyle.backgroundColor = "transparent";
    }
    

    return (
      <div style={outerCircleStyle}>
          <div style={markerStyle}>
              <h3 style={emojiStyle}>🐱</h3>
          </div>
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
