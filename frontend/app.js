const API_URL = "http://localhost:8080";

async function uploadFile() {

    const fileInput =
        document.getElementById("fileInput");

    const file =
        fileInput.files[0];

    if (!file) {
        alert("Please select a file");
        return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {

        const response = await fetch(
            API_URL + "/files/upload",
            {
                method: "POST",
                body: formData
            }
        );

        const result =
            await response.text();

        alert(result);

        fileInput.value = "";

        loadFiles();

    } catch (error) {

        console.error(error);

        alert("Upload failed");
    }
}

async function loadFiles() {

    try {

        const response =
            await fetch(
                API_URL + "/files"
            );

        const files =
            await response.json();

        const table =
            document.getElementById(
                "fileTable"
            );

        table.innerHTML = "";

        files.forEach(file => {

            table.innerHTML += `
                <tr>

                    <td>${file.id}</td>

                    <td>${file.fileName}</td>

                    <td>
                        <span class="version-badge">
                            V${file.currentVersion}
                        </span>
                    </td>

                    <td>

                        <a
                            class="download-btn"
                            href="http://localhost:8080/files/download/${file.id}">
                            Download
                        </a>

                        <button
                            class="history-btn"
                            onclick="showHistory(${file.id})">
                            History
                        </button>

                        <button
                            class="rollback-btn"
                            onclick="rollbackFile(${file.id})">
                            Rollback
                        </button>

                        <button
                            class="delete-btn"
                            onclick="deleteFile(${file.id})">
                            Delete
                        </button>

                    </td>

                </tr>
            `;
        });

    } catch (error) {

        console.error(error);

        alert("Unable to load files");
    }
}

async function deleteFile(id) {

    const confirmDelete =
        confirm(
            "Are you sure you want to delete this file?"
        );

    if (!confirmDelete) {
        return;
    }

    try {

        const response =
            await fetch(
                API_URL + "/files/" + id,
                {
                    method: "DELETE"
                }
            );

        if (!response.ok) {
            throw new Error();
        }

        alert("File deleted successfully");

        loadFiles();

    } catch (error) {

        console.error(error);

        alert("Delete failed");
    }
}

async function showHistory(fileId) {

    try {

        const response =
            await fetch(
                API_URL + "/versions/" + fileId
            );

        const versions =
            await response.json();

        let historyText =
            "Version History\n\n";

        versions.forEach(version => {

            historyText +=
                "Version "
                + version.versionNumber
                + " - "
                + version.changeDescription
                + "\n";
        });

        alert(historyText);

    } catch (error) {

        console.error(error);

        alert("Unable to load version history");
    }
}

async function rollbackFile(fileId) {

    const versionNumber =
        prompt(
            "Enter version number to rollback to:"
        );

    if (!versionNumber) {
        return;
    }

    try {

        const response =
            await fetch(
                API_URL +
                "/versions/rollback/" +
                fileId +
                "/" +
                versionNumber,
                {
                    method: "POST"
                }
            );

        if (!response.ok) {
            throw new Error();
        }

        const result =
            await response.json();

        alert(
            "Rolled back to Version "
            + result.versionNumber
        );

        loadFiles();

    } catch (error) {

        console.error(error);

        alert("Rollback failed");
    }
}

window.onload = loadFiles;