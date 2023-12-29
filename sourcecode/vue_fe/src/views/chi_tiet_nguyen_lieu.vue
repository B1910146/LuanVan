<template>
    <div class="container">
        <div class="row">
            <div class='col-12'>
                <h1 class='text-center'>Chi Tiết Nguyên Liệu ID: {{ nguyenLieu.id }}</h1>
            </div>
            <div class="col-md-4">
                <img :src="'/assets/images/' + nguyenLieu.hinhAnh" alt="Hình ảnh nguyên liệu" class="img-fluid">
            </div>
            <div class="col-md-5">
                <h2>{{ nguyenLieu.tenNguyenLieu }}</h2>
<!--                <p><strong>ID:</strong> {{ nguyenLieu.id }}</p>-->
                <p><strong>Giá nhập:</strong> {{ nguyenLieu.giaNhap }}</p>
                <p><strong>Đơn vị tính:</strong> {{ nguyenLieu.donViTinh }}</p>
                <p><strong>Mô tả:</strong> {{ nguyenLieu.moTa }}</p>
                <p><strong>VAT:</strong> {{ nguyenLieu.vAT }}</p>
                <p><strong>Nhóm nguyên liệu:</strong> {{ nguyenLieu.nhomNguyenLieu.tenNhom }}</p>
                <p><strong>Nhà cung cấp:</strong> {{ nguyenLieu.nhaCungCap.tenNCC }}</p>
                <button v-if='availableAction()' @click='suaNguyenLieu(nguyenLieu.id)' class='btn btn-warning m-3'>Sửa</button>
<!--                <button class='btn btn-danger'>Xóa</button>-->
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        data() {
            return {
                nguyenLieu: null,
            };
        },
        // mounted() {
        //     this.fetchData();
        // },
        methods: {
            fetchData() {
                const nguyenLieuID = this.$route.params.id;
                const token = localStorage.getItem('token');
                axios.get(`http://localhost:8081/api/nguyen-lieu/${nguyenLieuID}`, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`,
                    }
                }).then(response => {
                    this.nguyenLieu = response.data;
                }).catch(error => {
                    console.error(error);
                });
            },
            suaNguyenLieu(id) {
                window.location.href = `/nguyen-lieu/${id}/sua`;
            },
            availableAction() {
                const per = localStorage.getItem('permission');
                return (per === 'quankho' || per === 'chu');
            },
        },
        created() {
            this.fetchData();
        }
    };
</script>
