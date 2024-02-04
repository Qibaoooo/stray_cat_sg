let setUserinfoLocal = (info) => {
    localStorage.setItem('scs_userinfo', JSON.stringify(info))
}

let getUserinfoFromLocal = () => {
    return JSON.parse(localStorage.getItem('scs_userinfo'))
}

let clearUserInfoAndRedirectToLogin = () => {
    alert("please login first.")
    setUserinfoLocal("") // clear expired userinfo if any
    window.location.href = "/login"
}

export { setUserinfoLocal, getUserinfoFromLocal, clearUserInfoAndRedirectToLogin }