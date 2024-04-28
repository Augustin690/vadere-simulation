class WarningBox extends HTMLElement {
    constructor() {
        super();
        this.attachShadow({mode: 'open'});
        this.shadowRoot.innerHTML =
            '  <div class="warning-box-container">\n' +
            '    <strong> ⚠ Warning:</strong><br>\n' +
            '    <slot></slot>\n' +
            '  </div>\n';
    }
}
customElements.define('warning-box', WarningBox);