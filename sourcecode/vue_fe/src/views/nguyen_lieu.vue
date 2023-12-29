<template>
    <div class="container">
        <h2>Danh Sách Nguyên Liệu</h2>
        <button v-if='availableAction()' class="add-button btn btn-success text-black font-weight-bold m-2" @click="addItem1">Thêm mới</button>
        <input class="search-input" type="text" v-model="searchTerm" placeholder="Tìm kiếm..." />
        <table class="data-table table table-dark table-striped">
            <thead>
            <tr>
                <th @click="sortColumns('id')">ID</th>
                <th @click="sortColumns('hinhAnh')">Ảnh</th>
                <th @click="sortColumns('tenNguyenLieu')">Tên</th>
                <th @click="sortColumns('giaNhap')">Giá Nhập</th>
                <th @click="sortColumns('donViTinh')">Đơn Vị Tính</th>
                <th @click="sortColumns('vAT')">Thuế VAT</th>
                <th @click="sortColumns('nhomNguyenLieu')">Nhóm</th>
                <th @click="sortColumns('nhaCungCap')">Nhà Cung Cấp</th>
                <th>Quản Lý</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item, index) in displayedData" :key="item.id">
                <td id='phieuNhapID' @click='chiTietPhieuNhap(item.id)'>{{ item.id }}</td>
                <td><img class='anhNguyenLieu' :src="'/assets/images/' + item.hinhAnh"></td>
                <td>{{ item.tenNguyenLieu }}</td>
                <td>{{ item.giaNhap }}</td>
                <td>{{ item.donViTinh }}</td>
                <td>{{ item.vAT }}</td>
                <td>{{ item.nhomNguyenLieu.tenNhom }}</td>
                <td>{{ item.nhaCungCap.tenNCC }}</td>
                <td>
                    <div class='d-flex flex-column align-items-center'>
                        <button class="btn btn-primary" @click='chiTietNguyenLieu(item.id)'>Chi Tiết</button><br>
                        <button v-if='availableAction()' @click='suaNguyenLieu(item.id)' class='btn btn-warning mb-1'>Sửa</button><br>
<!--                        <button class="btn btn-danger">Xóa</button>-->
                    </div>
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
                axios.get('http://localhost:8081/api/nguyen-lieu', {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`,
                    }
                }).then(response => {
                    this.items = response.data;
                }).catch(error => {
                    console.error(error);
                });
            },
            addItem1() {
                window.location.href = '/nguyen-lieu/them';
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

            chiTietNguyenLieu(id) {
                window.location.href = `/chitietnguyenlieu/${id}`;
            },

            suaNguyenLieu(id) {
                window.location.href = `/nguyen-lieu/${id}/sua`;
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
    }

    .add-button {
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
    }

    th,
    td {
        border: 1px solid #ccc;
        padding: 8px;
    }

    th {font-weight: bold;
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
        color: white;
        border: none;
        cursor: pointer;
    }

    .pagination-button:disabled {
        cursor: not-allowed;
    }

    #phieuNhapID {
        cursor: pointer;
        text-align: center;
        font-weight: bold;
    }
    
    .anhNguyenLieu {
        width: 200px;
        height: 200px;
    }
</style>
