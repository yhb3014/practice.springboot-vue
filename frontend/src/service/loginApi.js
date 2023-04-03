import axios from "axios";

//eslint-disable-next-line no-unused-vars
const getUserInfo = (emailId, password) => {
  const param = {
    emailId: emailId,
    password: password,
  };

  // return this.$axios.post("/api/user/login", param);
  return axios.post("/api/user/login", param);
};

export default {
  async doLogin(emailId, password) {
    try {
      const getUserInfoPromise = getUserInfo(emailId, password);
      const [userInfoResponse] = await Promise.all([getUserInfoPromise]);
      if (userInfoResponse.data.length === 0) {
        return "ERROR";
      } else {
        localStorage.setItem("accessToken", userInfoResponse.data.accessToken);
        localStorage.setItem(
          "refreshToken",
          userInfoResponse.data.refreshToken
        );
        return userInfoResponse;
      }
    } catch (err) {
      console.log(err);
    }
  },
};