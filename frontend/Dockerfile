FROM node:current-alpine AS build

WORKDIR /workspace

RUN apk add --update --no-cache build-base \
                                libtool \
                                autoconf \
                                automake \
                                zlib-dev \
                                pkgconfig \
                                nasm \
                                python

COPY package.json yarn.lock ./
RUN yarn --frozen-lockfile

COPY . .
RUN yarn build -c prod-nuxt.config.js

ENV NUXT_HOST 0.0.0.0

EXPOSE 3000

CMD [ "yarn", "start" ]
