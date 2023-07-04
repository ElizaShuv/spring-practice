import React, { useState } from 'react';
import ProductManagement from './ProductManagement';

const ProductManagementActivation = ({ products, setProducts }) => {
    const [isProductManagementActive, setProductManagementActive] = useState(false);

    const toggleProductManagement = () => {
        setProductManagementActive(!isProductManagementActive);
    };

    return (
        <div>
            <button onClick={toggleProductManagement}>
                {isProductManagementActive ? 'Выйти из редактора' : 'Редактировать'}
            </button>
            {isProductManagementActive && (
                <ProductManagement products={products} setProducts={setProducts} />
            )}
        </div>
    );
};

export default ProductManagementActivation;