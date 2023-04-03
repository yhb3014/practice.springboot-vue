import { EMAIL_ID, ERROR_STATE, IS_AUTH } from "./mutationType";
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

let processResponse = (store, loginResponse) => {
  switch (loginResponse) {
    case "ERROR":
      setErrorState(store, "ERROR");
      setIsAuth(store, false);
      break;
    default:
      setEmailId(store, loginResponse.emailId);
      setErrorState(store, "");
      setIsAuth(store, true);
  }
};

export default {
  async login(store, { emailId, password }) {
    let loginResponse = await loginApi.doLogin(emailId, password);
    processResponse(store, loginResponse);
    return store.getters.getIsAuth;
  },
};
