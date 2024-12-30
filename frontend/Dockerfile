FROM node:20 AS reactbuild

WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .

RUN npm run build

# Serve React App with Nginx
FROM nginx:1.21.0-alpine

COPY --from=reactbuild /app/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 3000

CMD ["nginx", "-g", "daemon off;"]
