import { createRouter, createWebHistory } from 'vue-router';

import Home from '../views/index.vue';
import store from '../store';

// Vue.use(VueRouter);
// import { createApp } from 'vue';
// const app = createApp();
// app.use(createRouter);

const routes = [
    //dashboard
    { path: '/home', name: 'Home', component: Home },

    {
        path: '/thong-ke',
        name: 'thong-ke',
        component: () => import(/* webpackChunkName: "index2" */ '../views/apps/invoice/thong_ke.vue'),
    },

    //auth
    {
        // path: '/auth/login-boxed',
        path: '/',
        name: 'login-boxed',
        component: () => import(/* webpackChunkName: "auth-login-boxed" */ '../views/auth/login_boxed.vue'),
        meta: { layout: 'auth' },
    },
    // {
    //     path: '/auth/register-boxed',
    //     name: 'register-boxed',
    //     component: () => import(/* webpackChunkName: "auth-register-boxed" */ '../views/auth/register_boxed.vue'),
    //     meta: { layout: 'auth' },
    // },

    {
        path: '/users/profile',
        name: 'profile',
        component: () => import(/* webpackChunkName: "users-profile" */ '../views/users/profile.vue'),
    },

    {
        path: '/nguyen-lieu',
        name: 'nguyen-lieu',
        component: () => import(/* webpackChunkName: "widgets" */ '../views/nguyen_lieu.vue'),
    },

    {
        path: '/nguyen-lieu/them',
        name: 'them-nguyen-lieu',
        component: () => import(/* webpackChunkName: "widgets" */ '../views/add_nguyen_lieu.vue'),
    },

    {
        path: '/nguyen-lieu/:id/sua',
        name: 'sua-nguyen-lieu',
        component: () => import(/* webpackChunkName: "widgets" */ '../views/sua_nguyen_lieu.vue'),
    },

    {
        path: '/chitietnguyenlieu/:id',
        name: 'chi-tiet-nguyen-lieu',
        component: () => import(/* webpackChunkName: "widgets" */ '../views/chi_tiet_nguyen_lieu.vue'),
    },

    {
        path: '/phieu-nhap',
        name: 'phieu-nhap',
        component: () => import(/* webpackChunkName: "widgets" */ '../views/apps/invoice/phieu_nhap.vue'),
    },

    {
        path: '/chitietphieunhap/:id',
        name: 'chi-tiet-phieu-nhap',
        component: () => import(/* webpackChunkName: "widgets" */ '../views/apps/invoice/chi_tiet_phieu_nhap.vue'),
    },

    {
        path: '/phieu-nhap/them/:id',
        name: 'them-phieu-nhap',
        component: () => import(/* webpackChunkName: "widgets" */ '../views/apps/invoice/add.vue'),
    },

    {
        path: '/nha-kho',
        name: 'kho',
        component: () => import(/* webpackChunkName: "apps-invoice-list" */ '../views/kho.vue'),
    },

    {
        path: '/chitietnhakho/:id',
        name: 'chi-tiet-nha-kho',
        component: () => import(/* webpackChunkName: "widgets" */ '../views/chi_tiet_kho.vue'),
    },

    {
        path: '/nha-cung-cap',
        name: 'nha-cung-cap',
        component: () => import(/* webpackChunkName: "apps-invoice-list" */ '../views/ncc.vue'),
    },

    {
        path: '/nha-cung-cap/them',
        name: 'them-nha-cung-cap',
        component: () => import(/* webpackChunkName: "apps-invoice-list" */ '../views/them_ncc.vue'),
    },

    {
        path: '/nhom-nguyen-lieu',
        name: 'nhom-nguyen-lieu',
        component: () => import(/* webpackChunkName: "apps-invoice-list" */ '../views/nhomnl.vue'),
    },

    {
        path: '/nhom-nguyen-lieu/them',
        name: 'them-nhom-nguyen-lieu',
        component: () => import(/* webpackChunkName: "apps-invoice-list" */ '../views/them_nhom.vue'),
    },

    {
        path: '/don-nhap',
        name: 'don-nhap',
        component: () => import(/* webpackChunkName: "widgets" */ '../views/apps/invoice/don_nhap.vue'),
    },

    {
        path: '/chitietdonnhap/:id',
        name: 'chi-tiet-don-nhap',
        component: () => import(/* webpackChunkName: "widgets" */ '../views/apps/invoice/chi_tiet_don_nhap.vue'),
    },

    {
        path: '/don-nhap/them',
        name: 'them-don-nhap',
        component: () => import(/* webpackChunkName: "widgets" */ '../views/apps/invoice/add_don_nhap.vue'),
    },

    {
        path: '/nha-kho/them',
        name: 'them-kho',
        component: () => import(/* webpackChunkName: "apps-invoice-list" */ '../views/apps/them_kho.vue'),
    },

    {
        path: '/tai-khoan/cap-moi',
        name: 'cap-moi-tai-khoan',
        component: () => import(/* webpackChunkName: "apps-invoice-list" */ '../views/apps/account/cung_cap_tai_khoan.vue'),
    },

    {
        path: '/tai-khoan/vhh',
        name: 'vo-hieu-hoa-tai-khoan',
        component: () => import(/* webpackChunkName: "apps-invoice-list" */ '../views/apps/account/vhh_tai_khoan.vue'),
    },

    //app/invoices
    {
        path: '/apps/invoice/list',
        name: 'invoice-list',
        component: () => import(/* webpackChunkName: "apps-invoice-list" */ '../views/apps/invoice/list.vue'),
    },

    {
        path: '/change-pw',
        name: 'doi-mat-khau',
        component: () => import(/* webpackChunkName: "widgets" */ '../views/auth/change_password.vue'),
    },

    {
        path: '/don-xuat',
        name: 'don-xuat',
        component: () => import(/* webpackChunkName: "widgets" */ '../views/apps/invoice/don_xuat.vue'),
    },
    
    {
        path: '/chitietdonxuat/:id',
        name: 'chi-tiet-don-xuat',
        component: () => import(/* webpackChunkName: "widgets" */ '../views/apps/invoice/chi_tiet_don_xuat.vue'),
    },
    {
        path: '/don-xuat/them/:id',
        name: 'them-don-xuat',
        component: () => import(/* webpackChunkName: "widgets" */ '../views/apps/invoice/add_don_xuat.vue'),
    },
    {
        path: '/apps/invoice/preview',
        name: 'invoice-preview',
        component: () => import(/* webpackChunkName: "apps-invoice-preview" */ '../views/apps/invoice/preview.vue'),
    },
    {
        path: '/apps/invoice/add',
        name: 'invoice-add',
        component: () => import(/* webpackChunkName: "apps-invoice-add" */ '../views/apps/invoice/add.vue'),
    },
    {
        path: '/apps/invoice/edit',
        name: 'invoice-edit',
        component: () => import(/* webpackChunkName: "apps-invoice-edit" */ '../views/apps/invoice/edit.vue'),
    },

    {
        path: '/tables/v3-table/export',
        name: 'v3-table-export',
        component: () => import(/* webpackChunkName: "tables-v3-table-export" */ '../views/tables/v3-table/export.vue'),
    },
];

const router = new createRouter({
    history: createWebHistory(),
    linkExactActiveClass: 'active',
    routes,
    scrollBehavior(to, from, savedPosition) {
        if (savedPosition) {
            return savedPosition;
        } else {
            return { left: 0, top: 0 };
        }
    },
});

router.beforeEach((to, from, next) => {
    if (to.meta && to.meta.layout && to.meta.layout == 'auth') {
        store.commit('setLayout', 'auth');
    } else {
        store.commit('setLayout', 'app');
    }
    next(true);
});

export default router;
