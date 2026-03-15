function fetchCustomerDetails() {
    var select = document.getElementById("customer-select");
    var selectedName = select.value;
    var card = document.getElementById("customer-card");
    var loader = document.getElementById("loader");

    // Hide card initially when changing selection
    card.classList.remove("active");

    if (!selectedName) {
        setTimeout(function () {
            card.style.display = "none";
        }, 500); // Wait for transition
        return;
    }

    // Show loader
    loader.style.display = "block";
    card.style.display = "none";

    // Artificial delay for loading UX
    setTimeout(function () {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "customers.xml?t=" + new Date().getTime(), true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                loader.style.display = "none";
                card.style.display = "block";

                if (xhr.status === 200 && xhr.responseXML) {
                    var xmlDoc = xhr.responseXML;
                    var customers = xmlDoc.getElementsByTagName("customer");

                    for (var i = 0; i < customers.length; i++) {
                        var nameNode = customers[i].getElementsByTagName("name")[0];
                        if (nameNode && nameNode.textContent === selectedName) {
                            var customer = customers[i];

                            // Parse fields
                            var name = customer.getElementsByTagName("name")[0]?.textContent || "N/A";
                            var email = customer.getElementsByTagName("email")[0]?.textContent || "N/A";
                            var phone = customer.getElementsByTagName("phone")[0]?.textContent || "N/A";
                            var shipping = customer.getElementsByTagName("shipping_address")[0]?.textContent || "N/A";
                            var city = customer.getElementsByTagName("city")[0]?.textContent || "N/A";
                            var country = customer.getElementsByTagName("country")[0]?.textContent || "N/A";
                            var zip = customer.getElementsByTagName("zipcode")[0]?.textContent || "N/A";
                            var membership = customer.getElementsByTagName("membership")[0]?.textContent || "Standard";
                            var totalOrders = customer.getElementsByTagName("total_orders")[0]?.textContent || "0";
                            var lastOrderDate = customer.getElementsByTagName("last_order_date")[0]?.textContent || "N/A";
                            var image = customer.getElementsByTagName("image")[0]?.textContent || "";

                            // Populate Data
                            document.getElementById("detail-name").textContent = name;
                            document.getElementById("detail-email").textContent = email;
                            document.getElementById("detail-phone").textContent = phone;
                            document.getElementById("detail-street").textContent = shipping;
                            document.getElementById("detail-city-country").textContent = city + ", " + country;
                            document.getElementById("detail-zip").textContent = zip;
                            document.getElementById("detail-orders").textContent = totalOrders;
                            document.getElementById("detail-lastorder").textContent = lastOrderDate;

                            // Membership Badge Styling
                            var memBadge = document.getElementById("detail-membership");
                            memBadge.textContent = membership;
                            if (membership.toLowerCase().includes("prime")) {
                                memBadge.classList.add("prime");
                            } else {
                                memBadge.classList.remove("prime");
                            }

                            // Image Handling
                            var imageEl = document.getElementById("detail-image");
                            if (image) {
                                imageEl.src = image;
                            } else {
                                imageEl.src = "data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='%23cccccc'><path d='M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z'/></svg>";
                            }

                            // Trigger animation
                            setTimeout(function () {
                                card.classList.add("active");
                            }, 50);

                            break;
                        }
                    }
                }
            }
        };
        xhr.send();
    }, 400); // 400ms loader delay
}
