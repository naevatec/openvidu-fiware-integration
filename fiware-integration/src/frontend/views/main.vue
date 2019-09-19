<template>
    <div class="container">
        <div class="form-container">
            <h1>Create a webrtc camera <i class="fas fa-video"></i></h1>
            <p>Introduce the uuid of your camera or leave it blank to auto generate one.</p>
            <div class="inputs">
                <input type="text" placeholder="<<Autogenerate>>" v-model="uuid" @input="validateUuid">
            </div>
            <div class="errors" v-if="error !== ''">{{ error }}</div>
            <div class="button">
                <button @click="sendUuid">Create camera</button>
            </div>
        </div>
    </div>
</template>

<script>
    const uuidRegex = /^[a-zA-Z0-9_]+$/;

    module.exports = {
        data() {
            return {
                uuid: "",
                error: ""
            };
        },
        methods: {
            async sendUuid() {
                if (!this.validateUuid()) {
                    return;
                }

                let data = this.uuid;
                if (data === "") {
                    data = null;
                }

                let result = await doPost(apiPath + "/camera", {
                    cameraUuid: data,
                    protocol: "webrtc"
                });

                if (result.isOk) {
                    this.$store.commit("changeView", "cameraCreated");
                    this.$store.commit("changeData", result.response);
                } else {
                    console.log("ERROR", result);
                }
            },
            validateUuid() {
                if (this.uuid !== "" && !uuidRegex.test(this.uuid)) {
                    this.error = "Uuids only accept letters from a to z and numbers";
                    return false;
                } else {
                    this.error = "";
                    return true;
                }
            }
        }
    };
</script>

<style scoped>
    .container {
        display: flex;
        justify-content: center;
        align-items: center;
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
