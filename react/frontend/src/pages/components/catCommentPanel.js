import React, { useEffect, useState } from "react";
import { getComments } from "pages/utils/api/apiComment";

const CatCommentPanel = ({ id }) => {
  const [comments, SetComments] = useState([]);

  useEffect(() => {
    getComments(id).then((resp) => {
      SetComments(resp.data);
    });
  }, []);
  return (
    <div style={{ minHeight: "100vh" }}>
      <h2>Comments</h2>
      <div
        className="mx-5"
      >
        {comments.map(comment => {
          return (
            <table>
              <tr>
                <td>avator</td>
                <td>{comment.username}</td>
                <td>{comment.time}</td>
              </tr>
              <tr>
                <td></td>
                <td>{comment.content}</td>
              </tr>
            </table>
          )
        })}
      </div>
    </div>
  );
};

export default CatCommentPanel