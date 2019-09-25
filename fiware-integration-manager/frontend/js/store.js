const globalStore = {
    state: {
        cameras: [],
        selectedCamera: null
    },
    mutations: {
        changeCameras(state, value) {
            state.cameras.splice(0, state.cameras.length, ...value);

            // Find the camera.
            let selectedCamera = status.selectedCamera;
            let res = state.cameras.find((it) => it.cameraUuid === selectedCamera);

            if (res === null) {
                state.selectedCamera = null;
            }
        },
        changeSelectedCamera(state, value) {
            state.selectedCamera = value;
        }
    }
};
