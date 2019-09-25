<template>
    <div class="full-size">
        <comp-camera-select ref="select"></comp-camera-select>
        <div v-if="selectedCamera !== null" class="second-row">
            <comp-camera-viewer></comp-camera-viewer>
            <comp-update-form @refresh="refresh"></comp-update-form>
        </div>
        <!--        <comp-event-logger></comp-event-logger>-->
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
            "comp-camera-select": "url:comps/camera-select.vue"
        }
    };
</script>

<style>
    .full-size {
        width: 100%;
        height: 100%;
        padding: 20px;
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
