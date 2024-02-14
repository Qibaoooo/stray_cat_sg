import axios from "axios";
import { backendIP, getJsonHeadersWithJWT } from "../properties";

const createNewVerification=({
    imageURL,
    
    userId,
})=>{
    let payload={
    imageURL,
    
    userId,
    };
    return axios.post(`${backendIP}/api/verification`,payload,{
        headers: getJsonHeadersWithJWT(),
    });
};

export{createNewVerification};