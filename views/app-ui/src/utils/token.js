import Cookie from 'js-cookie'

function setCookie(key, value) {
    Cookie.set(key, value);
}

export function setToken(value) {
    Cookie.set('token', value);
}

export function getToken() {
    return Cookie.get('token');
}

export function removeToken() {
    Cookie.remove('token');
}