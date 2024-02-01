import React from "react";
import { Col, Row } from "react-bootstrap";
import { useSearchParams } from "react-router-dom";
import CatDetailsPanel from "./components/CatDetailsPanel";
import CatCommentPanel from "./components/CatCommentPanel";

const CatDetailsPage = () => {
  const [searchParams] = useSearchParams();
  const id = searchParams.get("id");

  return (
    <div>
      <h5>CatDetailsPage</h5>
      {id ? (
        <Row>
          <Col xs={6}>
            <CatDetailsPanel id={id}></CatDetailsPanel>
          </Col>
          <Col xs={6}>
            <CatCommentPanel></CatCommentPanel>
          </Col>
        </Row>
      ) : (
        <div>
          <Col xs={4}></Col>
          <Col xs={8}></Col>
        </div>
      )}
    </div>
  );
};

export default CatDetailsPage;
