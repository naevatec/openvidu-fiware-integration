<template>
    <div class="container">
        <div id="video-container"></div>
        <q-card>
            <q-card-section class="title">
                <h6><i class="fas fa-video"></i> WebRtc camera <i class="fas fa-video"></i></h6>
            </q-card-section>
            <q-card-section>
                <q-input outlined v-model="ovData.cameraUuid" label="Uuid" disabled></q-input>
                <div class="errors" v-if="error !== ''">{{ error }}</div>
            </q-card-section>
            <q-card-actions class="actions">
                <q-btn color="negative" label="Remove camera" @click="removeCamera"></q-btn>
            </q-card-actions>
        </q-card>
    </div>
</template>

<script>
    module.exports = {
        data() {
            return {
                OV: null,
                session: null,
                error: ""
            };
        },
        computed: {
            ...Vuex.mapState({
                ovData: "data"
            })
        },
        methods: {
            async removeCamera() {
                let result = await doDelete(apiPath + "/camera/" + this.ovData.cameraUuid, {});

                if (result.isOk) {
                    console.log("OK", result); // TODO remove
                    this.$store.commit("changeView", "main");
                    this.$store.commit("changeData", null);
                } else {
                    console.log("ERROR", result);
                }
            }
        },
        mounted() {
            window.onbeforeunload = () => {
                this.session.disconnect();
                this.session = null;
                this.OV = null;
            };

            this.OV = new OpenVidu();
            this.session = this.OV.initSession();

            this.session.on("streamCreated", (event) => {
                this.session.subscribe(event.stream, "video-container");
            });

            this.session.connect(this.ovData.ovtoken, {publisher: true})
            .then(() => {
                const publisher = this.OV.initPublisher("video-container", {
                    audioSource: undefined, // The source of audio. If undefined default microphone
                    videoSource: undefined, // The source of video. If undefined default webcam
                    publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
                    publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
                    resolution: "640x480",  // The resolution of your video
                    frameRate: 30,			// The frame rate of your video
                    insertMode: "APPEND",	// How the video is inserted in the target element 'video-container'
                    mirror: false       	// Whether to mirror your local video or not
                });

                publisher.on("videoElementCreated", (event) => {
                    document.querySelector("#video-container video").srcObject = event.element.srcObject;
                    event.element.setAttribute("muted", true); // Mute local video
                });

                this.session.publish(publisher);
            })
            .catch(error => {
                console.warn("There was an error connecting to the session:", error.code, error.message);
            });
        },
        beforeDestroy() {
            window.onbeforeunload();
            window.onbeforeunload = null;
        }
    };
</script>

<style scoped>
    .container {
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        width: 100%;
        height: 100%;
    }

    .title {
        text-align: center;
    }

    .actions {
        display: flex;
        justify-content: center;
    }
</style>
