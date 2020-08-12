<template>
  <v-card class="card">
    <v-card-title class="title">
      Average lifetime of a Pull Request
    </v-card-title>
    <v-card-text class="text">
      {{ averageLiftime }}
    </v-card-text>
  </v-card>
</template>

<script>
import humanreadableLifetime from "./lifetimeCalculator"

export default {
  data() {
    return {
      averageLiftetimeInMinutes: null,
    }
  },
  computed: {
    averageLiftime() {
      if (!this.averageLiftetimeInMinutes) {
        return "No data"
      }

      return humanreadableLifetime(this.averageLiftetimeInMinutes, false)
    },
  },
  async mounted() {
    this.averageLiftetimeInMinutes = await this.$axios
      .$get("/api/pull-request/lifetime")
      .catch(() => (this.averageLiftetimeInMinutes = null))
  },
}
</script>

<style>
.title {
  font-weight: 400 !important;
}
.card {
  background-color: #26a69a !important;
  color: white !important;
}
.text {
  font-size: 16px !important;
  color: #eeeeee !important;
}
</style>
