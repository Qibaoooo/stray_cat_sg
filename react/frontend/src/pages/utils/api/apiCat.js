import axios from "axios";
import { backendIP, getJsonHeadersWithJWT } from "../properties";

let getCat = (id) => {
    return axios.get(`${backendIP}/api/cat/${id}`, {
        headers: getJsonHeadersWithJWT(),
    });
}

let getAllCats = () => {
    return axios.get(`${backendIP}/api/cat`, {
        headers: getJsonHeadersWithJWT(),
    })
}

export { getCat, getAllCats };
