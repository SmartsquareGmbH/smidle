<template>
  <v-card style="background-color: #26a69a; color: white;">
    <v-card-title>
      Average lifetime of a Pull Request
    </v-card-title>
    <v-card-text style="font-size: 16px; color: #eeeeee;">
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
    this.averageLiftetimeInMinutes = await this.$axios.$get("/api/pull-request/lifetime")
  },
}
</script>
