<template>
    <q-card>
        <q-card-section>
            <div class="text-h6 text-center"><i class="fas fa-filter"></i> Update filters <i class="fas fa-filter"></i>
            </div>
        </q-card-section>
        <q-card-section class="button-container">
            <q-select outlined v-model="filter" :options="filterOptions" label="Filter" dense></q-select>
            <q-select outlined v-model="events" :options="eventOptions" label="Events" multiple dense></q-select>
            <q-checkbox v-model="selectedCamera.active || false" label="Is active?" disabled></q-checkbox>
        </q-card-section>
        <q-card-actions class="actions">
            <q-btn color="primary" label="Update" @click="update"></q-btn>
        </q-card-actions>
        <q-card-actions class="actions2">
            <q-btn color="negative" label="Remove camera" @click="remove"></q-btn>
            <q-btn color="primary" icon="fas fa-sync" label="Refresh" @click="refresh"></q-btn>
        </q-card-actions>
    </q-card>
</template>

<script>
    module.exports = {
        data() {
            return {
                filter: "None",
                filterOptions: ["None", "Plate detector"],
                events: [],
                availableEvents: {
                    "None": [],
                    "Plate detector": ["xx", "yy"]
                }
            };
        },
        computed: {
            eventOptions() {
                return this.availableEvents[this.filter];
            }, ...Vuex.mapState(["selectedCamera"])
        },
        methods: {
            async update() {
                let response = await doPost(apiPath + "camera/" + this.selectedCamera.cameraUuid, {
                    filter: this.filter === "None" ? null : this.filter,
                    events: this.events
                });

                if (!response.isOk) {
                    console.log(response);
                    return;
                }

                this.refresh();
            },
            async refresh() {
                let response = await doGet(apiPath + "camera/" + this.selectedCamera.cameraUuid);

                if (!response.isOk) {
                    console.log(response);
                    return;
                }

                let camera = response.response;

                this.filter = camera.filter;
                this.events = camera.events;

                if (!this.filter) {
                    this.filter = "None";
                    this.events = [];
                }
            },
            async remove() {
                let response = await doDelete(apiPath + "camera/" + this.selectedCamera.cameraUuid);

                if (!response.isOk) {
                    console.log(response);
                    return;
                }

                this.$store.commit("changeSelectedCamera", null);
                this.$emit("refresh");
            }
        },
        watch: {
            filter() {
                this.events = [];
            }
        },
        mounted() {
            this.refresh();
        }
    };
</script>

<style scoped>
    .actions {
        display: flex;
        justify-content: center;
    }

    .actions2 {
        display: flex;
        justify-content: space-between;
    }
</style>
