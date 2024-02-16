import axios from "axios";
import { getJsonHeaders, getJsonHeadersWithJWT } from "../properties";
import { backendIP } from "../properties";

const login = (u, p) => {
  return axios.post(
    `${backendIP}/api/auth/login`,
    { username: u, password: p },
    {
      headers: getJsonHeaders(),
    }
  );
};

const register = (u, p) => {
  return axios.post(
    `${backendIP}/api/auth/register`,
    { username: u, password: p },
    {
      headers: getJsonHeaders(),
    }
  );
}

const editProfile = (u, p, id) => {
  return axios.put(
    `${backendIP}/api/scsusers?id=${id}`,
    { username: u, password: p },
    {
      headers: getJsonHeadersWithJWT(),
    }
  );
}

export { login, register, editProfile };
