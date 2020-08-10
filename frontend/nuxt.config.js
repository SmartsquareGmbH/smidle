import colors from "vuetify/es5/util/colors"

export default {
  mode: "spa",
  head: {
    title: "Smidle",
    meta: [
      { charset: "utf-8" },
      { name: "viewport", content: "width=device-width, initial-scale=1" },
      {
        hid: "description",
        name: "description",
        content: process.env.npm_package_description || "",
      },
    ],
    link: [{ rel: "icon", type: "image/x-icon", href: "/favicon.ico" }],
  },
  pwa: {
    manifest: {
      theme_color: "#35B0AE",
      name: "Smidle",
      short_name: "Smidle",
    },
    meta: {
      name: "Smidle",
      // ogHost: "smidle.smartsquare.de",
    },
  },
  css: ["typeface-roboto/index.css", "@mdi/font/css/materialdesignicons.css"],
  plugins: [],
  components: true,
  buildModules: ["@nuxtjs/eslint-module", "@nuxtjs/vuetify"],
  modules: ["@nuxtjs/axios"],
  axios: {
    proxy: true,
  },
  loading: { color: "#35B0AE" },
  loadingIndicator: {
    name: "pulse",
    color: "#4DB6AC",
    background: "white",
  },
  layoutTransition: {
    name: "fade-transition",
  },
  pageTransition: {
    name: "slide-x-transition",
  },
  vuetify: {
    theme: {
      themes: {
        light: {
          primary: "#4DB6AC",
          secondary: "#C2185B",
          accent: colors.grey.lighten1,
        },
      },
    },
    defaultAssets: {
      icons: false,
      font: false,
    },
  },
  build: {},
  proxy: {
    "/api": {
      target: `http://localhost:8080`,
      changeOrigin: true, // need?
    },
  },
}
