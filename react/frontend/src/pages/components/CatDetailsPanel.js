import { getCat } from "pages/utils/api/Cat";
import React, { useEffect, useState } from "react";
import { Row } from "react-bootstrap";

const CatDetailsPanel = ({ id }) => {
  const [cat, SetCat] = useState({});
  const [imgUrl, SetImgUrl] = useState("");

  useEffect(() => {
    getCat(id).then((resp) => {
      SetCat(resp.data);
      SetImgUrl(resp.data.catSightings[0].imagesURLs[0]);
    });
  }, []);

  return (
    <div style={{ minHeight: "100vh" }}>
      <p>CatDetailsPanel - {id}</p>
      <img className="m-5" src={imgUrl}></img>
      <div className="mx-5" style={{ border: "1px solid black", borderRadius:"2rem" }}>
        <h5>Cat Details</h5>
        {/* todo */}
      </div>
    </div>
  );
};

export default CatDetailsPanel;
