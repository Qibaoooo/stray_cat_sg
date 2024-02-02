import React from "react";
// import GoogleMapReact from "google-map-react";
import { Col, Row } from "react-bootstrap";
import MapSidePanel from "./components/mapSidePanel";
import CatMap from "./components/catMap";

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
