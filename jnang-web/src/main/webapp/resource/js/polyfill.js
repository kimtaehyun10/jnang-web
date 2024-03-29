/**
* @ProjectDescription browser polyfill js
*
* @Author RGJ
* @Version 1.0
*/
if(typeof Object.values === 'undefined'){
	Object.values = function(obj){
		var array = new Array();
		for(var key in obj){
			array.push(obj[key]);
		}
		return array;
	}
}
if(typeof Object.keys === 'undefined'){
	Object.keys = function(obj){
		var array = new Array();
		for (var key in obj){
	      array.push(obj[key]);
	    }
	    return array;
	}
}
if(!Object.assign){
  Object.defineProperty(Object, 'assign', {
    enumerable: false,
    configurable: true,
    writable: true,
    value: function(target) {
      'use strict';
      if (target === undefined || target === null) {
        throw new TypeError('Cannot convert first argument to object');
      }

      var to = Object(target);
      for (var i = 1; i < arguments.length; i++) {
        var nextSource = arguments[i];
        if (nextSource === undefined || nextSource === null) {
          continue;
        }
        nextSource = Object(nextSource);

        var keysArray = Object.keys(Object(nextSource));
        for (var nextIndex = 0, len = keysArray.length; nextIndex < len; nextIndex++) {
          var nextKey = keysArray[nextIndex];
          var desc = Object.getOwnPropertyDescriptor(nextSource, nextKey);
          if (desc !== undefined && desc.enumerable) {
            to[nextKey] = nextSource[nextKey];
          }
        }
      }
      return to;
    }
  });
}
