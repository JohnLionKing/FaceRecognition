var video = document.getElementById('video');
var errBack = function(e) {
    console.log('An error has occurred!', e)
};
            


window.onload = function() {
   
    source = document.getElementById('canvas');
    source_context = source.getContext('2d');
    capture_faces = [];
    var count = 0;
    index = 0;
    
     if(navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
    // Not adding `{ audio: true }` since we only want video now
    navigator.mediaDevices.getUserMedia({ video: true }).then(function(stream) {
        video.src = window.URL.createObjectURL(stream);
        video.play();
    });
    } else if(navigator.getUserMedia) { // Standard
        navigator.getUserMedia({ video: true }, function(stream) {
            video.src = stream;
            video.play();
        }, errBack);
    } else if(navigator.webkitGetUserMedia) { // WebKit-prefixed
        navigator.webkitGetUserMedia({ video: true }, function(stream){
            video.src = window.webkitURL.createObjectURL(stream);
            video.play();
        }, errBack);
    } else if(navigator.mozGetUserMedia) { // Mozilla-prefixed
        navigator.mozGetUserMedia({ video: true }, function(stream){
            video.src = window.URL.createObjectURL(stream);
            video.play();
        }, errBack);
    }

    var tracker = new tracking.ObjectTracker('face');
    tracker.setInitialScale(4);
    tracker.setStepSize(2);
    tracker.setEdgesDensity(0.1);

    var trackTask = tracking.track('#video', tracker, { camera: true });

    tracker.on('track', function(event) {

        source_context.clearRect(0, 0, source.width, source.height);
        event.data.forEach(function(rect) {
            source_context.strokeStyle = '#a64ceb';
            source_context.strokeRect(rect.x, rect.y, rect.width, rect.height);
            if(++count % 8 == 0)
            {
                index++;
                if(index <= 10)
                    takepicture(rect);
                else
                {
                    trackTask.stop();
                    document.getElementById("prompt").innerHTML = "Face images were captured successfully.";
                }
            }
            source_context.font = '11px Helvetica';
            source_context.fillStyle = "#fff";
            source_context.fillText('x: ' + rect.x + 'px', rect.x + rect.width - 5, rect.y + 11);
            source_context.fillText('y: ' + rect.y + 'px', rect.x + rect.width - 5, rect.y + 22);
        });
    });

    var gui = new dat.GUI();
    gui.add(tracker, 'edgesDensity', 0.1, 0.5).step(0.01);
    gui.add(tracker, 'initialScale', 1.0, 10.0).step(0.1);
    gui.add(tracker, 'stepSize', 1, 5).step(0.1);
};

function takepicture(rect) {
    var video = $("#video").get(0);
    buffer = document.createElement('canvas');
    source_img = source_context.getImageData(rect.x, rect.y, rect.width, rect.height);
    buffer.width = 92;
    buffer.height = 112;
    buffer_context = buffer.getContext('2d');
    buffer_context.drawImage(video, rect.x*2, rect.y*2 + 20, rect.width*2, rect.height*2, 0, 0, 92, 112);
    data = buffer.toDataURL('image/png');
    capture_faces[capture_faces.length] = data;
    $("#capture_" + index).attr('src', data);
}
//
//function SendData() {
//    var email = document.getElementById("email").value;
//    var password = document.getElementById("password").value;
////                alert(oldFace[0]);
//    //var faces = ['asdfasf', 'werwer'];
//    $.post("login", 
//        {
//            faces     : capture_faces,
//            email     : email,
//            password  : password
//        },
//        function(data) { 
//            console.log("Image Sent Successfully\n"); 
//    });
//}