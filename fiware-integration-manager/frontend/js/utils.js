function getFilterAndEventsFromCamera(camera) {
    let filter = null;
    let events = [];
    if (camera.controlledAsset) {
        camera.controlledAsset.forEach((ev) => {
            const filterEvent = ev.split(".");
            filter = filterEvent[0] + "." + filterEvent[1];
            events.push(filterEvent[2]);
        });
    }

    return {
        filter,
        events
    };
}

function syntaxHighlight(json) {
    if (typeof json != "string") {
        json = JSON.stringify(json, undefined, 2);
    }
    json = json.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
    return json.replace(
        /("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g,
        function (match) {
            let cls = "number";
            if (/^"/.test(match)) {
                if (/:$/.test(match)) {
                    cls = "key";
                } else {
                    cls = "string";
                }
            } else if (/true|false/.test(match)) {
                cls = "boolean";
            } else if (/null/.test(match)) {
                cls = "null";
            }
            return "<span class=\"" + cls + "\">" + match + "</span>";
        });
}
