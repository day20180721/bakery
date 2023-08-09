class MyElement extends HTMLElement {
    constructor() {
        super();
        // 元素在这里创建
    }

    connectedCallback() {
        // 在元素被添加到文档之后，浏览器会调用这个方法
        //（如果一个元素被反复添加到文档／移除文档，那么这个方法会被多次调用）
        console.log("connectedCallback");
    }

    disconnectedCallback() {
        // 在元素从文档移除的时候，浏览器会调用这个方法
        // （如果一个元素被反复添加到文档／移除文档，那么这个方法会被多次调用）
        console.log("disconnectedCallback");
    }

    static get observedAttributes() {
        return ["id"];
    }

    attributeChangedCallback(name, oldValue, newValue) {
        // 当上面数组中的属性发生变化的时候，这个方法会被调用
        console.log("attributeChangedCallback");
    }

    adoptedCallback() {
        // 在元素被移动到新的文档的时候，这个方法会被调用
        // （document.adoptNode 会用到, 非常少见）
        console.log("adoptedCallback");
    }

    // 还可以添加更多的元素方法和属性
}
customElements.define("my-element", MyElement);