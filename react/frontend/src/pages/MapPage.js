import React from "react";
// import GoogleMapReact from "google-map-react";
import { Col, Row } from "react-bootstrap";
import MapSidePanel from "./components/MapSidePanel";
import CatMap from "./components/CatMap";

const MapPage = () => {
  return (
    <Row>
      <Col xs={9}>
        <CatMap />
      </Col>
      <Col>
        <MapSidePanel></MapSidePanel>
      </Col>
    </Row>
  );
};

export default MapPage;
