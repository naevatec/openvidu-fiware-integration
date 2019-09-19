// Constants
var apiPath = window.location + "api/v1";

$(document).ready(function () {
    Vue.use(httpVueLoader);
    Vue.use(Vuex);

    // Keep reference inside vue.
    Vue.prototype.$console = console;

    const store = new Vuex.Store({
        state: {
            actualView: "main",
            data: null
        },
        mutations: {
            changeView(state, value) {
                state.actualView = value;
            },
            changeData(state, value) {
                state.data = value;
            }
        }
    });

    const app = new Vue({
        el: "#app",
        store,
        components: {
            "view-app": "url:views/app.vue"
        }
    });
});

// HTTP Methods
async function doPost(url, data) {
    try {
        let response = await $.ajax({
            url: url,
            type: "POST",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        });

        return {
            isOk: true,
            response
        };
    } catch (e) {
        return {
            isOk: false,
            error: e.responseJSON
        };
    }
}

async function doDelete(url, data) {
    try {
        let response = await $.ajax({
            url: url,
            type: "DELETE",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        });

        return {
            isOk: true,
            response
        };
    } catch (e) {
        return {
            isOk: false,
            error: e.responseJSON
        };
    }
}
