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
    msgDiv.setAttribute("class", "message_block");
    content.appendChild(msgDiv);

    var msgAuthor = document.createElement("div");
    msgAuthor.setAttribute("class", "post-author");
    msgAuthor.appendChild(document.createTextNode("NAME & DATE"));
    msgDiv.appendChild(msgAuthor);

    var msgText = document.createElement("div");
    msgText.setAttribute("class", "post-text");
    msgText.appendChild(document.createTextNode(msg.text));
    msgDiv.appendChild(msgText);

    var msgOptions = document.createElement("div");
    msgOptions.setAttribute("class", "post-Options");
    msgOptions.appendChild(document.createTextNode("comment"));
    msgDiv.appendChild(msgOptions);
}

function showForm() {
    document.getElementById("msgForm").style.display = '';
    document.getElementById("msg_start_button").style.display = "none";
}

function hideForm() {
    document.getElementById("msgForm").style.display = "none";
    document.getElementById("msg_start_button").style.display = '';
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
}