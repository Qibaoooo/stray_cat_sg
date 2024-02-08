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
  }) => {
    let payload = {
      content,
      labels,
      cat_id,
    };
    return axios.post(`${backendIP}/api/comments`, payload, {
      headers: getJsonHeadersWithJWT(),
    });
  };

export { getComments, createNewComment};