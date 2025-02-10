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

COPY entrypoint.sh /usr/local/bin/entrypoint.sh
RUN chmod +x /usr/local/bin/entrypoint.sh

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]

EXPOSE 3000

# FROM node:20

# RUN npm install -g serve

# COPY --from=reactbuild /app/build /app/build

# COPY entrypoint.sh /usr/local/bin/entrypoint.sh
# RUN chmod +x /usr/local/bin/entrypoint.sh

# ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]

# EXPOSE 3000

# CMD ["serve", "-s", "build", "-l", "3000"]