<template>
    <div class="currency-pair-selector">
      <div class="currency-input">
        <select v-model="selectedCurrency" class="currency-dropdown">
          <option v-for="currency in currencies" :key="currency.code" :value="currency.code">
            {{ currency.name }}
          </option>
        </select>
      </div>
      <div class="currency-input">
        <select v-model="targetCurrency" class="currency-dropdown">
          <option v-for="currency in currencies" :key="currency.code" :value="currency.code">
            {{ currency.name }}
          </option>
        </select>
      </div>
      <button class="fetch-button" @click="fetchData">Fetch Data</button>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        selectedCurrency: 'USD',
        targetCurrency: 'EUR',
        currencies: [
          { code: 'USD', name: 'United States Dollar' },
          { code: 'EUR', name: 'Euro' },
          { code: 'GBP', name: 'British Pound' },
          { code: 'JPY', name: 'Japanese Yen' },
          { code: 'AUD', name: 'Australian Dollar' },
        ],
      };
    },
    methods: {
      fetchData() {
        // Placeholder function for fetching data
        console.log('Fetching data for:', this.selectedCurrency, 'to', this.targetCurrency);
        // Add your data fetching logic here (e.g., API call)
      },
    },
    watch: {
      selectedCurrency() {
        this.$emit('currencyPairChanged', { base: this.selectedCurrency, target: this.targetCurrency });
      },
      targetCurrency() {
        this.$emit('currencyPairChanged', { base: this.selectedCurrency, target: this.targetCurrency });
      },
    },
  };
  </script>
  
  <style scoped>
  .currency-pair-selector {
    display: flex;
    flex-direction: row;
    gap: 20px;
    padding: 20px;
    background-color: #2c3e50;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    width: 100%;
    box-sizing: border-box;
    justify-content: center;
    align-items: center; /* Align all items vertically */
  }
  
  .currency-input {
    background-color: #3a4a5c;
    padding: 10px;
    border-radius: 8px;
    border: 1px solid #444;
    box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1);
    display: flex;
    align-items: center;
  }
  
  .currency-dropdown {
    background-color: transparent;
    border: none;
    color: #fff;
    font-size: 1rem;
    padding: 5px;
    outline: none;
    cursor: pointer;
    border-radius: 5px;
    min-width: 200px; /* Set a minimum width to keep the dropdown wide enough */
  }
  
  .currency-dropdown option {
    color: black; /* Set the text color of dropdown options */
  }
  
  /* Styling for the fetch button */
  .fetch-button {
    background-color: transparent;
    border: 2px solid #9ec1e6;
    color: #9ec1e6;
    padding: 10px 20px;
    border-radius: 8px;
    font-size: 1rem;
    cursor: pointer;
    transition: background-color 0.3s, color 0.3s;
  }
  
  .fetch-button:hover {
    background-color: #4caf50;
    color: #2c3e50;
  }
  
  .fetch-button:focus {
    outline: none;
    box-shadow: 0 0 5px #9ec1e6;
  }
  </style>
  