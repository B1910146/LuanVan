<template>
    <div class="container">
        <h1>Thêm đơn nhập mới</h1>
        <form @submit.prevent="submitForm">
            <div class="form-group">
                <label for="phieuNhap">Phiếu nhập</label>
                <select class="form-control" id="phieuNhap" v-model="form.phieuNhap">
                    <option v-for="phieuNhap in danhSachPhieuNhap" :value="phieuNhap.id" :key="phieuNhap.id">{{ phieuNhap.name }}</option>
                </select>
            </div>
            <div class="form-group">
                <label for="tongTienThanhToan">Tổng tiền thanh toán</label>
                <input type="number" class="form-control" id="tongTienThanhToan" v-model="form.tongTienThanhToan">
            </div>
            <div class="form-group">
                <label for="tienNo">Tiền nợ</label>
                <input type="number" class="form-control" id="tienNo" v-model="form.tienNo">
            </div>
            <button type="submit" class="btn btn-primary">Thêm đơn nhập</button>
        </form>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                form: {
                    phieuNhap: null,
                    tongTienThanhToan: null,
                    tienNo: null
                },
                danhSachPhieuNhap: [
                    { id: 1, name: "Phiếu nhập A" },
                    { id: 2, name: "Phiếu nhập B" },
                    { id: 3, name: "Phiếu nhập C" }
                ]
            };
        },
        methods: {
            submitForm() {
                // Thực hiện xử lý lưu đơn nhập mới
                console.log(this.form);
                // Reset form sau khi lưu thành công
                this.form.phieuNhap = null;
                this.form.tongTienThanhToan = null;
                this.form.tienNo = null;
            }
        }
    };
</script>

<style>
    .container {
        margin-top: 20px;
    }
</style>
