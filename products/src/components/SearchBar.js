import React, { useState } from 'react';

const SearchBar = () => {
    const [searchCompleted, setSearchCompleted] = useState(false);

    const handleSearchSubmit = (event) => {
        event.preventDefault();
        setSearchCompleted(true);
    };

    const handleInputChange = () => {
        setSearchCompleted(false);
    };

    return (
        <div>
            <form onSubmit={handleSearchSubmit}>
                <input type="text" placeholder="Поиск продуктов..." onChange={handleInputChange} />
                <button type="submit">Поиск</button>
            </form>
            {searchCompleted && <p>Поиск выполнен</p>}
        </div>
    );
}

export default SearchBar;

