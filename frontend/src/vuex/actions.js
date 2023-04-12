import {
  ACCESS_TOKEN,
  ERROR_STATE,
  IS_AUTH,
  REFRESH_TOKEN,
  USER_NAME,
} from "./mutationType";
import loginApi from "../service/loginApi";

let setUserName = ({ commit }, data) => {
  commit(USER_NAME, data);
};

let setErrorState = ({ commit }, data) => {
  commit(ERROR_STATE, data);
};

let setIsAuth = ({ commit }, data) => {
  commit(IS_AUTH, data);
};

let setAccessToken = ({ commit }, data) => {
  commit(ACCESS_TOKEN, data);
};

let setRefreshToken = ({ commit }, data) => {
  commit(REFRESH_TOKEN, data);
};

let processResponse = (store, loginResponse) => {
  switch (loginResponse) {
    case "ERROR":
      setErrorState(store, "ERROR");
      setIsAuth(store, false);
      break;
    default:
      setUserName(store, loginResponse.data.userName);
      setErrorState(store, "");
      setIsAuth(store, true);
      setAccessToken(store, loginResponse.data.data.accessToken);
      setRefreshToken(store, loginResponse.data.data.refreshToken);
  }
};

export default {
  async login(store, { userName, password }) {
    let loginResponse = await loginApi.doLogin(userName, password);
    processResponse(store, loginResponse);
    return store.getters.getIsAuth;
  },
};
