<script lang="ts">
    import { goto } from "$app/navigation";
    // @ts-ignore
    import FaArrowLeft from 'svelte-icons/fa/FaArrowLeft.svelte'
    import { onMount } from 'svelte';
    import { fetchStalen } from "$lib/fetchFunctions";

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
            {#each filteredStalen as staal}
                <div class="grid grid-cols-7 gap-4 bg-white rounded-lg h-16 items-center px-3">
                    <div>
                        <p class="text-gray-400">Code</p>
                        <p>{staal?.staalCode || 'Loading...'}</p>
                    </div>
                    <div>
                        <p class="text-gray-400">Aanmaakdatum</p>
                        <p>{new Date(staal?.aanmaakDatum).toLocaleDateString() || 'Loading...'}</p>
                    </div>
                    <div>
                        <p class="text-gray-400">Naam</p>
                        <p>{staal?.patientAchternaam || 'Loading...'}</p>
                    </div>
                    <div>
                        <p class="text-gray-400">Voornaam</p>
                        <p>{staal?.patientVoornaam || 'Loading...'}</p>
                    </div>
                    <div>
                        <p class="text-gray-400">Geslacht</p>
                        <p>{staal?.patientGeslacht === 'V' ? 'Vrouw' : 'Man'}</p>
                    </div>
                    <div>
                        <p class="text-gray-400">Geboortedatum</p>
                        <p>{new Date(staal?.patientGeboorteDatum).toLocaleDateString() || 'Loading...'}</p>
                    </div>
                    <div>
                        <p class="text-gray-400 font-bold">Laborant</p>
                        <p>{staal?.laborantNaam || 'Loading...'}</p>
                    </div>
                </div>
            {/each}
        </div>
    </div>
</div>