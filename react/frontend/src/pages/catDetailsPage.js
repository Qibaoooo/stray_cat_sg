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
  const [imgUrl, SetImgUrl] = useState([]);
  const [refresh, setRefresh] = useState(false);

  useEffect(() => {
    getCat(id).then((resp) => {
      SetCat(resp.data);
      let _urls = []
      resp.data.catSightings.forEach((sighting) => {
        _urls = [..._urls, ...sighting.imagesURLs]
      });
      SetImgUrl(_urls);
    });
  }, [refresh]); 

  return (
    <div style={{ backgroundColor: "#FFFAD9" }}>
      <h3>&nbsp;</h3>
      <Row className="g-0">
        <Col className="g-0" xs={6}>
          <CatDetailsPanel cat={cat} displayImgUrl={imgUrl}></CatDetailsPanel>
        </Col>

        <Col className="g-0" xs={6}>
          {!!cat.isApproved ? (
            <CatCommentPanel id={id} onRefresh={() => setRefresh(!refresh)}></CatCommentPanel>
          ) : (
            <div>
              <h5 style={{ marginTop: "40vh" }}>
                <p style={{ fontSize: "3rem" }}>🚧</p>
                This cat's information is pending approval by site admins.
              </h5>
            </div>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CatDetailsPage;
