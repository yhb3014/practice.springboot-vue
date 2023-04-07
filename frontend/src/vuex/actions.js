import {
  ACCESS_TOKEN,
  EMAIL_ID,
  ERROR_STATE,
  IS_AUTH,
  REFRESH_TOKEN,
} from "./mutationType";
import loginApi from "../service/loginApi";

let setEmailId = ({ commit }, data) => {
  commit(EMAIL_ID, data);
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
      setEmailId(store, loginResponse.data.emailId);
      setErrorState(store, "");
      setIsAuth(store, true);
      setAccessToken(store, loginResponse.data.data.accessToken);
      setRefreshToken(store, loginResponse.data.data.refreshToken);
  }
};

export default {
  async login(store, { emailId, password }) {
    let loginResponse = await loginApi.doLogin(emailId, password);
    processResponse(store, loginResponse);
    return store.getters.getIsAuth;
  },
};
