FROM node:23-slim AS build

WORKDIR /app

RUN npm install -g @angular/cli

COPY . .

RUN npm install

RUN npm run build --prod

EXPOSE 4200

CMD ["ng", "serve", "--host", "0.0.0.0"]