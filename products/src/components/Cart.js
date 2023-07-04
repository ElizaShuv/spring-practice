const CartItem = ({ product, quantity, onRemove }) => {
    return (
        <div className="cart-item">
            <div>{product.name}</div>
            <div>Цена: {product.price}</div>
            <div>Количество: {quantity}</div>
            <div>Сумма: {product.price * quantity}</div>
            <button onClick={() => onRemove(product.id, quantity)}>Удалить</button>
        </div>
    );
};

const Cart = ({ cartItems, onRemoveItem, onPayClick }) => {
    const groupedCartItems = cartItems.reduce((acc, item) => {
        const existingItem = acc.find((el) => el.product.id === item.id);
        if (existingItem) {
            existingItem.quantity += 1;
        } else {
            acc.push({product: item, quantity: 1});
        }
        return acc;
    }, []);

    const totalSum = groupedCartItems.reduce((acc, item) => {
        return acc + item.product.price * item.quantity;
    }, 0);


    return (
        <div className="cart-container, products-item">
            <h2>Корзина</h2>
            {groupedCartItems.map((item) => (
                <CartItem
                    key={item.product.id}
                    product={item.product}
                    quantity={item.quantity}
                    onRemove={onRemoveItem}
                    />
                ))}
                <div className="total-sum">Общая сумма: {totalSum}</div>
                <button onClick={onPayClick}>Оплатить</button>
            </div>
        );
    };
export default Cart;
