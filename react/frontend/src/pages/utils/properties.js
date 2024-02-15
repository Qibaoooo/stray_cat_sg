import { getUserinfoFromLocal } from "./userinfo";

export const getJsonHeaders = () => {
  return {
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-Methods": "GET, POST, PATCH, PUT, DELETE, OPTIONS",
    "Access-Control-Allow-Headers": "Origin, Content-Type, X-Auth-Token",
  };
};

export const getJsonHeadersForMLApi = () => {
  return {
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-Methods": "GET, POST, PATCH, PUT, DELETE, OPTIONS",
    "Access-Control-Allow-Headers": "Origin, Content-Type, X-Auth-Token",
  };
};

export const getJsonHeadersWithJWT = () => {
  if (getUserinfoFromLocal() === null) {
    console.log("getUserinfoFromLocal() returned null. Please login first.");
    return;
  }
  return {
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-Methods": "GET, POST, PATCH, PUT, DELETE, OPTIONS",
    "Access-Control-Allow-Headers": "Origin, Content-Type, X-Auth-Token",
    Authorization: "Bearer " + getUserinfoFromLocal().jwt,
  };
};

export const getImagePNGHeadersWithJWT = () => {
  let headers = getJsonHeadersWithJWT();
  headers = { "Content-Type": "image/png", ...headers };
  return headers;
};
export const getImageJPGHeadersWithJWT = () => {
  let headers = getJsonHeadersWithJWT();
  headers = { "Content-Type": "image/jpeg", ...headers };
  return headers;
};

export const SingaporeGeoCoord = {
  lat: 1.365,
  lng: 103.817,
};

export const backendIP = process.env.REACT_APP_BACKEND_IP
  ? process.env.REACT_APP_BACKEND_IP
  : "http://localhost:8080";

export const machineLearningIP =
  "https://stray-cats-ml-container.azurewebsites.net";
