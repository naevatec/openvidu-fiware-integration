/**
 * Modified version to parse correctly media queries.
 * Added: getScopedRule
 * Modified: process (after getScopedRule)
 */

(function umd(root, factory) {
    if (typeof module === "object" && typeof exports === "object") {
        module.exports = factory();
    } else if (typeof define === "function" && define.amd) {
        define([], factory);
    } else {
        root.httpVueLoader = factory();
    }
})(this, function factory() {
    "use strict";

    let scopeIndex = 0;

    StyleContext.prototype = {

        withBase(callback) {

            let tmpBaseElt;
            if (this.component.baseURI) {

                // firefox and chrome need the <base> to be set while inserting or modifying <style> in a document.
                tmpBaseElt = document.createElement("base");
                tmpBaseElt.href = this.component.baseURI;

                const headElt = this.component.getHead();
                headElt.insertBefore(tmpBaseElt, headElt.firstChild);
            }

            callback.call(this);

            if (tmpBaseElt) {
                this.component.getHead().removeChild(tmpBaseElt);
            }
        },

        scopeStyles(styleElt, scopeName) {

            function getScopedRule(rule) {
                const scopedSelectors = [];

                rule.selectorText.split(/\s*,\s*/).forEach(function (sel) {
                    scopedSelectors.push(scopeName + " " + sel);
                    const segments = sel.match(/([^ :]+)(.+)?/);
                    scopedSelectors.push(segments[1] + scopeName + (segments[2] || ""));
                });

                return scopedSelectors.join(",") + rule.cssText.substr(rule.selectorText.length);
            }

            function process() {

                const sheet = styleElt.sheet;
                const rules = sheet.cssRules;

                for (let i = 0; i < rules.length; ++i) {

                    const rule = rules[i];

                    if (rule.type === 1) {
                        sheet.deleteRule(i);
                        sheet.insertRule(getScopedRule(rule), i);
                    } else if (rule.type === 4) {
                        let tmpScopedMedia = "";

                        for (let j = 0; j < rule.cssRules.length; ++j) {
                            const mediaRule = rule.cssRules[j];
                            if (mediaRule.type === 1) {
                                tmpScopedMedia += getScopedRule(mediaRule);
                            }
                        }

                        const mediaScoped = "@media " + rule.conditionText + "{" + tmpScopedMedia + "}";

                        sheet.deleteRule(i);
                        sheet.insertRule(mediaScoped, i);
                    }
                }
            }

            try {
                // firefox may fail sheet.cssRules with InvalidAccessError
                process();
            } catch (ex) {

                if (ex instanceof DOMException && ex.code === DOMException.INVALID_ACCESS_ERR) {

                    styleElt.sheet.disabled = true;
                    styleElt.addEventListener("load", function onStyleLoaded() {

                        styleElt.removeEventListener("load", onStyleLoaded);

                        // firefox need this timeout otherwise we have to use document.importNode(style, true)
                        setTimeout(function () {

                            process();
                            styleElt.sheet.disabled = false;
                        });
                    });
                    return;
                }

                throw ex;
            }
        },

        compile() {
            const hasTemplate = this.template !== null;

            const scoped = this.elt.hasAttribute("scoped");
            const scoped_inside = this.elt.hasAttribute("scoped-inside");

            if (scoped_inside) {
                // no template, no scopable style needed
                if (!hasTemplate) {
                    return;
                }

                // firefox does not tolerate this attribute
                this.elt.removeAttribute("scoped");

            } else if (scoped) {
                // no template, no scopable style needed
                if (!hasTemplate) {
                    return;
                }

                // firefox does not tolerate this attribute
                this.elt.removeAttribute("scoped");
            }

            this.withBase(function () {

                this.component.getHead().appendChild(this.elt);
            });

            if (scoped_inside)
            // TODO
            {
                this.scopeStyles(this.elt, "[" + this.component.getScopeId() + "]");
            } else if (scoped) {
                this.scopeStyles(this.elt, "[" + this.component.getScopeId() + "]");
            }

            return Promise.resolve();
        },

        getContent() {

            return this.elt.textContent;
        },

        setContent(content) {

            this.withBase(function () {

                this.elt.textContent = content;
            });
        }
    };

    function StyleContext(component, elt) {

        this.component = component;
        this.elt = elt;
    }


    ScriptContext.prototype = {

        getContent() {

            return this.elt.textContent;
        },

        setContent(content) {

            this.elt.textContent = content;
        },

        compile(module) {

            const childModuleRequire = function (childURL) {

                return httpVueLoader.require(resolveURL(this.component.baseURI, childURL));
            }.bind(this);

            const childLoader = function (childURL, childName) {

                return httpVueLoader(resolveURL(this.component.baseURI, childURL), childName);
            }.bind(this);

            try {
                Function("exports", "require", "httpVueLoader", "module", this.getContent())
                .call(this.module.exports, this.module.exports, childModuleRequire, childLoader, this.module);
            } catch (ex) {

                if (!("lineNumber" in ex)) {

                    return Promise.reject(ex);
                }
                const vueFileData = responseText.replace(/\r?\n/g, "\n");
                const lineNumber = vueFileData.substr(0, vueFileData.indexOf(script)).split("\n").length +
                    ex.lineNumber - 1;
                throw new (ex.constructor)(ex.message, url, lineNumber);
            }

            return Promise.resolve(this.module.exports)
            .then(httpVueLoader.scriptExportsHandler.bind(this))
            .then(function (exports) {

                this.module.exports = exports;
            }.bind(this));
        }
    };

    function ScriptContext(component, elt) {

        this.component = component;
        this.elt = elt;
        this.module = {exports: {}};
    }


    TemplateContext.prototype = {

        getContent() {

            return this.elt.innerHTML;
        },

        setContent(content) {

            this.elt.innerHTML = content;
        },

        getRootElt() {

            let tplElt = this.elt.content || this.elt;

            if ("firstElementChild" in tplElt) {
                return tplElt.firstElementChild;
            }

            for (tplElt = tplElt.firstChild; tplElt !== null; tplElt = tplElt.nextSibling) {
                if (tplElt.nodeType === Node.ELEMENT_NODE) {
                    return tplElt;
                }
            }

            return null;
        },

        compile() {

            return Promise.resolve();
        }
    };

    function TemplateContext(component, elt) {

        this.component = component;
        this.elt = elt;
    }


    Component.prototype = {

        getHead() {

            return document.head || document.getElementsByTagName("head")[0];
        },

        getScopeId() {

            if (this._scopeId === "") {

                this._scopeId = "data-s-" + (scopeIndex++).toString(36);
                this.template.getRootElt().setAttribute(this._scopeId, "");
            }
            return this._scopeId;
        },

        load(componentURL) {

            return httpVueLoader.httpRequest(componentURL)
            .then(function (responseText) {

                this.baseURI = componentURL.substr(0, componentURL.lastIndexOf("/") + 1);
                const doc = document.implementation.createHTMLDocument("");

                // IE requires the <base> to come with <style>
                doc.body.innerHTML = (this.baseURI ? "<base href=\"" + this.baseURI + "\">" : "") + responseText;

                for (let it = doc.body.firstChild; it; it = it.nextSibling) {

                    switch (it.nodeName) {
                        case "TEMPLATE":
                            this.template = new TemplateContext(this, it);
                            break;
                        case "SCRIPT":
                            this.script = new ScriptContext(this, it);
                            break;
                        case "STYLE":
                            this.styles.push(new StyleContext(this, it));
                            break;
                    }
                }

                return this;
            }.bind(this));
        },

        _normalizeSection(eltCx) {

            let p;

            if (eltCx === null || !eltCx.elt.hasAttribute("src")) {

                p = Promise.resolve(null);
            } else {

                p = httpVueLoader.httpRequest(eltCx.elt.getAttribute("src"))
                .then(function (content) {

                    eltCx.elt.removeAttribute("src");
                    return content;
                });
            }

            return p
            .then(function (content) {

                if (eltCx !== null && eltCx.elt.hasAttribute("lang")) {

                    const lang = eltCx.elt.getAttribute("lang");
                    eltCx.elt.removeAttribute("lang");
                    return httpVueLoader.langProcessor[lang.toLowerCase()].call(this,
                        content === null ? eltCx.getContent() : content);
                }
                return content;
            }.bind(this))
            .then(function (content) {

                if (content !== null) {
                    eltCx.setContent(content);
                }
            });
        },

        normalize() {

            return Promise.all(
                Array.prototype.concat(this._normalizeSection(this.template), this._normalizeSection(this.script),
                    this.styles.map(this._normalizeSection)))
            .then(function () {

                return this;
            }.bind(this));
        },

        compile() {

            return Promise.all(
                Array.prototype.concat(this.template && this.template.compile(), this.script && this.script.compile(),
                    this.styles.map(function (style) {
                        return style.compile();
                    })))
            .then(function () {

                return this;
            }.bind(this));
        }
    };

    function Component(name) {

        this.name = name;
        this.template = null;
        this.script = null;
        this.styles = [];
        this._scopeId = "";
    }

    function identity(value) {

        return value;
    }

    function parseComponentURL(url) {

        const comp = url.match(/(.*?)([^/]+?)\/?(\.vue)?(\?.*|#.*|$)/);
        return {
            name: comp[2],
            url: comp[1] + comp[2] + (comp[3] === undefined ? "/index.vue" : comp[3]) + comp[4]
        };
    }

    function resolveURL(baseURL, url) {

        if (url.substr(0, 2) === "./" || url.substr(0, 3) === "../") {
            return baseURL + url;
        }
        return url;
    }


    httpVueLoader.load = function (url, name) {

        return function () {

            return new Component(name).load(url)
            .then(function (component) {

                return component.normalize();
            })
            .then(function (component) {

                return component.compile();
            })
            .then(function (component) {

                const exports = component.script !== null ? component.script.module.exports : {};

                if (component.template !== null) {
                    exports.template = component.template.getContent();
                }

                if (exports.name === undefined) {
                    if (component.name !== undefined) {
                        exports.name = component.name;
                    }
                }

                exports._baseURI = component.baseURI;

                return exports;
            });
        };
    };


    httpVueLoader.register = function (Vue, url) {

        const comp = parseComponentURL(url);
        Vue.component(comp.name, httpVueLoader.load(comp.url));
    };

    httpVueLoader.install = function (Vue) {

        Vue.mixin({

            beforeCreate() {

                const components = this.$options.components;

                for (let componentName in components) {

                    if (typeof (components[componentName]) === "string" && components[componentName].substr(0, 4) ===
                        "url:") {

                        const comp = parseComponentURL(components[componentName].substr(4));

                        const componentURL = ("_baseURI" in this.$options) ?
                            resolveURL(this.$options._baseURI, comp.url) : comp.url;

                        if (isNaN(componentName)) {
                            components[componentName] = httpVueLoader.load(componentURL, componentName);
                        } else {
                            components[componentName] =
                                Vue.component(comp.name, httpVueLoader.load(componentURL, comp.name));
                        }
                    }
                }
            }
        });
    };

    httpVueLoader.require = function (moduleName) {

        return window[moduleName];
    };

    httpVueLoader.httpRequest = function (url) {

        return new Promise(function (resolve, reject) {
            const innerUrl = new URL(url, document.URL);

            const xhr = new XMLHttpRequest();
            xhr.open("GET", innerUrl);

            xhr.onreadystatechange = function () {

                if (xhr.readyState === 4) {

                    if (xhr.status >= 200 && xhr.status < 300) {
                        resolve(xhr.responseText);
                    } else {
                        console.error("[error] Cannot load " + url);
                        reject(xhr.status);
                    }
                }
            };

            xhr.send(null);
        });
    };

    httpVueLoader.langProcessor = {
        html: identity,
        js: identity,
        css: identity
    };

    httpVueLoader.scriptExportsHandler = identity;

    function httpVueLoader(url, name) {

        const comp = parseComponentURL(url);
        return httpVueLoader.load(comp.url, name);
    }

    return httpVueLoader;
});
