<template>
    <div class="container">
        <h1>Thêm Nguyên Liệu</h1>
        <form @submit.prevent="submitForm" enctype='multipart/form-data'>
            <div class="form-group">
                <label for="tenNguyenLieu">Tên Nguyên Liệu:</label>
                <input type="text" class="form-control" id="tenNguyenLieu" v-model="nguyenLieu.tenNguyenLieu" required>
            </div>
            <div class="form-group">
                <label for="idNhom">Nhóm Nguyên Liệu:</label>
                <select class="form-control" id="idNhom" v-model="nguyenLieu.idNhom" required>
                    <option v-for="nhom in nhomNguyenLieu" :key="nhom.id" :value="nhom.id">{{ nhom.tenNhom }}</option>
                </select>
            </div>
            <div class="form-group">
                <label for="idNCC">Nhà Cung Cấp:</label>
                <select class="form-control" id="idNCC" v-model="nguyenLieu.idNCC" required>
                    <option v-for="nhaCungCap in danhSachNhaCungCap" :key="nhaCungCap.id" :value="nhaCungCap.id">{{ nhaCungCap.tenNCC }}</option>
                </select>
            </div>
            <div class="form-group">
                <label for="giaNhap">Giá Nhập:</label>
                <input type="number" class="form-control" id="giaNhap" v-model="nguyenLieu.giaNhap" required>
            </div>
            <div class="form-group">
                <label for="donViTinh">Đơn Vị Tính:</label>
                <input type="text" class="form-control" id="donViTinh" v-model="nguyenLieu.donViTinh" required>
            </div>
            <div class="form-group">
                <label for="moTa">Mô Tả:</label>
                <textarea class="form-control" id="moTa" v-model="nguyenLieu.moTa" required></textarea>
            </div>
            <div class="form-group">
                <label for="vAT">VAT:</label>
                <input type="number" class="form-control" id="vAT" v-model="nguyenLieu.vAT" required>
            </div>
            <div class="form-group">
                <label for="file">Hình Ảnh:</label>
                <input type="file" class="form-control-file" id="file" @change="handleFileChange" required>
            </div>
            <button type="submit" class="btn btn-primary">Thêm</button>
        </form>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        data() {
            return {
                nguyenLieu: {
                    ten: '',
                    idNhom: '',
                    idNCC: '',
                    giaNhap: '',
                    donViTinh: '',
                    moTa: '',
                    vAT: '',
                    file: null,
                },
                nhomNguyenLieu: [],
                danhSachNhaCungCap: [],
            };
        },
        mounted() {
            this.fetchNhomNguyenLieu();
            this.fetchDanhSachNhaCungCap();
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
    handleFileChange(event) {
      this.nguyenLieu.file = event.target.files[0];
    },
    submitForm() {
      const formData = new FormData();
      formData.append('tenNguyenLieu', this.nguyenLieu.tenNguyenLieu);
      formData.append('idNhom', this.nguyenLieu.idNhom);
      formData.append('idNCC', this.nguyenLieu.idNCC);
      formData.append('giaNhap', this.nguyenLieu.giaNhap);
      formData.append('donViTinh', this.nguyenLieu.donViTinh);
      formData.append('moTa', this.nguyenLieu.moTa);
      formData.append('vAT', this.nguyenLieu.vAT);
      formData.append('file', this.nguyenLieu.file); 
      console.log(this.nguyenLieu.file);
      // Gọi API endpoint để thêm nguyên liệu mới
      const token = localStorage.getItem('token');
      axios.post('http://localhost:8081/api/nguyen-lieu', formData, {
          headers: {
              'Content-Type': 'multipart/form-data',
              'Authorization': `Bearer ${token}`,
          }
        })
        .then(response => {
          // Xử lý phản hồi thành công
          console.log(response.data);
          // Reset form sau khi thêm thành công
          this.nguyenLieu.ten = '';
          this.nguyenLieu.idNhom = '';
          this.nguyenLieu.idNCC = '';
          this.nguyenLieu.giaNhap = '';
          this.nguyenLieu.donViTinh = '';
          this.nguyenLieu.moTa = '';
          this.nguyenLieu.vAT = '';
          this.nguyenLieu.file = null;
          // Hiển thị thông báo thành công
          alert('Thêm nguyên liệu mới thành công!');
            window.location.href= '/nguyen-lieu';
        })
        .catch(error => {
          // Xử lý lỗi
            console.log(formData);
          console.error(error);
          // Hiển thị thông báo lỗi
          alert('Đã xảy ra lỗi khi thêm nguyên liệu mới!');
          window.location.href= '/nguyen-lieu';
        });
    },
  },
};
</script>
