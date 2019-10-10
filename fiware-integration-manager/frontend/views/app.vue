<template>
    <div class="full-size">
        <div class="view">
            <comp-camera-select ref="select"></comp-camera-select>
            <div class="second-row">
                <comp-camera-viewer></comp-camera-viewer>
                <comp-update-form v-if="selectedCamera !== null" @refresh="refresh"></comp-update-form>
            </div>
            <comp-event-logger v-if="selectedCamera !== null"></comp-event-logger>
        </div>
        <comp-footer class="footer"></comp-footer>
    </div>
</template>

<script>
    module.exports = {
        data() {
            return {
                error: ""
            };
        },
        computed: {
            ...Vuex.mapState(["selectedCamera"])
        },
        methods: {
            refresh() {
                this.$refs.select.refresh();
            }
        },
        components: {
            "comp-camera-viewer": "url:comps/camera-viewer.vue",
            "comp-update-form": "url:comps/update-form.vue",
            "comp-event-logger": "url:comps/event-logger.vue",
            "comp-camera-select": "url:comps/camera-select.vue",
            "comp-footer": "url:comps/footer.vue"
        }
    };
</script>

<style>
    .full-size {
        width: 100%;
        height: 100vh;
        overflow: auto;
    }

    .view {
        width: 100%;
        height: calc(100vh - 80px);
        padding: 20px;
        overflow: auto;
    }

    .footer {
        width: 100%;
        height: 80px;
        padding: 10px;
        overflow: hidden;
    }

    .second-row {
        display: flex;
        margin-top: 20px;
        flex-direction: row;
    }

    .second-row > * {
        width: calc(50% - 10px);
    }

    .second-row > *:first-child {
        margin-right: 20px;
    }
</style>
