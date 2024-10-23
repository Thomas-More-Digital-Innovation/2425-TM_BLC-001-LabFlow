<script lang="ts">
    import { goto } from "$app/navigation";
    // @ts-ignore
    import FaArrowLeft from 'svelte-icons/fa/FaArrowLeft.svelte'
    // @ts-ignore
    import GoX from 'svelte-icons/go/GoX.svelte';
    // @ts-ignore
    import FaTrashAlt from 'svelte-icons/fa/FaTrashAlt.svelte';
    import { onMount } from 'svelte';
    import { fetchStalen } from "$lib/fetchFunctions";
    import { getCookie } from "$lib/globalFunctions";

    const token = getCookie('authToken') || '';

    let searchCode = '';

    // functie voor het filteren op basis van staalcode
    let filteredStalen: any[] = [];
    let stalen: any[] = [];


    function filterStalenMetCode() {
        filteredStalen = stalen.filter(staal => {
            const codeMatch = staal.staalCode.toString().toLowerCase().includes(searchCode.toLowerCase());
            return codeMatch;
        });
    }

    onMount(async () => {
        const result = await fetchStalen();
        if (result) {
            stalen = result.stalen;
            filteredStalen = result.filteredStalen;
        }
    });

    ///// DELETE staal /////
    async function deleteStaal(id: string) {
        console.log(id);
        try {
            await fetch(`http://localhost:8080/api/deletestaal/${id}`, {
                method: "DELETE",
                headers: {
                    "Authorization": "Bearer " + token
                },
            });
        } catch (error) {
            console.error("Staal kon niet worden verwijderd: ", error);
        }
        const result = await fetchStalen();
        if (result) {
            stalen = result.stalen;
            filteredStalen = result.filteredStalen;
        }        
        return;
    }
</script>

<div class="flex flex-col w-full ml-5">
    <div class="flex flex-row justify-between w-full h-14 mb-5">
        <h1 class="font-bold text-3xl">Tests beheren</h1>
        <button type="button" on:click={async () => { await goto("/stalen"); }} class="bg-gray-400 text-xl rounded-lg p-3 text-white h-12 w-32 justify-center items-center flex">
            <div class="w-4 h-4 mr-2"><FaArrowLeft/></div>
            <p>Terug</p>
        </button>
    </div>

    <div class="bg-slate-200 w-full h-full rounded-2xl p-5">

        <div class="flex space-x-5 mb-5">
            <input 
                type="text"
                id="searchCode" 
                name="searchCode" 
                placeholder="zoeken op code"
                bind:value={searchCode} on:input={filterStalenMetCode}
                class="w-2/5 h-12 rounded-lg text-black pl-3">
        </div>
        <div class="space-y-3">
            <!-- Header -->
            <div class="grid grid-cols-8 bg-gray-300 rounded-lg h-10 items-center px-3 font-bold">
                <div class="col-span-1">
                    <p>Code</p>
                </div>
                <div class="col-span-1 text-center">
                    <p>Aanmaakdatum</p>
                </div>
                <div class="col-span-1 text-center">
                    <p>Naam</p>
                </div>
                <div class="col-span-1 text-center">
                    <p>Voornaam</p>
                </div>
                <div class="col-span-1 text-center">
                    <p>Geslacht</p>
                </div>
                <div class="col-span-1 text-center">
                    <p>Geboortedatum</p>
                </div>
                <div class="col-span-1 text-right">
                    <p>Laborant</p>
                </div>
                <div class="col-span-1 text-right">
                    <p>Acties</p>
                </div>
            </div>

            <div class="space-y-3">
                {#each filteredStalen as staal, index}
                    <div class="grid grid-cols-8 bg-white rounded-lg h-16 items-center px-3 shadow-md">
                        <div class="col-span-1">
                            <p>{staal?.staalCode || 'Loading...'}</p>
                        </div>
                        <div class="col-span-1 text-center">
                            <p>{new Date(staal?.aanmaakDatum).toLocaleDateString() || 'Loading...'}</p>
                        </div>
                        <div class="col-span-1 text-center">
                            <p>{staal?.patientAchternaam || 'Loading...'}</p>
                        </div>
                        <div class="col-span-1 text-center">
                            <p>{staal?.patientVoornaam || 'Loading...'}</p>
                        </div>
                        <div class="col-span-1 text-center">
                            <p>{staal?.patientGeslacht === 'V' ? 'Vrouw' : 'Man'}</p>
                        </div>
                        <div class="col-span-1 text-center">
                            <p>{new Date(staal?.patientGeboorteDatum).toLocaleDateString() || 'Loading...'}</p>
                        </div>
                        <div class="col-span-1 text-right">
                            <p>{staal?.laborantNaam || 'Loading...'}</p>
                        </div>
                        <!-- Acties -->
                        <div class="col-span-1 flex justify-end">
                            {#if staal.confirmDelete}
                                <button type="button" on:click={() => deleteStaal(staal?.id)} class="h-10 w-10 bg-red-500 p-2 rounded-lg text-white">
                                    <FaTrashAlt />
                                </button>
                            {:else}
                                <button type="button" on:click={() => {
                                    stalen.forEach((c, i) => {
                                        if (i !== index) c.confirmDelete = false;
                                    });
                                    staal.confirmDelete = true;
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
</div>