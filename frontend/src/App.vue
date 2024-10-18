<template>
  <div id="app">
    <header class="header">
      <h1>World Link Bank FX Trade</h1>
    </header>
    <main class="dashboard-content">
      <div class="currencypairselector">
        <CurrencyPairSelector :pairs="currencyPairs" @pairSelected="setSelectedPair"/>
      </div>
      <div class="top-row">
        <div class="tile small">
          <CurrencyConverter
              :baseCurrency="selectedBaseCurrency"
              :targetCurrency="selectedTargetCurrency"
          />
        </div>
        <div class="tile small">
          <LiveFxDisplay :pair="selectedPair" />
        </div>
        <div class="tile small">
          <LiveWorldNews/>
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
import LiveWorldNews from './LiveWorldNews.vue';
import CurrencyConverter from './CurrencyConverter.vue';

export default {
  data() {
    return {
      selectedPair: 'USD/EUR',
      selectedBaseCurrency: 'USD',
      selectedTargetCurrency: 'EUR',
    };
  },
  methods: {
    setSelectedPair(pair) {
      this.selectedPair = pair;
      [this.selectedBaseCurrency, this.selectedTargetCurrency] = pair.split('/');
    }
  },
  components: {
    CurrencyPairSelector,
    LiveFxDisplay,
    HistoricalChart,
    LiveWorldNews,
    CurrencyConverter,
  },
};
</script>
