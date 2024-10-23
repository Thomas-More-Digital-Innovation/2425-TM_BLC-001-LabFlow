import { getCookie, fetchAll } from '$lib/globalFunctions';
import { goto } from '$app/navigation';
import { fetchAllWithoutPrefix } from '$lib/globalFunctions';

const token = getCookie('authToken') ?? '';
let testcategorieën: any[] = [];
let eenheden: any[] = [];
let stalen: any[] = [];
let filteredStalen: any[] = [];

// laden categorieën
export async function loadTestCategorieën() {
    if (token != null) {
        try {
            testcategorieën = await fetchAll(token, 'testcategorieen');
            return testcategorieën;
        } catch (error) {
            console.error("testcategorieën konden niet gefetched worden:", error);
        }
    } else {
        console.error("jwt error");
        goto('/login');
    }
}

// laden eenheden voor popup test aanmaken
export async function loadEenheden() {
    if (token != null) {
        try {
            eenheden = await fetchAll(token, 'readeenheid');
            return eenheden;
        } catch (error) {
            console.error("eenheden kon niet gefetched worden:", error);
        }
    } else {
        console.error("jwt error");
        goto('/login');
    }
}

// fetchen van stalen
export async function fetchStalen() {
    if (token) {
        try {
            const stalen = await fetchAll(token, 'stalen');
            const filteredStalen = stalen;
            return { stalen, filteredStalen };
        } catch (error) {
            console.error("Stalen konden niet gefetched worden:", error);
        }
    } else {
        console.error("JWT error: token missing of invalid");
        goto('/login');
    }
}

// fetchen van users
export async function fetchUsers() {
    if (token) {
        try {
            const users = await fetchAllWithoutPrefix(token, 'getusers');
            return users;
        } catch (error) {
            console.error("Users konden niet gefetched worden: ", error);
        }
    } else {
        console.error("JWT error: token missing of invalid");
        goto('/login');
    }
}