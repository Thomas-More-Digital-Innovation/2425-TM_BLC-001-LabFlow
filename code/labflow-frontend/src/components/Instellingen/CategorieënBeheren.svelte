<script lang="ts">
    import { goto } from "$app/navigation";
    // @ts-ignore
    import FaArrowLeft from 'svelte-icons/fa/FaArrowLeft.svelte'
    // @ts-ignore
    import GoX from 'svelte-icons/go/GoX.svelte'
    // @ts-ignore
    import FaTrashAlt from 'svelte-icons/fa/FaTrashAlt.svelte'
    import { loadTestCategorieën } from "../../lib/fetchFunctions";
	import { onMount } from "svelte";

    let categorieën: any[] = [];

    onMount(async () => {
        const result = await loadTestCategorieën();
        if (result) {
            categorieën = result;
        }
        console.log(categorieën);
    });

    async function deleteCategorie(id: string) {
        console.log(id);
    }
</script>

<div class="flex flex-col w-full ml-5">
    <div class="flex flex-row justify-between w-full h-14 mb-5">
        <h1 class="font-bold text-3xl">Categorieën beheren</h1>
        <button type="button" on:click={async () => { await goto("/stalen"); }} class="bg-gray-400 text-xl rounded-lg p-3 text-white h-12 w-32 justify-center items-center flex">
            <div class="w-4 h-4 mr-2"><FaArrowLeft/></div>
            <p>Terug</p>
        </button>
    </div>

    <div class="bg-slate-200 w-full h-full rounded-2xl p-5">
        <div class="space-y-3">
            {#each categorieën as categorie, index}
                <div class="grid grid-cols-12 gap-4 bg-white rounded-lg h-20 items-center px-3 shadow-md">
                    <div class="col-span-2">
                        <input type="text" class="text-gray-400 text-sm"/>
                        <p>{categorie?.naam || ''}</p>
                    </div>
                    <div class="flex">
                        <svg viewBox="0 0 100 100" xmlns="http://www.w3.org/2000/svg" class="w-6 h-6">
                            <circle cx="50" cy="50" r="50" fill={categorie?.kleur || '#000'} />
                        </svg>
                    </div>
                    <div>
                        {#if categorie.confirmDelete}
                            <button type="button" on:click={() => deleteCategorie(categorie?.id)} class="h-10 w-10 bg-red-500 p-2 rounded-lg text-white">
                                <FaTrashAlt />
                            </button>
                        {:else}
                        <button type="button" on:click={() => {
                            categorieën.forEach((c, i) => {
                                if (i !== index) c.confirmDelete = false;
                            });
                            categorie.confirmDelete = true;
                        }} class="h-10 w-10 bg-red-300 p-2 rounded-lg text-white">
                            <GoX />
                        </button>
                    {/if}
                    </div>
                </div>
            {/each}
        </div>
    </div>
</div>