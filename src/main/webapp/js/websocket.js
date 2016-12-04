window.onload = init;
const url = "ws://"+ window.location.host + window.location.pathname;
const socket = new WebSocket(url);
socket.onmessage = onMessage;

function onMessage(event) {
    const msg = JSON.parse(event.data);
    printMsg(msg);
}

function addMsg(text, postId, type) {
    const msg = {
        post_id: postId,
        text: text,
        time: '',
        user_id: '',
        from_firstName: '',
        from_lastName: '',
        type: type
    };
    socket.send(JSON.stringify(msg));
}


function forPrintMsg(msg, parent) {
    const msgAuthorHeader = document.createElement("div");
    msgAuthorHeader.setAttribute("class", "post-header");
    parent.appendChild(msgAuthorHeader);

    const msgAuthor = document.createElement("a");
    msgAuthor.setAttribute("href", "/Profile/" + msg.user_id);
    msgAuthor.setAttribute("class", "post-author");
    msgAuthor.appendChild(document.createTextNode(msg.from_firstName + " " + msg.from_lastName ));
    msgAuthorHeader.appendChild(msgAuthor);

    const msgDate = document.createElement("div");
    msgDate.setAttribute("class", "post-date");
    msgDate.appendChild(document.createTextNode(msg.time));
    msgAuthorHeader.appendChild(msgDate);

    const msgText = document.createElement("pre");
    msgText.setAttribute("class", "post-text");
    msgText.appendChild(document.createTextNode(msg.text));
    parent.appendChild(msgText);
}

function printMsg(msg) {
    const relation = document.body.dataset.relation;
    if (msg.post_id === -1) {
        const content = document.getElementById("content");
        const mD = document.createElement("div");
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
        content.insertBefore(mD, content.getElementsByTagName("div")[0]);
        if (relation != 0) {
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
        }

    } else if (relation != 0) {
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
    i.focus();
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

function showForm() {
    const form = document.getElementById("msgForm");
    form.style.display = '';
    document.getElementById("msg_start_button").style.display = "none";
}

function hideForm() {
    document.getElementById("msgForm").style.display = "none";
    document.getElementById("msg_start_button").style.display = '';
}

function init() {
    hideForm();
}