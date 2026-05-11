async function getData(url) {
  try {
    const response = await fetch(url)
    const data = await response.json()
    return data
  } catch (err) {
    console.error(err)
  }
}

async function renderTable() {
  try {
    const allUsers = await getData("http://localhost:8080/api/users")
    const userTable = document.querySelector("tbody")
    userTable.textContent = ""
    allUsers.forEach((singleUser) => {
      userTable.insertAdjacentHTML(
        "beforeend",
        `
    <tr>
      <td>${singleUser.id}</td>
      <td>${singleUser.username}</td>
      <td>${singleUser.firstName}</td>
      <td>${singleUser.lastName}</td>
      <td>${singleUser.email}</td>
      <td>${singleUser.roles.map((role) => role.name.replace("ROLE_", "")).join(", ")}</td>
      <td>
        <button type="button" class="btn btn-info btn-sm" data-bs-toggle="modal"
          data-bs-target="#updateModal" data-bs-my-mode="update"
          data-bs-id="${singleUser.id}">Update</button>
        <button type="button" class="btn btn-danger btn-sm"
        data-bs-toggle="modal" data-bs-target="#updateModal"
        data-bs-my-mode="delete"
        data-bs-id="${singleUser.id}">Delete</button>
      </td>
    </tr>`,
      )
    })
  } catch (err) {
    console.error(err)
  }
}

document.addEventListener("DOMContentLoaded", renderTable)
document.querySelector("#home-tab").addEventListener("click", renderTable)
