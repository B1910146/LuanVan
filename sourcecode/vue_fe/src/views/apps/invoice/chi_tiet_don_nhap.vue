<template>
    <div class="container mt-5">
        <div class="row">
            <div class="col-md-6 offset-md-3">
                <h2 class="text-center">Đơn Nhập</h2>
                <!--                <div>{{phieuNhap}}</div>-->
                <div class="card">
                    <div class="card-body">
                        <div class="form-group">
                            <label for="id">ID:</label>
                            <input type="text" class="form-control" v-model="donNhap.donNhapDTO.id" disabled>
                        </div>
                        <div class="form-group">
                            <label for="ngay_lap">Ngày lập:</label>
                            <input type="text" class="form-control" v-model="donNhap.donNhapDTO.ngayLap" disabled>
                        </div>
                        <div class="form-group">
                            <label for="tong_tien_hang">Tổng tiền hàng:</label>
                            <input type="text" class="form-control" v-model="donNhap.donNhapDTO.tongTienHang" disabled>
                        </div>
<!--                        <div class="form-group">-->
<!--                            <label for="v_at">VAT:</label>-->
<!--                            <input type="text" class="form-control" v-model="donNhap.vAT" disabled>-->
<!--                        </div>-->
                        <div class="form-group">
                            <label for="phi_ship">Phí ship:</label>
                            <input type="text" class="form-control" v-model="donNhap.donNhapDTO.phiShip" disabled>
                        </div>
                        <div class="form-group">
                            <label for="giam_gia">Giảm giá:</label>
                            <input type="text" class="form-control" v-model="donNhap.donNhapDTO.giamGia" disabled>
                        </div>
                        <div class="form-group">
                            <label for="tong_tien_thanh_toan">Tổng tiền thanh toán:</label>
                            <input type="text" class="form-control" v-model="donNhap.donNhapDTO.tongTienThanhToan" disabled>
                        </div>
                        <div class="form-group">
                            <label for="tien_no">Tiền nợ:</label>
                            <input type="text" class="form-control" v-model="donNhap.donNhapDTO.tienNo" disabled>
                        </div>
                        <div class="form-group">
                            <label for="ghi_chu">Ghi chú:</label>
                            <textarea class="form-control" v-model="donNhap.donNhapDTO.ghiChu" disabled></textarea>
                        </div>
                        <div class="form-group">
                            <label for="nha_cung_cap">Nhà cung cấp:</label>
                            <input type="text" class="form-control" v-model="donNhap.donNhapDTO.nhaCungCap.tenNCC" disabled>
                        </div>
                        <div class="form-group">
                            <label for="nha_kho">Nhà kho:</label>
                            <input type="text" class="form-control" v-model="donNhap.donNhapDTO.nhaKho.tenKho" disabled>
                        </div>
                        <div class="form-group">
                            <label for="tai_khoan">Người Lập:</label>
                            <input type="text" class="form-control" v-model="donNhap.donNhapDTO.taiKhoan.hoTen" disabled>
                        </div>
                        <div class="form-group">
                            <label for="nguoiXacNhan">Người Xác Nhận</label>
                            <input type="text" class="form-control" v-model="donNhap.nguoiXacNhanDon" disabled>
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

                    <tr v-for="chiTiet in donNhap.chiTietDonNhapList">

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
                donNhap: null,
            };
        },
        methods: {
            fetchData() {
                const donNhapID = this.$route.params.id;
                const token = localStorage.getItem('token');
                axios
                    .get(`http://localhost:8081/api/don-nhap/${donNhapID}`, {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${token}`,
                        },
                    })
                    .then(response => {
                        this.donNhap = response.data;
                        // Xử lý ngày lập ở đây
                        const date = new Date(this.donNhap.donNhapDTO.ngayLap);
                        this.donNhap.donNhapDTO.ngayLap = date.toLocaleDateString();
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
