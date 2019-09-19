<template>
    <div class="container">
        <div id="video-container"></div>
        <div class="form-container">
            <h1>Webrtc camera <i class="fas fa-video"></i></h1>
            <div class="inputs">
                <input type="text" v-model="ovData.cameraUuid" disabled>
            </div>
            <div class="errors" v-if="error !== ''">{{ error }}</div>
            <div class="button">
                <button @click="removeCamera">Remove camera</button>
            </div>
        </div>
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
                    $("#video-container video").get(0).srcObject = event.element.srcObject;
                    $(event.element).prop("muted", true); // Mute local video
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

    .form-container {
        min-width: 120px;
        max-width: 500px;
        padding: 20px;
        border: 1px solid #4a4a4a;
    }

    .form-container > h1 {
        margin-bottom: 20px;
        text-align: center;
    }

    .form-container > p {
        font-size: 15px;
        text-align: justify;
        margin-bottom: 10px;
    }

    .form-container > .inputs {
        margin-bottom: 10px;
        text-align: center;
    }

    .form-container > .inputs > input {
        font-size: 18px;
        padding: 5px 10px;
        outline: none;
        -webkit-appearance: none;
        border-style: solid;
        border-radius: 2px;
        text-align: center;
    }

    .form-container > .inputs > ::placeholder {
        color: rgba(192, 192, 192, 0.97);
        text-align: center;
    }

    .form-container > .errors {
        margin-bottom: 10px;
        text-align: center;
        color: #ff0000;
    }

    .form-container > .button {
        text-align: center;
    }

    .form-container > .button > button {
        font-size: 20px;
        padding: 5px 10px;
        border-radius: 5px;
        background-color: #cde6ff;
        outline: none;
        -webkit-appearance: none;
    }

    .form-container > .button > button:active {
        background-color: #b5cde6;
        border-style: solid;
    }
</style>
