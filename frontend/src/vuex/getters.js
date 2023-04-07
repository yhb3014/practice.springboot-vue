export default {
  getEmailId: (state) => state.emailId,
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
