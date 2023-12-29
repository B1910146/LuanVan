<template>
    <div className='container'>
        <h1>Thống kê</h1>
        <div className='row'>
            <div className='col-md-4'>
                <div className='card'>
                    <div className='card-body'>
                        <h5 className='card-title'>Số lượng đơn nhập</h5>
                        <p className='card-text'>{{ donNhapCount }}</p>
                    </div>
                </div>
            </div>
            <div className='col-md-4'>
                <div className='card'>
                    <div className='card-body'>
                        <h5 className='card-title'>Số lượng đơn xuất</h5>
                        <p className='card-text'>{{ donXuatCount }}</p>
                    </div>
                </div>
            </div>
            <div className='col-md-4'>
                <div className='card'>
                    <div className='card-body'>
                        <h5 className='card-title'>Biểu đồ thu chi</h5>
                        <div id='chart'></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import { Chart, CategoryScale, LinearScale, BarController, BarElement, Title } from 'chart.js';

    Chart.register(CategoryScale, LinearScale, BarController, BarElement, Title);

    export default {
        data() {
            return {
                donNhapCount: 0,
                donXuatCount: 0,
                chartData: {
                    labels: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6'],
                    datasets: [
                        {
                            label: 'Thu',
                            backgroundColor: 'rgba(75, 192, 192, 0.5)',
                            borderColor: 'rgba(75, 192, 192, 1)',
                            data: [1000, 2000, 1500, 2500, 1800, 3000]
                        },
                        {
                            label: 'Chi',
                            backgroundColor: 'rgba(255, 99, 132, 0.5)',
                            borderColor: 'rgba(255, 99, 132, 1)',
                            data: [800, 1500, 1200, 2000, 1400, 2500]
                        }
                    ]
                }
            };
        },
        mounted() {
            this.getDonNhapCount();
            this.getDonXuatCount();
            this.renderChart();
        },
        methods: {
            getDonNhapCount() {
                this.donNhapCount = 10;
            },
            getDonXuatCount() {
                this.donXuatCount = 5;
            },
            renderChart() {
                const ctx = document.getElementById('chart').getContext('2d');
                new Chart(ctx, {
                    type: 'bar',
                    data: this.chartData,
                    options: {
                        responsive: true,
                        scales: {
                            x: {
                                grid: {
                                    display: false
                                }
                            },
                            y: {
                                beginAtZero: true
                            }
                        },
                        plugins: {
                            title: {
                                display: true,
                                text: 'Biểu đồ thu chi'
                            }
                        }
                    }
                });
            }
        }
    };
</script>

<style>
    .card {
        margin-bottom: 20px;
    }
</style>
