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
      ogHost: "smidle.smartsquare.de",
    },
  },
  css: ["typeface-roboto/index.css"],
  plugins: [],
  components: true,
  buildModules: ["@nuxtjs/eslint-module", "@nuxtjs/vuetify"],
  modules: ["@nuxtjs/axios"],
  axios: {
    proxy: true,
  },
  loading: { color: "#26A69A" },
  loadingIndicator: {
    name: "pulse",
    color: "#26A69A",
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
          primary: "#26A69A",
          secondary: "#C2185B",
          accent: colors.grey.lighten1,
        },
      },
    },
    icons: {
      iconfont: "mdiSvg",
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
