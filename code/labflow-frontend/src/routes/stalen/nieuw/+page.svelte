<script lang="ts">
    import Nav from "../../../components/nav.svelte";
    import { goto } from '$app/navigation';
    import { getCookie, fetchAll } from '$lib/globalFunctions';
    import { getRol } from '$lib/globalFunctions';
    // @ts-ignore
    import FaTrashAlt from 'svelte-icons/fa/FaTrashAlt.svelte'
    // @ts-ignore
    import FaRegEdit from 'svelte-icons/fa/FaRegEdit.svelte'

    let tests: any[] = [];

    async function loadData() {
        const token = getCookie('authToken') || '';

        if (token != null) {
            try {
                tests = await fetchAll(token, 'tests');
                console.log(tests);
            } catch (error) {
                console.error("data kon niet gefetched worden:", error);
            }
        } else {
            console.error("jwt error");
            goto('/login');
        }
    }

    loadData();

    const rol = getRol();
</script>

<Nav/>

<div class="px-8 flex flex-row">
    <div class="bg-slate-200 w-full h-full rounded-2xl p-5">
        <div class="w-full pb-5">
            <button class="bg-blue-600 rounded-lg p-3 text-white h-12 w-1/6"> Terug</button>
            <button class="bg-blue-600 rounded-lg p-3 text-white h-12 w-1/6"> Volgende</button>
        </div>
        <div class="space-y-3">
            {#each tests as test}
            <div class="grid grid-cols-12 gap-4 bg-white rounded-lg h-16 items-center px-3">
                <div class="col-span-1">
                    <input type="checkbox" name="select" id="select">
                </div>
                <div class="col-span-2">
                    <p class="text-gray-400">Code</p>
                    <p>{test?.testCode || 'Loading...'}</p>
                </div>
                <div class="col-span-3">
                    <p class="text-gray-400">Naam</p>
                    <p>{test?.naam || 'Loading...'}</p>
                </div>
                <div class="col-span-3">
                    <p class="text-gray-400">Categorie</p>
                    <p>{test?.testcategorie.naam || 'Loading...'}</p>
                </div>
                {#if rol === 'admin'}
                <div class="col-span-1 flex justify-end">
                    <div class="h-10 w-10 bg-blue-400 p-2 rounded-lg">
                        <FaRegEdit />
                    </div>
                </div>
                <div class="col-span-1 flex justify-end">
                    <div class="h-10 w-10 bg-red-500 p-2 rounded-lg">
                        <FaTrashAlt />
                    </div>
                </div>
                {/if}
            </div>            
            {/each}
        </div>
    </div>
</div>