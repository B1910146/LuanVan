<template>
    <div class="container">
        <h2>Thông Tin Nhà Cung Cấp</h2>
        <button v-if='availableAction()' class="add-button btn btn-success text-black font-weight-bold m-2" @click="addItem1">Thêm mới</button>
        <input class="search-input" type="text" v-model="searchTerm" placeholder="Tìm kiếm..." />
        <table class="data-table table table-dark table-striped">
            <thead>
            <tr>
                <th @click="sortColumns('id')">ID</th>
                <th @click="sortColumns('tenNCC')">Tên</th>
                <th @click="sortColumns('soDT')">SDT</th>
                <th @click="sortColumns('email')">Email</th>
                <th @click="sortColumns('diaChi')">Địa Chỉ</th>
                <th @click="sortColumns('ghiChu')">Ghi Chú</th>
<!--                <th @click="sortColumns('ngayTạo')">Ngày Tạo</th>-->
                <th v-if='availableAction()'>Quản Lý</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item, index) in displayedData" :key="item.id">
                <td>{{ item.id }}</td>
                <td width='20%'>
                    <textarea v-if='availableAction()' v-model="item.tenNCC" type="text" style='width: 100%' class="form-control" id="tenNCC" @input='updateNCC(item.id, item.tenNCC, "tenNCC")'></textarea>
                    <textarea v-else v-model="item.tenNCC" type="text" style='width: 100%' class="form-control" id="tenNCC" disabled></textarea>
                </td>
                <td>
                    <textarea maxlength='10' v-if='availableAction()' v-model="item.soDT" type="text" style='width: 100%' class="form-control" id="soDT" @input='updateNCC(item.id, item.soDT, "soDT")'></textarea>
                    <textarea maxlength='10' v-else v-model="item.soDT" type="text" style='width: 100%' class="form-control" id="soDT" disabled></textarea>
                </td>
                <td>
                    <textarea v-if='availableAction()' v-model="item.email" type="text" style='width: 100%' class="form-control" id="email" @input='updateNCC(item.id, item.email, "email")'></textarea>
                    <textarea v-else v-model="item.email" type="text" style='width: 100%' class="form-control" id="email" disabled></textarea>
                </td>
                <td>
                    <textarea v-if='availableAction()' v-model="item.diaChi" type="text" style='width: 100%' class="form-control" id="diaChi" @input='updateNCC(item.id, item.diaChi, "diaChi")'></textarea>
                    <textarea v-else v-model="item.diaChi" type="text" style='width: 100%' class="form-control" id="diaChi" disabled></textarea>
                </td>
                <td>
                    <textarea v-if='availableAction()' v-model="item.ghiChu" type="text" style='width: 100%' class="form-control" id="ghiChu" @input='updateNCC(item.id, item.ghiChu, "ghiChu")'></textarea>
                    <textarea v-else v-model="item.ghiChu" type="text" style='width: 100%' class="form-control" id="ghiChu" disabled></textarea>
                </td>
                <td v-if='availableAction()' width='10%'>
                    Nhập thông tin vào các trường để sửa
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
                axios.get('http://localhost:8081/api/nha-cung-cap', {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`,
                    }
                }).then(response => {
                    this.items = response.data;
                    // this.items.forEach(item => {
                    //     const date = new Date(item.ngayTao);
                    //     item.ngayTao = date.toLocaleDateString();
                    // })
                }).catch(error => {
                    console.error(error);
                });
            },
            addItem1() {
                window.location.href = '/nha-cung-cap/them';
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

            chiTietKho(id) {
                window.location.href = `/chitietnhakho/${id}`;
            },

            availableAction() {
                const per = localStorage.getItem('permission');
                return (per === 'quankho' || per === 'chu');
            },
            updateNCC(id, value, flag) {
                const token = localStorage.getItem('token');
                const data = {
                    id: id,
                    ...{ [flag]: value },
                };
                axios.patch(`http://localhost:8081/api/nha-cung-cap/${id}`, data, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`,
                    }
                }).then(response => {
                    // this.tenNhom = response.data.tenNhom;
                    // this.isDataLoaded = true;
                }).catch(error => {
                    console.error(error);
                });
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
<script setup>
</script>
