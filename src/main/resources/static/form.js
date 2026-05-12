const formElem = document.querySelector("#newUserForm")
const formElem2 = document.querySelector("#userForm")
const deleteButton = document.querySelector("#deleteButton")

formElem.onsubmit = async (e) => {
  const response = await submitData("POST", e, formElem)
  if (response) {
    formElem.reset()
    formElem.querySelector(`input[type="checkbox"]`).checked = true
    alert("Added new user.")
  }
}

function closeModal() {
  const modalElement = document.querySelector("#updateModal")
  const modalInst = bootstrap.Modal.getInstance(modalElement)
  modalInst.hide()
}

formElem2.onsubmit = async (e) => {
  const response = await submitData("PUT", e, formElem2)
  if (response) {
    closeModal()
  }
}

deleteButton.onclick = async (e) => {
  try {
    e.preventDefault()
    const theUserId = document.querySelector("#formId").value
    let response = await fetch(`/api/users?userId=${theUserId}`, {
      method: "DELETE",
    })
    if (response) {
      closeModal()
      renderTable()
    }
  } catch (err) {
    console.error(err)
  }
}

async function submitData(meth, e, el) {
  e.preventDefault()
  try {
    formData = new FormData(el)
    const userObj = Object.fromEntries(formData.entries())

    userObj.roleIds = formData.getAll("roleIds").map(Number)
    const requestBody = JSON.stringify(userObj)

    let response = await fetch("/api/users", {
      method: meth,
      headers: {
        "Content-Type": "application/json",
      },
      body: requestBody,
    })
    let result = await response.json()

    renderTable()
    return result
  } catch (err) {
    console.error(err)
  }
}
