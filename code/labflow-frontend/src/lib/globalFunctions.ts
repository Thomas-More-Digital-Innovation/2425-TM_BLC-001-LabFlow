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


// https://jasonwatmore.com/fetch-add-bearer-token-authorization-header-to-http-request#:~:text=The%20auth%20header%20with%20bearer,to%20the%20fetch()%20function.
// https://stackoverflow.com/questions/51264913/how-to-add-authorization-token-in-incoming-http-request-header
export async function fetchAll(token: string, subject: string): Promise<any[]> {
    // deze header is de jwt token nodig voor authenticatie
    const headers = {
        "Authorization": "Bearer " + token
    };

    const response = await fetch(`http://localhost:8080/api/${subject}`, { headers })
        .then(response => response.json());
        return response;
}


// https://stackoverflow.com/questions/10730362/get-cookie-by-name 
export function getCookie(name: string) {
    if (typeof document === 'undefined') {
        return null;
    }
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) {
        const part = parts.pop();
        if (part) return part.split(';').shift();
    }
    return null;
}