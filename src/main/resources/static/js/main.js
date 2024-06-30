//API CONTEXT
getProducts = () => {
  return fetch("/api/products")
    .then(response => response.json());
}

getCurrentOffer = () => {
    return fetch("/api/current-offer")
        .then(response => response.json());
}

const addProductToCart = (productId) => {
    return fetch(`/api/add-to-cart/${productId}`, {
        method: 'POST'
    });
}

const emptyCart = () => {
    return fetch("api/empty-the-cart/", {method:'POST'});

}

const acceptOffer = (acceptOfferRequest) => {
    return fetch("/api/accept-offer", {
        method: 'POST',
        headers: {
            "Content-Type":"application/json"
        },
        body:JSON.stringify(acceptOfferRequest)
    })
       .then(response => response.json());
}

createProductHtmlEl = (productData) => {
    const template = `
        <div>
            <img src="https://i.natgeofe.com/k/0dc6e854-7054-4249-98c7-86de63e6333a/orca-spyhopping_3x4.jpg" width= 200 height = 200 />
            <h4>${productData.name}</h4>
            <span>${productData.description}</span><br>
            <span>Price: ${productData.price} PLN</span>
            <button data-id ="${productData.id}"> Add to cart</button>
        </div>
    `;
    const newEl = document.createElement("li");
    newEl.innerHTML = template.trim();
    return newEl;
}

const refreshCurrentOffer = () => {
    const totalEl = document.querySelector('#offer_total');
    const totalDiscount = document.querySelector('#offer_total_after_discount');
    const itemsCountEl = document.querySelector('#offer_items-count');
    const discount = document.querySelector('#discount');
    const saved = document.querySelector('#saved');


    getCurrentOffer()
        .then(offer => {
            totalEl.textContent = `${offer.totalBeforeDiscount} PLN`;
            itemsCountEl.textContent = `${offer.quantity} 🛒`;
            saved.textContent = `You have saved ${offer.totalBeforeDiscount - offer.totalAfterDiscount} PLN`;
            totalDiscount.textContent = `Price After Discount: ${offer.totalAfterDiscount} PLN`;


            switch(offer.totalBeforeDiscount >= 0) {
                case offer.totalBeforeDiscount >= 300:
                    discount.textContent = ``;
                    break;
                case offer.totalBeforeDiscount >= 100:

                    discount.textContent = `Spend ${300-(offer.totalBeforeDiscount)} PLN more to get a -20% discount on your purchase`;
                    break;
                case offer.totalBeforeDiscount >= 0.1:
                    discount.textContent = `Spend ${100-(offer.totalBeforeDiscount)} PLN more to get a -10% discount on your purchase`;
                    break;
                default:
                    break;

            }
        })
}

const emptyCartBtn = document.querySelector("#emptyC");
emptyCartBtn.addEventListener("click", () => {
    emptyCart().then(() => refreshCurrentOffer());
});

const initializeCartHandler = (productHtmlEl) => {
    const addToCartBtn = productHtmlEl.querySelector("button[data-id]");
    addToCartBtn.addEventListener("click", () => {
        const productId = event.target.getAttribute("data-id");
        addProductToCart(productId)
            .then(() => refreshCurrentOffer());
    });
    return productHtmlEl;
}

const checkoutFormEl = document.querySelector('#checkout');
checkoutFormEl.addEventListener("submit", (event) => {
    event.preventDefault();

    const acceptOfferRequest = {
        firstName: checkoutFormEl.querySelector('input[name="first_name"]').value,
        lastName: checkoutFormEl.querySelector('input[name="lastname_name"]').value,
        email: checkoutFormEl.querySelector('input[name="email"]').value,
    }

    acceptOffer(acceptOfferRequest)
        .then(reservationDetails => window.location.href = reservationDetails.paymentUrl)
});

document.addEventListener("DOMContentLoaded", () => {
    const productsList = document.querySelector("#productsList");
    getProducts()
        .then(productsAsJson => productsAsJson.map(createProductHtmlEl))
        .then(productsHtmls => productsHtmls.map(initializeCartHandler))
        .then(productsHtmls => {
            productsHtmls.forEach(htmlEl => productsList.appendChild(htmlEl))
        });
})

