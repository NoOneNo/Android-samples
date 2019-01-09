

// var canvas = document.createElement("canvas");
var canvas = document.getElementById("canvas");

console.log("canvas.height:"+ canvas.height);
console.log("canvas.width:"+ canvas.width);

canvas.style.height = "${window.screen.height}px";
canvas.style.width =  "${window.screen.width}px";

canvas.height = window.screen.height*2;
canvas.width =  window.screen.width*2; // request contentLength: 1722401

// canvas.style.height = "${window.innerHeight}px";
// canvas.style.width =  "${window.innerWidth}px";



// canvas.height = 600;
// canvas.width =  300;


// canvas.style.width =  "350px";
// canvas.style.height = "650px";
//
// canvas.width =  350;
// canvas.height = 650;



console.log("window.screen.height:"+ window.screen.height);
console.log("window.innerHeight:"+ window.innerHeight);

// canvas.getContext("2d").fillRect(20, 20, 100, 100);


var context = canvas.getContext('2d');
var imageObj = new Image();
imageObj.onload = function() {
  context.drawImage(imageObj, 0, 0, canvas.width, canvas.height);
};
imageObj.src = 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546609343233&di=7cb4477bff553eefdd3b53d78614d39d&imgtype=0&src=http%3A%2F%2Ffilesrv.iyunshu.com%2FC%2F01575%2F2494966-fm.jpg';


// var imagedata = canvas.getContext("2d").getImageData(0,0, canvas.width, canvas.height);
// console.log("imagedata.length:"+ imagedata.data.length);
// var data = btoa(String.fromCharCode.apply(null, imagedata.data));





//
// console.log("pre send data:"+ new Date().getSeconds() + "." + new Date().getMilliseconds());
// oReq.onload = function (oEvent) {
//   console.log(oEvent);
//   console.log("oReq" + oReq);
//   console.log("oReq.response" + oReq.response);
//   console.log("oReq.responseText" + oReq.responseText);
//   console.log("send data:"+ new Date().getSeconds() + "." + new Date().getMilliseconds());
// };

// console.log("after send data:"+ new Date().getSeconds() + "." + new Date().getMilliseconds());


function sendCanvas() {
  console.time("todataURL");
  const dataURL = canvas.toDataURL( "image/png" );
  const data  = dataURL.substring( "data:image/png;base64,".length );
  console.log("data.length:"+ data.length);
  const blob = new Blob( [ data ], {type: 'text/plain'} );
  console.timeEnd("todataURL");

  sendBlob("base64image", blob)
}


function sendAsyncCanvas() {
  console.time("todataURL");
  canvas.toBlob(function(blob) {
    console.timeEnd("toBlob");
    sendBlob("image", blob)
  }, 'image/png', 1);
}

function sendClipCanvas() {
  const newCanvas = clipCanvas(canvas, 0, 0, canvas.width, canvas.height);

  newCanvas.toBlob(function(blob) {
    sendBlob("image", blob)
  }, 'image/png', 1);
}

function getcanvasBin(canvas) {
  const dataURL = canvas.toDataURL( "image/png" );

  const data = atob( dataURL.substring( "data:image/png;base64,".length ) ),
    asArray = new Uint8Array(data.length);

  for( var i = 0, len = data.length; i < len; ++i ) {
    asArray[i] = data.charCodeAt(i);
  }

  console.log("data.length: " + data.length);

  const blob = new Blob( [ asArray.buffer ], {type: "image/png"} );
  return blob
}



function clipCanvas(canvas, x, y, w, h) {
  console.time("clipCanvas");
  const ctx = canvas.getContext("2d");
  const clipImg = ctx.getImageData(x, y, w, h);
  console.timeEnd("clipCanvas");

  const imgCanvas = document.createElement("canvas");

  imgCanvas.style.width =  "${w}px";
  imgCanvas.style.height = "${h}px";

  imgCanvas.width =  w;
  imgCanvas.height = h;

  const imgCtx = imgCanvas.getContext("2d");

  imgCtx.putImageData(clipImg, 0, 0, 0, 0, w, h);
  return imgCanvas
}



function sendJSIStr(key, str) {
  console.time("sendJSIStr");
  JsAsyncBridge.putString(key, str);
  console.timeEnd("sendJSIStr");
}

function sendJSIByte(key, data) {
  console.time("sendJSIByte");
  JsAsyncBridge.putBytes(key, data);
  console.timeEnd("sendJSIByte");
}

function sendHttpStrWithBlob(key, str) {
  console.time("sendHttpStrWithBlob");

  console.time("str-to-blob");
  const blob = new Blob( [ str ], {type:"text/plain"} );
  console.timeEnd("str-to-blob");

  const url = JsAsyncBridge.getServerUrl() + key;
  const req = new XMLHttpRequest();

  req.onload = function (oEvent) {
    console.timeEnd("sendHttpStrWithBlob");
  };

  req.open("PUT", url, true);
  req.send(blob);
}

function sendHttpStr(key, str) {
  const url = JsAsyncBridge.getServerUrl() + key;
  const req = new XMLHttpRequest();

  console.time("sendHttpStr");
  req.onload = function (oEvent) {
    console.timeEnd("sendHttpStr");
    // console.log("oReq.response: "+ req.response);
  };

  req.open("PUT", url, true);
  req.send(str);
  // req.sendAsBinary(str);
}

function sendHttpStrWithArrayBuffer(key, str) {
  const url = JsAsyncBridge.getServerUrl() + key;
  const req = new XMLHttpRequest();

  console.time("sendHttpStr");
  req.onload = function (oEvent) {
    console.timeEnd("sendHttpStr");
    // console.log("oReq.response: "+ req.response);
  };

  req.open("PUT", url, true);
  req.send(str);
  // req.sendAsBinary(str);
}

function sendBlob(key, blob) {
  const url = JsAsyncBridge.getServerUrl() + key;
  const req = new XMLHttpRequest();
  console.time("sendBlob");

  req.onload = function (oEvent) {
    console.timeEnd("sendBlob");
    // console.log("oReq.response: "+ req.response);
  };

  req.onreadystatechange = function() {
    // console.log("this.status: " + this.status);
  };

  req.open("PUT", url, true);
  req.send(blob);
}

var port;

function sendStrMsgC(str) {
  console.time("sendStrMsgC");
  console.log("------postMessage1----------");
  port.postMessage(str);
  console.timeEnd("sendStrMsgC");
  console.log("------postMessage2----------");
}

onmessage = function (e) {
  console.log("------init postMessage----------" + f.data);
  port = e.ports[0];
  port.onmessage = function (f) {
    console.log("------postMessage3----------" + f.data);
  }
};

// function conpareSend() {
//   var data = "严严严严严严严严严严";
//   console.log("data.length: "+ data.length);
//
//   var uint8aa = new TextEncoder().encode(data);
//   console.log("data to uint8: " + uint8aa.length);
//
//   uint8aa = new TextEncoder("iso-8859-1").encode(data);
//   console.log("data to iso-8859-1: " + uint8aa.length);
//
//   sendJSIStr("text", data);
//   sendHttpStr("text", data);
//   // sendHttpStrWithBlob("text", data);
//   // sendStrMsgC(data);
// }

// function conpareSend() {
//   const dataURL = canvas.toDataURL( "image/png" );
//   var data = dataURL; //.substr(0, 2000000);
//   console.log("data.length: "+ data.length);
//
//   //const enc = new TextEncoder();
//   //const uint8aa = enc.encode(data);
//   //console.log("data to uint8: " + uint8aa.length);
//
//   // sendJSIStr("text", data);
//   // sendHttpStr("text", data);
//   sendHttpStr("arrayBuffer", data);
//   // sendHttpStrWithBlob("arrayBuffer", data);
//   // sendStrMsgC(data);
// }

// function conpareSend() {
//   const dataURL = canvas.toDataURL( "image/png" );
//   var data = dataURL.substr(0, 2000000);
//   console.log("data.length: "+ data.length);
//
//   data = "严严严严严严严严严严";
//
//   console.time("encode-utf-8");
//   var uint8aa = new TextEncoder().encode(data);
//   console.log("data to uint8: " + uint8aa.length); // *?= mem
//   console.timeEnd("encode-utf-8");
//
//   console.time("encode-8859");
//   uint8aa = new TextEncoder("iso-8859-1").encode(data);
//   console.log("data to iso-8859-1: " + uint8aa.length); //  = mem
//   console.timeEnd("encode-8859");
//
//   // console.time("encode-b64s");
//   // var b64s = btoa(data);
//   // console.log("data to b64s: " + b64s.length); // *2= mem // 不能处理中文!!!!
//   // console.timeEnd("encode-b64s");
//
//   console.time("str-to-blob");
//   var blob = new Blob( [ data ], {type:"text/plain"} );
//   console.log("data to blob: " + blob.size);
//   console.timeEnd("str-to-blob");
//
//     var fileReader = new FileReader();
//     fileReader.readAsArrayBuffer(blob);
//     fileReader.onload = function() {
//       var arrayBuffer = fileReader.result;
//       sendHttpStrWithArrayBuffer("arrayBuffer", arrayBuffer)
//     };
// }

// function conpareSend() {
//   console.time("toBlob");
//   canvas.toBlob(function(blob) {
//     console.timeEnd("toBlob");
//
//     // console.time("readAsDataURL");
//     // var reader = new FileReader();
//     // reader.readAsDataURL(blob);
//     // reader.onloadend = function() {
//     //   console.timeEnd("readAsDataURL");
//     //
//     //   const base64data = reader.result;
//     //   console.log("base64data: " + base64data.length);
//     //   sendStrJSI(base64data);
//     // };
//
//     var fileReader = new FileReader();
//     fileReader.readAsArrayBuffer(blob);
//     fileReader.onload = function() {
//       var arrayBuffer = fileReader.result;
//       var array = new Uint8Array(arrayBuffer);
//       console.log("arrayBuffer: " + array.length);
//
//       console.time("encode-8859");
//       var dec = new TextDecoder("iso-8859-1").decode(array);
//       console.log("data to iso-8859-1: " + dec.length); //  = mem
//       console.timeEnd("encode-8859");
//
//       sendJSIStr("arrayBuffer", dec);
//
//       // sendHttpStrWithArrayBuffer("arrayBuffer", arrayBuffer)
//
//
//
//       // console.time("encode-b64s");
//       // var b64s = btoa(array);
//       // console.log("data to b64s: " + b64s.length); // *2= mem
//       // console.timeEnd("encode-b64s");
//     };
//
//
//     // sendBlob("arrayBuffer", blob)
//   });
// }


// function conpareSend() {
//   console.time("toDataURL");
//   const dataURL = canvas.toDataURL( "image/png" );
//   console.timeEnd("toDataURL");
//
//   console.time("toBlob");
//   canvas.toBlob(function(blob) {
//     console.timeEnd("toBlob");
//   });
// }

function conpareSend() {
  var formData = new FormData();

  formData.append("username", "Groucho");
  // formData.append("accountnum", 123456); // number 123456 is immediately converted to a string "123456"

// HTML file input, chosen by user
  // formData.append("userfile", fileInputElement.files[0]);

// JavaScript file-like object
  var content = '<a id="a"><b id="b">hey!</b></a>'; // the body of the new file...
  var blob = new Blob([content], { type: "text/xml"});

  // formData.append("webmasterfile", blob);

  var request = new XMLHttpRequest();
  const url = JsAsyncBridge.getServerUrl();
  request.open("POST", url);
  request.send(formData);
}
