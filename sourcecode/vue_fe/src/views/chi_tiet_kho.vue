<template>
    <div class="container">
        <div class="row">
            <h1>Chi Tiết Kho ID: {{ item.nhaKhoDTO.id }}</h1>
<!--            <h1>{{item}}</h1>-->
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Thông tin kho</h5>
                        <ul class="list-group">
                            <li class="list-group-item">
                                <strong>Tên kho:</strong> {{ item.nhaKhoDTO.tenKho }}
                            </li>
                            <li class="list-group-item">
                                <strong>Địa chỉ:</strong> {{ item.nhaKhoDTO.diaChi }}
                            </li>
                            <li class="list-group-item">
                                <strong>Loại:</strong> {{ item.nhaKhoDTO.loai }}
                            </li>
                            <li class="list-group-item">
                                <strong>Ngày Tạo:</strong> {{ item.nhaKhoDTO.ngayTao }}
                            </li>
                            <!-- Thêm các thông tin khác về kho -->
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col-md-8">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope='col'>ID</th>
                        <th scope="col">Tên nguyên liệu</th>
                        <th scope="col">Số lượng</th>
                        <th scope="col">Đơn vị tính</th>
                        <th scope='col'>Tổng tiền</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="chiTiet in item.chiTietKhoDTOList">
                        <td>{{ chiTiet.id }}</td>
                        <td>{{ chiTiet.nguyenLieu.tenNguyenLieu }}</td>
                        <td>{{ chiTiet.soLuong }}</td>
                        <td>{{ chiTiet.nguyenLieu.donViTinh }}</td>
                        <td>{{ chiTiet.soLuong * chiTiet.nguyenLieu.giaNhap }} VNĐ</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        data() {
            return {
                item: null,
            };
        },
        methods: {
            fetchData() {
                const khoID = this.$route.params.id;
                const token = localStorage.getItem('token');
                axios.get(`http://localhost:8081/api/nha-kho/${khoID}`, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`,
                    }
                }).then(response => {
                    this.item = response.data;
                    const date = new Date(this.item.nhaKhoDTO.ngayTao);
                    this.item.nhaKhoDTO.ngayTao = date.toLocaleDateString();
                }).catch(error => {
                    console.error(error);
                });
            },
        },
        created() {
            this.fetchData();
        }
    };
</script>
