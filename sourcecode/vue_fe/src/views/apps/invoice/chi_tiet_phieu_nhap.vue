<template>
    <div class="container mt-5">
        <div class="row">
            <div class="col-md-6 offset-md-3">
                <h2 class="text-center">Phiếu Nhập</h2>
<!--                <div>{{phieuNhap}}</div>-->
                <div class="card">
                    <div class="card-body">
                        <div class="form-group">
                            <label for="id">ID:</label>
                            <input type="text" class="form-control" v-model="phieuNhap.phieuNhapDTO.id" disabled>
                        </div>
                        <div class="form-group">
                            <label for="ngay_lap">Ngày lập:</label>
                            <input type="text" class="form-control" v-model="phieuNhap.phieuNhapDTO.ngayLap" disabled>
                        </div>
                        <div class="form-group">
                            <label for="tong_tien_hang">Tổng tiền hàng:</label>
                            <input type="text" class="form-control" v-model="phieuNhap.phieuNhapDTO.tongTienHang" disabled>
                        </div>
                        <div class="form-group">
                            <label for="v_at">VAT:</label>
                            <input type="text" class="form-control" v-model="phieuNhap.phieuNhapDTO.vAT" disabled>
                        </div>
                        <div class="form-group">
                            <label for="phi_ship">Phí ship:</label>
                            <input type="text" class="form-control" v-model="phieuNhap.phieuNhapDTO.phiShip" disabled>
                        </div>
                        <div class="form-group">
                            <label for="giam_gia">Giảm giá:</label>
                            <input type="text" class="form-control" v-model="phieuNhap.phieuNhapDTO.giamGia" disabled>
                        </div>
                        <div class="form-group">
                            <label for="tong_tien_thanh_toan">Tổng tiền thanh toán:</label>
                            <input type="text" class="form-control" v-model="phieuNhap.phieuNhapDTO.tongTienThanhToan" disabled>
                        </div>
                        <div class="form-group">
                            <label for="tien_no">Tiền nợ:</label>
                            <input type="text" class="form-control" v-model="phieuNhap.phieuNhapDTO.tienNo" disabled>
                        </div>
                        <div class="form-group">
                            <label for="ghi_chu">Ghi chú:</label>
                            <textarea class="form-control" v-model="phieuNhap.phieuNhapDTO.ghiChu" disabled></textarea>
                        </div>
                        <div class="form-group">
                            <label for="nha_cung_cap">Nhà cung cấp:</label>
                            <input type="text" class="form-control" v-model="phieuNhap.phieuNhapDTO.nhaCungCap.tenNCC" disabled>
                        </div>
                        <div class="form-group">
                            <label for="nha_kho">Nhà kho:</label>
                            <input type="text" class="form-control" v-model="phieuNhap.phieuNhapDTO.nhaKho.tenKho" disabled>
                        </div>
                        <div class="form-group">
                            <label for="tai_khoan">Tài khoản:</label>
                            <input type="text" class="form-control" v-model="phieuNhap.phieuNhapDTO.taiKhoan.hoTen" disabled>
                        </div>
                    </div>
                </div>
<!--                <div>{{phieuNhap}}</div>-->
                <h4 class="my-4">Chi tiết phiếu</h4>
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Số lượng</th>
                        <th scope="col">Thành tiền</th>
                        <th scope="col">Nguyên liệu</th>
                    </tr>
                    </thead>
                    
                    <tbody>
                    
                    <tr v-for="chiTiet in phieuNhap.chiTietPhieuNhapList">
<!--                        <td>{{ chiTiet }}</td>-->
                        <td>{{ chiTiet.id }}</td>
                        <td>{{ chiTiet.soLuong }}</td>
                        <td>{{ chiTiet.thanhTien }}</td>
                        <td>{{ chiTiet.nguyenLieu.tenNguyenLieu }}</td>
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
                phieuNhap: null,
            };
        },
        methods: {
            fetchData() {
                const phieuNhapID = this.$route.params.id;
                const token = localStorage.getItem('token');
                axios
                    .get(`http://localhost:8081/api/phieu-nhap/${phieuNhapID}`, {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${token}`,
                        },
                    })
                    .then(response => {
                        this.phieuNhap = response.data;
                        // Xử lý ngày lập ở đây
                        const date = new Date(this.phieuNhap.phieuNhapDTO.ngayLap);
                        this.phieuNhap.phieuNhapDTO.ngayLap = date.toLocaleDateString();
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
