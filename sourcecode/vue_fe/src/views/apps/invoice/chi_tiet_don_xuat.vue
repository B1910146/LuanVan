<template>
    <div class="container mt-5">
        <div class="row">
            <div class="col-md-6 offset-md-3">
                <h2 class="text-center">Đơn Xuất</h2>
                <!--                <div>{{phieuNhap}}</div>-->
                <div class="card">
                    <div class="card-body">
                        <div class="form-group">
                            <label for="id">ID:</label>
                            <input type="text" class="form-control" v-model="donXuat.donXuatDTO.id" disabled>
                        </div>
                        <div class="form-group">
                            <label for="ngay_lap">Ngày lập:</label>
                            <input type="text" class="form-control" v-model="donXuat.donXuatDTO.ngayLap" disabled>
                        </div>
                        <div class="form-group">
                            <label for="tong_tien_hang">Tổng tiền hàng:</label>
                            <input type="text" class="form-control" v-model="donXuat.donXuatDTO.tongTienHang" disabled>
                        </div>
                        <div class="form-group">
                            <label for="nha_kho">Nhà kho:</label>
                            <input type="text" class="form-control" v-model="donXuat.donXuatDTO.nhaKho.tenKho" disabled>
                        </div>
                        <div class="form-group">
                            <label for="tai_khoan">Người Lập:</label>
                            <input type="text" class="form-control" v-model="donXuat.donXuatDTO.taiKhoan.hoTen" disabled>
                        </div>
                        <div class="form-group">
                            <label for="nguoiXacNhan">Người Xác Nhận</label>
                            <input type="text" class="form-control" v-model="donXuat.nguoiXacNhanDon" disabled>
                        </div>
                    </div>
                </div>
                <!--                <div>{{phieuNhap}}</div>-->
                <h4 class="my-4">Chi tiết đơn</h4>
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Nguyên liệu</th>
                        <th scope="col">Số lượng</th>
                        <th scope="col">Thành tiền</th>
                    </tr>
                    </thead>

                    <tbody>

                    <tr v-for="chiTiet in donXuat.chiTietDonXuatList">

                        <td>{{ chiTiet.id }}</td>
                        <td>{{ chiTiet.nguyenLieu.tenNguyenLieu }}</td>
                        <td>{{ chiTiet.soLuong }}</td>
                        <td>{{ chiTiet.thanhTien }}</td>
                    </tr>
                    </tbody>
                </table>
                <button class="btn btn-secondary" @click="goBack">Trở lại</button>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        data() {
            return {
                donXuat: null,
            };
        },
        methods: {
            fetchData() {
                const donXuatID = this.$route.params.id;
                const token = localStorage.getItem('token');
                axios
                    .get(`http://localhost:8081/api/don-xuat/${donXuatID}`, {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${token}`,
                        },
                    })
                    .then(response => {
                        this.donXuat = response.data;
                        // Xử lý ngày lập ở đây
                        const date = new Date(this.donXuat.donXuatDTO.ngayLap);
                        this.donXuat.donXuatDTO.ngayLap = date.toLocaleDateString();
                    })
                    .catch(error => {
                        console.error(error);
                    });
            },
            goBack() {
                // Xử lý điều hướng quay lại trang trước đó
            },
        },
        created() {
            this.fetchData();
        },
    };
</script>
