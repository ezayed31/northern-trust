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
      setInterval(this.fetchPrices, 1000); //updates every one second 
    },
    methods: {
      fetchPrices() {
        // Save the current prices as the previous prices before fetching new ones
        this.previousBidPrice = this.bidPrice;
        this.previousAskPrice = this.askPrice;
  
        // Replace this with your actual API call to fetch live bid/ask prices
        const newBidPrice = (149.500 + Math.random() * 0.05).toFixed(3);
        const newAskPrice = (149.510 + Math.random() * 0.05).toFixed(3);
  
        // Update the bid and ask prices
        this.bidPrice = newBidPrice;
        this.askPrice = newAskPrice;
  
        // Determine if the new prices are higher or lower than the previous prices
        if (this.previousBidPrice !== null) {
          this.bidUp = parseFloat(newBidPrice) >= parseFloat(this.previousBidPrice);
        }
        if (this.previousAskPrice !== null) {
          this.askUp = parseFloat(newAskPrice) >= parseFloat(this.previousAskPrice);
        }
      },
    },
  };
  </script>
  
  <style scoped>
  .live-fx-display {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 100%;
    padding: 20px;
    background-color: #2c3e50;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    color: #fff;
    text-align: center;
    box-sizing: border-box;
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
    width: 100%;
  }
  
  .price-box {
    background-color: #3a4a5c;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
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
    transition: color 0.3s ease; /* Smooth transition when the color changes */
  }
  
  .price-up {
    color: #4caf50; /* Green for price increase */
  }
  
  .price-down {
    color: #f44336; /* Red for price decrease */
  }
  </style>
  