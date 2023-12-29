<template>
    <div class="container">
        <h2>Danh Sách Đơn Xuất</h2>
        <button v-if='availableAction()' class="add-button btn btn-success text-black font-weight-bold m-2" @click="addItem1">Thêm mới</button>
        <button v-if='availableAction()' class="add-button btn btn-success text-black font-weight-bold m-2" @click="addItem2">Thêm Bằng File Excel</button>
        <input class="search-input" type="text" v-model="searchTerm" placeholder="Tìm kiếm..." />
        <table class="data-table table table-dark table-striped">
            <thead>
            <tr>
                <th @click="sortColumns('id')">ID</th>
                <th @click="sortColumns('ngayLap')">Ngày Lập</th>
                <th @click="sortColumns('tienHang')">Tiền Hàng</th>
<!--                <th @click="sortColumns('tienThanhToan')">Tiền Thanh Toán</th>-->
<!--                <th @click="sortColumns('tienNo')">Tiền Nợ</th>-->
<!--                <th @click="sortColumns('nCC')">Nhà Cung Cấp</th>-->
                <th @click="sortColumns('nhaKho')">Nhà Kho</th>
                <th @click="sortColumns('nguoiLap')">Người Lập</th>
                <th @click="sortColumns('nguoiXacNhan')">Người Xác Nhận Hàng</th>
                <th>Quản Lý</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item, index) in displayedData" :key="item.id">
                <td id='donXuatID' @click='chiTietDonXuat(item.id)'>{{ item.donXuatDTO.id }}</td>
                <td>{{ item.donXuatDTO.ngayLap }}</td>
                <td>{{ item.donXuatDTO.tongTienHang }} VNĐ</td>
<!--                <td>{{ item.donNhapDTO.tongTienThanhToan }}</td>-->
<!--                <td>{{ item.donNhapDTO.tienNo }}</td>-->
<!--                <td>{{ item.donNhapDTO.nhaCungCap.tenNCC }}</td>-->
                <td>{{ item.donXuatDTO.nhaKho.tenKho }}</td>
                <td>{{ item.donXuatDTO.taiKhoan.hoTen }}</td>
                <td>{{ item.nguoiXacNhanDon }}</td>
                <td>
                    <button class='btn btn-warning' @click='chiTietDonXuat(item.donXuatDTO.id)'>Chi Tiết</button><br>
                    <button v-if="item.nguoiXacNhanDon === null" class="btn btn-primary" @click='xacNhan(item.donXuatDTO.id)'>Đủ Hàng</button>
                    <button v-else class="btn btn-primary" disabled> Đủ Hàng</button>
                </td>
            </tr>
            </tbody>
        </table>
        <div class="pagination">
            <button @click="previousPage" :disabled="currentPage === 1" class="pagination-button btn-primary">Trang trước</button>
            <span style='font-weight: bold; font-size: 16px; color: white; padding-top: 2px; margin: 6px'>{{ currentPage }}</span>
            <button @click="nextPage" :disabled="currentPage === totalPages" class="pagination-button btn-primary">Trang sau</button>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
    import alert from 'bootstrap/js/src/alert.js';
    import Swal from 'sweetalert2';

    export default {
        data() {
            return {
                items: [],
                searchTerm: "",
                sortColumn: null,
                sortDirection: "asc",
                itemsPerPage: 10,
                currentPage: 1,
            };
        },
        computed: {
            filteredData() {
                let filteredItems = this.items;
                if (this.searchTerm) {
                    const searchTerm = this.searchTerm.toLowerCase();
                    filteredItems = filteredItems.filter(item =>
                        Object.values(item).some(value =>
                            value.toString().toLowerCase().includes(searchTerm)
                        )
                    );
                }
                if (this.sortColumn) {
                    filteredItems.sort((a, b) => {
                        const columnA = a[this.sortColumn];
                        const columnB = b[this.sortColumn];
                        if (columnA < columnB) return this.sortDirection === "asc" ? -1 : 1;
                        if (columnA > columnB) return this.sortDirection === "asc" ? 1 : -1;
                        return 0;
                    });
                }
                return filteredItems;
            },
            totalPages() {
                return Math.ceil(this.filteredData.length / this.itemsPerPage);
            },
            displayedData() {
                const startIndex = (this.currentPage - 1) * this.itemsPerPage;
                const endIndex = startIndex + this.itemsPerPage;
                return this.filteredData.slice(startIndex, endIndex);
            },
        },
        methods: {
            fetchData() {
                const token = localStorage.getItem('token');
                axios.get('http://localhost:8081/api/don-xuat', {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`,
                    }
                }).then(response => {
                    this.items = response.data;
                    this.items.forEach(item => {
                        const date = new Date(item.donXuatDTO.ngayLap);
                        item.donXuatDTO.ngayLap = date.toLocaleDateString();

                        // axios.get(`http://localhost:8081/api/tai-khoan/${item.nguoiXacNhan}`, {
                        //     headers: {
                        //         'Content-Type': 'application/json',
                        //         'Authorization': `Bearer ${token}`,
                        //     }
                        // }).then(response => {
                        //     if (response.ok) {
                        //         const temp = response.data;
                        //         item.nguoiXacNhan = temp.hoTen;
                        //     }
                        // })
                    })
                }).catch(error => {
                    console.error(error);
                });
            },
            // findNguoiXacNhan(id) {
            //     const token = localStorage.getItem('token');
            //     axios.get(`http://localhost:8081/api/tai-khoan/${id}`, {
            //         headers: {
            //             'Content-Type': 'application/json',
            //             'Authorization': `Bearer ${token}`,
            //         }
            //     }).then(response => {
            //         if (response.ok) {
            //             const temp = response.data;
            //             return temp.hoTen;
            //         }
            //     })
            // }
            addItem1() {
                window.location.href = '/don-xuat/them/1';
            },
            addItem2() {
                window.location.href = '/don-xuat/them/2';
            },
            sortColumns(column) {
                if (column === this.sortColumn) {
                    this.sortDirection = this.sortDirection === "asc" ? "desc" : "asc";
                } else {
                    this.sortColumn = column;
                    this.sortDirection = "asc";
                }
            },
            previousPage() {
                if (this.currentPage > 1) {
                    this.currentPage--;
                }
            },
            nextPage() {
                if (this.currentPage < this.totalPages) {
                    this.currentPage++;
                }
            },

            chiTietDonXuat(id) {
                window.location.href = `/chitietdonxuat/${id}`;
            },

            availableAction() {
                const per = localStorage.getItem('permission');
                return (per === 'quankho' || per === 'chu');
            },

            xacNhan(id) {
                const token = localStorage.getItem('token');
                axios.get(`http://localhost:8081/api/don-xuat/${id}/xac-nhan`, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`,
                    }
                }).then(response => {
                    this.items = response.data;
                    Swal.fire({
                        icon: "success",
                        title: "Xác nhận thành công",
                        text: "Bạn đã xác nhận đơn xuất với ID = " + id + ".",
                    }).then(() => {
                        window.location.href = '/don-xuat';
                    });
                    // alert("Bạn đã xác nhận đơn nhập với ID = " + id + ".");
                    // window.location.href = '/don-nhap';
                }).catch(error => {
                    console.error(error);
                });
            }
        },
        created() {
            this.fetchData();
            // this.findNguoiXacNhan();
        },
    };
</script>

<style>
    .container {
        max-width: 100%;
        margin: 0 auto;
        padding: 20px;
    //background: #1abc9c;
    }

    .add-button {
    //background-color: #4caf50;
    //color: white;
        font-size: 16px;
        font-weight: bold;
        border: none;
        padding: 10px 20px;
        margin-bottom: 10px;
        cursor: pointer;
    }

    .search-input {
        width: 30%;
        padding: 10px;
        margin-bottom: 10px;
        font-size: 16px;
        float: right;
        border-radius: 5px;
    //color: grey;
    }

    ::placeholder {
        color: black;
    }

    .data-table {
        width: 100%;
        border-collapse: collapse;
    //background: #484848;
    }

    th,
    td {
        border: 1px solid #ccc;
        padding: 8px;
    }

    th {
    //background-color: #f2f2f2;
        font-weight: bold;
        font-size: 25px;
        cursor: pointer;
    }

    .pagination {
        margin-top: 10px;
        text-align: center;
    }

    .pagination-button {
        padding: 5px 10px;
        margin: 0 5px;
    //background-color: #4caf50;
        color: white;
        border: none;
        cursor: pointer;
    }

    .pagination-button:disabled {
    //background-color: #ccc;
        cursor: not-allowed;
    }

    #phieuNhapID {
        cursor: pointer;
    }
</style>
