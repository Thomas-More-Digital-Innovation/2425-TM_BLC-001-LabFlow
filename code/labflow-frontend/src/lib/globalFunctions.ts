import { jwtDecode } from "jwt-decode";
import type { DecodedToken } from "$lib/types";


export function getRol() {
    const token = getCookie('authToken');
    let rol: string | undefined;
    if (token) {
        const decoded = jwtDecode<DecodedToken>(token);
        rol = decoded.rol;
    }
    return rol;
}


// https://stackoverflow.com/questions/10730362/get-cookie-by-name 
export function getCookie(name: string) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) {
        const part = parts.pop();
        if (part) return part.split(';').shift();
    }
}