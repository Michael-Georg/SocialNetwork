window.onload = init;
var url = "ws://localhost:8080" + window.location.pathname;
var socket = new WebSocket(url);
socket.onmessage = onMessage;

function onMessage(event) {
    var msg = JSON.parse(event.data);
    printMsg(msg);
}

function addMsg(text, userId) {
    var msg = {
        user_id: userId,
        text: text
    };
    socket.send(JSON.stringify(msg));
}

function printMsg(msg) {
    var content = document.getElementById("content");

    var msgDiv = document.createElement("div");
    msgDiv.setAttribute("id", msg.id);
    msgDiv.setAttribute("class", "post");
    content.appendChild(msgDiv);
    var msgAuthor = document.createElement("div");

    msgDiv.setAttribute("class", "post-author");
    msgDiv.appendChild(msgAuthor);
    var msgText = document.createElement("div");

    msgText.setAttribute("class", "post-text");
    msgText.appendChild(document.createTextNode(msg.text));
    content.appendChild(msgText);
}

function showForm() {
    document.getElementById("msgForm").style.display = '';
}

function hideForm() {
    document.getElementById("msgForm").style.display = "none";
}

function formSubmit() {
    var form = document.getElementById("msgForm");
    var text = form.elements["text"].value;
    var userId = form.elements["userId"].value;
    hideForm();
    document.getElementById("msgForm").reset();
    addMsg(text, userId);
}

function init() {
    hideForm();
    var content = document.getElementById("content");

    var info = document.createElement("p");
    info.setAttribute("class", "post-text");
    info.appendChild(document.createTextNode("URL : " + url));
    content.appendChild(info);
}