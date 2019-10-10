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
            <q-btn v-if="ovData === null" color="primary" label="Connect" @click="connect"></q-btn>
        </q-card-actions>
    </q-card>
</template>

<script>
    module.exports = {
        data() {
            return {
                OV: null,
                session: null,
                publisher: null,
                token: null,
                ovData: null
            };
        },
        computed: {
            ...Vuex.mapState(["selectedCamera"])
        },
        methods: {
            publish() {
                if (this.publisher !== null) {
                    return;
                }

                console.log("publishing");

                this.publisher = this.OV.initPublisher("video-container", {
                    audioSource: undefined, // The source of audio. If undefined default microphone
                    videoSource: undefined, // The source of video. If undefined default webcam
                    publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
                    publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
                    resolution: "640x480",  // The resolution of your video
                    frameRate: 30,			// The frame rate of your video
                    insertMode: "APPEND",	// How the video is inserted in the target element 'video-container'
                    mirror: false       	// Whether to mirror your local video or not
                });

                this.publisher.on("videoElementCreated", (event) => {
                    document.querySelector("#video-container video").srcObject = event.element.srcObject;
                    event.element.setAttribute("muted", true); // Mute local video
                });

                this.session.publish(this.publisher);

            },
            unpublish() {
                if (this.publisher === null) {
                    return;
                }

                console.log("unpublishing");

                this.session.unpublish(this.publisher);
                this.publisher = null;
            },
            async connect() {
                let result = await doPost("http://localhost:8080/api/v1/camera", {
                    cameraUuid: "test",
                    description: "This is a description",
                    protocol: "webrtc"
                });

                if (result.isOk) {
                    this.ovData = result.response;
                } else {
                    console.error(result);
                    return;
                }

                this.session.connect(this.ovData.ovtoken, {
                    publisher: true,
                    amITheCamera: "yes-I-am"
                })
                .then(() => {
                    this.publish();
                }).catch(error => {
                    console.warn("There was an error connecting to the session:", error.code, error.message);
                });
            },
            async disconnect() {
                let result = await doDelete("http://localhost:8080/api/v1/camera/" + this.ovData.cameraUuid, {});

                if (result.isOk) {
                    this.ovData = null;
                } else {
                    console.error(result);
                }
            }
        },
        beforeDestroy() {
            this.disconnect();
        },
        mounted() {
            this.OV = new OpenVidu();
            this.session = this.OV.initSession();

            this.session.on("streamCreated", (event) => {
                this.session.subscribe(event.stream, "video-container");
            });

            this.session.on("switch-state", (event) => {
                let value = event.data === "true";

                if (value) {
                    this.publish();
                    console.log("a");
                } else {
                    this.unpublish();
                    console.log("v");
                }
            });
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
