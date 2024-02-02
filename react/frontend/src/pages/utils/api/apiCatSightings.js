// Check the backend ::toJSON implementation to get avaible fields.
// https://github.com/Qibaoooo/stray_cat_sg/blob/520a0890c4fe0b120fcb25465f27b0edc1896325/src/main/java/nus/iss/team11/model/CatSighting.java#L54

import axios from "axios";
import { backendIP, getJsonHeadersWithJWT } from "../properties";

let getAllCatSightings = () => {
    return axios.get(`${backendIP}/api/cat_sightings`, {
        headers: getJsonHeadersWithJWT(),
    });
}

export { getAllCatSightings };
