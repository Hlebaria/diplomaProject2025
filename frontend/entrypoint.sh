#!/bin/sh

echo "Backend URL: $REACT_APP_BACKEND_URL"

envsubst '${REACT_APP_BACKEND_URL} ${REACT_APP_KEYCLOAK_URL} ${REACT_APP_KEYCLOAK_REALM} ${REACT_APP_KEYCLOAK_CLIENT_ID}' < /usr/share/nginx/html/variableInject.js > /tmp/variableInject.js

mv /tmp/variableInject.js /usr/share/nginx/html/variableInject.js

nginx -g 'daemon off;'
