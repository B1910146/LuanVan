<template>
    <div id='container1'>
    <div class="form auth-boxed">
        <div class="form-container outer">
            <div class="form-form">
                <div class="form-form-wrap">
                    <div class="form-container">
                        <div class="form-content">
                            <h1 class="">Đăng Nhập</h1>
                            <p class="">Đăng nhập vào hệ thống Quản Lý Kho của Nhà Hàng ABC</p>

                            <form class="text-start" @submit="submitForm">
                                <div class="form">
                                    <div id="username-field" class="field-wrapper input">
                                        <label for="username">Tên Đăng Nhập</label>
                                        <svg
                                            xmlns="http://www.w3.org/2000/svg"
                                            width="24"
                                            height="24"
                                            viewBox="0 0 24 24"
                                            fill="none"
                                            stroke="currentColor"
                                            stroke-width="2"
                                            stroke-linecap="round"
                                            stroke-linejoin="round"
                                            class="feather feather-user"
                                        >
                                            <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                                            <circle cx="12" cy="7" r="4"></circle>
                                        </svg>
                                        <input v-model="formData.username" type="text" class="form-control" placeholder="UserABC" />
                                    </div>

                                    <div id="password-field" class="field-wrapper input mb-2">
                                        <div class="d-flex justify-content-between">
                                            <label for="password">Mật Khẩu</label>
<!--                                            <router-link to="/auth/pass-recovery-boxed" class="forgot-pass-link">Forgot Password?</router-link>-->
                                        </div>
                                        <svg
                                            xmlns="http://www.w3.org/2000/svg"
                                            width="24"
                                            height="24"
                                            viewBox="0 0 24 24"
                                            fill="none"
                                            stroke="currentColor"
                                            stroke-width="2"
                                            stroke-linecap="round"
                                            stroke-linejoin="round"
                                            class="feather feather-lock"
                                        >
                                            <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
                                            <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
                                        </svg>
                                        <input v-model="formData.password" :type="pwd_type" class="form-control" placeholder="Mật Khẩu" />
                                        <svg
                                            @click="set_pwd_type"
                                            xmlns="http://www.w3.org/2000/svg"
                                            width="24"
                                            height="24"
                                            viewBox="0 0 24 24"
                                            fill="none"
                                            stroke="currentColor"
                                            stroke-width="2"
                                            stroke-linecap="round"
                                            stroke-linejoin="round"
                                            id="toggle-password"
                                            class="feather feather-eye"
                                        >
                                            <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
                                            <circle cx="12" cy="12" r="3"></circle>
                                        </svg>
                                    </div>
                                    <div class="d-sm-flex justify-content-between">
                                        <div class="field-wrapper">
                                            <button type="submit" class="btn btn-primary">Đăng Nhập</button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
</template>

<script setup>
    import { reactive, ref } from 'vue';
    import "/src/assets/sass/authentication/auth-boxed.scss";
    // import axios from "axios";
    // import { mapMutations, mapState } from 'vuex';
    // // import { useStore } from 'vuex';

    import { useMeta } from "/src/composables/use-meta";
    import Swal from 'sweetalert2';
    // import { jwt } from 'jsonwebtoken';
    useMeta({ title: "Đăng Nhập" });

    const pwd_type = ref("password");
    // const jwt = require('jsonwebtoken');

    const set_pwd_type = () => {
        if (pwd_type.value === "password") {
            pwd_type.value = "text";
        } else {
            pwd_type.value = "password";
        }
    };

    const formData = reactive({
        username: '',
        password: ''
    });
    
    // const store = useStore();
    // const { setToken } = mapMutations(['setToken']);
    // const getToken = ref(store.state.token);
    // const { getToken } = mapState(['token']);
    const submitForm = async (event) => {
        event.preventDefault();

        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(formData)
        };

        try {
            const response = await fetch("http://localhost:8081/api/authenticate", requestOptions);
            const body = await response.json();

            if (body && body.token) {
                const token = body.token;
                const secretKey = 'NmI4ZTVkNTkxYjE3ZmI1MzJhODMyMzFiYTY4NGQ4NDY3NWVhNmJhNTQzM2JhZGEyYzkxYWRmN2Q4ZjFjNTU5MWMyNjVlY2IxNDk5NjNmYjRjZjdmZjNhNjc1NjFjYmYzMjYyMDkxN2FlNzBjZWI0ZTAzY2I1ZDg3YWFiMmJmMzg=';
                localStorage.setItem("token", token);
                if (body.username.includes('admin')) {
                    localStorage.setItem("permission", "admin");
                } else if (body.username.includes('user')) {
                    localStorage.setItem("permission", "kiemhang");
                } else if (body.username.includes('manager')) {
                    localStorage.setItem("permission", "quankho");
                } else if (body.username.includes('owner')) {
                    localStorage.setItem("permission", "chu");
                }
                if (localStorage.getItem('permission') === 'admin') {
                    window.location.href = "/tai-khoan/cap-moi";
                } else {
                    window.location.href = "/home";
                }
            } else {
                Swal.fire({
                    icon: "warning",
                    title: "Warning",
                    text: "Sai thông tin đăng nhập!",
                }).then(() => {
                    next({ name: "/" });
                });
                console.error("Token not found in response body");
            }
        } catch (error) {
            console.error("Error: ", error);
        }
        
        // axios
        //     .post("http://localhost:8081/api/authenticate", formData)
        //     .then((response) => {
        //         // Xử lý phản hồi thành công
        //         // console.log(response.data);
        //         localStorage.setItem("token", response.data.token);
        //         window.location.href = "/home";
        //     })
        //     .catch((error) => {
        //         // Xử lý lỗi
        //         console.error(error);
        //     });
    }
</script>
<style>
    #container1 {
        background-image: url("public/assets/images/kho_bg.jpg");
    }
</style>
