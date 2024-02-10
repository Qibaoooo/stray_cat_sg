import { getAllCats } from "pages/utils/api/apiCat";
import { getAllCatSightings } from "pages/utils/api/apiCatSightings";
import { clearUserInfoAndRedirectToLogin } from "pages/utils/userinfo";
import React, { useEffect, useState } from "react";
import { Table } from "react-bootstrap";
import { useLocation } from "react-router-dom";
import { getTopCats } from "pages/utils/api/apiLostCat";

const TopCatsList = () => {
  const CatCols = ["", "Cat Name", "Cat breed", "Match Possibility (%)"];

  const [tableCols, SetTableCols] = useState(CatCols);
  const [cats, SetCats] = useState([]);
  const [sightings, SetSightings] = useState([]);
  const location = useLocation();
  const { matches } = location.state;
  const actualMatches = JSON.parse(matches);

  useEffect(() => {
    // Add get cats based on the matching
    getTopCats(actualMatches)
    .then((resp) => {
      let data = resp.data.sort((a,b) => b.probability - a.probability)
      SetCats(data);
      console.log(data);
    })
    .catch((e) => {
      window.location.href = "/lost";
    });
  }, []);

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
        {cats.map((cat, index, array) => {
              return (
                <tr
                  key={index}
                  onClick={() => {
                    window.location.href = `/catDetails?id=${cat.id}`;
                  }}
                >
                  <td>
                    <img
                      src={cat.imagesURLs[0]}
                      style={{ width: "50px" }}
                    ></img>
                  </td>
                  <td>{cat.sightingName}</td>
                  <td>{cat.suggestedCatBreed}</td>
                  <td>{Math.round(cat.probability * 100 * 100)/100 }</td>
                </tr>
              );
            })}
      </tbody>
    </Table>
  );
};

export default TopCatsList;