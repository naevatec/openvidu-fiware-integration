<template>
    <q-card class="main" v-if="show">
        <q-card-section>
            <div class="text-h6 text-center"><i class="fas fa-info-circle"></i> Events
                <i class="fas fa-info-circle"></i>
            </div>
        </q-card-section>
        <q-card-section class="button-container">
            <q-list bordered padding separator class="rounded-borders">
                <template v-for="(event, index) in events" v-key="'ev' + index">
                    <q-item clickable v-ripple>
                        <q-item-section>
                            <q-item-label overline>{{event.id}}</q-item-label>
                            <q-item-label><b>[{{event.index}}] {{event.filterType}} - {{event.eventType}}</b>
                            </q-item-label>
                            <q-item-label class="code" v-html="syntaxHighlight(event.data)"></q-item-label>
                            <q-item-label caption>{{event.dateCreated}}</q-item-label>
                        </q-item-section>
                    </q-item>
                </template>
            </q-list>
        </q-card-section>
    </q-card>
</template>

<script>
    let connection = new NGSI.Connection(orionUrl);

    module.exports = {
        data() {
            return {
                currentDate: new Date(),
                show: false,
                subscriberId: null,
                events: [],
                interval: null
            };
        },
        computed: {
            ...Vuex.mapState(["selectedCamera"])
        },
        methods: {
            refresh() {
                if (this.interval !== null) {
                    clearInterval(this.interval);
                    this.interval = null;
                    this.events.splice(0, this.events.length);
                }

                // Avoid to init if there is no filter.
                let filterEvents = getFilterAndEventsFromCamera(this.selectedCamera);
                const filter = filterEvents.filter;
                const events = filterEvents.events;

                if (filter === null) {
                    return;
                }

                this.show = true;

                let handler = async () => {
                    let response = await connection.v2.listEntities({
                        type: filter
                    });

                    let index = 1;
                    response.results.forEach((ev) => {
                        ev.index = index;
                        ev.dateCreated = ev.dateCreated.value;
                        ev.sessionName = ev.deviceSource.value;
                        ev.filterType = ev.type;
                        ev.eventType = ev.eventType.value;
                        ev.data = ev.data ? ev.data.value : null;
                        delete ev.type;
                        delete ev.deviceSource;
                        index++;
                    });

                    // Same events and camera.
                    let results = response.results.filter((ev) => {
                        let eventTypeAsCamelCase = ev.eventType.replace(/-(\w)/g, (x, y) => y.toUpperCase())
                        .replace(/^(\w)/, (x, y) => y.toUpperCase());
                        return ev.sessionName === this.selectedCamera.id && events.includes(eventTypeAsCamelCase);
                    });

                    results = results.sort((a, b) => {
                        if (a.dateCreated > b.dateCreated) {
                            return -1;
                        }
                        if (b.dateCreated > a.dateCreated) {
                            return 1;
                        }
                        return 0;
                    });

                    this.events.splice(0, this.events.length, ...results);
                };
                handler();
                this.interval = setInterval(handler, 1000);
            },
            syntaxHighlight(data) {
                return syntaxHighlight(data);
            }
        },
        mounted() {
            this.refresh();
        },
        watch: {
            selectedCamera() {
                this.show = false;
                this.refresh();
            }
        },
        beforeDestroy() {
            if (this.interval !== null) {
                clearInterval(this.interval);
                this.interval = null;
            }
        }
    };
</script>

<style scoped>
    .main {
        margin: 20px 0;
    }

    .code {
        font-family: monospace;
        margin: 20px 0;
        padding: 10px;
        background-color: #2b2b2b;
        color: #ffffff;
        white-space: pre-wrap;
    }

    span.key {
        color: #d4b65a;
    }

    span.string {
        color: #7192b9;
    }
</style>
