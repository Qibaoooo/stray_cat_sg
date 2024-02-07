import React from "react";
// import GoogleMapReact from "google-map-react";
import { Col, Row } from "react-bootstrap";
import MapSidePanel from "./components/mapSidePanel";
import CatMap from "./components/catMap";

const MapPage = () => {
  return (
    <Row className="g-0">
      <Col className="g-0" xs={8}>
        <CatMap />
      </Col>
      <Col className="g-0" xs={4}>
        <MapSidePanel></MapSidePanel>
      </Col>
    </Row>
  );
};

export default MapPage;
