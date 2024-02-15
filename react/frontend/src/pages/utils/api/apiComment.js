import axios from "axios";
import { backendIP, getJsonHeadersWithJWT } from "../properties";

let getComments = (id) => {
    return axios.get(`${backendIP}/api/comments/${id}`, {
        headers: getJsonHeadersWithJWT(),
    });
}

let createNewComment = ({
    content,
    labels,
    cat_id,
    username,
    flag,
  }) => {
    let payload = {
      content,
      labels,
      cat_id,
      username,
      flag,
    };
    return axios.post(`${backendIP}/api/comments`, payload, {
      headers: getJsonHeadersWithJWT(),
    });
};

let getAllComments = () => {
  return axios.get(`${backendIP}/api/getallcomments`,{
    headers: getJsonHeadersWithJWT(),
  });
};

export { getComments, createNewComment, getAllComments};