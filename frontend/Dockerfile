FROM node:21-alpine

RUN apk add --no-cache busybox-extras


WORKDIR /app

COPY package.json package-lock.json ./
RUN npm install

COPY . .
RUN npm run build

EXPOSE  3000

CMD ["npm", "start"]
