import axios from "axios";
import { backendIP, getJsonHeadersWithJWT } from "../properties";

let getComments = (id) => {
    return axios.get(`${backendIP}/api/comments/${id}`, {
        headers: getJsonHeadersWithJWT(),
    });
}

export { getComments };