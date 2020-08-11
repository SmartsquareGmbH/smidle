<template>
  <v-container class="ma-0 pa-0">
    <v-card>
      <v-card-text class="px-6">
        <v-select
          v-model="filterLifetime"
          :items="filterLifetimeOptions"
          item-text="text"
          item-value="text"
          return-object
          label="Filter Lifetime of Pull Requests:"
          class="mx-1 mt-4 mb-3"
        />
        <v-data-table
          :headers="headers"
          :items="filteredPullRequests"
          :items-per-page="15"
          :footer-props="{ 'items-per-page-options': filterNumberOptions }"
          :loading="loading"
          loading-text="Loading Data"
          no-data-text="No Data"
        >
          <template v-slot:item="{ item }">
            <tr style="cursor: pointer;" @click="openURL(item.url)">
              <td>{{ item.title }}</td>
              <td>{{ item.url }}</td>
              <td>{{ item.lifetimeReadable }}</td>
              <td>{{ item.merged }}</td>
            </tr>
          </template>
        </v-data-table>
      </v-card-text>
    </v-card>
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
      loading: true,
      headers: [
        { text: "Pull Request", value: "title" },
        { text: "URL", value: "url" },
        { text: "Life Time", value: "lifetimeMinutes" },
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
    const response = await this.$axios.$get("/api/pull-request")

    for (const i in response) {
      this.pullRequests.push({
        title: response[i].title,
        url: response[i].url,
        lifetimeInMinutes: response[i].lifetimeMinutes,
        lifetimeReadable: humanreadableLifetime(response[i].lifetimeMinutes, true),
        merged: this.isMerged(response[i].mergedAt),
      })
    }

    this.loading = false
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

<style>
.v-text-field .v-label--active {
  font-size: 18px;
  color: #757575;
}
.v-select__slot {
  padding-top: 6px;
}
.v-select__selection--comma {
  padding-left: 1px;
  font-size: 14px;
  color: #757575 !important;
}
</style>
