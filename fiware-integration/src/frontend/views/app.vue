<template>
    <div class="full-size">
        <view-main v-if="actualView === 'main'"></view-main>
        <view-camera-created v-else-if="actualView === 'cameraCreated'"></view-camera-created>
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
            "view-camera-created": "url:views/cameraCreated.vue"
        }
    };
</script>

<style>
    .full-size {
        width: 100%;
        height: 100%;
    }
</style>
