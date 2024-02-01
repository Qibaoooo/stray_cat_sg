import React, { useEffect, useState } from "react";
import { Col, Row } from "react-bootstrap";
import { useSearchParams } from "react-router-dom";
import CatDetailsPanel from "./components/CatDetailsPanel";
import CatCommentPanel from "./components/CatCommentPanel";
import { getCat } from "./utils/api/Cat";

const CatDetailsPage = () => {
  const [searchParams] = useSearchParams();
  const id = searchParams.get("id");

  const [cat, SetCat] = useState({});
  const [imgUrl, SetImgUrl] = useState("");

  useEffect(() => {
    getCat(id).then((resp) => {
      SetCat(resp.data);
      SetImgUrl(resp.data.catSightings[0].imagesURLs[0]);
    });
  }, []);

  return (
    <div>
      <h5>CatDetailsPage</h5>
        <Row>
          <Col xs={6}>
            <CatDetailsPanel cat={cat} displayImgUrl={imgUrl}></CatDetailsPanel>
          </Col>
          <Col xs={6}>
            <CatCommentPanel isApproved={cat.isApproved}></CatCommentPanel>
          </Col>
        </Row>
    </div>
  );
};

export default CatDetailsPage;
