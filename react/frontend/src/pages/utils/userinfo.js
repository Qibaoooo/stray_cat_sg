let setUserinfoLocal = (info) => {
    localStorage.setItem('scs_userinfo', JSON.stringify(info))
}

let getUserinfoFromLocal = () => {
    let userinfo = JSON.parse(localStorage.getItem('scs_userinfo'))
    return userinfo
}

let clearUserInfoAndRedirectToLogin = () => {
    localStorage.removeItem('scs_userinfo')
    alert("please login first.")
    window.location.href = "/login"
}

export { setUserinfoLocal, getUserinfoFromLocal, clearUserInfoAndRedirectToLogin }