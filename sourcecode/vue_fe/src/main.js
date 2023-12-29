import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
const app = createApp(App);

// bootstrap
import * as bootstrap from "bootstrap";
window.bootstrap = bootstrap;

// modals
import "./assets/sass/components/custom-modal.scss";

// perfect scrollbar
import PerfectScrollbar from "vue3-perfect-scrollbar";
import "vue3-perfect-scrollbar/dist/vue3-perfect-scrollbar.css";

//vue-meta
import { createHead } from "@vueuse/head";
const head = createHead();

//Sweetalert
import Swal from "sweetalert2";
window.Swal = Swal;

// nouislider - later remove and add to page due to not working in page
import VueNouislider from "vue3-nouislider";
import "vue3-nouislider/dist/vue3-nouislider.css";

// vue input mask
import Maska from "maska";

// smooth scroll
import { registerScrollSpy } from "vue3-scroll-spy/dist/index";
registerScrollSpy(app, { offset: 118 });

//vue-i18n
import i18n from "./i18n";

// datatables
import { ClientTable } from "v-tables-3";

// json to excel
import vue3JsonExcel from "vue3-json-excel";

//vue-wizard
import VueFormWizard from "vue3-form-wizard";
import "vue3-form-wizard/dist/style.css";

// set default settings
import appSetting from "./app-setting";
window.$appSetting = appSetting;
window.$appSetting.init();

//markdown editor
import VueEasymde from 'vue3-easymde';
import "easymde/dist/easymde.min.css";
import { mapState } from 'vuex';


router.beforeEach((to, from, next) => {
    const isAuthenticated = localStorage.getItem("token");
    const per = localStorage.getItem("permission");
    
    if (to.name === "login-boxed" && isAuthenticated) {
        Swal.fire({
            icon: "warning",
            title: "Warning",
            text: "Bạn đã đăng nhập, không thể vào trang Đănh nhập nữa!",
        }).then(() => {
            next({ name: "Home" });
        });
    } else if (to.name !== "login-boxed" && !isAuthenticated) {
        Swal.fire({
            icon: "warning",
            title: "Warning",
            text: "Xin hãy đăng nhập để vào trang này!",
        }).then(() => {
            next({ name: "login-boxed" });
        });
    } else if ((per !== 'admin' && per !== 'chu') && to.name === 'cap-moi-tai-khoan') {
        Swal.fire({
            icon: "warning",
            title: "Warning",
            text: "Bạn không có quyền vào trang này!",
        }).then(() => {
            next({ name: "Home" });
        });
    } else if ((per === 'admin') && to.name === 'Home') {
        Swal.fire({
            icon: "warning",
            title: "Warning",
            text: "Bạn không có quyền vào trang này!",
        }).then(() => {
            next({ name: "cap-moi-tai-khoan" });
        });
    } else if ((per !== 'admin') && to.name === 'vo-hieu-hoa-tai-khoan') {
        Swal.fire({
            icon: "warning",
            title: "Warning",
            text: "Bạn không có quyền vào trang này!",
        }).then(() => {
            next({ name: "Home" });
        });
    } else if ((per !== 'quankho' && per !== 'chu') && to.name === 'them-phieu-nhap') {
        Swal.fire({
            icon: "warning",
            title: "Warning",
            text: "Bạn không có quyền vào trang này!",
        }).then(() => {
            next({ name: "Home" });
        });
    } else if ((per !== 'quankho' && per !== 'chu') && to.name === 'them-don-nhap') {
        Swal.fire({
            icon: "warning",
            title: "Warning",
            text: "Bạn không có quyền vào trang này!",
        }).then(() => {
            next({ name: "Home" });
        });
    } else if ((per !== 'quankho' && per !== 'chu') && to.name === 'them-don-xuat') {
        Swal.fire({
            icon: "warning",
            title: "Warning",
            text: "Bạn không có quyền vào trang này!",
        }).then(() => {
            next({ name: "Home" });
        });
    } else if ((per !== 'quankho' && per !== 'chu') && to.name === 'sua-nguyen-lieu') {
        Swal.fire({
            icon: "warning",
            title: "Warning",
            text: "Bạn không có quyền vào trang này!",
        }).then(() => {
            next({ name: "Home" });
        });
    } else {
        next();
    }
});

app.use(store).use(router).use(i18n).use(PerfectScrollbar).use(VueNouislider).use(Maska).use(ClientTable).use(vue3JsonExcel).use(VueFormWizard).use(head).use(VueEasymde).mount("#app");


