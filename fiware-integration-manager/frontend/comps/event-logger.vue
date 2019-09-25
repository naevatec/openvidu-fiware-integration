<template>
    <div class="container">
        <div id="video-container">640x480</div>
        <div class="form-container">
            <h1>Connect to camera <i class="fas fa-video"></i></h1>
            <div class="inputs">
                <input type="text" disabled>
            </div>
            <div class="errors">{{ error }}</div>
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
