//  To get vectors of image from a single image [GET]
//  This API takes in the URL of the image (stored in our Azure blob)
//  example: https://stray-cats-ml-container.azurewebsites.net/getembedding/?filename=https://scsimages.blob.core.windows.net/images/cat_sightings_1_photo_0.jpg

import axios from "axios";
import {getJsonHeadersForMLApi, machineLearningIP } from "../properties";

let getVectorsOfImage = (filename) => {
  return axios.get(`${machineLearningIP}/getembedding/?filename=${filename}`,{
    headers: getJsonHeadersForMLApi(),
  });
};

export { getVectorsOfImage };
