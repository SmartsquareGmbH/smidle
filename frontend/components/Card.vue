<template>
  <v-card>
    <v-card-title>
      Average lifetime of a Pull Request
    </v-card-title>
    <v-card-text style="font-size: 16px;">
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
      if (this.averageLiftetimeInMinutes === null) {
        return "No data"
      }

      return humanreadableLifetime(this.averageLiftetimeInMinutes)
    },
  },
  async mounted() {
    this.averageLiftetimeInMinutes = await this.$axios.$get("/api/pull-request/lifetime")
  },
}
</script>
