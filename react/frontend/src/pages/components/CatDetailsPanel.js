import { getCat } from "pages/utils/api/Cat";
import React, { useEffect, useState } from "react";
import { Stack } from "react-bootstrap";

const CatDetailsPanel = ({ cat, displayImgUrl }) => {
  const [imgUrl, SetImgUrl] = useState("");

  const tdStyles = {
    paddingRight: "1rem",
    width: "6rem",
    textAlign: "end",
  };

  return (
    <Stack style={{ minHeight: "100vh" }}>
      <img className="m-3" src={displayImgUrl} alt="cat"></img>
      <div
        className="mx-3"
        style={{
          border: "1px solid black",
          borderRadius: "2rem",
          textAlign: "center",
        }}
      >
        <h5>Cat Details</h5>
        <table
          cellPadding={0}
          cellSpacing={0}
          style={{ borderCollapse: "collapse", margin: "auto" }}
        >
          <tr>
            <td style={tdStyles}>Name:</td>
            <td>
              <span>{cat.catName}</span>
            </td>
          </tr>
          <tr>
            <td style={tdStyles}>ID:</td>
            <td>
              <span>{cat.id}</span>
            </td>
          </tr>
          <tr>
            <td style={tdStyles}>Labels:</td>
            <td>
              <span>{cat.labels}</span>
            </td>
          </tr>
          <tr>
            <td style={tdStyles}>Location:</td>
            <td>
              <span>?</span>
            </td>
          </tr>
        </table>
      </div>
    </Stack>
  );
};

export default CatDetailsPanel;
