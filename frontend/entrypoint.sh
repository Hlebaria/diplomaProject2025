#!/bin/sh

echo "Backend URL: $REACT_APP_BACKEND_URL"

envsubst "$REACT_APP_BACKEND_URL" < /etc/nginx/nginx.conf > /tmp/nginx.conf
mv /tmp/nginx.conf /etc/nginx/nginx.conf
nginx -g 'daemon off;'
