import axios from "axios";
import { backendIP, getJsonHeadersWithJWT } from "../properties";

const createNewVerification = ({
  imageURL,
  userId,
}) => {
  let payload = {
    imageURL,
    userId,
  };
  return axios.post(`${backendIP}/api/verification`, payload, {
    headers: getJsonHeadersWithJWT(),
  });
};

const getPendingVerifications = () => {
    return axios.get(`${backendIP}/api/verification`, {
        headers: getJsonHeadersWithJWT(),
    })
};

const rejectVerifications = (id) => {
    return axios.delete(`${backendIP}/api/verification?id=${id}`, {
      headers: getJsonHeadersWithJWT(),
    });
  };



const approveVerifications = (id) => {
    return axios.post(`${backendIP}/api/verification/approve?id=${id}`, {
      headers: getJsonHeadersWithJWT(),
    });
  };

export { createNewVerification, getPendingVerifications,rejectVerifications,approveVerifications };
