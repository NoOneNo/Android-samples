

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
imageObj.src = 'https://www.html5canvastutorials.com/demos/assets/darth-vader.jpg';


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
  console.log("-------to dataURL-----------");
  console.log("------" + new Date().getSeconds() + "." + new Date().getMilliseconds() + "--------");
  const dataURL = canvas.toDataURL( "image/png" );
  const data  = dataURL.substring( "data:image/png;base64,".length );
  console.log("data.length:"+ data.length);
  const blob = new Blob( [ data ], {type: 'text/plain'} );
  console.log("------" + new Date().getSeconds() + "." + new Date().getMilliseconds() + "--------");


  sendBlob("base64image", blob)
}


function sendAsyncCanvas() {
  canvas.toBlob(function(blob) {
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

function sendBlob(key, blob) {
  console.log("-------sendBlob-----------");
  const url = JsAsyncBridge.getServerUrl() + key;
  const req = new XMLHttpRequest();

  req.onload = function (oEvent) {
    console.log("oReq.response: "+ req.response);
    console.log("------" + new Date().getSeconds() + "." + new Date().getMilliseconds() + "--------");
  };

  req.onreadystatechange = function() {
    console.log("this.status: " + this.status);
  };
  req.open("PUT", url, true);
  req.send(blob);
  console.log("------" + new Date().getSeconds() + "." + new Date().getMilliseconds() + "--------");
}

function clipCanvas(canvas, x, y, w, h) {
  console.log("------clipCanvas----------");
  console.log("------" + new Date().getSeconds() + "." + new Date().getMilliseconds() + "--------");
  const ctx = canvas.getContext("2d");
  const clipImg = ctx.getImageData(x, y, w, h);

  console.log("------" + new Date().getSeconds() + "." + new Date().getMilliseconds() + "--------");

  const imgCanvas = document.createElement("canvas");

  imgCanvas.style.width =  "${w}px";
  imgCanvas.style.height = "${h}px";

  imgCanvas.width =  w;
  imgCanvas.height = h;

  const imgCtx = imgCanvas.getContext("2d");

  imgCtx.putImageData(clipImg, 0, 0, 0, 0, w, h);
  console.log("------" + new Date().getSeconds() + "." + new Date().getMilliseconds() + "--------");
  return imgCanvas
}

