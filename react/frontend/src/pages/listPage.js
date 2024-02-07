import React from 'react'
import { Col, Row } from 'react-bootstrap';
import MapSidePanel from './components/mapSidePanel';
import CatList from './components/catList';

const listPage = () => {
    return (
        <Row className="g-0">
          <Col className="g-0" xs={8}>
            <CatList />
          </Col>
          <Col className="g-0" xs={4}>
            <MapSidePanel />
          </Col>
        </Row>
      );
}

export default listPage