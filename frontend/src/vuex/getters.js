export default {
  getUserName: (state) => state.userName,
  getToken: (state) => {
    return {
      accessToken: state.accessToken,
      refreshToken: state.refreshToken,
    };
  },
  getNewAccessToken: (state) => state.accessToken,
  getErrorState: (state) => state.errorState,
  getIsAuth: (state) => state.isAuth,
  loggedIn(state) {
    return !state.user;
  },
};
