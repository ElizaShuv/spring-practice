import React, { useState } from 'react';
import './App.css';
import { Products } from "./components/ProductCard";
import Cart from "./components/Cart";
import { products } from "./components/ProductCard";
import ProductManagement from "./components/ProductManagement";
import {Profile} from "./components/Profile";
import ProductManagementActivation from "./components/ProductManagementActivation";
import SearchBar from "./components/SearchBar";

function App() {
    const [cartItems, setCartItems] = useState([]);
    const [availableProducts, setAvailableProducts] = useState(products);
    const [searchText, setSearchText] = useState("");

    const addToCart = (product) => {
        const updatedCartItems = [...cartItems];
        const updatedAvailableProducts = [...availableProducts];

        const availableProduct = updatedAvailableProducts.find(
            (p) => p.id === product.id
        );

        if (availableProduct && availableProduct.count > 0) {
            updatedCartItems.push(product);
            availableProduct.count--;
        }

        setCartItems(updatedCartItems);
        setAvailableProducts(updatedAvailableProducts);
    };

    const handleRemoveItem = (productId, quantity) => {
        const updatedCartItems = cartItems.filter((item) => item.id !== productId);
        setCartItems(updatedCartItems);

        setAvailableProducts((prevAvailableProducts) => {
            const updatedProducts = prevAvailableProducts.map((product) => {
                if (product.id === productId) {
                    return {
                        ...product,
                        count: product.count + quantity
                    };
                }
                return product;
            });
            return updatedProducts;
        });
    };
    const handlePayClick = () => {
        alert('Оплата произведена!');
        setCartItems([]);
    };

    return (
        <div className="container">
            <h1>Продуктовый магазин</h1>
            <h4>Профиль</h4>
            <div>
                <h2><Profile/></h2>
            </div>
            <h4>Продукты</h4>
            <SearchBar />
            <div>
                <ProductManagementActivation
                    products={availableProducts}
                    setProducts={setAvailableProducts}
                />
            </div>
            <div className="main-container">
                <div className="products-container">
                    <Products products={availableProducts} addToCart={addToCart}/>
                </div>
            </div>
            <h4> </h4>
            <Cart cartItems={cartItems} onRemoveItem={handleRemoveItem} onPayClick={handlePayClick} />
        </div>
    );
}

    export default App;
