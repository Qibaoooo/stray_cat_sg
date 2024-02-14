// Check the backend ::toJSON implementation to get avaible fields.
// https://github.com/Qibaoooo/stray_cat_sg/blob/520a0890c4fe0b120fcb25465f27b0edc1896325/src/main/java/nus/iss/team11/model/CatSighting.java#L54

import axios from "axios";
import { backendIP, getJsonHeadersWithJWT } from "../properties";

const getAllCatSightings = () => {
  return axios.get(`${backendIP}/api/cat_sightings?pending=false`, {
    headers: getJsonHeadersWithJWT(),
  });
};

const getPendingCatSightings = () => {
  return axios.get(`${backendIP}/api/cat_sightings?pending=true`, {
    headers: getJsonHeadersWithJWT(),
  });
};

const approveCatSighting = (id) => {
  return axios.post(`${backendIP}/api/cat_sightings/approve?id=${id}`, {
    headers: getJsonHeadersWithJWT(),
  });
};

const rejectCatSighting = (id) => {
  return axios.delete(`${backendIP}/api/cat_sightings?id=${id}`, {
    headers: getJsonHeadersWithJWT(),
  });
};

const createNewCatSightings = ({
  sightingName,
  locationLat,
  locationLong,
  time,
  suggestedCatName,
  suggestedCatBreed,
  tempImageURLs,
  vectorMap,
}) => {
  let payload = {
    sightingName,
    locationLat,
    locationLong,
    time,
    suggestedCatName,
    suggestedCatBreed,
    tempImageURLs,
    vectorMap,
  };
  return axios.post(`${backendIP}/api/cat_sightings`, payload, {
    headers: getJsonHeadersWithJWT(),
  });
};

const reassignCatSighting = (catSightingId, newCatId) => {
  return axios.post(
    `${backendIP}/api/reassign_sighting?catSightingId=${catSightingId}&newCatId=${newCatId}`,
    {
      headers: getJsonHeadersWithJWT(),
    }
  );
};

export {
  getAllCatSightings,
  createNewCatSightings,
  getPendingCatSightings,
  approveCatSighting,
  rejectCatSighting,
  reassignCatSighting,
};
