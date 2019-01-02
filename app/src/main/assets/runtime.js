

var canvas = document.createElement("canvas");

console.log("canvas.height:"+ canvas.height);
console.log("canvas.width:"+ canvas.width);

canvas.style.height = "${window.screen.height}px";
canvas.style.width =  "${window.screen.width}px";

// canvas.style.height = "${window.innerHeight}px";
// canvas.style.width =  "${window.innerWidth}px";

canvas.height = window.screen.height*10;
canvas.width =  window.screen.width*10;

// canvas.height = 600;
// canvas.width =  300;

console.log("window.screen.height:"+ window.screen.height);
console.log("window.innerHeight:"+ window.innerHeight);

canvas.getContext("2d").fillRect(20, 20, 100, 100);


// var imagedata = canvas.getContext("2d").getImageData(0,0, canvas.width, canvas.height);
// console.log("imagedata.length:"+ imagedata.data.length);
// var data = btoa(String.fromCharCode.apply(null, imagedata.data));

var dataURL = canvas.toDataURL( "image/png" );
// var data = dataURL + dataURL+ dataURL+ dataURL;
var data = dataURL;

console.log("data.length:"+ data.length);
var blob = new Blob( [ data ], {type: 'text/plain'} );



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
  var url = "http://127.0.0.1:7070/";
  var oReq = new XMLHttpRequest();

  oReq.onload = function (oEvent) {
    console.log("get data:"+ new Date().getSeconds() + "." + new Date().getMilliseconds());

    console.log("oReq.response.length:"+ oReq.response.length);

    var myCanvas = document.getElementById('canvas');
    var ctx = myCanvas.getContext('2d');
    var img = new Image;
    img.onload = function(){
      ctx.drawImage(img,0,0); // Or at whatever offset you like
    };
    img.src = oReq.response;
  };

  oReq.onreadystatechange = function() {
    console.log("this.status: " + this.status);
  };

// oReq.open("GET", url, true);
// oReq.send(null);

  oReq.open("POST", url, true);
  console.log("pre get data:"+ new Date().getSeconds() + "." + new Date().getMilliseconds());
  oReq.send(blob);

  // var dataFromApp = AndroidApp.getAppData(data);
  console.log("pre get data:"+ new Date().getSeconds() + "." + new Date().getMilliseconds());
  // console.log("data.length:"+ dataFromApp.length);
}




