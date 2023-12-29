<template>
    <div class='container'>
        <form @submit.prevent="submitForm">
            <div class="mb-3">
                <label for="currentPassword" class="form-label">Mật khẩu hiện tại:</label>
                <input type="password" class="form-control" id="currentPassword" v-model="currentPassword">
            </div>
            <div class="mb-3">
                <label for="newPassword" class="form-label">Mật khẩu mới:</label>
                <input type="password" class="form-control" id="newPassword" v-model="newPassword">
            </div>
            <div class="mb-3">
                <label for="newPasswordConfirm" class="form-label">Xác nhận mật khẩu mới:</label>
                <input type="password" class="form-control" id="newPasswordConfirm" v-model="newPasswordConfirm">
            </div>
            <button type="submit" class="btn btn-primary">Thay đổi mật khẩu</button>
        </form>
    </div>
</template>
<script setup>
    import { ref } from 'vue';
    import axios from 'axios';
    import Swal from 'sweetalert2';

    const currentPassword = ref('');
    const newPassword = ref('');
    const newPasswordConfirm = ref('');

    const submitForm = () => {
        if (newPassword.value === newPasswordConfirm.value) {
            console.log("Current password:", currentPassword.value);
            console.log("New password:", newPassword.value);
            const token = localStorage.getItem('token');
            const endpoint = 'http://localhost:8081/api/tai-khoan';

            const data = {
                currentPassword: currentPassword.value,
                newPassword: newPassword.value
            };

            console.log(data);

            axios.patch(endpoint, data, {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`
                }
            })
                .then(response => {
                    console.log(response.data);
                    Swal.fire({
                        icon: "success",
                        title: "Thay đổi mật khẩu thành công",
                        text: "Bạn đã thay đổi mật khẩu thành công.",
                    }).then(() => {
                        window.location.href = '/home';
                    });
                })
                .catch(error => {
                    console.error(error);
                    Swal.fire({
                        icon: "warning",
                        title: "Thay đổi mật khẩu thất bại",
                        text: "Mật khẩu cũ không chính xác.",
                    }).then(() => {
                        next({ name: "/change-pw" });
                    });
                });
        } else {
            Swal.fire({
                icon: "warning",
                title: "Thay đổi mật khẩu thất bại",
                text: "Xác nhận mật khẩu mới không chính xác.",
            }).then(() => {
                next({ name: "/change-pw" });
            });
        }
    };
</script>
