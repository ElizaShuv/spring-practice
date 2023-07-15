import React, { useState } from 'react';

const ProductManagement = ({ products, setProducts }) => {
    const [newProduct, setNewProduct] = useState({
        id: '',
        imageUrl: '',
        name: '',
        price: '',
        count: '',
        imageSize: 90
    });
    const [editProduct, setEditProduct] = useState(null);

    const handleCreateProduct = () => {
        setProducts([...products, newProduct]);
        setNewProduct({
            id: '',
            imageUrl: '',
            name: '',
            price: '',
            count: '',
            imageSize: 90
        });
    };

    const handleEditProduct = (id, updatedProduct) => {
        setProducts(products.map((product) =>
            product.id === id ? { ...product, ...updatedProduct } : product
        ));
        setEditProduct(null); // Выход из режима редактирования после сохранения
    };

    const handleDeleteProduct = (id) => {
        setProducts(products.filter((product) => product.id !== id));
    };

    return (
        <div>
            <h2>Управление продуктами</h2>
            <div>
                <h3>Создание продукта</h3>
                <input
                    type="text"
                    placeholder="Имя"
                    value={newProduct.name}
                    onChange={(e) => setNewProduct({ ...newProduct, name: e.target.value })}
                />
                <input
                    type="number"
                    placeholder="Цена"
                    value={newProduct.price}
                    onChange={(e) => setNewProduct({ ...newProduct, price: e.target.value })}
                />
                <input
                    type="number"
                    placeholder="Количество"
                    value={newProduct.count}
                    onChange={(e) => setNewProduct({ ...newProduct, count: e.target.value })}
                />
                <input
                    type="text"
                    placeholder="Изображение"
                    value={newProduct.imageUrl}
                    onChange={(e) => setNewProduct({ ...newProduct, imageUrl: e.target.value })}
                />
                <button onClick={handleCreateProduct}>Создать продукт</button>
            </div>
            <div>
                <h3>Редактирование продукта</h3>
                {editProduct ? (
                    <div>
                        <input
                            type="text"
                            value={editProduct.name}
                            onChange={(e) => setEditProduct({ ...editProduct, name: e.target.value })}
                        />
                        <input
                            type="number"
                            value={editProduct.price}
                            onChange={(e) => setEditProduct({ ...editProduct, price: e.target.value })}
                        />
                        <input
                            type="number"
                            value={editProduct.count}
                            onChange={(e) => setEditProduct({ ...editProduct, count: e.target.value })}
                        />
                        <input
                            type="text"
                            value={editProduct.imageUrl}
                            onChange={(e) => setEditProduct({ ...editProduct, imageUrl: e.target.value })}
                        />
                        <button onClick={() => handleEditProduct(editProduct.id, editProduct)}>Сохранить</button>
                    </div>
                ) : (

                    // Отображение продуктов и кнопка редактирования
                    products.map((product) => (
                        <div key={product.id}>
                            <div>{product.name}</div>
                            <button onClick={() => setEditProduct(product)}>Редактировать</button>
                            <button onClick={() => handleDeleteProduct(product.id)}>Удалить</button>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
};

export default ProductManagement;
