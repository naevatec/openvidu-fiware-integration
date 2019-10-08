<template>
    <div class="container">
        <q-card>
            <q-card-section class="title">
                <h6><i class="fas fa-video"></i> Create a webrtc camera <i class="fas fa-video"></i></h6>
            </q-card-section>
            <q-card-section>
                <p>Introduce the uuid of your camera or leave it blank to auto generate one.</p>
                <q-input outlined v-model="uuid" label="Uuid" placeholder="Leave blank to autogenerate it"></q-input>
                <q-input outlined v-model="description" label="Description" class="q-mt-md"></q-input>
                <div class="errors" v-if="error !== ''">{{ error }}</div>
            </q-card-section>
            <q-card-actions class="actions">
                <q-btn color="primary" label="Create camera" @click="sendUuid"></q-btn>
            </q-card-actions>
        </q-card>
    </div>
</template>

<script>
    const uuidRegex = /^[a-zA-Z0-9_]+$/;

    module.exports = {
        data() {
            return {
                uuid: "",
                description: "",
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
                    description: this.description,
                    protocol: "webrtc"
                });

                if (result.isOk) {
                    this.$store.commit("changeView", "cameraCreated");
                    this.$store.commit("changeData", result.response);
                } else {
                    console.error(result);
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

    .title {
        text-align: center;
    }

    .actions {
        display: flex;
        justify-content: center;
    }
</style>
