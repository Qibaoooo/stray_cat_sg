import { getAllCats } from "pages/utils/api/apiCat";
import React, { useEffect, useState } from "react";
import { Table } from "react-bootstrap";

const CatListTable = ({ viewType }) => {
  const tableCols = ["", "Cat Name", "Cat breed", "Labels"];
  const [cats, SetCats] = useState([]);

  useEffect(() => {
    getAllCats().then((resp) => {
      
      SetCats(resp.data);
    })
    .catch(e =>{
      console.log(e)
    })
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
            <tr>
              <td>
                <img src={cat.catSightings[0].imagesURLs[0]} style={{width:"50px"}}></img>
              </td>
              <td>{cat.catName}</td>
              <td>{cat.catBreed}</td>
              <td>{cat.labels.join(", ")}</td>
            </tr>
          );
        })}
      </tbody>
    </Table>
  );
};

export default CatListTable;
