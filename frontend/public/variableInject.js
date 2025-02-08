
function resolveEnvVariable(envVar) {
    return envVar.includes("${REACT_APP_") ? undefined : envVar;
}

window.REACT_APP_BACKEND_URL = resolveEnvVariable("${REACT_APP_BACKEND_URL}");
window.REACT_APP_KEYCLOAK_URL = resolveEnvVariable("${REACT_APP_KEYCLOAK_URL}");
window.REACT_APP_KEYCLOAK_REALM = resolveEnvVariable("${REACT_APP_KEYCLOAK_REALM}");
window.REACT_APP_KEYCLOAK_CLIENT_ID = resolveEnvVariable("${REACT_APP_KEYCLOAK_CLIENT_ID}");


console.log("Backend URL is: " + window.REACT_APP_BACKEND_URL);
console.log("keycloak things: " + window.REACT_APP_KEYCLOAK_URL + window.REACT_APP_KEYCLOAK_REALM + window.REACT_APP_KEYCLOAK_CLIENT_ID);

// window.REACT_APP_BACKEND_URL = "${REACT_APP_BACKEND_URL}";
// if (window.REACT_APP_BACKEND_URL = "${REACT_APP_" + "BACKEND_URL}"){
// window.REACT_APP_BACKEND_URL = undefined;
// }
// window.REACT_APP_KEYCLOAK_URL = "${REACT_APP_KEYCLOAK_URL}";
// if (window.REACT_APP_KEYCLOAK_URL = "${REACT_APP_" + "KEYCLOAK_URL}"){
// window.REACT_APP_KEYCLOAK_URL = undefined;
// }
// window.REACT_APP_KEYCLOAK_REALM = "${REACT_APP_KEYCLOAK_REALM}";
// if (window.REACT_APP_KEYCLOAK_REALM = "${REACT_APP_" + "KEYCLOAK_REALM}"){
// window.REACT_APP_KEYCLOAK_REALM = undefined;
// }
// window.REACT_APP_KEYCLOAK_CLIENT_ID = "${REACT_APP_KEYCLOAK_CLIENT_ID}";
// if (window.REACT_APP_KEYCLOAK_CLIENT_ID = "${REACT_APP_" + "KEYCLOAK_CLIENT_ID}"){
// window.REACT_APP_KEYCLOAK_CLIENT_ID = undefined;
// }