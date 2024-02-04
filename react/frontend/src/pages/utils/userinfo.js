let setUserinfoLocal = (info) => {
    localStorage.setItem('scs_userinfo', JSON.stringify(info))
}

let getUserinfoFromLocal = () => {
    let userinfo = JSON.parse(localStorage.getItem('scs_userinfo'))
    if (userinfo === null) {
        clearUserInfoAndRedirectToLogin()
    }
    return userinfo
}

let clearUserInfoAndRedirectToLogin = () => {
    alert("please login first.")
    localStorage.removeItem('scs_userinfo')
    window.location.href = "/login"
}

export { setUserinfoLocal, getUserinfoFromLocal, clearUserInfoAndRedirectToLogin }