<template>
    <div class='form-container'>
        <h2 class='align-content-center'>Thêm Nhóm Nguyên Liệu Mới</h2>
        <form @submit.prevent="submitForm">
            <div class="form-group">
                <label for="tenNhom">Tên Nhóm:</label>
                <input type="text" id="tenNhom" v-model="tenNhom" required>
            </div>
            <button type="submit" class="btn btn-primary">Gửi</button>
        </form>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                tenNhom: '',
            };
        },
        methods: {
            submitForm() {
                const data = {
                    tenNhom: this.tenNhom,
                };

                const token = localStorage.getItem('token');
                fetch('http://localhost:8081/api/nhom-nguyen-lieu', {
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
                            window.location.href = '/nhom-nguyen-lieu';
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
