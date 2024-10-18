<template>
    <div class="news-container">
      <h2>Forex News</h2>
      <div v-if="news.length">
        <ul class="news-list">
          <li v-for="(article, index) in news" :key="index" class="news-item">
            <a :href="article.url" target="_blank" class="news-headline">
              {{ article.title }}
            </a>
          </li>
        </ul>
      </div>
      <div v-else class="loading">
        Loading news...
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        news: [],
        apiKey: '436787e80fee4814ba414c335fd74e9f',
      };
    },
    mounted() {
      this.fetchNews();
      setInterval(this.fetchNews, 60000); // Refresh every minute
    },
    methods: {
      async fetchNews() {
        try {
          // Replace this with your actual news API endpoint and parameters
          const response = await axios.get(
            `https://newsapi.org/v2/everything?q=forex&language=en&pageSize=5&apiKey=${this.apiKey}`
          );
          this.news = response.data.articles;
        } catch (error) {
          console.error('Error fetching news:', error);
        }
      },
    },
  };
  </script>

<style scoped>
.news-container {
  padding: 20px;
  background-color: #3E6F50; /* Softer forest green for the main background */
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  color: #F5FFFA; /* Mint cream for text, ensuring readability */
  width: 100%;
  box-sizing: border-box;
  overflow-y: auto;
  max-height: 400px; /* Limit the height for better scrollability */
}

h2 {
  font-size: 1.5rem;
  margin-bottom: 20px;
  color: #F5FFFA; /* Mint cream for headings, adding a touch of lightness */
}

.news-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.news-item {
  margin-bottom: 15px;
}

.news-headline {
  font-size: 1rem;
  color: #FFFFFF; /* White for headlines, maintaining high contrast */
  text-decoration: none;
  transition: color 0.3s;
  display: block;
}

.news-headline:hover {
  color: #76b041; /* A lighter, more pleasant green for hover effects */
}

.loading {
  font-size: 1rem;
  text-align: center;
  color: #ddd; /* Light grey for loading messages, subtle and non-intrusive */
}
</style>

  