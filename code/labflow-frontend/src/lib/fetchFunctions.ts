import { getCookie, fetchAll } from '$lib/globalFunctions';
import { goto } from '$app/navigation';

const token = getCookie('authToken') ?? '';
let testcategorieën: any[] = [];
let eenheden: any[] = [];


// laden categorieën
export async function loadTestCategorieën() {
    if (token != null) {
        try {
            testcategorieën = await fetchAll(token, 'testcategorieen');
            return testcategorieën;
        } catch (error) {
            console.error("testcategorieën kon niet gefetched worden:", error);
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