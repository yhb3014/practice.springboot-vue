import axios from "axios";

axios.interceptors.request.use(function (config) {
  const accessToken = localStorage.getItem("accessToken");
  const grantType = localStorage.getItem("grantType");
  config.headers["Authorization"] = grantType + accessToken;
  return config;
});

axios.interceptors.response.use(
  function (response) {
    try {
      return response;
    } catch (error) {
      console.error("response : ", error.message);
    }
  },
  async function (error) {
    if (error.response.data.status == 401) {
      await axios
        .post("/api/user/refresh")
        .then((res) => {
          localStorage.setItem("accessToken", res.data);
          window.location.reload();
        })
        .catch((error) => {
          console.log(error);
          localStorage.removeItem("accessToken");
        });
      return Promise.reject(error);
    }
  }
);

export default axios;
