<template>
    <div class="layout-px-spacing dash_1">
        <teleport to="#breadcrumb">
            <ul class="navbar-nav flex-row">
                <li>
                    <div class="page-header">
                        <nav class="breadcrumb-one" aria-label="breadcrumb">
                            <ol class="breadcrumb">
                                <li class="breadcrumb-item"><a href="javascript:;">Trang chủ</a></li>
                                <li class="breadcrumb-item active" aria-current="page"><span>Tổng quát</span></li>
                            </ol>
                        </nav>
                    </div>
                </li>
            </ul>
        </teleport>

        <div class="button-container">
            <div class="button-row" v-for="(row, index) in buttonRows" :key="index">
                <h3 class="button-row-label">{{ row.label }}</h3>
                <div class="button-group">
                    <router-link v-for="button in row.buttons" :key="button.id" :to="button.route" class="button">
                        {{ button.label }}
                    </router-link>
                </div>
            </div>
        </div>
    </div> 
</template>

        <script>
            export default {
                data() {
                    return {
                        buttonRows: [
                            {
                                label: 'Quản Lý Chung',
                                buttons: [
                                    { id: 1, label: 'Kho', route: '/nha-kho' },
                                    { id: 2, label: 'Nhà Cung Cấp', route: '/nha-cung-cap' },
                                    { id: 3, label: 'Nhóm NL', route: '/nhom-nguyen-lieu' },
                                    { id: 4, label: 'Nguyên Liệu', route: '/nguyen-lieu' },
                                ]
                            },
                            {
                                label: 'Nhập Hàng',
                                buttons: [
                                    { id: 5, label: 'Phiếu Nhập', route: '/phieu-nhap' },
                                    { id: 6, label: 'Đơn Nhập', route: '/don-nhap' }
                                ]
                            },
                            {
                                label: 'Xuất Hàng',
                                buttons: [
                                    { id: 7, label: 'Đơn Xuất', route: '/don-xuat' }
                                ]
                            },
                            {
                                label: 'Doanh Thu',
                                buttons: [
                                    { id: 8, label: 'Thống Kê', route: '/thong-ke' }
                                ]
                            },
                            {
                                label: 'Quản Lý Tài Khoản',
                                buttons: [
                                    { id: 9, label: 'Cấp Tài Khoản', route: '/tai-khoan/cap-moi' },
                                    { id: 10, label: 'Vô Hiệu Hóa Tài Khoản', route: '/tai-khoan/vhh' }
                                ]
                            }
                        ]
                    };
                },
                methods: {
                    isNotAdmin() {
                        const per = localStorage.getItem("permission");
                        return per !== 'admin';
                    },
                },
                created() {
                    this.isNotAdmin();
                    console.log(localStorage.getItem('permission'));
                }
            };
        </script>

<style>
    .button-container {
        display: flex;
        flex-direction: column;
        //align-items: center;
    }

    .button-row {
        display: flex;
        flex-direction: column;
        //align-items: center;
        margin-top: 30px;
    }

    .button-row-label {
        font-size: 24px;
        color: #fff;
        margin-bottom: 10px;
        font-weight: bold;
    }

    .button-group {
        display: flex;
        flex-wrap: wrap;
        //justify-content: center;
    }

    .button {
        width: 250px;
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 20px 40px;
        margin: 15px;
        border-radius: 5px;
        background-color: #333;
        font-size: 22px;
        font-weight: bold;
        color: #fff;
        text-decoration: none;
    }

    .button:hover {
        background-color: #555;
    }

    .button:active {
        background-color: #222;
    }
</style>
