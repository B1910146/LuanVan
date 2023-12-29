<template>
    <div>
        <h1>Cập nhật nguyên liệu</h1>
        <form @submit.prevent="submitForm" enctype='multipart/form-data'>
            <div class="form-group">
                <label for="tenNguyenLieu">Tên nguyên liệu:</label>
                <input v-if='isDataLoaded' v-model="nguyenLieu.tenNguyenLieu" type="text" class="form-control" id="tenNguyenLieu">
            </div>
            <div class="form-group">
                <label for="idNhom">Nhóm:</label>
                <select v-if='isDataLoaded' v-model="nguyenLieu.nhomNguyenLieu.id" class="form-control" id="idNhom" disabled>
                    <option v-for="nhom in nhomNguyenLieu" :value="nhom.id">{{ nhom.tenNhom }}</option>
                </select>
            </div>
            <div class="form-group">
                <label for="idNCC">Nhà cung cấp:</label>
                <select v-if='isDataLoaded' v-model="nguyenLieu.nhaCungCap.id" class="form-control" id="idNCC" disabled>
                    <option v-for="ncc in danhSachNhaCungCap" :value="ncc.id">{{ ncc.tenNCC }}</option>
                </select>
            </div>
            <div class="form-group">
                <label for="giaNhap">Giá nhập:</label>
                <input v-if='isDataLoaded' v-model="nguyenLieu.giaNhap" type="number" class="form-control" id="giaNhap">
            </div>
            <div class="form-group">
                <label for="moTa">Mô tả:</label>
                <textarea v-if='isDataLoaded' v-model="nguyenLieu.moTa" class="form-control" id="moTa"></textarea>
            </div>
            <div class="form-group">
                <label for="donViTinh">Đơn vị tính:</label>
                <input v-if='isDataLoaded' v-model="nguyenLieu.donViTinh" type="text" class="form-control" id="donViTinh">
            </div>
            <div class="form-group">
                <label for="vAT">VAT:</label>
                <input v-if='isDataLoaded' v-model="nguyenLieu.vAT" type="number" class="form-control" id="vAT">
            </div>
            <div class="form-group">
                <img v-if='isDataLoaded' :src="'/assets/images/' + nguyenLieu.hinhAnh" alt="Hình ảnh nguyên liệu" class="img-fluid">
                <br>
                <label for="file">Chọn ảnh:</label>
                <input type="file" class="form-control-file" id="file" @change="handleFileChange">
            </div>
            <button type="submit" class="btn btn-primary">Cập nhật</button>
        </form>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        data() {
            return {
                // nguyenLieu: null,
                isDataLoaded: false,
                // tenNguyenLieu: '',
                // idNhom: '',
                // idNCC: '',
                // giaNhap: '',
                // moTa: '',
                // donViTinh: '',
                // vAT: '',
                // file: null,
                nguyenLieu: {
                    tenNguyenLieu: '',
                    nhomNguyenLieu: {
                        id: '',
                    },
                    nhaCungCap: {
                        id: '',
                    },
                    giaNhap: '',
                    donViTinh: '',
                    moTa: '',
                    vAT: '',
                    file: null,
                },
                nhomNguyenLieu: [],
                danhSachNhaCungCap: []
            };
        },
        mounted() {
            this.fetchNhomNguyenLieu();
            this.fetchDanhSachNhaCungCap();
            this.fetchData();
        },
        methods: {
            fetchNhomNguyenLieu() {
                // Gọi API endpoint để lấy danh sách nhóm nguyên liệu
                const token = localStorage.getItem('token');
                axios.get('http://localhost:8081/api/nhom-nguyen-lieu', {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`,
                    }
                })
                    .then(response => {
                        this.nhomNguyenLieu = response.data;
                    })
                    .catch(error => {
                        console.error(error);
                    });
            },
            fetchDanhSachNhaCungCap() {
                // Gọi API endpoint để lấy danh sách nhà cung cấp
                const token = localStorage.getItem('token');
                axios.get('http://localhost:8081/api/nha-cung-cap', {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`,
                    }
                })
                    .then(response => {
                        this.danhSachNhaCungCap = response.data;
                    })
                    .catch(error => {
                        console.error(error);
                    });
            },
            async fetchData() {
                const nguyenLieuID = this.$route.params.id;
                const token = localStorage.getItem('token');
                try {
                    await axios.get(`http://localhost:8081/api/nguyen-lieu/${nguyenLieuID}`, {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${token}`,
                        }
                    }).then(response => {
                        this.nguyenLieu = response.data;
                        this.isDataLoaded = true;
                    }).catch(error => {
                        console.error(error);
                    });
                } catch (error) {
                    console.error(error);
                }
            },
            handleFileChange(event) {
                this.nguyenLieu.file = event.target.files[0];
            },
            submitForm() {
                const nguyenLieuID = this.$route.params.id;
                const formData = new FormData();
                formData.append('id', nguyenLieuID);
                formData.append('tenNguyenLieu', this.nguyenLieu.tenNguyenLieu);
                // formData.append('idNhom', this.nguyenLieu.nhomNguyenLieu.id);
                // formData.append('idNCC', this.nguyenLieu.nhaCungCap.id);
                formData.append('giaNhap', this.nguyenLieu.giaNhap);
                formData.append('donViTinh', this.nguyenLieu.donViTinh);
                formData.append('moTa', this.nguyenLieu.moTa);
                formData.append('vAT', this.nguyenLieu.vAT);
                if (this.nguyenLieu.file != null) {
                    formData.append('file', this.nguyenLieu.file);
                }
                
                const token = localStorage.getItem('token');
                axios.patch(`http://localhost:8081/api/nguyen-lieu/${nguyenLieuID}`, formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'Authorization': `Bearer ${token}`,
                    }
                })
                    .then(response => {
                        console.log(response.data);
                        this.nguyenLieu.tenNguyenLieu = '';
                        // this.nguyenLieu.idNhom = '';
                        // this.nguyenLieu.idNCC = '';
                        this.nguyenLieu.giaNhap = '';
                        this.nguyenLieu.donViTinh = '';
                        this.nguyenLieu.moTa = '';
                        this.nguyenLieu.vAT = '';
                        this.nguyenLieu.file = null;
                        alert('Thay đổi thông tin nguyên liệu thành công!');
                        window.location.href= `/chitietnguyenlieu/${nguyenLieuID}`;
                    })
                    .catch(error => {
                        // Xử lý lỗi
                        // console.log(formData);
                        console.error(error);
                        // Hiển thị thông báo lỗi
                        alert('Đã xảy ra lỗi khi thay đổi thông tin nguyên liệu.');
                        window.location.href= `/nguyen-lieu/${nguyenLieuID}/sua`;
                    });
            },
        }
    };
</script>
