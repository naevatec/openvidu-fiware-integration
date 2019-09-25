const globalStore = {
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
};
