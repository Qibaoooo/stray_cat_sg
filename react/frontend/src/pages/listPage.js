import React from 'react'
import { Col, Row } from 'react-bootstrap';
import MapSidePanel from './components/mapSidePanel';
import CatList from './components/catList';

const listPage = () => {
    return (
        <Row>
          <Col xs={8}>
            <CatList />
          </Col>
          <Col xs={4}>
            <MapSidePanel />
          </Col>
        </Row>
      );
}

export default listPage