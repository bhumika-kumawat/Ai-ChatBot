const chatBox = document.getElementById("chatBox");
const messageInput = document.getElementById("messageInput");
const sendButton = document.getElementById("sendButton");


async function sendMessage() {

    const message = messageInput.value.trim();

    if (message === "") {
        return;
    }

    // Show user message
    addMessage(message, "user-message");

    // Clear input
    messageInput.value = "";

    try {

        const response = await fetch("/api/chat", {
            method: "POST",
            headers: {
                "Content-Type": "text/plain"
            },
            body: message
        });

        console.log("Response status:", response.status);

        const botResponse = await response.text();

        console.log("Bot response:", botResponse);

        if (!response.ok) {
            throw new Error(botResponse);
        }

        // Show bot response
        addMessage(botResponse, "bot-message");

    } catch (error) {

        console.error("Error:", error);

        addMessage(
            "Sorry, something went wrong. Please try again.",
            "bot-message"
        );
    }
}


function addMessage(message, type) {

    const messageDiv = document.createElement("div");

    messageDiv.classList.add("message", type);

    const avatar = document.createElement("div");
    avatar.classList.add("avatar");

    if (type === "bot-message") {
        avatar.textContent = "AI";
    } else {
        avatar.textContent = "You";
    }

    const content = document.createElement("div");
    content.classList.add("message-content");

    const paragraph = document.createElement("p");
    paragraph.textContent = message;

    content.appendChild(paragraph);

    messageDiv.appendChild(avatar);
    messageDiv.appendChild(content);

    chatBox.appendChild(messageDiv);

    // Automatically scroll to bottom
    chatBox.scrollTop = chatBox.scrollHeight;
}


// Send when button is clicked
sendButton.addEventListener("click", sendMessage);


// Send when Enter is pressed
messageInput.addEventListener("keydown", function(event) {

    if (event.key === "Enter") {
        sendMessage();
    }

});