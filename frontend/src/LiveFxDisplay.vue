<template>
  <div class="live-fx-display">
    <h2>Live Bid/Ask Prices for {{ pair }}</h2>
    <div v-if="fxData">
      <p>Bid: {{ fxData.bid }}</p>
      <p>Ask: {{ fxData.ask }}</p>
    </div>
    <div v-else>
      <p>Loading...</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  props: ['pair'],
  data() {
    return {
      fxData: null
    };
  },
  mounted() {
    this.fetchFxData();
    setInterval(this.fetchFxData, 1000);  // Fetch new data every 1 seconds
  },
  methods: {
    async fetchFxData() {
      try {
        const response = await axios.get(`https://api.exchangerate-api.com/v4/latest/${this.pair.split('/')[0]}`);
        const rates = response.data.rates;
        this.fxData = {
          bid: rates[this.pair.split('/')[1]] - 0.001,  // Simulate bid
          ask: rates[this.pair.split('/')[1]] + 0.001   // Simulate ask
        };
      } catch (error) {
        console.error('Error fetching FX data', error);
      }
    }
  }
};
</script>

<style scoped>
.live-fx-display {
  margin-top: 20px;
  border: 1px solid #ddd;
  padding: 20px;
}
</style>
