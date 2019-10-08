<template>
    <q-card class="bg-blue-grey-1 footer">
        <a href="https://www.naevatec.com/" class="img-container">
            <q-img class="img" :ratio="16/9" contain src="img/naeva.png"></q-img>
        </a>
        <a href="https://openvidu.io/" class="img-container">
            <q-img class="img" :ratio="16/9" contain src="img/openvidu.png"></q-img>
        </a>
        <a href="https://www.fiware.org/" class="img-container">
            <q-img class="img" :ratio="16/9" contain src="img/fiware.png"></q-img>
        </a>
        <a href="http://www.apache.org/licenses/LICENSE-2.0" class="img-container tag-container">
            <span>Licensed under: &nbsp;</span>
            <img src="https://img.shields.io/badge/license-Apache2-orange.svg">
        </a>
    </q-card>
</template>

<style scoped>
    a {
        color: inherit;
        text-decoration: inherit;
    }

    .footer {
        display: flex;
        justify-content: space-between;
        flex-wrap: wrap;
        height: 80px;
    }

    .img-container {
        height: 100%;
        width: 25%;
    }

    .img {
        max-height: 100%;
        max-width: 100%;
    }

    .tag-container {
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .license {
        text-align: center;
    }

    @media (max-width: 870px) {
        span {
            display: none;
        }
    }
</style>
