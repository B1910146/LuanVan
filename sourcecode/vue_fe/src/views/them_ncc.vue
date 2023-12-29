<template>
    <div class='form-container'>
        <h2 class='align-content-center'>Thêm Thông Tin Nhà Cung Cấp Mới</h2>
        <form @submit.prevent="submitForm">
            <div class="form-group">
                <label for="tenNCC">Tên Nhà Cung Cấp:</label>
                <input type="text" id="tenNCC" v-model="tenNCC" required>
            </div>
            <div class="form-group">
                <label for="soDT">Số Điện Thoại:</label>
                <input type="text" id="soDT" v-model="soDT" required>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="text" id="email" v-model="email" required>
            </div>
            <div class="form-group">
                <label for="diaChi">Địa Chỉ:</label>
                <input type="text" id="diaChi" v-model="diaChi" required>
            </div>
            <div class="form-group">
                <label for="ghiChu">Ghi Chú:</label>
                <textarea id="ghiChu" v-model="ghiChu" required></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Gửi</button>
        </form>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                tenNCC: '',
                soDT: '',
                email: '',
                diaChi: '',
                ghiChu: '',
            };
        },
        methods: {
            submitForm() {
                const data = {
                    tenNCC: this.tenNCC,
                    soDT: this.soDT,
                    email: this.email,
                    diaChi: this.diaChi,
                    ghiChu: this.ghiChu,
                };

                const token = localStorage.getItem('token');
                fetch('http://localhost:8081/api/nha-cung-cap', {
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
                            window.location.href = '/nha-cung-cap';
                        } else {
                            console.error("Xảy ra lỗi khi thêm mới.");
                        }
                    })
                    .catch(error => {
                        console.log("Xảy ra lỗi khi gửi yêu cầu: ", error);
                    })
                console.log(data);
            }
        }
    };
</script>

<style>
    .form-container {
        max-width: 400px;
        margin: 0 auto;
    }

    .form-group {
        margin-bottom: 20px;
    }

    label {
        display: block;
        margin-bottom: 5px;
    }

    input[type="text"],
    select {
        width: 100%;
        padding: 8px;
        font-size: 14px;
        border: 1px solid #ccc;
        border-radius: 4px;
    }

    button {
        padding: 10px 20px;
        font-size: 16px;
        background-color: #007bff;
        color: #fff;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    button:hover {
        background-color: #0056b3;
    }

    button:disabled {
        background-color: #ccc;
        cursor: not-allowed;
    }
</style>
