#!/bin/sh

echo "Backend URL: $REACT_APP_BACKEND_URL"

envsubst '${REACT_APP_BACKEND_URL}' < /app/build/index.html > /tmp/index.html
mv /tmp/index.html /app/build/index.html

serve -s /app/build -l 3000
