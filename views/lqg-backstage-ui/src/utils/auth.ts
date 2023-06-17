import cookie from 'js-cookie'
// ts 使用 npm i --save-dev @types/js-cookie

export function setAuth(token: string) {
    cookie.set('admin-token', token);
}

export function getAuth() {
    return cookie.get('admin-token');
}

export function removeAuth() {
    cookie.remove('admin-token')
}