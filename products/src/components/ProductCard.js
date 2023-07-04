export const products = [
    {
        id: 1,
        imageUrl: 'https://koptevskij-rynok.ru/upload/iblock/9c1/9c13d3fdfa4cf3fc549a9929e34fffcf.jpeg',
        name: "Манго",
        price: 200,
        count: 20,
        imageSize: 90
    },
    {
        id: 2,
        imageUrl:'',
        name: "Ананас",
        price: 300,
        count: 10,
        imageSize: 90
    },
    {
        id: 3,
        imageUrl: 'https://palladi.ru/upload/iblock/36f/36fc8b6807c94ebb17bb3c8adde80632.jpg',
        name: "Апельсин",
        price: 50,
        count: 100,
        imageSize: 90
    },
    {
        id: 4,
        imageUrl: 'https://eng.kupimir.club/upload/iblock/ee7/ee7c3fceaefe7bf311b988fc2af63cf9.jpg',
        name: "Банан",
        price: 50,
        count: 100,
        imageSize: 90
    },
    {
        id: 5,
        imageUrl: 'https://iaysr.tmgrup.com.tr/4f7164/0/0/0/0/0/0?u=https://iahbr.tmgrup.com.tr/album/2017/10/20/her-gun-elma-yerseniz-1508504029908.png&mw=1024',
        name: "Яблоко",
        price: 20,
        count: 100,
        imageSize: 90
    },
]

export const Products = ({ products, addToCart }) => {
    const handleAddToCart = (product) => {
        addToCart(product);
    };

    return (
        <>
            {products.map((product) => (
                <div key={product.id} className="products-item">
                    <img
                        src={product.imageUrl}
                        alt={'Фото ' + product.name}
                        style={{
                            width: product.imageSize,
                            height: product.imageSize
                        }}
                    />
                    <div>{product.name}</div>
                    <div>Цена: {product.price}</div>
                    <div>Количество: {product.count}</div>
                    <button
                        onClick={() => handleAddToCart(product)}
                        disabled={product.count === 0}
                    >
                        Добавить в корзину
                    </button>
                </div>
            ))}
        </>
    );
};

