const user = {
    name: 'Иван',
    login: 'ivan',
    email: 'ivan@mail.ru'
}
export const Profile = () => {
    return (
        <>
            <div>{user.name}</div>
            <div> {user.login}</div>
            <div> E-mail: { user.email}</div>
        </>
    );
}