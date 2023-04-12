import * as types from "./mutationType";
// import VueCookies from "vue-cookies";

export default {
  [types.USER_NAME](state, userName) {
    state.userName = userName;
  },
  [types.ERROR_STATE](state, errorState) {
    state.errorState = errorState;
  },
  [types.IS_AUTH](state, isAuth) {
    state.isAuth = isAuth;
  },
  [types.ACCESS_TOKEN](state, accessToken) {
    state.accessToken = accessToken;
  },
  [types.REFRESH_TOKEN](state, refreshToken) {
    state.refreshToken = refreshToken;
  },
};
