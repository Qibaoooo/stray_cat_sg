import React from 'react'
import { Col, Row } from 'react-bootstrap';
import MapSidePanel from './components/mapSidePanel';
import LostCatForm from './components/lostCatForm';

const LostCat = () => {
  return (
    <Row>
      <Col xs={8}>
        <LostCatForm />
      </Col>
      <Col xs={4}>
        <MapSidePanel></MapSidePanel>
      </Col>
    </Row>
  );
}

export default LostCat