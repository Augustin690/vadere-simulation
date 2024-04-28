class DocHeader extends HTMLElement {
    constructor() {
        super();
        this.attachShadow({mode: 'open'});
        this.shadowRoot.innerHTML =
            '  <div class="doc-header-container">\n' +
            '  <a href=\'/back\'>&lt; Back</a>\n' +
            '    <h1><slot></slot></h1>' +
            '  </div>\n';
    }
}
customElements.define('doc-header', DocHeader);