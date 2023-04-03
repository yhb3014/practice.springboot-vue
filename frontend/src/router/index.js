import { createRouter, createWebHistory } from "vue-router";
import PageHome from "@/views/PageHome.vue";
import BoardList from "@/views/board/BoardList.vue";
import BoardWrite from "@/views/board/BoardWrite.vue";
import SignIn from "@/views/user/SignIn.vue";
import SignUp from "@/views/user/SignUp.vue";
import store from "@/vuex/store";

const checkToken = () => (from, to, next) => {
  const token = localStorage.getItem("accessToken");
  if (token) {
    store.state.isLogin = true;
    return next();
  }

  next("/user/signIn");
};

const routes = [
  {
    path: "/",
    name: "PageHome",
    component: PageHome,
  },
  {
    path: "/board/list",
    name: "BoardList",
    component: BoardList,
    beforeEnter: checkToken(),
  },
  {
    path: "/user/signIn",
    name: "SignIn",
    component: SignIn,
  },
  {
    path: "/user/signUp",
    name: "SignUp",
    component: SignUp,
  },
  {
    path: "/board/write",
    name: "BoardWrite",
    component: BoardWrite,
    beforeEnter: checkToken(),
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
