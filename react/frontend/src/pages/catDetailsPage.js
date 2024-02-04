import React, { useEffect, useState } from "react";
import { Col, Row } from "react-bootstrap";
import { useSearchParams } from "react-router-dom";

import CatDetailsPanel from "./components/catDetailsPanel";
import CatCommentPanel from "./components/catCommentPanel";

import { getCat } from "./utils/api/apiCat";


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
    <div style={{backgroundColor: '#FFFAD9' }}>
      <h3>&nbsp;</h3>
        <Row>
          <Col xs={6}>
            <CatDetailsPanel cat={cat} displayImgUrl={imgUrl}></CatDetailsPanel>
          {/* <Button onClick={handleButtonClick}>Back to Home</Button> */}
          </Col>

          <Col xs={6}>
            <CatCommentPanel id={id}></CatCommentPanel>
          </Col>
        </Row>
    </div>
  );
};

export default CatDetailsPage;
