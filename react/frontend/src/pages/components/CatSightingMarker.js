import React, {useState} from "react";
import { Button } from "react-bootstrap";
import "./CatSightingMarker.css"

const CatSightingMarker = ({text}) => {
  const [showInfoWindow, setShowInfoWindow] = useState(false);
  const toggleInfoWindow = () => {
    setShowInfoWindow(!showInfoWindow);
  };

  const closeInfoWindow = () => {
    setShowInfoWindow(false);
  };


  return (
    <div style={{ position: 'relative' }}>
      <Button
        size="sm"
        style={{ backgroundColor: "transparent", border: "0px" }}
        onClick={toggleInfoWindow}
      >
        <h3>🐱</h3>
      </Button>

      {showInfoWindow && (
        <div className="info-window">
          <button className="close-button" onClick={closeInfoWindow}>&times;</button>
          <h4>{text}</h4>
          <p>{text || 'Details about the sighting...'}</p>
        </div>
      )}
    </div>
  );
};

export default CatSightingMarker;
