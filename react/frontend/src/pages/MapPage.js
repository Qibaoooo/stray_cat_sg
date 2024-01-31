import React, { useRef, useState } from "react";
// import GoogleMapReact from "google-map-react";
import { Col, Row } from "react-bootstrap";
import SidePanel from "./components/SidePanel";
import CatMap from "./components/CatMap";

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
