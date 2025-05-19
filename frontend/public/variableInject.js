
function resolveEnvVariable(envVar) {
    return envVar.includes("${REACT_APP_") ? undefined : envVar;
}

window.REACT_APP_BACKEND_URL = resolveEnvVariable("${REACT_APP_BACKEND_URL}");
window.REACT_APP_KEYCLOAK_URL = resolveEnvVariable("${REACT_APP_KEYCLOAK_URL}");
window.REACT_APP_KEYCLOAK_REALM = resolveEnvVariable("${REACT_APP_KEYCLOAK_REALM}");
window.REACT_APP_KEYCLOAK_CLIENT_ID = resolveEnvVariable("${REACT_APP_KEYCLOAK_CLIENT_ID}");

if(window.REACT_APP_KEYCLOAK_URL){
    window.REACT_APP_KEYCLOAK_URL = "https://" + window.REACT_APP_KEYCLOAK_URL;
}

console.log("Backend URL is: " + window.REACT_APP_BACKEND_URL);
console.log("keycloak things: " + window.REACT_APP_KEYCLOAK_URL + window.REACT_APP_KEYCLOAK_REALM + window.REACT_APP_KEYCLOAK_CLIENT_ID);
