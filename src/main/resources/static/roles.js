async function renderRoles() {
  const roleChecksboxes = document.querySelectorAll(
    "#role-container, #formRole",
  )
  const roles = await getData("http://localhost:8080/api/roles")
  for (let role of roles) {
    roleChecksboxes.forEach((roleCheckbox) => {
      roleCheckbox.insertAdjacentHTML(
        "beforeend",
        `<div class="form-check mb-4" style="width:auto;">
      <input class="form-check-input role-checkbox" type="checkbox"
      id="roles-${role.id}" value="${role.id}" name="roleIds" onchange="waterfallCheckbox(this)">
      <label class="form-check-label" for="roles-${role.id}">
      ${role.name.replace("ROLE_", "")}</label>
      </div>`,
      )
    })
  }
  const checkboxes = document.querySelectorAll(".role-checkbox")
  checkboxes[0].checked = true
}

renderRoles()
