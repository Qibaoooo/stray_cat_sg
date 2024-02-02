import React, { useEffect, useState } from "react";
import { Col, Row } from "react-bootstrap";
import { useSearchParams } from "react-router-dom";
import CatDetailsPanel from "./components/CatDetailsPanel";
import CatCommentPanel from "./components/CatCommentPanel";
import { getCat } from "./utils/api/Cat";
import { Button } from "bootstrap";

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
  
  // const handleButtonClick = () => {
    
  //   console.log("Button Clicked!");
  // };

  return (
    <div style={{backgroundColor: '#FFFAD9' }}>
      <h5 >CatDetailsPage</h5>
        <Row>
          <Col xs={6}>
            <CatDetailsPanel cat={cat} displayImgUrl={imgUrl}></CatDetailsPanel>
          {/* <Button onClick={handleButtonClick}>Back to Home</Button> */}
          </Col>
          
          <Col xs={6}>
            <CatCommentPanel isApproved={cat.isApproved}></CatCommentPanel>
          </Col>
        </Row>
    </div>
  );
};

export default CatDetailsPage;
