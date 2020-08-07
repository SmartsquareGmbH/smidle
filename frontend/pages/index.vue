<template>
  <v-container>
    <v-row>
      <v-col cols="11">
        <Card />
      </v-col>
      <v-col cols="11">
        <Table />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Card from "../components/Card"
import Table from "../components/Table"

export default {
  components: {
    Card,
    Table,
  },
}
</script>
