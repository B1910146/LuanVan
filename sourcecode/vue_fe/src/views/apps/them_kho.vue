<template>
    <div class='form-container'>
        <h2 class='align-content-center'>Tạo Kho</h2>
        <form @submit.prevent="submitForm">
            <div class="form-group">
                <label for="tenKho">Tên Kho:</label>
                <input type="text" id="tenKho" v-model="tenKho" required>
            </div>
            <div class="form-group">
                <label for="diaChi">Địa Chỉ:</label>
                <input type="text" id="diaChi" v-model="diaChi" required>
            </div>
            <div class="form-group">
                <label for="loai">Loại:</label>
                <select id="loai" v-model="loai" required>
                    <option value="Kho Mát">Kho Mát</option>
                    <option value="Kho Lạnh">Kho Lạnh</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Gửi</button>
        </form>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                tenKho: '',
                diaChi: '',
                loai: 'Kho Mát'
            };
        },
        methods: {
            submitForm() {
                const data = {
                    tenKho: this.tenKho,
                    diaChi: this.diaChi,
                    loai: this.loai
                };
                
                const token = localStorage.getItem('token');
                fetch('http://localhost:8081/api/nha-kho', {
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
                            window.location.href = '/nha-kho';
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
