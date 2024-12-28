# FROM node:20 AS reactbuild
FROM node:20

WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .

RUN npm run build

# # Stage 2: Serve React App with Nginx
# FROM nginx:1.21.0-alpine

# COPY --from=reactbuild /app/build /usr/share/nginx/html
# COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 3000
CMD ["npm", "start"]
# CMD ["nginx", "-g", "daemon off;"]
