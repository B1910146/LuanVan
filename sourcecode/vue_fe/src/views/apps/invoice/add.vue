<template>
    <div class="container">
        <h1>Thêm phiếu nhập mới</h1>
        <form @submit="submitForm" v-if='addMethod()'>
            <div class="form-group">
                <label for="idNCC">Nhà cung cấp</label>
                <select v-model="idNCC" class="form-control" id="idNCC">
                    <option v-for="ncc in nccList" :key="ncc.id" :value="ncc.id">{{ ncc.tenNCC }}</option>
                </select>
            </div>
            <div class="form-group">
                <label for="idKho">Kho</label>
                <select v-model="idKho" class="form-control" id="idKho">
                    <option v-for="kho in khoList" :key="kho.id" :value="kho.id">{{ kho.tenKho }}</option>
                </select>
            </div>
            <div class="form-group">
                <label for="nguyenLieu">Nguyên liệu</label>
                <div v-for="nguyenLieu in nguyenLieuList" :key="nguyenLieu.id">
                    <input type="checkbox" v-model="selectedNguyenLieu" :value="nguyenLieu.id" id="nguyenLieu">
                    <label>{{ nguyenLieu.tenNguyenLieu }}</label>
                    <input v-if="selectedNguyenLieu.includes(nguyenLieu.id)" type="number" min='1' v-model="selectedNguyenLieuQuantity[nguyenLieu.id]" :id="'soLuong-' + nguyenLieu.id" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label for="tongTienHang">Tổng tiền hàng</label>
                <input type="number" v-model="tongTienHang" id="tongTienHang" class="form-control">
            </div>
            <div class="form-group">
                <label for="tongTienThanhToan">Tổng tiền thanh toán</label>
                <input v-model="tongTienThanhToan" type="number" class="form-control" id="tongTienThanhToan">
            </div>
            <div class="form-group">
                <label for="phiShip">Phí ship</label>
                <input v-model="phiShip" type="number" class="form-control" id="phiShip">
            </div>
            <div class="form-group">
                <label for="giamGia">Giảm giá</label>
                <input v-model="giamGia" type="number" class="form-control" id="giamGia">
            </div>
            <div class="form-group">
                <label for="ghiChu">Ghi chú</label>
                <textarea v-model="ghiChu" class="form-control" id="ghiChu"></textarea>
            </div>
<!--            <div class="form-group">-->
<!--                <label for="nguyenLieu">Nguyên liệu</label>-->
<!--                <div v-for="nguyenLieu in nguyenLieuList" :key="nguyenLieu.id">-->
<!--                    <input type="checkbox" v-model="chiTietPhieuNhapList" :value="nguyenLieu.id" id="nguyenLieu">-->
<!--                    <label>{{ nguyenLieu.tenNguyenLieu }} <label for='soLuong'> - Số lượng:</label> <input type='number' id='soLuong'></label>-->
<!--                </div>-->
<!--            </div>-->
            <button type="submit" class="btn btn-primary">Thêm phiếu nhập</button>
        </form>
    <!--        ==============================================================================================================================================-->
        <!--        ==============================================================================================================================================-->
        <!--        ==============================================================================================================================================-->
        <!--        ==============================================================================================================================================-->
        <!--        ==============================================================================================================================================-->
        <form @submit="submitForm2" v-else enctype='multipart/form-data'>
            <div class="form-group">
                <label for="idNCC">Nhà cung cấp</label>
                <select v-model="idNCC" class="form-control" id="idNCC">
                    <option v-for="ncc in nccList" :key="ncc.id" :value="ncc.id">{{ ncc.tenNCC }}</option>
                </select>
            </div>
            <div class="form-group">
                <label for="idKho">Kho</label>
                <select v-model="idKho" class="form-control" id="idKho">
                    <option v-for="kho in khoList" :key="kho.id" :value="kho.id">{{ kho.tenKho }}</option>
                </select>
            </div>
            <div class="form-group">
                <label for="phiShip">Phí ship</label>
                <input v-model="phiShip" type="number" class="form-control" id="phiShip">
            </div>
            <div class="form-group">
                <label for="giamGia">Giảm giá</label>
                <input v-model="giamGia" type="number" class="form-control" id="giamGia">
            </div>
            <div class="form-group">
                <label for="ghiChu">Ghi chú</label>
                <textarea v-model="ghiChu" class="form-control" id="ghiChu"></textarea>
            </div>
            <div class="form-group">
                <label for="file">Chọn file Excel:</label>
                <input type="file" class="form-control-file" id="file" @change="handleFileUpload" required>
<!--                <input type="file" class="form-control-file" id="file" @change="selectedFile = $event.target.files[0]" />-->
            </div>
            <div class="form-group">
                <label for="tongTienHang">Tổng tiền hàng</label>
                <input v-if='caculateCheck()' type="number" v-model="tongTienHang" id="tongTienHang" class="form-control" disabled>
<!--                <span v-if='caculateCheck()'>{{ tongTienHang }}</span>-->
            </div>
            <div class="form-group">
                <label for="tongTienThanhToan">Tổng tiền thanh toán</label>
                <input v-model="tongTienThanhToan" type="number" class="form-control" id="tongTienThanhToan">
            </div>
            <button type="submit" class="btn btn-primary">Thêm phiếu nhập</button>
        </form>
    </div>
</template>

<script>
    import axios from 'axios';
    import Swal from 'sweetalert2';
    import { read, utils } from 'xlsx';
    const XLSX = { read, utils };

    export default {
        data() {
            return {
                selectedNCC: null,
                selectedKho: null,
                tongTienThanhToan: null,
                phiShip: null,
                giamGia: null,
                ghiChu: null,
                selectedNguyenLieu: [],
                selectedNguyenLieuQuantity: {},
                nccList: null,
                khoList: null,
                nguyenLieuList: null,
                chiTietList: [],
                file: null,
                selectedFile: null,
                isCaculated: false,
                tienNo: null,
            };
        },
        watch: {
            selectedNguyenLieu: {
                handler() {
                    this.calculateTongTienHang();
                },
                deep: true
            },
            selectedNguyenLieuQuantity: {
                handler() {
                    this.calculateTongTienHang();
                },
                deep: true
            },
            // selectedFile: {
            //     handler() {
            //         this.handleFileUpload();
            //     }
            // }
        },
        methods: {
            caculateCheck() {
                return this.isCaculated === true;
            },
            calculateTongTienHang() {
                let tongTienHang = 0;
                for (const nguyenLieuId of this.selectedNguyenLieu) {
                    const soLuong = this.selectedNguyenLieuQuantity[nguyenLieuId];
                    const nguyenLieu = this.nguyenLieuList.find(item => item.id === nguyenLieuId);
                    if (nguyenLieu) {
                        const giaNhap = nguyenLieu.giaNhap;
                        tongTienHang += (giaNhap * soLuong) + (giaNhap * soLuong * (nguyenLieu.vAT/100));
                    }
                }
                this.tongTienHang = tongTienHang;
            },
            handleFileUpload(event) {
                const file = event.target.files[0];
                const reader = new FileReader();
                let tongTienHang = 0;

                reader.onload = (e) => {
                    const data = new Uint8Array(e.target.result);
                    const workbook = XLSX.read(data, { type: 'array' });
                    const worksheet = workbook.Sheets[workbook.SheetNames[0]];
                    const jsonData = XLSX.utils.sheet_to_json(worksheet);

                    // const selectedNguyenLieu = []; 
                    // const selectedNguyenLieuQuantity = [];

                    jsonData.forEach((row) => {
                        const IDNguyenLieu = row.IDNguyenLieu;
                        const SoLuong = row.SoLuong;
                        console.log(IDNguyenLieu);
                        console.log(SoLuong);
                        // this.selectedNguyenLieu.push(IDNguyenLieu);
                        // this.selectedNguyenLieuQuantity.push(SoLuong);
                        const nguyenLieu = this.nguyenLieuList.find(item => item.id === IDNguyenLieu);
                        if (nguyenLieu) {
                            const giaNhap = nguyenLieu.giaNhap;
                            tongTienHang += (giaNhap * SoLuong) + (giaNhap * SoLuong * (nguyenLieu.vAT/100));
                        }
                    });
                    this.tongTienHang = tongTienHang - tongTienHang+this.giamGia/100 + this.phiShip;
                    console.log(this.tongTienHang);
                    this.isCaculated = true;
                    this.selectedFile = file;
                };

                reader.readAsArrayBuffer(file);
            },
            submitForm(event) {
                event.preventDefault();
                
                for (const nguyenLieuId of this.selectedNguyenLieu) {
                    const soLuong = this.selectedNguyenLieuQuantity[nguyenLieuId];

                    const chiTietPhieuNhap = {
                        idNguyenLieu: nguyenLieuId,
                        soLuong: soLuong
                    };
                    this.chiTietList.push(chiTietPhieuNhap);
                }


                const formData = {
                    idKho: this.idKho,
                    idNCC: this.idNCC,
                    tongTienThanhToan: this.tongTienThanhToan,
                    // tienNo: 0,
                    phiShip: this.phiShip,
                    giamGia: this.giamGia,
                    ghiChu: this.ghiChu,
                    chiTietPhieuNhapList: this.chiTietList,
                };
                
                console.log(formData);
                const token = localStorage.getItem('token');
                axios.post('http://localhost:8081/api/phieu-nhap', formData, {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${token}`
                    }
                })
                  .then(response => {
                    console.log(response.data);
                      Swal.fire({
                          icon: "success",
                          title: "Thêm phiếu nhập thành công",
                          text: "Đã thêm phiếu nhập thành công.",
                      }).then(() => {
                          next({ name: "/phieu-nhap" });
                      });
                  }).catch(error => {
                    console.error(error);
                  });
            },
            submitForm2(event) {
                event.preventDefault();
                const tienNo = this.tongTienHang - this.tongTienThanhToan;
                const formData = {
                    idKho: this.idKho,
                    idNCC: this.idNCC,
                    tongTienThanhToan: this.tongTienThanhToan,
                    // tienNo: 0,
                    phiShip: this.phiShip,
                    giamGia: this.giamGia,
                    ghiChu: this.ghiChu,
                    file: this.selectedFile,
                    tienNo: tienNo,
                    // chiTietPhieuNhapList: this.chiTietList,
                };

                console.log(formData.idKho);
                console.log(formData.idNCC);
                console.log(formData.tongTienThanhToan);
                console.log(formData.phiShip);
                console.log(formData.giamGia);
                console.log(formData.ghiChu);
                console.log(formData.file);
                console.log(formData.tienNo);
                const token = localStorage.getItem('token');
                axios.post('http://localhost:8081/api/phieu-nhap/excel', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        Authorization: `Bearer ${token}`
                    }
                })
                    .then(response => {
                        console.log(response.data);
                        Swal.fire({
                            icon: "success",
                            title: "Thêm phiếu nhập thành công",
                            text: "Đã thêm phiếu nhập thành công.",
                        }).then(() => {
                            next({ name: "/phieu-nhap" });
                        });
                    })
                    .catch(error => {
                        Swal.fire({
                            icon: "warning",
                            title: "File Lỗi.",
                            text: "File Excel bị lỗi dữ liệu.",
                        }).then(() => {
                            next({ name: "/phieu-nhap/them/2" });
                        });
                        console.error(error);
                    });
            },
            
            getKhoList() {
                const token = localStorage.getItem('token');
                axios.get('http://localhost:8081/api/nha-kho', {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`,
                    }
                }).then(response => {
                    this.khoList = response.data;
                }).catch(error => {
                    console.error(error);
                });
            },

            getNCCList() {
                const token = localStorage.getItem('token');
                axios.get('http://localhost:8081/api/nha-cung-cap', {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`,
                    }
                }).then(response => {
                    this.nccList = response.data;
                }).catch(error => {
                    console.error(error);
                });
            },

            getNLList() {
                const token = localStorage.getItem('token');
                axios.get('http://localhost:8081/api/nguyen-lieu', {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`,
                    }
                }).then(response => {
                    this.nguyenLieuList = response.data;
                }).catch(error => {
                    console.error(error);
                });
            },
            addMethod() {
                const methodID = this.$route.params.id;
                return methodID === "1";
            }
        },
        created() {
            this.getKhoList();
            this.getNCCList();
            this.getNLList();
        }
    };
</script>

<style>
    .container {
        max-width: 600px;
        margin: 0 auto;
        padding: 20px;
    }
</style>
