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

export { createNewVerification, getPendingVerifications };
