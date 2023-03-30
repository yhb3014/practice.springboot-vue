import { createApp } from "vue";
import App from "./App.vue";
import axios from "axios";
import router from "./router";
import "./assets/common.css";
import SummernoteEditor from "vue3-summernote-editor";

axios.defaults.baseURL = process.env.VUE_APP_API_URL;

const app = createApp(App);

app.config.globalProperties.$axios = axios;
app.component("SummernoteEditor", SummernoteEditor);

app.use(router).mount("#app");
