window.onload = init;
const url = "ws://localhost:8080" + window.location.pathname;
const socket = new WebSocket(url);
socket.onmessage = onMessage;

function onMessage(event) {
    const msg = JSON.parse(event.data);
    printMsg(msg);
}

function addMsg(text, postId, type) {
    const msg = {
        // user_id: userId,
        post_id: postId,
        text: text,
        from_firstName: '',
        from_lastName: '',
        type: type
    };
    socket.send(JSON.stringify(msg));
}


function forPrintMsg(msg, parent) {
    const msgAuthor = document.createElement("div");
    msgAuthor.setAttribute("class", "post-author");
    msgAuthor.appendChild(document.createTextNode(msg.from_firstName));
    parent.appendChild(msgAuthor);

    const msgText = document.createElement("div");
    msgText.setAttribute("class", "post-text");
    msgText.appendChild(document.createTextNode(msg.text));
    parent.appendChild(msgText);
}

function printMsg(msg) {
    if (msg.post_id === -1) {
        const content = document.getElementById("content");
        const mD = document.createElement("div");
        // mD.setAttribute("id", msg.id);
        mD.setAttribute("class", "post-wrapper");

        const post = document.createElement("div");
        post.setAttribute("class", "message_block");
        mD.appendChild(post);

        const comments = document.createElement("div");
        comments.setAttribute("id", msg.id);
        comments.setAttribute("value", "0");
        comments.setAttribute("class", "comments-wrapper");
        mD.appendChild(comments);

        forPrintMsg(msg, post);
        post.appendChild(addButton(document.body.dataset.cmsg, () => {
            if (comments.getAttribute('value') === '0') {
                comments.setAttribute('value', '1');
                addMsg('', comments.id, "comments");
                createForm(comments.id);
            } else {
                comments.style.display = '';
            }
            comments.parentNode.getElementsByTagName("button")[0].style.display = "none";
        }));

        content.insertBefore(mD, content.getElementsByTagName("div")[0]);

    } else {
        const comment = document.getElementById(msg.post_id);
        if (comment.getAttribute("value") !== '0') {
            const msgDiv = document.createElement("div");
            msgDiv.setAttribute("id", msg.id);
            msgDiv.setAttribute("class", "comment_block");
            comment.insertBefore(msgDiv, comment.getElementsByTagName("form")[0]);
            forPrintMsg(msg, msgDiv);
        }
    }
}

function addButton(value, evt) {
    const b = document.createElement("button");
    b.setAttribute("class", "post-button");
    b.setAttribute("value", value);
    b.appendChild(document.createTextNode(value));
    b.addEventListener('click', evt, false);
    return b;
}

function startFormSubmit() {
    const form = document.getElementById("msgForm");
    formSubmit(form, -1);
    hideForm();
}

function formSubmit(form, postId) {
    const text = form.elements["text"].value;
    // const userId = document.getElementById("msgForm").elements["userId"].value;
    form.reset();
    addMsg(text, postId, "add");
}

function createForm(postId) {
    const f = document.createElement("form");
    f.setAttribute("class", "comment-form");
    f.setAttribute("action", "");
    const i = document.createElement("textarea");
    i.setAttribute('class', "comment-input");
    i.setAttribute('name', "text");
    i.setAttribute('maxlength', '255');
    i.autofocus;
    f.appendChild(i);
    f.appendChild(addButton(document.body.dataset.smsg, (evt) => {
        formSubmit(document.getElementById(postId).getElementsByTagName("form")[0], f.parentNode.id);
        evt.preventDefault();
    }));
    f.appendChild(addButton(document.body.dataset.rmsg, (evt) => {
        hideComments(postId);
        document.getElementById(postId).parentNode.getElementsByTagName("button")[0].style.display = "";
        evt.preventDefault();
    }));
    document.getElementById(postId).appendChild(f);
}

function hideComments(postId) {
    const element = document.getElementById(postId);
    element.style.display = "none";
}

// function getComments(postId) {
//     const commentWrap = document.getElementById(postId);
//     commentWrap.setAttribute('value', '1');
//     addMsg('', 1, postId, "comments");
//     commentWrap.parentNode.getElementsByTagName("button")[0].style.display = "none";
//     createForm(postId);
// }

function showForm() {
    document.getElementById("msgForm").style.display = '';
    document.getElementById("msg_start_button").style.display = "none";
}

function hideForm() {
    document.getElementById("msgForm").style.display = "none";
    document.getElementById("msg_start_button").style.display = '';
}

function init() {
    hideForm();
}