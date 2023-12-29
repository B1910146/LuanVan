<template>
    <div class="container">
        <h2>Danh Sách Phiếu Nhập</h2>
<!--        <h1>{{ displayedData }}</h1>-->
        <button v-if='availableAction()' class="add-button btn btn-success text-black font-weight-bold m-2" @click="addItem1">Thêm Thủ Công</button>
        <button v-if='availableAction()' class="add-button btn btn-success text-black font-weight-bold m-2" @click="addItem2">Thêm Bằng File Excel</button>
        <input class="search-input" type="text" v-model="searchTerm" placeholder="Tìm kiếm..." />
        <table class="data-table table table-dark table-striped">
            <thead>
            <tr>
                <th @click="sortColumns('id')">ID</th>
                <th @click="sortColumns('ngayLap')">Ngày Lập</th>
                <th @click="sortColumns('tienHang')">Tiền Hàng</th>
                <th @click="sortColumns('tienThanhToan')">Tiền Thanh Toán</th>
                <th @click="sortColumns('tienNo')">Tiền Nợ</th>
                <th @click="sortColumns('nCC')">Nhà Cung Cấp</th>
                <th @click="sortColumns('nhaKho')">Nhà Kho</th>
                <th @click="sortColumns('nguoiLap')">Người Lập</th>
                <th>Quản Lý</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item, index) in displayedData" :key="item.id">
                <td id='phieuNhapID' @click='chiTietPhieuNhap(item.id)'>{{ item.id }}</td>
                <td>{{ item.ngayLap }}</td>
                <td>{{ item.tongTienHang }}</td>
                <td>{{ item.tongTienThanhToan }}</td>
                <td>{{ item.tienNo }}</td>
                <td>{{ item.nhaCungCap.tenNCC }}</td>
                <td>{{ item.nhaKho.tenKho }}</td>
                <td>{{ item.taiKhoan.hoTen }}</td>
                <td>
                    <button class='btn btn-warning' @click='chiTietPhieuNhap(item.id)'>Chi Tiết</button>
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
                axios.get('http://localhost:8081/api/phieu-nhap', {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`,
                    }
                }).then(response => {
                    this.items = response.data;
                    console.log(this.items);
                    this.items.forEach(item => {
                        const date = new Date(item.ngayLap);
                        item.ngayLap = date.toLocaleDateString();
                    })
                }).catch(error => {
                    console.error(error);
                });
            },
            addItem1() {
                window.location.href = '/phieu-nhap/them/1';
            },
            addItem2() {
                window.location.href = '/phieu-nhap/them/2';
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
            
            chiTietPhieuNhap(id) {
                window.location.href = `/chitietphieunhap/${id}`;
            },
            availableAction() {
                const per = localStorage.getItem('permission');
                return (per === 'quankho' || per === 'chu');
            }
        },
        created() {
            this.fetchData();
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
