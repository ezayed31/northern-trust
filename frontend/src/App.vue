<template>
  <div id="app">
    <header class="header">
      <h1>Live FX Rates Dashboard</h1>
      <CurrencyPairSelector :pairs="currencyPairs" @pairSelected="setSelectedPair" />
    </header>
    <main class="dashboard-content">
      <section class="live-fx">
        <LiveFxDisplay :pair="selectedPair" />
      </section>
      <section class="historical-chart">
        <HistoricalChart :pair="selectedPair" />
      </section>
    </main>
  </div>
</template>

<script>
import CurrencyPairSelector from '@/CurrencyPairSelector.vue';
import LiveFxDisplay from '@/LiveFxDisplay.vue';
import HistoricalChart from '@/HistoricalChart.vue';

export default {
  data() {
    return {
      currencyPairs: ['USD/EUR', 'USD/JPY', 'GBP/USD', 'AUD/USD', 'EUR/GBP', 'USD/CHF'],
      selectedPair: 'USD/JPY',
    };
  },
  methods: {
    setSelectedPair(pair) {
      this.selectedPair = pair;
    }
  },
  components: {
    CurrencyPairSelector,
    LiveFxDisplay,
    HistoricalChart,
  },
};
</script>

<style scoped>
/* Make the entire app fill the viewport */
#app {
  display: flex;
  flex-direction: column;
  width: 100vw;
  height: 100vh;
  background-color: #2c3e50;
  color: white;
  overflow: hidden;
  box-sizing: border-box;
}

/* Header styling */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  width: 100%;
  background-color: #1e2a38;
  box-sizing: border-box;
}

h1 {
  margin: 0;
}

select {
  padding: 5px;
  border-radius: 5px;
  border: 1px solid #ccc;
}

/* Main content area that grows to fill the rest of the space */
.dashboard-content {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  flex-grow: 1;
  width: 100%;
  gap: 20px;
  padding: 20px;
  box-sizing: border-box;
}

/* Flex items for the components */
.live-fx,
.historical-chart {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
  background-color: #333;
  overflow: auto;
  box-sizing: border-box;
  height: 100%;
}

.historical-chart {
  height: 100%; /* Ensure the chart fills its container */
}

/* Media query for smaller screens */
@media (max-width: 768px) {
  .dashboard-content {
    flex-direction: column;
    align-items: stretch;
  }

  .live-fx,
  .historical-chart {
    width: 100%;
    margin-bottom: 20px;
  }
}
</style>
