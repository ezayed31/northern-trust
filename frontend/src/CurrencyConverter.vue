<template>
    <div class="converter-container">
      <h2>Currency Converter</h2>
      <div class="conversion-details">
        <p>{{ baseCurrency }} / {{ targetCurrency }}</p>
      </div>
      <div class="conversion-inputs">
        <div class="input-group">
          <input
            type="number"
            v-model="amount"
            min="0"
            class="amount-input"
            @input="convertCurrency"
            placeholder="Enter amount"
          />
          <span class="currency-label1">{{ baseCurrency }}</span>
        </div>
        <div class="converted-amount">
          <span>{{ convertedAmount }}</span>
          <span class="currency-label2">{{ targetCurrency }}</span>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    props: ['baseCurrency', 'targetCurrency'],
    data() {
      return {
        amount: 1,
        convertedAmount: 0,
        apiKey: 'YOUR_EXCHANGE_RATE_API_KEY', // Replace with your actual API key
      };
    },
    watch: {
      baseCurrency: 'convertCurrency',
      targetCurrency: 'convertCurrency',
    },
    mounted() {
      this.convertCurrency();
    },
    methods: {
      async convertCurrency() {
        if (!this.baseCurrency || !this.targetCurrency) return;
  
        try {
          // Fetch the conversion rate from an API
          const response = await axios.get(
            `https://api.exchangerate-api.com/v4/latest/${this.baseCurrency}`
          );
          const rate = response.data.rates[this.targetCurrency];
  
          if (rate) {
            this.convertedAmount = (this.amount * rate).toFixed(2);
          } else {
            this.convertedAmount = 'N/A';
            console.error('Conversion rate not available');
          }
        } catch (error) {
          console.error('Error fetching conversion rate:', error);
        }
      },
    },
  };
  </script>

<style scoped>
.converter-container {
  padding: 10px;
  background-color: #3E6F50; /* Softer forest green for the main background */
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  color: #F5FFFA; /* Mint cream for text */
  justify-content: center;
  align-content: center;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
}

h2 {
  font-size: 1.5rem;
  margin-bottom: 20px;
  text-align: center;
  color: #f5f5f5; /* Keeping it slightly off-white for the heading */
}

.conversion-details {
  margin-bottom: 20px;
  font-size: 1.25rem;
  color: #F5FFFA; /* Mint cream, a very light and soothing color for text */
  text-align: center;
}

.conversion-inputs {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.input-group {
  display: flex;
  align-items: center;
  gap: 10px;
}

.amount-input {
  flex: 1;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #3E6F50; /* A slightly darker, complementary shade of green for borders */
  background-color: #F5FFFA; /* Mint cream for the input background, providing a neutral and soft backdrop */
  color: #4F7942; /* Echoing the main forest green for text, for readability and design coherence */
  outline: none;
  font-size: 1rem;
  width: 100%;
}

.converted-amount {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 1.2rem;
  color: #4F7942; /* Using the main forest green here for consistency */
  background-color: #FFFDD0; /* Mint cream background for output area */
  padding: 10px;
  border-radius: 5px;
}

.currency-label1 {
  font-size: 1rem;
  color: #f5f5f5; /* A complementary darker green for labels to maintain aesthetic balance */
}

.currency-label2 {
  font-size: 1rem;
  color: #3E6F50; /* A complementary darker green for labels to maintain aesthetic balance */
}
</style>

  