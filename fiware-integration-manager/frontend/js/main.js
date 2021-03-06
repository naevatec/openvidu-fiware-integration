// Constants
var orionUrl = "<orion path is not loaded>";
var apiUrl = "<api path is not loaded>";

// HTTP Methods
let caller = async function doDelete(url, method, data) {
    Quasar.LoadingBar.start();

    return new Promise(function (resolve) {
        let request = new XMLHttpRequest();
        request.open(method, url, true);

        if (data) {
            request.setRequestHeader("Content-type", "application/json");
            request.setRequestHeader("Access-Control-Allow-Origin", "*");
        }

        request.onload = function () {
            Quasar.LoadingBar.stop();

            let response;
            try {
                response = JSON.parse(request.response);
            } catch (e) {
                response = request.response;
            }

            resolve({
                isOk: true,
                response
            });
        };
        request.onerror = function (e) {
            Quasar.LoadingBar.stop();

            resolve({
                isOk: false,
                error: e.responseJSON
            });
        };

        if (data) {
            request.send(JSON.stringify(data));
        } else {
            request.send();
        }
    });
};

async function doGet(url) {
    return caller(url, "GET");
}

async function doPost(url, data) {
    return caller(url, "POST", data);
}

async function doDelete(url) {
    return caller(url, "DELETE");
}

// Main actions.
(async function () {
    // Asks for the config JSON
    let config = await doGet(window.location + "config.json");

    if (!config.isOk) {
        alert("Error loading the config file");
        return;
    }

    orionUrl = config.response.orionUrl;

    Vue.use(httpVueLoader);
    Vue.use(Vuex);

    Quasar.iconSet.set(Quasar.iconSet.fontawesomeV5);

    // Keep reference inside vue.
    Vue.prototype.$console = console;

    const store = new Vuex.Store(globalStore);
    const app = new Vue({
        el: "#app",
        store,
        components: {
            "view-app": "url:views/app.vue"
        }
    });
})();
