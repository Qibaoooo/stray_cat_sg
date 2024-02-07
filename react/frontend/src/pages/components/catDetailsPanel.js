import { getCat } from "pages/utils/api/apiCat";
import React, { useEffect, useState } from "react";
import { Carousel, Stack } from "react-bootstrap";

const CatDetailsPanel = ({ cat, displayImgUrl }) => {
  const tdStyles = {
    paddingRight: "1rem",
    width: "6rem",
    textAlign: "end",
    fontSize: "1.2rem",
    fontFamily: "Comic Sans MS, cursive",
    fontWeight: "bold",
  };

  const imgStyle = {
    maxWidth: "90%",
    maxHeight: "90%",
    height: "auto",
    borderRadius: "50px",
  };

  const contentStyle = {
    fontSize: "1rem",
    fontFamily: "Comic Sans MS, cursive",
  };

  return (
    <Stack
      className="m-5"
      style={{
        minHeight: "100vh",
        display: "flex",
        alignItems: "center",
        gap: "10px",
      }}
    >
      <Carousel data-bs-theme="dark" slide={false}>
        {displayImgUrl.map((url, index, array) => {
          return (
            <Carousel.Item key={index}>
              <div style={{ height: "45vh", width: "50vh" }}>
                <img className="m-3" src={url} alt="cat" style={imgStyle}></img>
              </div>
            </Carousel.Item>
          );
        })}
      </Carousel>
      <div
        className="mx-3"
        style={{
          border: "4px solid black",
          borderRadius: "2rem",
          textAlign: "center",
          backgroundColor: "white",
          overflow: "hidden",
          width: "550px",
          height: "250px",
        }}
      >
        <h5
          style={{
            fontSize: "1.5rem",
            fontFamily: "Comic Sans MS, cursive",
            padding: "15px",
            fontWeight: "bold",
          }}
        >
          Cat Details
        </h5>
        <table
          cellPadding={3}
          cellSpacing={0}
          style={{ borderCollapse: "collapse", margin: "auto" }}
        >
          <tbody>
            <tr>
              <td style={tdStyles}>Name:</td>
              <td>
                <span style={contentStyle}>{cat.catName}</span>
              </td>
            </tr>
            <tr>
              <td style={tdStyles}>ID:</td>
              <td>
                <span style={contentStyle}>{cat.id}</span>
              </td>
            </tr>
            <tr>
              <td style={tdStyles}>Labels:</td>
              <td>
                <span style={contentStyle}>{cat.labels}</span>
              </td>
            </tr>
            <tr>
              <td style={tdStyles}>Location:</td>
              <td>
                <span style={contentStyle}>?</span>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </Stack>
  );
};

export default CatDetailsPanel;
