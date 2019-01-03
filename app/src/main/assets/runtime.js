

// var canvas = document.createElement("canvas");
var canvas = document.getElementById("canvas");

console.log("canvas.height:"+ canvas.height);
console.log("canvas.width:"+ canvas.width);

canvas.style.height = "${window.screen.height}px";
canvas.style.width =  "${window.screen.width}px";

// canvas.style.height = "${window.innerHeight}px";
// canvas.style.width =  "${window.innerWidth}px";

canvas.height = window.screen.height;
canvas.width =  window.screen.width;

// canvas.height = 600;
// canvas.width =  300;


canvas.style.height = "350px";
canvas.style.width =  "350px";

canvas.height = 350;
canvas.width =  350;


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


function sendReq() {

  var dataURL = canvas.toDataURL( "image/png" );
// var data = dataURL + dataURL+ dataURL+ dataURL;
  var olddata = dataURL.substring( "data:image/png;base64,".length );
  var data = olddata;

  // var e = new TextDecoder("iso-8859-1");
  // var data = e.decode(olddata.buffer);

  console.log("data:"+ data.substr(0, 30));
  console.log("data:"+ data.substr(data.length-30, data.length));
  console.log("data.length:"+ data.length);


  var blob = new Blob( [ data ], {type: 'text/plain'} );

  var url = JsAsyncBridge.getServerUrl() + "base64image";
  var oReq = new XMLHttpRequest();

  oReq.onload = function (oEvent) {
    // console.log("get data:"+ new Date().getSeconds() + "." + new Date().getMilliseconds());

    // console.log("oReq.response.length:"+ oReq.response.length);

    // var myCanvas = document.getElementById('canvas');
    // var ctx = myCanvas.getContext('2d');
    // var img = new Image;
    // img.onload = function(){
    //   ctx.drawImage(img,0,0); // Or at whatever offset you like
    // };
    // img.src = oReq.response;
  };

  oReq.onreadystatechange = function() {
    // console.log("this.status: " + this.status);
  };

// oReq.open("GET", url, true);
// oReq.send(null);

  oReq.open("POST", url, true);
  // console.log("pre get data:"+ new Date().getSeconds() + "." + new Date().getMilliseconds());
  oReq.send(blob);

  // var dataFromApp = AndroidApp.getAppData(data);
  // console.log("pre get data:"+ new Date().getSeconds() + "." + new Date().getMilliseconds());
  // console.log("data.length:"+ dataFromApp.length);
}



function sendReq2() {

  var dataURL = canvas.toDataURL( "image/png" );

  var ctx = canvas.getContext('2d');
  var img = new Image;
  img.onload = function(){
    ctx.drawImage(img,0,0, 200, 200); // Or at whatever offset you like
  };
  img.src = dataURL;


  var data = atob( dataURL.substring( "data:image/png;base64,".length ) ),
    asArray = new Uint8Array(data.length);

  for( var i = 0, len = data.length; i < len; ++i ) {
    asArray[i] = data.charCodeAt(i);
  }

  console.log("data.length: " + data.length);

  var blob = new Blob( [ asArray.buffer ], {type: "image/png"} );

  canvas.toBlob(function(bl) {
    var url = JsAsyncBridge.getServerUrl() + "image";
    var oReq = new XMLHttpRequest();

    oReq.onload = function (oEvent) {
      console.log("oReq.response: "+ oReq.response);
    };

    oReq.onreadystatechange = function() {
      console.log("this.status: " + this.status);
    };
    oReq.open("POST", url, true);
    oReq.send(blob);
  }, 'image/png', 0.5);

}

function sendReq3() {

  var dataURL = canvas.toDataURL( "image/png" );

  var ctx = canvas.getContext('2d');
  var img = new Image;
  img.onload = function(){
    ctx.drawImage(img,0,0, 200, 200); // Or at whatever offset you like
  };
  img.src = dataURL;


  var data = atob( dataURL.substring( "data:image/png;base64,".length ) ),
    asArray = new Uint8Array(data.length);

  for( var i = 0, len = data.length; i < len; ++i ) {
    asArray[i] = data.charCodeAt(i);
  }

  console.log("data.length: " + data.length);

  var blob = new Blob( [ asArray.buffer ], {type: "image/png"} );

  canvas.toBlob(function(bl) {
    var url = JsAsyncBridge.getServerUrl() + "image";
    var oReq = new XMLHttpRequest();

    oReq.onload = function (oEvent) {
      console.log("oReq.response: "+ oReq.response);
    };

    oReq.onreadystatechange = function() {
      console.log("this.status: " + this.status);
    };
    oReq.open("POST", url, true);
    oReq.send(blob);
  }, 'image/png', 0.5);

}


