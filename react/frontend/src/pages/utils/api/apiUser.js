import axios from "axios";
import { getJsonHeadersWithJWT } from "../properties";
import { backendIP } from "../properties";

const getUserById = (id) => {
  return axios.get(`${backendIP}/api/scsusers/${id}`, {
    headers: getJsonHeadersWithJWT(),
  });
};

const getAllUser = () => {
  return axios.get(`${backendIP}/api/scsusers`, {
    headers: getJsonHeadersWithJWT(),
  });
};

export { getUserById, getAllUser };
