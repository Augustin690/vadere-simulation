class DocMember extends HTMLElement {

    constructor() {
        super();
        this.attachShadow({mode: 'open'});

        this.shadowRoot.innerHTML =
            '<div class="doc-member-container">\n' +
            '<h2 class="member-name"></h2>' +
            '<p><slot></slot></p>\n' +
            '</div>\n';

    }

    static get observedAttributes() {
        return ['name','href','type'];
    }

    attributeChangedCallback(name, oldValue, newValue) {
        if (name === 'name') {
            this.shadowRoot.querySelector('.member-name').innerHTML = newValue;
        }
        if (name === 'type') {
            this.shadowRoot.querySelector('.member-name').innerHTML = this.getAttribute("name") + ' : ' + newValue;
        }
        if (name === 'href') {
            if(newValue !=="") {
                this.shadowRoot.querySelector('.member-name').innerHTML = this.getAttribute("name") + ' : ' + "<a  href='" + this.getAttribute("href") + "'>" + this.getAttribute("type") + "</a>"
            }
        }
    }
}
customElements.define('doc-member', DocMember);