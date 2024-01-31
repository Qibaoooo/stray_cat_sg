import React from "react";
import { SingaporeGeoCoord } from "./utils/properties";
import GoogleMapReact from "google-map-react";
import { Col, Row } from "react-bootstrap";
import SidePanel from "./components/SidePanel";
import CatSightingMarker from "./components/CatSightingMarker";

const CatMap = () => {
  let apiKey = process.env.REACT_APP_GOOGLE_MAPS_API_KEY;
  console.log(apiKey);

  const onDrag = (map) => {
    console.log(JSON.stringify(map.center));
  };

  return (
    <div style={{ height: "100vh", width: "100%" }}>
      <GoogleMapReact
        bootstrapURLKeys={{ key: apiKey }}
        defaultCenter={SingaporeGeoCoord}
        defaultZoom={12}
        onDrag={onDrag}
      >
        <CatSightingMarker
          lat={SingaporeGeoCoord.lat}
          lng={SingaporeGeoCoord.lng}
        />
      </GoogleMapReact>
    </div>
  );
};

const MapPage = () => {
  return (
    <Row>
      <Col xs={9}>
        <CatMap />
      </Col>
      <Col>
        <SidePanel></SidePanel>
      </Col>
    </Row>
  );
};

export default MapPage;
