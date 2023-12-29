<template>
    <div class="container">
        <h1>Thêm đơn xuất mới</h1>
        <form @submit="submitForm">
<!--            <div class="form-group">-->
<!--                <label for="idNCC">Nhà cung cấp</label>-->
<!--                <select v-model="idNCC" class="form-control" id="idNCC">-->
<!--                    <option v-for="ncc in nccList" :key="ncc.id" :value="ncc.id">{{ ncc.tenNCC }}</option>-->
<!--                </select>-->
<!--            </div>-->
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
                    <input v-if="selectedNguyenLieu.includes(nguyenLieu.id)" type="number" v-model="selectedNguyenLieuQuantity[nguyenLieu.id]" :id="'soLuong-' + nguyenLieu.id" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label for="tongTienHang">Tổng tiền hàng</label>
                <input type="number" v-model="tongTienHang" id="tongTienHang" class="form-control">
            </div>
<!--            <div class="form-group">-->
<!--                <label for="tongTienThanhToan">Tổng tiền thanh toán</label>-->
<!--                <input v-model="tongTienThanhToan" type="number" class="form-control" id="tongTienThanhToan">-->
<!--            </div>-->
<!--            <div class="form-group">-->
<!--                <label for="phiShip">Phí ship</label>-->
<!--                <input v-model="phiShip" type="number" class="form-control" id="phiShip">-->
<!--            </div>-->
<!--            <div class="form-group">-->
<!--                <label for="giamGia">Giảm giá</label>-->
<!--                <input v-model="giamGia" type="number" class="form-control" id="giamGia">-->
<!--            </div>-->
<!--            <div class="form-group">-->
<!--                <label for="ghiChu">Ghi chú</label>-->
<!--                <textarea v-model="ghiChu" class="form-control" id="ghiChu"></textarea>-->
<!--            </div>-->
            <!--            <div class="form-group">-->
            <!--                <label for="nguyenLieu">Nguyên liệu</label>-->
            <!--                <div v-for="nguyenLieu in nguyenLieuList" :key="nguyenLieu.id">-->
            <!--                    <input type="checkbox" v-model="chiTietPhieuNhapList" :value="nguyenLieu.id" id="nguyenLieu">-->
            <!--                    <label>{{ nguyenLieu.tenNguyenLieu }} <label for='soLuong'> - Số lượng:</label> <input type='number' id='soLuong'></label>-->
            <!--                </div>-->
            <!--            </div>-->
            <button type="submit" class="btn btn-primary">Thêm đơn xuất</button>
        </form>
    </div>
</template>

<script>
    import axios from 'axios';
    import Swal from 'sweetalert2';

    export default {
        data() {
            return {
                // selectedNCC: null,
                selectedKho: null,
                // tongTienThanhToan: null,
                // phiShip: null,
                // giamGia: null,
                // ghiChu: null,
                selectedNguyenLieu: [],
                selectedNguyenLieuQuantity: {},
                // nccList: null,
                khoList: null,
                nguyenLieuList: null,
                chiTietList: [],
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
            }
        },
        methods: {
            calculateTongTienHang() {
                let tongTienHang = 0;
                for (const nguyenLieuId of this.selectedNguyenLieu) {
                    const soLuong = this.selectedNguyenLieuQuantity[nguyenLieuId];
                    const nguyenLieu = this.nguyenLieuList.find(item => item.id === nguyenLieuId);
                    if (nguyenLieu) {
                        const giaNhap = nguyenLieu.giaNhap;
                        tongTienHang += giaNhap * soLuong;
                    }
                }
                this.tongTienHang = tongTienHang;
            },
            submitForm(event) {
                event.preventDefault();

                for (const nguyenLieuId of this.selectedNguyenLieu) {
                    const soLuong = this.selectedNguyenLieuQuantity[nguyenLieuId];

                    const chiTietDonXuat = {
                        idNguyenLieu: nguyenLieuId,
                        soLuong: soLuong
                    };
                    this.chiTietList.push(chiTietDonXuat);
                }


                const formData = {
                    idKho: this.idKho,
                    // idNCC: this.idNCC,
                    // tongTienThanhToan: this.tongTienThanhToan,
                    // tienNo: 0,
                    // phiShip: this.phiShip,
                    // giamGia: this.giamGia,
                    // ghiChu: this.ghiChu,
                    chiTietPhieuNhapRequestList: this.chiTietList,
                };

                console.log(formData);
                const token = localStorage.getItem('token');
                axios.post('http://localhost:8081/api/don-xuat', formData, {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${token}`
                    }
                })
                    .then(response => {
                        console.log(response.data);
                        Swal.fire({
                            icon: "success",
                            title: "Thêm đơn xuất thành công",
                            text: "Đã thêm đơn xuất thành công.",
                        }).then(() => {
                            next({ name: "/don-xuat" });
                        });
                    })
                    .catch(error => {
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
