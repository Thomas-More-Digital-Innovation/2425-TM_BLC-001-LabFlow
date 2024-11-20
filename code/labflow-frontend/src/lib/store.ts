import { writable } from "svelte/store";

// session store
function createSessionStore(key:string, initialValue:string) {
    let storedValue;
    if (typeof window !== 'undefined') {
        // Only access sessionStorage in the browser
        storedValue = sessionStorage.getItem(key);
    }

    const store = writable(storedValue ? JSON.parse(storedValue) : initialValue);

    if (typeof window !== 'undefined') {
        store.subscribe((value) => {
            if (value !== undefined) {
                sessionStorage.setItem(key, JSON.stringify(value));
            }
        });
    }

    return store;
}

export const staalCodeStore = createSessionStore('staalCode', 'defaultCode');