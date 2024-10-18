<template>
  <div class="historical-chart-container">
    <!-- Display the pair name as a title -->
    <h2 class="currency-pair-title">{{ pair }} Historical Data</h2>

    <div class="chart-container">
      <canvas id="historicalChart"></canvas>
    </div>
  </div>
</template>

<script>
import { Line } from 'vue-chartjs';
import { Chart, registerables } from 'chart.js';
import axios from 'axios';

// Register Chart.js components
Chart.register(...registerables);

export default {
  props: ['pair'],
  data() {
    return {
      chart: null, // Reference to the chart instance
    };
  },
  mounted() {
    this.fetchHistoricalData();
  },
  watch: {
    pair(newPair, oldPair) {
      if (newPair !== oldPair) {
        this.fetchHistoricalData(); // Refetch the data when the currency pair changes
      }
    },
  },
  methods: {
    async fetchHistoricalData() {
      try {
        const [baseCurrency, targetCurrency] = this.pair.split('/');

        const response = await axios.get('http://localhost:8080/api/fxrates/timeseries', {
          params: {
            function: 'FX_DAILY',
            fromSymbol: baseCurrency,
            toSymbol: targetCurrency,
            outputSize: 'compact',
            dataType: 'json',
          },
        });

        // Log the full API response
        console.log('API Response:', response.data);

        // Check for the existence of 'Time Series FX (Daily)'
        const timeSeries = response.data['Time Series FX (Daily)'];
        if (!timeSeries) {
          throw new Error('Time Series FX (Daily) not found in response');
        }

        const dates = Object.keys(timeSeries).reverse(); // Get all dates and reverse them for chronological order
        const openPrices = dates.map((date) => timeSeries[date]['1. open']);
        const closePrices = dates.map((date) => timeSeries[date]['4. close']);
        const highPrices = dates.map((date) => timeSeries[date]['2. high']);
        const lowPrices = dates.map((date) => timeSeries[date]['3. low']);

        this.renderChart(dates, openPrices, closePrices, highPrices, lowPrices);
      } catch (error) {
        console.error('Error fetching historical data:', error.message);
      }
    }
    ,
    renderChart(dates, openPrices, closePrices, highPrices, lowPrices) {
      // Destroy previous chart instance if it exists to avoid duplicates
      if (this.chart) {
        this.chart.destroy();
      }

      // Create a new chart instance
      const ctx = document.getElementById('historicalChart').getContext('2d');
      this.chart = new Chart(ctx, {
        type: 'line',
        data: {
          labels: dates, // Dates will be the labels for the x-axis
          datasets: [
            {
              label: 'Open',
              data: openPrices,
              borderColor: 'rgba(75, 192, 192, 1)',
              backgroundColor: 'rgba(75, 192, 192, 0.2)',
              fill: false,
            },
            {
              label: 'Close',
              data: closePrices,
              borderColor: 'rgba(153, 102, 255, 1)',
              backgroundColor: 'rgba(153, 102, 255, 0.2)',
              fill: false,
            },
            {
              label: 'High',
              data: highPrices,
              borderColor: 'rgba(255, 206, 86, 1)',
              backgroundColor: 'rgba(255, 206, 86, 0.2)',
              fill: false,
            },
            {
              label: 'Low',
              data: lowPrices,
              borderColor: 'rgba(255, 99, 132, 1)',
              backgroundColor: 'rgba(255, 99, 132, 0.2)',
              fill: false,
            },
          ],
        },
        options: {
          responsive: true,
          maintainAspectRatio: false, // This ensures the chart fills the container
          scales: {
            x: {
              title: {
                display: true,
                text: 'Date',
              },
            },
            y: {
              title: {
                display: true,
                text: 'Price',
              },
              beginAtZero: false,
            },
          },
        },
      });
    },
  },
};
</script>

<style scoped>
.historical-chart-container {
  width: 100%; /* Ensures the component takes full width */
  display: flex;
  flex-direction: column;
  align-items: center; /* Centers the content */
}

.currency-pair-title {
  font-size: 1.5rem; /* Adjust the size as needed */
  margin-bottom: 20px;
}

.chart-container {
  width: 100%; /* Full width */
  height: 500px; /* Adjust the height of the chart */
}

canvas {
  width: 100%;
  height: 100%;
}
</style>


