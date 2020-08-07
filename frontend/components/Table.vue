<template>
  <v-container class="ma-0 pa-0">
    <v-row>
      <v-col cols="2">
        <v-select
          v-model="filterLifetime"
          :items="filterLifetimeOptions"
          item-text="text"
          item-value="text"
          return-object
          label="Lifetime"
        />
      </v-col>
    </v-row>
    <v-data-table
      :headers="headers"
      :items="filteredPullRequests"
      :items-per-page="15"
      :footer-props="{ 'items-per-page-options': filterNumberOptions }"
    >
      <template v-slot:body="{ items }">
        <tbody>
          <tr v-for="item in items" :key="item.id" style="cursor: pointer;" @click="openURL(item.url)">
            <td>{{ item.title }}</td>
            <td>{{ item.url }}</td>
            <td>{{ item.lifetime }}</td>
            <td>{{ item.merged }}</td>
          </tr>
        </tbody>
      </template>
    </v-data-table>
  </v-container>
</template>

<script>
import humanreadableLifetime from "./lifetimeCalculator"

export default {
  data() {
    const filterLifetimeOptions = [
      { text: "<= 12 hours", condition: (pullRequest) => pullRequest.lifetimeInMinutes <= 720 },
      { text: ">= 12 hours", condition: (pullRequest) => pullRequest.lifetimeInMinutes >= 720 },
      { text: ">= 1 days", condition: (pullRequest) => pullRequest.lifetimeInMinutes >= 1440 },
      { text: ">= 2 days", condition: (pullRequest) => pullRequest.lifetimeInMinutes >= 2880 },
      { text: ">= 3 days", condition: (pullRequest) => pullRequest.lifetimeInMinutes >= 4320 },
      { text: "All", condition: () => true },
    ]

    return {
      headers: [
        { text: "Pull Request", value: "title" },
        { text: "URL", value: "url" },
        { text: "Life Time", value: "lifetime" },
        { text: "Merged", value: "merged" },
      ],
      pullRequests: [],
      filterNumberOptions: [5, 15, 25, 50, 100],
      filterLifetimeOptions,
      filterLifetime: filterLifetimeOptions[filterLifetimeOptions.length - 1],
    }
  },
  computed: {
    filteredPullRequests() {
      return this.pullRequests.filter(this.filterLifetime.condition)
    },
  },
  async mounted() {
    this.pullRequests = await this.$axios.$get("/api/pull-request").then((response) => {
      const returnObject = []

      for (const i in response) {
        returnObject.push({
          title: response[i].title,
          url: response[i].url,
          lifetimeInMinutes: response[i].lifetime,
          lifetime: humanreadableLifetime(response[i].lifetime),
          merged: this.isMerged(response[i].mergedAt),
        })
      }

      return returnObject
    })
  },
  methods: {
    isMerged(merged) {
      if (merged === null) {
        return "Not merged"
      } else {
        return "Merged"
      }
    },
    openURL(url) {
      window.open(url, "_blank")
    },
  },
}
</script>
