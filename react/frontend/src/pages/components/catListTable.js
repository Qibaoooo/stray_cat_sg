import { getAllCats } from "pages/utils/api/apiCat";
import { getAllCatSightings } from "pages/utils/api/apiCatSightings";
import { clearUserInfoAndRedirectToLogin } from "pages/utils/userinfo";
import React, { useEffect, useState } from "react";
import { Table } from "react-bootstrap";

const CatListTable = ({ viewType }) => {
  const CatCols = ["", "Cat Name", "Cat breed", "Labels"];
  const SightingCols = ["", "Sighting Name", "Location", "Time"];

  const [tableCols, SetTableCols] = useState(CatCols);
  const [cats, SetCats] = useState([]);
  const [sightings, SetSightings] = useState([]);

  useEffect(() => {
    if (viewType === "cat") {
      getAllCats()
        .then((resp) => {
          SetCats(resp.data);
          SetTableCols(CatCols);
        })
        .catch((e) => {
          clearUserInfoAndRedirectToLogin();
        });
    } else {
      getAllCatSightings()
        .then((resp) => {
          SetSightings(resp.data);
          SetTableCols(SightingCols);
        })
        .catch((e) => {
          clearUserInfoAndRedirectToLogin();
        });
    }
  }, [viewType]);

  return (
    <Table className="my-3" striped bordered={false} hover variant="primary">
      <thead>
        <tr>
          <th>{tableCols[0]}</th>
          <th>{tableCols[1]}</th>
          <th>{tableCols[2]}</th>
          <th>{tableCols[3]}</th>
        </tr>
      </thead>
      <tbody>
        {viewType === "cat"
          ? cats.map((cat, index, array) => {
              return (
                <tr onClick={()=>{window.location.href = `/catDetails?id=${cat.id}`}}>
                  <td>
                    <img
                      src={cat.catSightings[0].imagesURLs[0]}
                      style={{ width: "50px" }}
                    ></img>
                  </td>
                  <td>{cat.catName}</td>
                  <td>{cat.catBreed}</td>
                  <td>{cat.labels.join(", ")}</td>
                </tr>
              );
            })
          : sightings.map((sighting, index, array) => {
              return (
                <tr onClick={()=>{window.location.href = `/catDetails?id=${sighting.cat}`}}>
                  <td>
                    <img
                      src={sighting.imagesURLs[0]}
                      style={{ width: "50px" }}
                    ></img>
                  </td>
                  <td>{sighting.sightingName}</td>
                  <td>{sighting.locationLat + ", " + sighting.locationLong}</td>
                  <td>{sighting.time}</td>
                </tr>
              );
            })}
      </tbody>
    </Table>
  );
};

export default CatListTable;
