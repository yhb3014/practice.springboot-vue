import * as types from "./mutationType";
import VueCookies from "vue-cookies";

export default {
  [types.EMAIL_ID](state, emailId) {
    state.emailId = emailId;
  },
  [types.LOGIN_TOKEN](state, payload) {
    VueCookies.set("accessToken", payload.accessToken, "60s");
    VueCookies.set("refreshToken", payload.refreshToken, "1h");
    state.accessToken = payload.accessToken;
    state.refreshToken = payload.refreshToken;
  },
  [types.REFRESH_TOKEN](state, payload) {
    VueCookies.set("accessToken", payload.accessToken, "60s");
    VueCookies.set("refreshToken", payload.refreshToken, "1h");
    state.accessToken = payload.accessToken;
  },
  [types.REMOVE_TOKEN]() {
    VueCookies.remove("accessToken");
    VueCookies.remove("refreshToken");
  },
  [types.ERROR_STATE](state, errorState) {
    state.errorState = errorState;
  },
  [types.IS_AUTH](state, isAuth) {
    state.isAuth = isAuth;
  },
};
