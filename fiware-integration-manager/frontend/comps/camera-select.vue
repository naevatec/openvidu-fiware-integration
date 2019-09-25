<template>
    <q-card class="main">
        <q-card-section>
            <div class="text-h6 text-center"><i class="fas fa-video"></i> Choose the camera you want to manage
                <i class="fas fa-video"></i></div>
        </q-card-section>
        <q-card-section class="button-container">
            <q-btn :key="key"
                   :color="amISelected(camera.cameraUuid) ? 'white' : 'secondary'"
                   :text-color="amISelected(camera.cameraUuid) ? 'black' : 'white'"
                   v-for="camera, key in cameras"
                   :label="camera.cameraUuid"
                   @click="selectCamera(camera)"></q-btn>
        </q-card-section>
        <q-card-actions class="actions">
            <q-btn color="primary" icon="fas fa-sync" label="Refresh" @click="refresh"></q-btn>
        </q-card-actions>
    </q-card>
</template>

<script>
    module.exports = {
        data() {
            return {
                error: ""
            };
        },
        computed: {
            ...Vuex.mapState(["cameras", "selectedCamera"])
        },
        methods: {
            amISelected(cameraUuid) {
                return this.selectedCamera === null || cameraUuid !== this.selectedCamera.cameraUuid;
            },
            selectCamera(camera) {
                this.$store.commit("changeSelectedCamera", camera);
            },
            async refresh() {
                let response = await doGet(apiPath + "cameras/");

                if (!response.isOk) {
                    console.log(response);
                    return;
                }

                this.$store.commit("changeCameras", response.response);
            }
        },
        mounted() {
            this.refresh();
        }
    };
</script>

<style scoped>
    .main {
        margin: auto;
    }

    .button-container {
        display: grid;
        grid-column-gap: 10px;
        grid-row-gap: 10px;
        grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
    }

    .button-container > * {
        margin-bottom: 20px;
        justify-self: center;
    }

    .actions {
        display: flex;
        justify-content: center;
    }
</style>
