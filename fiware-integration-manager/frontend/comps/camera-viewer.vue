<template>
    <q-card>
        <q-card-section>
            <div class="text-h6 text-center"><i class="fas fa-video"></i> Video <i class="fas fa-video"></i>
            </div>
        </q-card-section>
        <q-card-section class="button-container">
            <div id="video-container"></div>
        </q-card-section>
        <q-card-actions class="actions">
            <q-btn v-if="session === null" color="primary" label="Connect" @click="connect"></q-btn>
            <q-btn v-else color="primary" label="Disconnect" @click="disconnect"></q-btn>
        </q-card-actions>
    </q-card>
</template>

<script>
    module.exports = {
        data() {
            return {
                OV: null,
                session: null,
                token: null
            };
        },
        computed: {
            ...Vuex.mapState(["selectedCamera"])
        },
        methods: {
            async connect() {
                // Get token
                const response = await doGet(apiUrl + "/camera/" + this.selectedCamera.id + "/token");

                if (!response.isOk) {
                    console.error("Cannot get a token", response);
                    return;
                }

                this.token = response.response;

                // Connect
                window.onbeforeunload = () => {
                    this.session.disconnect();
                    this.OV = null;
                    this.session = null;
                    this.token = null;
                };

                this.OV = new OpenVidu();
                this.session = this.OV.initSession();

                this.session.on("streamCreated", (event) => {
                    this.session.subscribe(event.stream, "video-container");
                });

                this.session.connect(this.token, {publisher: false})
                .catch(error => {
                    console.warn("There was an error connecting to the session:", error.code, error.message);
                });
            },
            async disconnect() {
                if (this.session != null) {
                    window.onbeforeunload();
                    window.onbeforeunload = null;
                }
            }
        },
        beforeDestroy() {
            this.disconnect();
        },
        watch: {
            selectedCamera() {
                if (this.session != null) {
                    this.disconnect();
                    this.connect();
                }
            }
        }
    };
</script>

<style scoped>
    #video-container {
        display: flex;
        width: 100%;
        max-height: 480px;
        background-color: black;
        justify-content: center;
    }

    #video-container video {
        max-width: 100%;
        max-height: 100%;
    }

    .actions {
        display: flex;
        justify-content: center;
    }
</style>
