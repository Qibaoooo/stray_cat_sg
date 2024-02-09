const setUserinfoLocal = (info) => {
  localStorage.setItem("scs_userinfo", JSON.stringify(info));
};

const getUserinfoFromLocal = () => {
  let userinfo = JSON.parse(localStorage.getItem("scs_userinfo"));
  return userinfo;
};

const clearUserInfoAndRedirectToLogin = () => {
  localStorage.removeItem("scs_userinfo");
  window.location.href = "/login";
};

const clearUserInfoAndReload = () => {
  localStorage.removeItem("scs_userinfo");
  window.location.reload();
};

const requireLoginUser = () => {
  let userinfo = JSON.parse(localStorage.getItem("scs_userinfo"));
  if (userinfo == null) {
    clearUserInfoAndRedirectToLogin();
  }
  if (userinfo.expirationTime < Date.now()) {
    clearUserInfoAndRedirectToLogin();
  }
};

export {
  setUserinfoLocal,
  getUserinfoFromLocal,
  clearUserInfoAndRedirectToLogin,
  clearUserInfoAndReload,
  requireLoginUser,
};
