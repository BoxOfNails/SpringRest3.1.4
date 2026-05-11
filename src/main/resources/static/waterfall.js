function waterfallCheckbox(clickedCheckbox) {
  const container = clickedCheckbox.closest(".modal-body") || document
  const checkboxes = Array.from(container.querySelectorAll(".role-checkbox"))
  const index = checkboxes.indexOf(clickedCheckbox)

  if (clickedCheckbox.checked) {
    for (let i = 0; i < index; i++) {
      checkboxes[i].checked = true
    }
  } else {
    for (let i = index + 1; i < checkboxes.length; i++) {
      checkboxes[i].checked = false
    }
  }

  const anyChecked = checkboxes.some((cb) => cb.checked)
  if (!anyChecked) {
    checkboxes[0].checked = true
  }
}
