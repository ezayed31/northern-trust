<template>
  <div class="pair-selector">
    <label for="currencyPair">Select Currency Pair:</label>
    <select v-model="selected" @change="pairSelected">
      <option v-for="pair in pairs" :key="pair" :value="pair">{{ pair }}</option>
    </select>
  </div>
</template>

<script>
export default {
  props: ['pairs'],
  data() {
    return {
      selected: this.pairs[0]
    };
  },
  methods: {
    pairSelected() {
      this.$emit('pairSelected', this.selected);
    }
  }
};
</script>
