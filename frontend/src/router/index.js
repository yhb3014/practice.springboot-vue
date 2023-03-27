import { createRouter, createWebHistory } from "vue-router";
import PageHome from "@/views/PageHome.vue";
import BoardList from "@/views/board/BoardList.vue";
import SignIn from "@/views/user/SignIn.vue";
import SignUp from "@/views/user/SignUp.vue";

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
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
