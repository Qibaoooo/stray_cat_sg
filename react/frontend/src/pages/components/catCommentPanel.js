import React, { useEffect, useState } from "react";
import { getComments } from "pages/utils/api/apiComment";
import { Table } from "react-bootstrap";
import send_btn from "../../images/send_btn.png";

const CatCommentPanel = ({ id }) => {
  const [comments, setComments] = useState([]);
  const [publiccomment, setPublicComment] = useState('');
  const handleCommentChange = (event) => {
    setPublicComment(event.target.value);
  };

  const handleSubmit = () => {
    // 在这里执行提交评论的逻辑，可以将评论传递给父组件或者发送到服务器

    // 清空输入框
    setPublicComment('');
  };

  useEffect(() => {
    getComments(id).then((resp) => {
      setComments(resp.data);
    });
  }, []);
  return (
    <div style={{ minHeight: "100vh" }}>
      <div style={{ backgroundColor: '#FFFBEE', paddingTop: "5px", paddingBottom: "5px", marginRight: "50px" }}>
        <h1 style={{ color: "#FD8293" }}><b>Comments</b></h1>

        {comments.length > 0 ? (comments.map(comment => {
          return (
            <div style={{ paddingRight: "30px" }}>
              <table width="100%"
                style={{ backgroundColor: '#FFFBEE' }}>
                <tr>
                  <td width="15%">avator</td>
                  <td align="left" style={{ fontSize: "24px" }}>
                    <div style={{ borderRadius: "10px", backgroundColor: "#DAF3A4", marginTop: "5px", marginBottom: "7px", paddingLeft: "5px", paddingRight: "5px", display: "inline-block" }}>
                      {comment.scsUser.username}
                    </div>
                  </td>
                  <td align="right" style={{ opacity: 0.5, fontSize: "16px" }}>
                    {comment.time}
                  </td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td colSpan={2} align="left" style={{ fontSize: "20px" }}>
                    <table width="100%" style={{ border: "2px solid black", borderCollapse: "collapse", backgroundColor: "#FFFFFF" }}>
                      <tr>
                        <td style={{ paddingLeft: "5px", paddingRight: "5px" }}>
                          <div style={{ borderRadius: "10px", backgroundColor: "#ECFFEE", marginTop: "5px", marginBottom: "7px", paddingLeft: "5px", paddingRight: "5px", display: "inline-block", color: "#00B112" }}>
                            label
                          </div>
                        </td>
                      </tr>
                      <tr>
                        <td style={{ paddingLeft: "5px", paddingRight: "5px" }}>
                          {comment.content}
                        </td>
                      </tr>
                      <tr style={{ fontSize: "2px" }}><td>&nbsp;</td></tr>
                    </table>
                  </td>
                </tr>
              </table>
            </div>
          )
        })) : <div>Leave your first comment here!!!</div>}
        <div style={{ paddingRight: "20px",marginTop:"10px" }}>
        <table width="100%">
          <tr>
            <td width="15%">
              avator
            </td>
            <td>
              <input style={{ width: '100%' ,fontSize:"20px"}}
                placeholder="Leave your comment here..."
                value={publiccomment}
                onChange={handleCommentChange}
              ></input>
            </td>
            <td width="50px">
              <button onClick={handleSubmit} style={{ height: '100%', border: 'none', padding: 0 }}>
                <img src={send_btn} alt="send_btn" style={{height:"30px", width:'30px'}}/>
              </button>
            </td>
          </tr>
        </table>
        </div>
      </div>
    </div>
  );
};

export default CatCommentPanel