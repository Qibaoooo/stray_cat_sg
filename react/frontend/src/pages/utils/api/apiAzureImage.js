import axios from "axios";
import { backendIP, getImagePNGHeadersWithJWT } from "../properties";

let uploadSightingPhoto = (imageFile, fileName, fileType) => {
  return axios.post(`${backendIP}/api/images?fileName=${fileName}.${fileType}`, imageFile, {
    headers: getImagePNGHeadersWithJWT(),
  });
};

export { uploadSightingPhoto };
