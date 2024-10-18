import './assets/main.css'
<template>
  <div id="app">
    <header class="header">
      <h1>Dashboard</h1>
    </header>
    <main class="dashboard-content">
      <div class="currencypairselector">
        <CurrencyPairSelector :pairs="currencyPairs" @pairSelected="setSelectedPair"/>
      </div>
      <div class="top-row">
        <div class="tile small">
          
        </div>
        <div class="tile small">
          <LiveFxDisplay :pair="selectedPair" />
        </div>
        <div class="tile small">
          <p>placeholder</p>
        </div>
        <div class="tile small">
          <!-- Add any additional component or information here -->
          <p>palceholder</p>
        </div>
      </div>
      <div class="bottom-row">
        <div class="tile large">
          <HistoricalChart :pair="selectedPair" />
        </div>
      </div>
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

