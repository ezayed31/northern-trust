<template>
    <div class="live-fx-display">
      <h2>Live Prices for {{ pair }}</h2>
      <div class="prices-container">
        <div class="price-box">
          <h3>Bid</h3>
          <p :class="{'price-up': bidUp, 'price-down': !bidUp}">{{ bidPrice }}</p>
        </div>
        <div class="price-box">
          <h3>Ask</h3>
          <p :class="{'price-up': askUp, 'price-down': !askUp}">{{ askPrice }}</p>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    props: ['pair'],
    data() {
      return {
        bidPrice: null,
        askPrice: null,
        previousBidPrice: null,
        previousAskPrice: null,
        bidUp: true,
        askUp: true,
      };
    },
    mounted() {
      this.fetchPrices();
      setInterval(this.fetchPrices, 1000); // Update every 5 seconds
    },
    methods: {
      async fetchPrices() {
        if (!this.pair) return;
  
        const [baseCurrency, targetCurrency] = this.pair.split('/');
        try {
          // Fetch live FX data from an API
          // Replace this with your actual API endpoint and API key
          const response = await axios.get(
            `https://api.exchangerate-api.com/v4/latest/${baseCurrency}`
          );
  
          const rate = response.data.rates[targetCurrency];
  
          if (rate) {
            this.updatePrices(rate);
          } else {
            console.error('Rate not available for this pair');
          }
        } catch (error) {
          console.error('Error fetching FX rates:', error);
        }
      },
      updatePrices(rate) {
        // Save the current prices as the previous prices
        this.previousBidPrice = this.bidPrice;
        this.previousAskPrice = this.askPrice;
  
        // For simplicity, let's assume bid and ask prices are close to the rate
        this.bidPrice = (rate - 0.001).toFixed(3);
        this.askPrice = (rate + 0.001).toFixed(3);
  
        // Determine if the prices are up or down
        if (this.previousBidPrice !== null) {
          this.bidUp = parseFloat(this.bidPrice) >= parseFloat(this.previousBidPrice);
        }
        if (this.previousAskPrice !== null) {
          this.askUp = parseFloat(this.askPrice) >= parseFloat(this.previousAskPrice);
        }
      },
    },
    watch: {
      pair: {
        handler() {
          this.fetchPrices();
        },
        immediate: true,
      },
    },
  };
  </script>
  
  <style scoped>
  .fx-display {
    padding: 20px;
    background-color: #2c3e50;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    color: #fff;
    width: 100%;
    box-sizing: border-box;
    text-align: center;
  }
  
  h2 {
    font-size: 1.5rem;
    margin-bottom: 20px;
    color: #f5f5f5;
  }
  
  .prices-container {
    display: flex;
    gap: 20px;
    justify-content: center;
  }
  
  .price-box {
    background-color: #3a4a5c;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    align-items: center;
    min-width: 100px;
  }
  
  h3 {
    font-size: 1.2rem;
    margin-bottom: 10px;
    color: #9ec1e6;
  }
  
  p {
    font-size: 1.8rem;
    margin: 0;
    font-weight: bold;
    color: #f5f5f5;
    transition: color 0.3s ease;
  }
  
  .price-up {
    color: #4caf50; /* Green for price increase */
  }
  
  .price-down {
    color: #f44336; /* Red for price decrease */
  }
  </style>
  