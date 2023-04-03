import { createApp } from "vue";
import App from "./App.vue";
import axios from "./axios/axios";
import router from "./router";
import store from "./vuex/store";
import VueCookies from "vue-cookies";
import "./assets/common.css";

axios.defaults.baseURL = process.env.VUE_APP_API_URL;

const app = createApp(App);

app.config.globalProperties.$axios = axios;
app.config.globalProperties.$store = store;

app.use(store).use(router).use(VueCookies).mount("#app");
