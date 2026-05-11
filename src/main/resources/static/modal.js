const updateModal = document.getElementById("updateModal")
if (updateModal) {
  updateModal.addEventListener("show.bs.modal", async (event) => {
    const allUsers = await getData("http://localhost:8080/api/users")
    // Button that triggered the modal
    const button = event.relatedTarget

    // Extract info from data-* attributes
    const userId = button.getAttribute("data-bs-id")
    const mode = button.getAttribute("data-bs-my-mode")
    const user = allUsers.find((u) => u.id == userId)

    // Extract the elements
    const modalTitle = updateModal.querySelector(".modal-title")
    const formControl = updateModal.querySelectorAll(".to-be-disabled")
    const formUpdateButton = updateModal.querySelector("#updateButton")
    const formDeleteButton = updateModal.querySelector("#deleteButton")
    const formPasswordContainer =
      updateModal.querySelector("#passwordContainer")

    const checkboxes = updateModal.querySelectorAll(".role-checkbox")

    const formId = updateModal.querySelector("#formId")
    const formUsername = updateModal.querySelector("#formUsername")
    const formPassword = updateModal.querySelector("#formPassword")
    const formFirstName = updateModal.querySelector("#formFirstName")
    const formLastName = updateModal.querySelector("#formLastName")
    const formEmail = updateModal.querySelector("#formEmail")
    const formRole = updateModal.querySelector("#formRole")

    // Prepopulate the form
    formId.value = user.id
    formUsername.value = user.username
    formPassword.value = user.formPassword
    formFirstName.value = user.firstName
    formLastName.value = user.lastName
    formEmail.value = user.email

    checkboxes.forEach((cb) => {
      const hasRole = user.roles.some((role) => role.id.toString() === cb.value)
      cb.checked = hasRole
    })

    if (!Array.from(checkboxes).some((cb) => cb.checked)) {
      checkboxes[0].checked = true
    }

    // Update the modal's content based on which button was pressed.

    if (mode == `update`) {
      modalTitle.textContent = `Update user`
      formRole.disabled = false
      formControl.forEach((element) => {
        element.disabled = false
      })
      checkboxes.forEach((element) => {
        element.disabled = false
      })
      formPasswordContainer.classList.remove("d-none")
      formPasswordContainer.classList.add("d-flex")
      formDeleteButton.hidden = true
      formUpdateButton.hidden = false
    } else {
      modalTitle.textContent = `Delete user`
      formDeleteButton.setAttribute("href", `/admin/delete?userId=${user.id}`)
      formRole.disabled = true
      formControl.forEach((element) => {
        element.disabled = true
      })
      checkboxes.forEach((element) => {
        element.disabled = true
      })
      formPasswordContainer.classList.add("d-none")
      formPasswordContainer.classList.remove("d-flex")
      formUpdateButton.hidden = true
      formDeleteButton.hidden = false
    }
  })
}
