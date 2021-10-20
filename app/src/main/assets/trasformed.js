"use strict";

function ownKeys(object, enumerableOnly) {
  var keys = Object.keys(object);

  if (Object.getOwnPropertySymbols) {
    var symbols = Object.getOwnPropertySymbols(object);

    if (enumerableOnly) {
      symbols = symbols.filter(function (sym) {
        return Object.getOwnPropertyDescriptor(object, sym).enumerable;
      });
    }

    keys.push.apply(keys, symbols);
  }

  return keys;
}

function _objectSpread(target) {
  for (var i = 1; i < arguments.length; i++) {
    var source = arguments[i] != null ? arguments[i] : {};

    if (i % 2) {
      ownKeys(Object(source), true).forEach(function (key) {
        _defineProperty(target, key, source[key]);
      });
    } else if (Object.getOwnPropertyDescriptors) {
      Object.defineProperties(target, Object.getOwnPropertyDescriptors(source));
    } else {
      ownKeys(Object(source)).forEach(function (key) {
        Object.defineProperty(target, key, Object.getOwnPropertyDescriptor(source, key));
      });
    }
  }

  return target;
}

function _defineProperty(obj, key, value) {
  if (key in obj) {
    Object.defineProperty(obj, key, {
      value: value,
      enumerable: true,
      configurable: true,
      writable: true
    });
  } else {
    obj[key] = value;
  }

  return obj;
}

function _typeof(obj) {
  "@babel/helpers - typeof";

  if (typeof Symbol === "function" && typeof Symbol.iterator === "symbol") {
    _typeof = function _typeof(obj) {
      return typeof obj;
    };
  } else {
    _typeof = function _typeof(obj) {
      return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj;
    };
  }

  return _typeof(obj);
}

(function () {
  var handlers;
  var ISALES = {
    console: function (_console) {
      function console(_x, _x2, _x3) {
        return _console.apply(this, arguments);
      }

      console.toString = function () {
        return _console.toString();
      };

      return console;
    }(function (name, arr, flag) {
      let data = {
          interactiveName: arr[0],
          ...arr[1],
          interActiveCallback:arr[2]||'undefined'
      }
      console.group("".concat(name, " -->"));
      console.table(arr);
      console.groupEnd();
    }),
    callApp: function callApp(name, data) {
      var innerData = data;

      var cb = function cb(res) {
        window.ISALES.console("ISALES.callback 默认回调函数（缺省时触发）", [name, res]);
      };

      var customFn;

      if (data.callback && typeof data.callback === "function") {
        cb = data.callback|| data.callBack;
        data.callback ? delete data.callback :delete data.callBack;
      }

      if (data.customFn && typeof data.customFn === "function") {
        customFn = data.customFn;
        delete data.customFn;
      }

      var callback = function callback(res) {
        console.log('ali callback', name, res, customFn);
        window.ISALES.onAfterInterActive(name, res, customFn || "");
        cb(res);
      };

      var fn = function fn() {
        console.log('ready function', name, innerData);
        window.ISALES.onBeforeInterActive(name, innerData, customFn);
        AlipayJSBridge.call(name, innerData, callback);
      };

      if (window.AlipayJSBridge) {
        fn();
      } else {
        // 如果没有注入则监听注入的事件
        document.addEventListener("AlipayJSBridgeReady", fn, false);
      }
    },
    trackInfo: function trackInfo(type, name, data) {
      window.ISALES.console("ISALES.trackInfo 自定义函数", [type, name, data]);
    },
    onAfterInterActive: function onAfterInterActive(name, res, fn) {
      window.ISALES.console("ISALES.onAfterInterActive 内置回调执行", [name, res, fn], ["name", "data", "callback"]);
      typeof _typeof(fn) === "function" && fn("afterActive", name, res);
      window.ISALES.trackInfo("afterActive", name, res);
    },
    onBeforeInterActive: function onBeforeInterActive(name, data, fn) {
      window.ISALES.console("ISALES.onBeforeInterActive 交互前执行", [name, data, fn], ["name", "data", "callback"]);
      typeof fn === "function" && fn("beforeActive", name, data);
      window.ISALES.trackInfo("beforeActive", name, data);
    },
    ready: function ready(callback) {
      if (window.AlipayJSBridge) {
        callback && callback();
      } else {
        // 如果没有注入则监听注入的事件
        document.addEventListener("AlipayJSBridgeReady", callback, false);
      }
    },
    messageHandler: function messageHandler(name) {
      var ua = window.navigator.userAgent;

      if (ua.indexOf('Android') > -1 || ua.indexOf('Adr') > -1) {
        return function (info) {
          window.ISALES.resetInfo(name, info);
        };
      }

      return {
        postMessage: function postMessage(info) {
          console.log('post---mess', info);
          window.ISALES.resetInfo(name, info);
        }
      };
    },
    resetInfo: function resetInfo(name, info) {
      console.log('reset info', name, info);
      var infoObj = {};

      try {
        infoObj = typeof info === 'string' ? JSON.parse(info) : info;
      } catch (error) {
        console.error("内置版 interActiveWidthApp参数报错 -->", info);
      }

      if (name) window.ISALES.callApp(name, infoObj);
      name = null;
    },
    messageWidthApp: function messageWidthApp(name, opt) {
      var data = {};

      try {
        data = JSON.parse(opt);
      } catch (error) {
        console.error("内置版 messageWidthApp参数报错 -->", name, opt);
      }

      window.ISALES.callApp(name, data);
      name = null;
    }
  };

  if (!window.ISALES) {
    window.ISALES = ISALES;
  } else {
    window.ISALES = _objectSpread(_objectSpread({}, window.ISALES), ISALES);
  }

  var aIsales = new Proxy(window.ISALES, {
    get: function get(obj, prop) {
      var name = !obj[prop] ? "callApp" : prop;
      return obj[name];
    },
    set: function set(obj, prop, vValue) {
      if (prop in obj) {
        return true;
      }

      return obj[prop] = vValue;
    }
  });
  var handlerProxy = new Proxy(window.webkit.messageHandlers, {
    get: function get(obj, prop) {
      if (/PSD|sensors/.test(prop) && window.webkit) {
        return handlers[prop];
      } // var name = !obj[prop] ? "messageHandler" : prop;


      return aIsales.messageHandler(prop);
    },
    set: function set(obj, prop, vValue) {
      if (prop in obj) {
        return true;
      }

      return obj[prop] = vValue;
    }
  });
  Object.defineProperty(window, "ISALES", {
    get: function get() {
      return aIsales;
    },
    set: function set(newValue) {
      for (var key in newValue) {
        if (Object.hasOwnProperty.call(newValue, key)) {
          var element = newValue[key];

          if (!aIsales[key] && !/native|card|message|image|touch|video|ready/i.test(key)) {
            aIsales[key] = element;
          }
        }
      }
    }
  });

  if (window.webkit && window.webkit.messageHandlers) {
    handlers = window.webkit.messageHandlers;
    Object.defineProperty(window.webkit, 'messageHandlers', {
      get: function get() {
        // console.log('get message')
        return handlerProxy;
      }
    });
  }

  if (!window.android) {
    window.android = {};
  }

  Object.defineProperty(window, 'android', {
    get: function get() {
      // console.log('get message')
      return handlerProxy;
    }
  });
  console.warn('内置sdk初始化')
}).call(function () {
  return this || (typeof window !== "undefined" ? window : global);
}());