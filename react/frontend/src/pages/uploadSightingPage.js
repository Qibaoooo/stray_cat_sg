import React, { useEffect } from 'react'
import { Col, Row } from 'react-bootstrap';
import MapSidePanel from './components/mapSidePanel';
import SightingForm from './components/sightingForm';
import { requireLoginUser } from './utils/userinfo';

const UploadSighting = () => {
  
  useEffect(()=>{
    requireLoginUser()
  })

  return (
    <Row className="g-0">
      <Col className="g-0" xs={8}>
        <SightingForm />
      </Col>
      <Col className="g-0" xs={4}>
        <MapSidePanel></MapSidePanel>
      </Col>
    </Row>
  );
}

export default UploadSighting