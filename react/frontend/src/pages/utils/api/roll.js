import axios from "axios";
import { getJsonHeadersWithJWT } from "../properties";

let getRoll = (BACKEND_IP) => {
    return axios.get(`${BACKEND_IP}/roll`, {
        headers: getJsonHeadersWithJWT(),
    });
}
