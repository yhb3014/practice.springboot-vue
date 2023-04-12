import axios from "axios";

//eslint-disable-next-line no-unused-vars
const getUserInfo = (userName, password) => {
  const param = {
    userName: userName,
    password: password,
  };

  return axios.post("/api/user/login", param);
};

export default {
  async doLogin(userName, password) {
    try {
      const getUserInfoPromise = getUserInfo(userName, password);
      const [userInfoResponse] = await Promise.all([getUserInfoPromise]);
      if (userInfoResponse.data.length === 0) {
        return "ERROR";
      } else {
        userInfoResponse.data.userName = userName;
        localStorage.setItem(
          "accessToken",
          userInfoResponse.data.data.accessToken
        );
        localStorage.setItem(
          "refreshToken",
          userInfoResponse.data.data.refreshToken
        );
        localStorage.setItem("grantType", userInfoResponse.data.data.grantType);
        return userInfoResponse;
      }
    } catch (err) {
      console.log(err);
    }
  },
};
