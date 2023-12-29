<template>
    <div class="container">
        <h1>Cấp tài khoản</h1>
        <form @submit.prevent="submitForm">
            <div class="form-group">
                <label for="vaiTro">Vai trò</label>
                <select v-model="selectedRole" id="vaiTro" class="form-control">
                    <option value="kiemHang">Kiểm hàng</option>
                    <option value="quanKho">Quản kho</option>
                    <option value="chuNhaHang">Chủ nhà hàng</option>
                </select>
            </div>
            <div class="form-group">
                <label for="hoTen">Họ tên</label>
                <input type="text" v-model="hoTen" id="hoTen" class="form-control">
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="text" v-model="email" id="email" class="form-control">
            </div>
            <button type="submit" class="btn btn-primary">Cấp mới</button>
        </form>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                selectedRole: 'kiemHang',
                hoTen: '',
                email: '',
            };
        },
        methods: {
            submitForm() {
                const data = {
                    selectedRole: this.selectedRole,
                    hoTen: this.hoTen,
                    email: this.email,
                };
                const token = localStorage.getItem('token');
                fetch('http://localhost:8081/api/tai-khoan', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    },
                    body: JSON.stringify(data),
                })
                    .then(response => {
                        if (response.ok) {
                            alert("Đã thêm mới thành công.");
                            window.location.href = '/tai-khoan/cap-moi';
                        } else {
                            alert("Xảy ra lỗi khi thêm mới.");
                            console.error("Xảy ra lỗi khi thêm mới.");
                        }
                    })
                    .catch(error => {
                        alert("Xảy ra lỗi khi gửi yêu cầu.");
                        console.log("Xảy ra lỗi khi gửi yêu cầu: ", error);
                    })
                console.log(data);
                console.log('Vai trò:', this.selectedRole);
                console.log('Họ tên:', this.hoTen);
            }
        }
    };
</script>
