<template>
    <div class="container">
        <h1>Vô hiệu hóa tài khoản</h1>
        <form @submit.prevent="disableAccount">
            <div class="form-group">
                <label for="username">Chọn tài khoản:</label>
                <select id="username" class="form-control" v-model="username">
                    <option value="">-- Chọn tài khoản --</option>
                    <option v-for="account in accounts" :value="account.username" :key="account.id">{{ account.hoTen }} + {{ account.username }}</option>
                </select>
            </div>
            <div class="form-group">
                <label for="confirmPass">Mật khẩu xác nhận:</label>
                <input type="password" id="confirmPass" class="form-control" v-model="confirmPass" />
            </div>
            <button type="submit" class="btn btn-primary">Vô hiệu hóa</button>
        </form>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        data() {
            return {
                username: '',
                confirmPass: '',
                accounts: []
            };
        },
        mounted() {
            this.fetchTaiKhoan();
            // this.fetchDanhSachNhaCungCap();
        },
        methods: {
            availableAcc(username) {
                return username === "###";
            },
            disableAccount() {
                const data = {
                    username: this.username,
                    confirmPass: this.confirmPass,
                };

                const token = localStorage.getItem('token');
                fetch('http://localhost:8081/api/tai-khoan/vhh', {
                    method: 'PATCH',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    },
                    body: JSON.stringify(data),
                })
                // axios.patch('http://localhost:8081/api/tai-khoan/vhh', data, {
                //     headers: {
                //         'Content-Type': 'application/json',
                //         Authorization: `Bearer ${token}`
                //     }
                // })
                    .then(response => {
                        if (response.ok) {
                            alert("Vô hiệu hóa thành công.");
                            window.location.href = '/tai-khoan/vhh';
                        } else {
                            alert("Xảy ra lỗi khi vô hiệu hóa.");
                            console.error("Xảy ra lỗi khi vô hiệu hóa.");
                        }
                    })
                    .catch(error => {
                        alert("Xảy ra lỗi khi gửi yêu cầu.");
                        console.log("Xảy ra lỗi khi gửi yêu cầu: ", error);
                    })
                console.log(data);
                
            },
            fetchTaiKhoan() {
                // Gọi API endpoint để lấy danh sách nhóm nguyên liệu
                const token = localStorage.getItem('token');
                axios.get('http://localhost:8081/api/tai-khoan', {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`,
                    }
                })
                    .then(response => {
                        this.accounts = response.data.filter(account => account.username !== "###");
                    })
                    .catch(error => {
                        console.error(error);
                    });
            },
        }
    };
</script>

<style>
    .container {
        max-width: 400px;
        margin: 0 auto;
        padding: 20px;
    }
</style>
