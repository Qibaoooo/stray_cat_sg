import React, {useEffect} from 'react'
import { Col, Row } from 'react-bootstrap';
import MapSidePanel from './components/mapSidePanel';
import LostCatForm from './components/lostCatForm';
import { requireLoginUser } from './utils/userinfo';

const LostCat = () => {
  useEffect(()=>{
    requireLoginUser()
  })
  return (
    <Row className="g-0">
      <Col className="g-0" xs={8}>
        <LostCatForm />
      </Col>
      <Col className="g-0" xs={4}>
        <MapSidePanel></MapSidePanel>
      </Col>
    </Row>
  );
}

export default LostCat