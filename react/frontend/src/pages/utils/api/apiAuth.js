import axios from "axios";
import { getJsonHeaders, getJsonHeadersWithJWT } from "../properties";
import { backendIP } from "../properties";

let login = (u, p) => {
  return axios.post(
    `${backendIP}/api/auth/login`,
    { username: u, password: p },
    {
      headers: getJsonHeaders(),
    }
  );
};

export { login };
