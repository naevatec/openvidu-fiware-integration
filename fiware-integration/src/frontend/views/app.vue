<template>
    <div class="full-size">
        <div class="view">
            <view-main v-if="actualView === 'main'"></view-main>
            <view-camera-created v-else-if="actualView === 'cameraCreated'"></view-camera-created>
        </div>
        <comp-footer class="footer"></comp-footer>
    </div>
</template>

<script>
    module.exports = {
        data() {
            return {
                uuid: "",
                error: ""
            };
        },
        computed: Vuex.mapState(["actualView"]),
        components: {
            "view-main": "url:views/main.vue",
            "view-camera-created": "url:views/cameraCreated.vue",
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
</style>
