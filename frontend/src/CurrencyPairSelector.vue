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

    <!-- Display fetched data -->
<!--    <div v-if="fxData">-->
<!--      <h3>Live Rates: {{ fxData.live }}</h3>-->
<!--      <h3>Pair Rate: {{ fxData.pair }}</h3>-->
<!--      <h3>Time Series: {{ fxData.timeSeries }}</h3>-->
<!--    </div>-->
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      selectedCurrency: 'USD',
      targetCurrency: 'EUR',
      fxData: null,
      currencies: [
        { code: 'USD', name: 'United States Dollar' },
        { code: 'EUR', name: 'Euro' },
        { code: 'GBP', name: 'British Pound' },
        { code: 'JPY', name: 'Japanese Yen' },
        { code: 'AUD', name: 'Australian Dollar' },
        { code: 'AED', name: 'United Arab Emirates Dirham' },
        { code: 'AFN', name: 'Afghan Afghani' },
        { code: 'ALL', name: 'Albanian Lek' },
        { code: 'AMD', name: 'Armenian Dram' },
        { code: 'ANG', name: 'Netherlands Antillean Guilder' },
        { code: 'AOA', name: 'Angolan Kwanza' },
        { code: 'ARS', name: 'Argentine Peso' },
        { code: 'AWG', name: 'Aruban Florin' },
        { code: 'AZN', name: 'Azerbaijani Manat' },
        { code: 'BAM', name: 'Bosnia-Herzegovina Convertible Mark' },
        { code: 'BBD', name: 'Barbadian Dollar' },
        { code: 'BDT', name: 'Bangladeshi Taka' },
        { code: 'BGN', name: 'Bulgarian Lev' },
        { code: 'BHD', name: 'Bahraini Dinar' },
        { code: 'BIF', name: 'Burundian Franc' },
        { code: 'BMD', name: 'Bermudian Dollar' },
        { code: 'BND', name: 'Brunei Dollar' },
        { code: 'BOB', name: 'Bolivian Boliviano' },
        { code: 'BRL', name: 'Brazilian Real' },
        { code: 'BSD', name: 'Bahamian Dollar' },
        { code: 'BTN', name: 'Bhutanese Ngultrum' },
        { code: 'BWP', name: 'Botswana Pula' },
        { code: 'BYN', name: 'Belarusian Ruble' },
        { code: 'BZD', name: 'Belize Dollar' },
        { code: 'CAD', name: 'Canadian Dollar' },
        { code: 'CDF', name: 'Congolese Franc' },
        { code: 'CHF', name: 'Swiss Franc' },
        { code: 'CLP', name: 'Chilean Peso' },
        { code: 'CNY', name: 'Chinese Yuan' },
        { code: 'COP', name: 'Colombian Peso' },
        { code: 'CRC', name: 'Costa Rican Colón' },
        { code: 'CUP', name: 'Cuban Peso' },
        { code: 'CVE', name: 'Cape Verdean Escudo' },
        { code: 'CZK', name: 'Czech Koruna' },
        { code: 'DJF', name: 'Djiboutian Franc' },
        { code: 'DKK', name: 'Danish Krone' },
        { code: 'DOP', name: 'Dominican Peso' },
        { code: 'DZD', name: 'Algerian Dinar' },
        { code: 'EGP', name: 'Egyptian Pound' },
        { code: 'ERN', name: 'Eritrean Nakfa' },
        { code: 'ETB', name: 'Ethiopian Birr' },
        { code: 'FJD', name: 'Fijian Dollar' },
        { code: 'FKP', name: 'Falkland Islands Pound' },
        { code: 'FOK', name: 'Faroese Króna' },
        { code: 'GEL', name: 'Georgian Lari' },
        { code: 'GGP', name: 'Guernsey Pound' },
        { code: 'GHS', name: 'Ghanaian Cedi' },
        { code: 'GIP', name: 'Gibraltar Pound' },
        { code: 'GMD', name: 'Gambian Dalasi' },
        { code: 'GNF', name: 'Guinean Franc' },
        { code: 'GTQ', name: 'Guatemalan Quetzal' },
        { code: 'GYD', name: 'Guyanese Dollar' },
        { code: 'HKD', name: 'Hong Kong Dollar' },
        { code: 'HNL', name: 'Honduran Lempira' },
        { code: 'HRK', name: 'Croatian Kuna' },
        { code: 'HTG', name: 'Haitian Gourde' },
        { code: 'HUF', name: 'Hungarian Forint' },
        { code: 'IDR', name: 'Indonesian Rupiah' },
        { code: 'ILS', name: 'Israeli New Shekel' },
        { code: 'IMP', name: 'Isle of Man Pound' },
        { code: 'INR', name: 'Indian Rupee' },
        { code: 'IQD', name: 'Iraqi Dinar' },
        { code: 'IRR', name: 'Iranian Rial' },
        { code: 'ISK', name: 'Icelandic Króna' },
        { code: 'JEP', name: 'Jersey Pound' },
        { code: 'JMD', name: 'Jamaican Dollar' },
        { code: 'JOD', name: 'Jordanian Dinar' },
        { code: 'KES', name: 'Kenyan Shilling' },
        { code: 'KGS', name: 'Kyrgyzstani Som' },
        { code: 'KHR', name: 'Cambodian Riel' },
        { code: 'KID', name: 'Kiribati Dollar' },
        { code: 'KMF', name: 'Comorian Franc' },
        { code: 'KRW', name: 'South Korean Won' },
        { code: 'KWD', name: 'Kuwaiti Dinar' },
        { code: 'KYD', name: 'Cayman Islands Dollar' },
        { code: 'KZT', name: 'Kazakhstani Tenge' },
        { code: 'LAK', name: 'Lao Kip' },
        { code: 'LBP', name: 'Lebanese Pound' },
        { code: 'LKR', name: 'Sri Lankan Rupee' },
        { code: 'LRD', name: 'Liberian Dollar' },
        { code: 'LSL', name: 'Lesotho Loti' },
        { code: 'LYD', name: 'Libyan Dinar' },
        { code: 'MAD', name: 'Moroccan Dirham' },
        { code: 'MDL', name: 'Moldovan Leu' },
        { code: 'MGA', name: 'Malagasy Ariary' },
        { code: 'MKD', name: 'Macedonian Denar' },
        { code: 'MMK', name: 'Burmese Kyat' },
        { code: 'MNT', name: 'Mongolian Tugrik' },
        { code: 'MOP', name: 'Macanese Pataca' },
        { code: 'MRU', name: 'Mauritanian Ouguiya' },
        { code: 'MUR', name: 'Mauritian Rupee' },
        { code: 'MVR', name: 'Maldivian Rufiyaa' },
        { code: 'MWK', name: 'Malawian Kwacha' },
        { code: 'MXN', name: 'Mexican Peso' },
        { code: 'MYR', name: 'Malaysian Ringgit' },
        { code: 'MZN', name: 'Mozambican Metical' },
        { code: 'NAD', name: 'Namibian Dollar' },
        { code: 'NGN', name: 'Nigerian Naira' },
        { code: 'NIO', name: 'Nicaraguan Córdoba' },
        { code: 'NOK', name: 'Norwegian Krone' },
        { code: 'NPR', name: 'Nepalese Rupee' },
        { code: 'NZD', name: 'New Zealand Dollar' },
        { code: 'OMR', name: 'Omani Rial' },
        { code: 'PAB', name: 'Panamanian Balboa' },
        { code: 'PEN', name: 'Peruvian Sol' },
        { code: 'PGK', name: 'Papua New Guinean Kina' },
        { code: 'PHP', name: 'Philippine Peso' },
        { code: 'PKR', name: 'Pakistani Rupee' },
        { code: 'PLN', name: 'Polish Zloty' },
        { code: 'PYG', name: 'Paraguayan Guarani' },
        { code: 'QAR', name: 'Qatari Riyal' },
        { code: 'RON', name: 'Romanian Leu' },
        { code: 'RSD', name: 'Serbian Dinar' },
        { code: 'RUB', name: 'Russian Ruble' },
        { code: 'RWF', name: 'Rwandan Franc' },
        { code: 'SAR', name: 'Saudi Riyal' },
        { code: 'SBD', name: 'Solomon Islands Dollar' },
        { code: 'SCR', name: 'Seychellois Rupee' },
        { code: 'SDG', name: 'Sudanese Pound' },
        { code: 'SEK', name: 'Swedish Krona' },
        { code: 'SGD', name: 'Singapore Dollar' },
        { code: 'SHP', name: 'Saint Helena Pound' },
        { code: 'SLE', name: 'Sierra Leonean Leone' },
        { code: 'SLL', name: 'Sierra Leonean Leone' },
        { code: 'SOS', name: 'Somali Shilling' },
        { code: 'SRD', name: 'Surinamese Dollar' },
        { code: 'SSP', name: 'South Sudanese Pound' },
        { code: 'STN', name: 'São Tomé and Príncipe Dobra' },
        { code: 'SYP', name: 'Syrian Pound' },
        { code: 'SZL', name: 'Eswatini Lilangeni' },
        { code: 'THB', name: 'Thai Baht' },
        { code: 'TJS', name: 'Tajikistani Somoni' },
        { code: 'TMT', name: 'Turkmenistani Manat' },
        { code: 'TND', name: 'Tunisian Dinar' },
        { code: 'TOP', name: 'Tongan Paʻanga' },
        { code: 'TRY', name: 'Turkish Lira' },
        { code: 'TTD', name: 'Trinidad and Tobago Dollar' },
        { code: 'TVD', name: 'Tuvaluan Dollar' },
        { code: 'TWD', name: 'New Taiwan Dollar' },
        { code: 'TZS', name: 'Tanzanian Shilling' },
        { code: 'UAH', name: 'Ukrainian Hryvnia' },
        { code: 'UGX', name: 'Ugandan Shilling' },
        { code: 'UYU', name: 'Uruguayan Peso' },
        { code: 'UZS', name: 'Uzbekistani Som' },
        { code: 'VES', name: 'Venezuelan Bolívar' },
        { code: 'VND', name: 'Vietnamese Dong' },
        { code: 'VUV', name: 'Vanuatu Vatu' },
        { code: 'WST', name: 'Samoan Tala' },
        { code: 'XAF', name: 'Central African CFA Franc' },
        { code: 'XCD', name: 'East Caribbean Dollar' },
        { code: 'XDR', name: 'Special Drawing Rights' },
        { code: 'XOF', name: 'West African CFA Franc' },
        { code: 'XPF', name: 'CFP Franc' },
        { code: 'YER', name: 'Yemeni Rial' },
        { code: 'ZAR', name: 'South African Rand' },
        { code: 'ZMW', name: 'Zambian Kwacha' },
        { code: 'ZWL', name: 'Zimbabwean Dollar' }
      ],
    };
  },
  methods: {
    async fetchData() {
      try {
        // Fetch live rates
        const liveRatesResponse = await axios.get('http://localhost:8080/api/fxrates/live', {
          params: { baseCurrency: this.selectedCurrency },
        });
        const liveRates = liveRatesResponse.data;

        // Fetch pair rate
        const pairRateResponse = await axios.get('http://localhost:8080/api/fxrates/pair', {
          params: {
            baseCurrency: this.selectedCurrency,
            targetCurrency: this.targetCurrency,
          },
        });
        const pairRate = pairRateResponse.data;

        // Fetch time series (FX_DAILY as default)
        const timeSeriesResponse = await axios.get('http://localhost:8080/api/fxrates/timeseries', {
          params: {
            function: 'FX_DAILY',
            fromSymbol: this.selectedCurrency,
            toSymbol: this.targetCurrency,
            outputSize: 'compact', // You can adjust this if needed
            dataType: 'json', // Default data type
          },
        });
        const timeSeries = timeSeriesResponse.data;

        // Update the fxData object with the responses
        this.fxData = {
          live: liveRates,
          pair: pairRate,
          timeSeries: timeSeries,
        };
      } catch (error) {
        console.error('Error fetching FX data:', error);
      }
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
