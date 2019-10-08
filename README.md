[![License badge](https://img.shields.io/badge/license-Apache2-orange.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Documentation badge](https://readthedocs.org/projects/fiware-orion/badge/?version=latest)](https://openvidu.io/docs/home/)
[![Docker badge](https://img.shields.io/docker/pulls/openvidu/openvidu-server-kms.svg)](https://hub.docker.com/r/openvidu/openvidu-server-kms)
[![Support badge](https://img.shields.io/badge/support-sof-yellowgreen.svg)](https://groups.google.com/forum/#!forum/openvidu)
[![Twitter Follow](https://img.shields.io/twitter/follow/openvidu.svg?style=social)](https://twitter.com/openvidu)


[![][OpenViduLogo]](https://openvidu.io) [![FIWARE Banner](https://fiware.github.io/tutorials.Relationships-Linked-Data/img/fiware.png)](https://www.fiware.org/developers)

openvidu-fiware-integration
===

OpenVidu is the only WebRTC technology that allows you to apply real-time audio and video filters in a easy way, and with this FIWARE Orion integration you may insert filter generated events as context information in Orion in a very simple and straight forward way. 

Visit [openvidu.io](https://openvidu.io)

<!-- This project is part of [FIWARE](https://www.fiware.org/). For more information
check the FIWARE Catalogue entry for
[Context Processing, Analysis and Visualization](https://github.com/Fiware/catalogue/tree/master/processing).

| :books:[Documentation](https://kurento.rtfd.io/) | :page_facing_up: [Site](https://www.kurento.org/) | :mortar_board: [Academy](https://fiware-academy.readthedocs.io/en/latest/processing/kurento) | :whale: [Docker Hub](https://hub.docker.com/r/fiware/stream-oriented-kurento/) | 
| ---|---|---|---| -->

[FIWARE]: http://www.fiware.org
[Kurento FIWARE Catalog Entry]: http://catalogue.fiware.org/enablers/stream-oriented-kurento

## Quick start guide

### How to execute the demo (Docker)

To get the openvidu-fiware-integration demo working, you must clone the repo and compile the `fiware-integration` and `openvidu-filters-client` modules using maven:

```sh
## CURRENT FOLDER: <your path>/

git clone https://github.com/naevatec/openvidu-fiware-integration.git
cd openvidu-fiware-integration

# Compile fiware-integration
cd fiware-integration/
mvn clean install

# Compile openvidu-filters-client
cd ../openvidu-filters-client/
mvn clean install
```

Both maven commands are configured to execute a script after the packaging in order to move the `.jar` file into the `docker/` folder.

Now that we have everything compiled, we have to move to the `docker/` folder and execute the docker compose command in order to build two images:

- **kms-platedetector**: this image is created from `kurento/kurento-media-server-dev` including the _platedetector_ filter to use it in the demo. To do that we must ensure that the compiled binaries of the _platedetector_ (`platedetector.tar.gz` file) exist inside the `docker/` folder.
- **java-app**: image from `java:8` used to execute the `.jar`s that we have compiled before.

And launch the following containers:

- **kms**: a kurento-media-server with the platedetector filter.
- **openvidu-server**: a nightly version of openvidu-server (without kms) with all the changes necessary to configure filters and retrieve their event hooks.
- **fiware-integration-manager**: a node application to serve a web page that allows us to control the cameras, their filters and retrieve the events that they generate.
- **orion-mongo**: a mongoDB for the **orion** container.
- **orion**: the _Orion_ container.
- **fiware-integration**: the api that integrates _OpenVidu_ with _Orion_. Moreover, it serves a web page to create a webrtc camera.
- **openvidu-filters-client**: an auxiliary application to modify the filters in _OpenVidu_. 

```sh
## CURRENT FOLDER: <your path>/openvidu-filters-client/

cd ../docker/
docker-compose up -d
```

### How to use the demo

Now that we have everything compiled and running, we can start using the demo.

> **WARN**: probably you should go to the OpenVidu URL `https://localhost:4443` and accept an exception in your web browser before doing the first step, because it uses a self-signed certificate. 

1. First of all we should create a webrtc camera going to `http://localhost:8080`, filling the form (or leaving it empty) and clicking the `` button to create a new camera.
2. Then we have to go to the management web `http://localhost:3000`, select the camera you have already created and update its filter and events to use the platedetector filter. You can also connect to it to see what the camera is recording.
3. Finally print a plate from Spain, put it in front of the camera and see how the event appear in the management web at the bottom. The events are retrieve directly from _Orion_, therefore you don't need the api anymore, just to create, update or remove the camera. 

Documentation
-------------

The Openvidu project provides detailed [documentation] including tutorials, installation and development guides. 

[documentation]: https://openvidu.io/docs/home/index.html


Openvidu Useful Links
---------------------

Usage:

* [Installation Guide](https://openvidu.io/docs/deployment/deploying-ubuntu/)
* [Developing Guide](https://openvidu.io/docs/developing/)
* [Openvidu demos](https://openvidu.io/demos)

Issues:

* [Bug Tracker](https://github.com/OpenVidu/openvidu/issues)
* [Community Support](https://openvidu.io/community)

News:

* [Openvidu Blog](https://medium.com/@openvidu)
* [Google Groups](https://groups.google.com/forum/#!forum/openvidu)
<!-- * [Openvidu RoadMap](ROADMAP.md) -->

Training: 

* [Openvidu tutorials](https://openvidu.io/tutorials)

## Contributors

Openvidu project exists thanks to all the people who contribute. 
<a href="https://github.com/undefined/undefinedgraphs/contributors"><img src="https://opencollective.com/openvidu/contributors.svg?width=890&button=false" /></a>


[OpenViduLogo]: https://secure.gravatar.com/avatar/5daba1d43042f2e4e85849733c8e5702?s=120

[NUBOMEDIA]: http://www.nubomedia.eu

