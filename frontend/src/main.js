import { createApp } from "vue";
import App from "./App.vue";
import axios from "axios";
import router from "./router";
import "./assets/common.css";

const app = createApp(App);

app.config.globalProperties.$axios = axios;
app.config.globalProperties.$server = "http://localhost:8080";
app.use(router).mount("#app");
