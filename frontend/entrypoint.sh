#!/bin/sh

echo "Backend URL: $REACT_APP_BACKEND_URL"

envsubst '${REACT_APP_BACKEND_URL}' < /usr/share/nginx/html/index.html > /tmp/index.html
mv /tmp/index.html /usr/share/nginx/html/index.html

nginx -g 'daemon off;'
