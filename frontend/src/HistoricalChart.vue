<template>
    <div class="chart-container">
      <canvas id="historicalChart"></canvas>
    </div>
  </template>
  
  <script>
  import { Line } from 'vue-chartjs';
  import axios from 'axios';
  
  export default {
    props: ['pair'],
    mounted() {
      this.fetchHistoricalData();
    },
    methods: {
      async fetchHistoricalData() {
        // Fetch data and render chart logic here
      },
    },
  };
  </script>
  
  <style scoped>
  .chart-container {
    width: 100%;
    height: 100%;
  }
  </style>
  