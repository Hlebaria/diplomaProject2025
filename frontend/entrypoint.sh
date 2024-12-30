
envsubst '$REACT_APP_BACKEND_URL' < /etc/nginx/nginx.conf > /etc/nginx/nginx.conf

nginx -g 'daemon off;'
