let setUserinfoLocal = (info) => {
    localStorage.setItem('scs_userinfo', JSON.stringify(info))
}

let getUserinfoFromLocal = () => {
    return JSON.parse(localStorage.getItem('scs_userinfo'))
}

export { setUserinfoLocal, getUserinfoFromLocal }