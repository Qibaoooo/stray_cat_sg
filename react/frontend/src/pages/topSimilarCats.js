import React, { useEffect } from 'react'
import { Col, Row } from 'react-bootstrap';
import MapSidePanel from './components/mapSidePanel';
import TopCatsList from './components/topCatsList';
import { requireLoginUser } from './utils/userinfo';

const UploadSighting = () => {
  
  useEffect(()=>{
    requireLoginUser()
  })

  return (
    <Row className="g-0">
      <Col className="g-0" xs={8}>
        < TopCatsList/>
      </Col>
      <Col className="g-0" xs={4}>
        <MapSidePanel></MapSidePanel>
      </Col>
    </Row>
  );
}

export default UploadSighting