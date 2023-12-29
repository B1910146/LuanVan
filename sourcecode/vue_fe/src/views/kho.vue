<template>
    <div class="container">
        <h2>Danh Sách Kho</h2>
        <button v-if='availableAction()' class="add-button btn btn-success text-black font-weight-bold m-2" @click="addItem1">Thêm mới</button>
        <button class='btn btn-primary m-2' @click="exportToExcel">Xuất ra Excel</button>
<!--        <button class='btn btn-primary m-2' @click="exportToCSV">Xuất ra CSV</button>-->
        <input class="search-input" type="text" v-model="searchTerm" placeholder="Tìm kiếm..." />
        <table class="data-table table table-dark table-striped" ref="dataTable">
            <thead>
            <tr>
                <th @click="sortColumns('id')">ID</th>
                <th @click="sortColumns('tenKho')">Tên</th>
                <th @click="sortColumns('diaChi')">Địa Chỉ</th>
                <th @click="sortColumns('loai')">Loại</th>
                <th @click="sortColumns('ngayTạo')">Ngày Tạo</th>
                <th>Quản Lý</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item, index) in displayedData" :key="item.id">
                <td id='phieuNhapID' @click='chiTietPhieuNhap(item.id)'>{{ item.id }}</td>
                <td>
                    <textarea v-if='availableAction()' v-model="item.tenKho" type="text" style='width: 100%' class="form-control" id="tenKho" @input='updateKho(item.id, item.tenKho, "tenKho")'></textarea>
                    <textarea v-else v-model="item.tenKho" type="text" style='width: 100%' class="form-control" id="tenKho" disabled></textarea>
                </td>
                <td>
                    <textarea v-if='availableAction()' v-model="item.diaChi" type="text" style='width: 100%' class="form-control" id="diaChi" @input='updateKho(item.id, item.diaChi, "diaChi")'></textarea>
                    <textarea v-else v-model="item.diaChi" type="text" style='width: 100%' class="form-control" id="diaChi" disabled></textarea>
                </td>
                <td>
                    <select v-model='item.loai' v-if='availableAction()' class="form-control" id="loai" @change='updateKho(item.id, item.loai, "loai")'>
                        <option v-if='item.loai === "Kho Lạnh"' value='Kho Lạnh' selected>Kho Lạnh</option>
                        <option v-else value='Kho Lạnh'>Kho Lạnh</option>
                        <option v-if='item.loai === "Kho Mát"' value='Kho Mát' selected>Kho Mát</option>
                        <option v-else value='Kho Mát'>Kho Mát</option>
                    </select>
                    <select v-else class="form-control" id="loai">
                        <option v-if='item.loai === "Kho Lạnh"' value='Kho Lạnh' selected>Kho Lạnh</option>
                        <option v-else value='Kho Lạnh'>Kho Lạnh</option>
                        <option v-if='item.loai === "Kho Mát"' value='Kho Mát' selected>Kho Mát</option>
                        <option v-else value='Kho Mát'>Kho Mát</option>
                    </select>
                </td>
                <td>{{ item.ngayTao }}</td>
                <td>
                    <div class='d-flex flex-column align-items-center'>
                        <button class="btn btn-primary" @click='chiTietKho(item.id)'>Chi Tiết</button><br>
<!--                        <button v-if='availableAction()' class='btn btn-warning mb-1'>Sửa</button><br>-->
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
    // import XLSX from 'xlsx';
    // import ExcelJS from 'exceljs';
    // import { ExportToCsv } from 'export-to-csv';

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
            // exportToExcel() {
            //     const table = this.$refs.dataTable;
            //
            //     const workbook = new ExcelJS.Workbook();
            //     const worksheet = workbook.addWorksheet('Dữ liệu');
            //
            //     const rows = table.getElementsByTagName('tr');
            //     for (let i = 0; i < rows.length; i++) {
            //         const cells = rows[i].getElementsByTagName('td');
            //         const rowData = [];
            //         for (let j = 0; j < cells.length; j++) {
            //             rowData.push(cells[j].innerText);
            //         }
            //         worksheet.addRow(rowData);
            //     }
            //
            //     workbook.xlsx.writeBuffer().then((buffer) => {
            //         const blob = new Blob([buffer], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
            //         const url = URL.createObjectURL(blob);
            //         const link = document.createElement('a');
            //         link.setAttribute('href', url);
            //         link.setAttribute('download', 'data.xlsx');
            //         link.click();
            //     });
            // },
            // exportToCSV() {
            //     const table = this.$refs.dataTable;
            //
            //     const options = {
            //         fieldSeparator: ',',
            //         quoteStrings: '"',
            //         decimalSeparator: '.',
            //         showLabels: true,
            //         showTitle: false,
            //         useTextFile: false,
            //         useBom: true,
            //         useKeysAsHeaders: true,
            //     };
            //
            //     const csvExporter = new ExportToCsv(options);
            //     const csvData = csvExporter.generateCsv(table.innerHTML);
            //
            //     const blob = new Blob([csvData], { type: 'text/csv;charset=utf-8;' });
            //     const url = URL.createObjectURL(blob);
            //     const link = document.createElement('a');
            //     link.setAttribute('href', url);
            //     link.setAttribute('download', 'data.csv');
            //     link.click();
            // },
            fetchData() {
                const token = localStorage.getItem('token');
                axios.get('http://localhost:8081/api/nha-kho', {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`,
                    }
                }).then(response => {
                    this.items = response.data;
                    this.items.forEach(item => {
                        const date = new Date(item.ngayTao);
                        item.ngayTao = date.toLocaleDateString();
                    })
                }).catch(error => {
                    console.error(error);
                });
            },
            addItem1() {
                window.location.href = '/nha-kho/them';
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
            updateKho(id, value, flag) {
                const token = localStorage.getItem('token');
                const data = {
                    id: id,
                    ...{ [flag]: value },
                };
                axios.patch(`http://localhost:8081/api/nha-kho/${id}`, data, {
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
