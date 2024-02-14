import React, { useEffect, useLayoutEffect } from "react";
import { Routes, Route } from "react-router-dom";
import MapPage from "pages/mapPage";
import CatDetailsPage from "pages/catDetailsPage";
import LoginPage from "pages/loginPage";
import ListPage from "pages/listPage";
import UploadSighting from "pages/uploadSightingPage";
import AccountPage from "pages/accountPage";
import LostCat from "pages/lostCat";
import CatResults from "pages/topSimilarCats"
<<<<<<< HEAD

import UploadVerification from "pages/uploadVerification";


import RegisterPage from "pages/registerPage";

import ChartAnalytics from "pages/components/chartAnalytics";
import AdminPage from "pages/adminPage";


=======
import RegisterPage from "pages/registerPage";
import ChartAnalytics from "pages/components/chartAnalytics";
>>>>>>> d98ce6d (Added user to the charts)

const RedirectToMapPage = () => {
  useEffect(() => {
    window.location.href = "/map"
  });

  return null;
};

function App() {
  useLayoutEffect(() => {
    document.body.className = document.body.className + " bg-primary";
  });

  return (
    <div style={{ fontFamily: "Comic Sans MS, cursive", textAlign:"center" }}>
      <Routes>
        <Route path="/" element={<RedirectToMapPage />} />
        <Route path="/map" element={<MapPage />} />
        <Route path="/list" element={<ListPage />} />
        <Route path="/catDetails" element={<CatDetailsPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/newSighting" element={<UploadSighting />} />
        <Route path="/account" element={<AccountPage />} />
        <Route path="/lost" element={<LostCat />} />
        <Route path="/result" element={<CatResults />} />
        <Route path="/uploadVfcation" element={<UploadVerification/>}/>
        <Route path="/analytics" element={<ChartAnalytics/>}/>
        <Route path="/admin" element={<AdminPage/>}/>
      </Routes>
    </div>
  );
}

export default App;
