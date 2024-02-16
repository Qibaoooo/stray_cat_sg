import React, { useEffect, useState } from "react";
import { createNewComment, getComments } from "pages/utils/api/apiComment";
import send_btn from "../../images/send_btn.png";
import label_btn from "../../images/label_btn.png";
import avatar from "../../images/avatar.png";
import { getUserinfoFromLocal } from "pages/utils/userinfo";
import { Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";


const CatCommentPanel = ({ id }) => {
  const [comments, setComments] = useState([]);
  const [publiccomment, setPublicComment] = useState('');
  const [flag, setFlag] = useState(false);
  const [isShow, setIsShow] = useState(false);
  let navigate = useNavigate();

  const handleCommentChange = (event) => {
    setPublicComment(event.target.value);
  };
  const [labels, setLabels] = useState([]);

  const handleFlagChange = (event) => {
    setFlag(event.target.checked);
  };
  const handleSubmit = (event) => {
    event.preventDefault();
    if (publiccomment.length < 1) {
      alert("please enter comment");
      return;
    }
    createNewComment(
      {
        content: publiccomment,
        labels: labels,
        cat_id: id,
        username: getUserinfoFromLocal().username,
        flag: flag,
      }).then(() => {
        getComments(id).then((resp) => {
          setComments(resp.data);
        });

        setPublicComment('');
        setFlag(false);
        setLabels([]);
        setIsShow(false);
      })
  };
  const chooseLabels = (event) => {
    event.preventDefault();
    setIsShow(!isShow);
  }
  const handleLabelChange = (event) => {
    const { value, checked } = event.target;
    if(checked&&labels.length>=5){
      alert("YOU CAN SELECT UP TO 5 LABELS!");
      return;
    }
    if (checked) {
      setLabels([...labels, value]);
    } else {
      setLabels(labels.filter(label => label !== value));
    }
  }

  useEffect(() => {
    getComments(id).then((resp) => {
      setComments(resp.data);
    });
  }, [id]);
  return (
    <div style={{ minHeight: "100vh" }}>
      <div style={{ backgroundColor: '#FFFBEE', paddingTop: "5px", paddingBottom: "5px", marginRight: "50px" }}>
        <h1 style={{ color: "#FD8293" }}><b>Comments</b></h1>

        {comments.length > 0 ? (comments.map(comment => {
          let styleBox = comment.flag ? { border: "2px solid black", borderCollapse: "collapse", backgroundColor: "#FFCCCB" } : { border: "2px solid black", borderCollapse: "collapse", backgroundColor: "#FFFFFF" };
          return (
            <div style={{ paddingRight: "30px" }}>
              <table width="100%"
                style={{ backgroundColor: '#FFFBEE' }}>
                <tr>
                  <td width="15%"> <img src={avatar} alt="avatar" style={{ height: "50px", width: "50px", borderRadius:"25px"}} /></td>
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
                    <table width="100%" style={styleBox}>
                      <tr>
                        <td style={{ paddingLeft: "5px", paddingRight: "5px" }}>
                          {(comment.newlabels.map(label => {
                            return(
                            <div style={{ borderRadius: "10px", backgroundColor: "#ECFFEE", marginTop: "5px", marginRight:"10px", marginBottom: "7px", paddingLeft: "5px", paddingRight: "5px", display: "inline-block", color: "#00B112" }}>
                            {label}
                            </div>)
                          }))
                            }
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
        <div style={{ paddingRight: "20px", marginTop: "10px" }}>
          <table width="100%">
            <tbody>
              <tr>
                <td width="15%">
                  <img src={avatar} alt="avatar" style={{ height: "50px", width: "50px" ,borderRadius:"25px"}} />
                </td>
                <td>
                  <input style={{ width: '100%', fontSize: "20px" }}
                    placeholder="Leave your comment here..."
                    value={publiccomment}
                    onChange={handleCommentChange}
                  >
                  </input>
                </td>
                <td>
                  <button onClick={chooseLabels} style={{ backgroundColor: 'transparent', border: '0px solid #000' }}>
                    <img src={label_btn} alt="label_btn" style={{ height: "30px", width: "30px" }} />
                  </button>
                </td>
                <td width="50px" >
                  <button onClick={handleSubmit} style={{ height: '100%', border: 'none', padding: 0 }}>
                    <img src={send_btn} alt="send_btn" style={{ height: "30px", width: '30px' }} />
                  </button>
                </td>
              </tr>
              {isShow && <tr>
                <td style={{fontSize:"20px" ,color: "#FD8293" }}>Labels</td>
                <td>
                  <table width="100%" style={{fontSize:"20px", textAlign:"left", color: "#FD8293" }}>
                    <tr style={{ color: "#FD8293" }}>
                      <th>body type</th>
                      <th>personality</th>
                      <th>others</th>
                    </tr>
                    <tr>
                      <td><label>
                        <input
                          type="checkbox"
                          value="Lean"
                          checked={labels.includes("Lean")}
                          onChange={handleLabelChange}
                        />
                        Lean
                      </label></td>
                      <td><label>
                        <input
                          type="checkbox"
                          value="Friendly"
                          checked={labels.includes("Friendly")}
                          onChange={handleLabelChange}
                        />
                        Friendly
                      </label></td>
                      <td><label>
                        <input
                          type="checkbox"
                          value="Mischievous"
                          checked={labels.includes("Mischievous")}
                          onChange={handleLabelChange}
                        />
                        Mischievous
                      </label></td>
                    </tr>
                    <tr>
                      <td><label>
                        <input
                          type="checkbox"
                          value="Chubby"
                          checked={labels.includes("Chubby")}
                          onChange={handleLabelChange}
                        />
                        Chubby
                      </label></td>
                      <td><label>
                        <input
                          type="checkbox"
                          value="Cool"
                          checked={labels.includes("Cool")}
                          onChange={handleLabelChange}
                        />
                        Cool
                      </label></td>
                      <td><label>
                        <input
                          type="checkbox"
                          value="Glutinous"
                          checked={labels.includes("Glutinous")}
                          onChange={handleLabelChange}
                        />
                        Glutinous
                      </label></td>
                    </tr>
                    <tr>
                      <td><label>
                        <input
                          type="checkbox"
                          value="Trim"
                          checked={labels.includes("Trim")}
                          onChange={handleLabelChange}
                        />
                        Trim
                      </label></td>
                      <td><label>
                        <input
                          type="checkbox"
                          value="Bold"
                          checked={labels.includes("Bold")}
                          onChange={handleLabelChange}
                        />
                        Bold
                      </label></td>
                      <td><label>
                        <input
                          type="checkbox"
                          value="Vocal"
                          checked={labels.includes("Vocal")}
                          onChange={handleLabelChange}
                        />
                        Vocal
                      </label></td>
                    </tr>
                    <tr>
                      <td><label>
                        <input
                          type="checkbox"
                          value="Muscular"
                          checked={labels.includes("Muscular")}
                          onChange={handleLabelChange}
                        />
                        Muscular
                      </label></td>
                      <td><label>
                        <input
                          type="checkbox"
                          value="Graceful"
                          checked={labels.includes("Graceful")}
                          onChange={handleLabelChange}
                        />
                        Graceful
                      </label></td>
                      <td><label>
                        <input
                          type="checkbox"
                          value="Playful"
                          checked={labels.includes("Playful")}
                          onChange={handleLabelChange}
                        />
                        Playful
                      </label></td>
                    </tr>
                    <tr>
                      <td><label>
                        <input
                          type="checkbox"
                          value="Lithe"
                          checked={labels.includes("Lithe")}
                          onChange={handleLabelChange}
                        />
                        Lithe
                      </label></td>
                      <td><label>
                        <input
                          type="checkbox"
                          value="Silly"
                          checked={labels.includes("Silly")}
                          onChange={handleLabelChange}
                        />
                        Silly
                      </label></td>
                      <td><label>
                        <input
                          type="checkbox"
                          value="Fluffy"
                          checked={labels.includes("Fluffy")}
                          onChange={handleLabelChange}
                        />
                        Fluffy
                      </label></td>
                    </tr>
                  </table>
                </td>
              </tr>}
              <tr>
                <td></td>
                <td width="75%" align="left">
                  <input
                    type="checkbox"
                    checked={flag}
                    onChange={handleFlagChange}
                  />
                  Flag Cat to help?
                </td>
                <td></td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <Button style={{ position:"fixed", right:"10vh", bottom:"10vh"}} onClick={()=>{
        navigate(-1)
      }}>Go Back</Button>
    </div>
  );
};

export default CatCommentPanel