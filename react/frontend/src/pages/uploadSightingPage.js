import React from 'react'
import { Col, Row } from 'react-bootstrap';
import MapSidePanel from './components/mapSidePanel';
import SightingForm from './components/sightingForm';

const UploadSighting = () => {
  return (
    <Row>
      <Col xs={8}>
        <SightingForm />
      </Col>
      <Col xs={4}>
        <MapSidePanel></MapSidePanel>
      </Col>
    </Row>
  );
}

export default UploadSighting