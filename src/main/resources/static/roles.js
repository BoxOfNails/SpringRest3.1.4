function renderRoles() {
  const roleChecksboxes = document.querySelectorAll(
    "#role-container, #formRole",
  )
  const roles = [{
    id: 1,
    name: "USER"
  }, {
    id: 2,
    name: "ADMIN"
  }]
  let increment = 1;
  for (let role of roles) {
    roleChecksboxes.forEach((roleCheckbox) => {
      roleCheckbox.insertAdjacentHTML(
        "beforeend",
        `<div class="form-check mb-4" style="width:auto;">
      <input class="form-check-input role-checkbox" type="checkbox"
      id="roles-${role.id}-${increment}" value="${role.id}" name="roleIds" onchange="waterfallCheckbox(this)">
      <label class="form-check-label" for="roles-${role.id}-${increment}">
      ${role.name}</label>
      </div>`,
      )
      increment++;
    })
  }
  const checkboxes = document.querySelectorAll(".role-checkbox")
  checkboxes[0].checked = true
}

renderRoles()
