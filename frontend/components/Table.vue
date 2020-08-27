<template>
  <v-container class="ma-0 pa-0">
    <v-card>
      <v-card-text class="px-6">
        <v-row>
          <v-col cols="4">
            <v-select
              v-if="showFilter"
              v-model="filterLifetime"
              :items="filterLifetimeOptions"
              item-text="text"
              item-value="text"
              return-object
              label="Lifetime"
              solo
              clearable
            />
          </v-col>

          <v-col cols="8">
            <v-select
              v-model="selectedRepositories"
              :items="pullRequests"
              item-text="repository"
              item-value="repository"
              chips
              label="Repositories"
              multiple
              solo
              clearable
            ></v-select>
          </v-col>
        </v-row>

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
            <tr style="cursor: pointer;" @click="window.open(item.url, '_blank')">
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
  data: () => ({
    headers: [
      { text: "Pull Request", value: "title" },
      { text: "URL", value: "url" },
      { text: "Life Time", value: "lifetimeMinutes" },
      { text: "Merged", value: "merged" },
    ],
    filterLifetimeOptions: [
      { text: "<= 12 hours", condition: (pullRequest) => pullRequest.lifetimeMinutes <= 720 },
      { text: ">= 12 hours", condition: (pullRequest) => pullRequest.lifetimeMinutes >= 720 },
      { text: ">= 1 days", condition: (pullRequest) => pullRequest.lifetimeMinutes >= 1440 },
      { text: ">= 2 days", condition: (pullRequest) => pullRequest.lifetimeMinutes >= 2880 },
      { text: ">= 3 days", condition: (pullRequest) => pullRequest.lifetimeMinutes >= 4320 },
    ],
    loading: true,
    showFilter: true,
    pullRequests: [],
    selectedRepositories: [],
    filterNumberOptions: [5, 15, 25, 50, 100],
    filterLifetime: null,
  }),
  computed: {
    lifetimeFilterEnabled() {
      return !!this.filterLifetime
    },
    repositoryFilterEnabled() {
      return this.selectedRepositories.length > 0
    },
    filteredPullRequests() {
      if (this.repositoryFilterEnabled && this.lifetimeFilterEnabled) {
        return this.pullRequests
          .filter(this.filterLifetime.condition)
          .filter((pr) => this.selectedRepositories.includes(pr.repository))
      } else if (this.repositoryFilterEnabled) {
        return this.pullRequests.filter((pr) => this.selectedRepositories.includes(pr.repository))
      } else if (this.lifetimeFilterEnabled) {
        return this.pullRequests.filter(this.filterLifetime.condition)
      } else {
        return this.pullRequests
      }
    },
  },
  async mounted() {
    const response = await this.$axios.$get("/api/pull-request").catch(() => {
      this.pullRequests = []
      this.showFilter = false
    })

    for (const pr of response) {
      const additionalProperties = {
        repository: pr.url.substring(pr.url.indexOf("github.com"), pr.url.length).split("/")[2],
        lifetimeReadable: humanreadableLifetime(pr.lifetimeMinutes, true),
        merged: pr.mergedAt === null ? "Not merged" : "Merged",
      }

      this.pullRequests.push(Object.assign(pr, additionalProperties))
    }

    this.loading = false
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
