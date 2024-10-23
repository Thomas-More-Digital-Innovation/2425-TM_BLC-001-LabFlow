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

    async function updateStaal(id: string) {
        const staal = stalen.find(s => s.id === id);
        console.log(staal);
        if (!staal) return;
        try {
            await fetch(`http://localhost:8080/api/updatestaal/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + token
                },
                body: JSON.stringify({
                    staalCode: staal.staalCode,
                    aanmaakDatum: staal.aanmaakDatum,
                    laborantNaam: staal.laborantNaam,
                    laborantRnummer: staal.laborantRnummer,
                    patientAchternaam: staal.patientAchternaam,
                    patientVoornaam: staal.patientVoornaam,
                    patientGeslacht: staal.patientGeslacht,
                    patientGeboorteDatum: staal.patientGeboorteDatum,
                    user: {
                        id: staal.user.id
                    }
                }),
            });
        } catch (error) {
            console.error("Staal kon niet worden aangepast: ", error);
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

    <div class="bg-slate-100 w-full h-full rounded-2xl p-5">

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
            <div class="grid grid-cols-8 bg-gray-300 rounded-lg h-10 items-center px-3 font-bold space-x-3">
                <div class="col-span-1 text-left">
                    <p>Aanmaakdatum</p>
                </div>
                <div class="col-span-1">
                    <p>Code</p>
                </div>
                <div class="col-span-1 text-left">
                    <p>Naam</p>
                </div>
                <div class="col-span-1 text-left">
                    <p>Voornaam</p>
                </div>
                <div class="col-span-1 text-left">
                    <p>Geslacht</p>
                </div>
                <div class="col-span-1 text-left">
                    <p>Geboortedatum</p>
                </div>
                <div class="col-span-1 text-left ">
                    <p>Laborant</p>
                </div>
                <div class="col-span-1 text-right">
                    <p>Acties</p>
                </div>
            </div>

            <div class="space-y-3">
                {#each filteredStalen as staal, index}
                    <div class="grid grid-cols-8 bg-white rounded-lg h-20 items-center px-3 shadow-md space-x-3">

                        <div class="col-span-1">
                            <span 
                                class=" rounded-lg h-14 text-lg pl-3 w-full p-2">
                                {new Date(staal.aanmaakDatum).toLocaleDateString() || 'Loading...'}
                            </span>
                        </div>
                        <div class="col-span-1">
                            <input type="text" 
                                on:blur={() => updateStaal(staal.id)} 
                                id="staal-{staal?.id}" 
                                bind:value={staal.staalCode} 
                                class="bg-gray-100 rounded-lg h-14 text-lg pl-3 w-full" />
                        </div>
                        <div class="col-span-1">
                            <input type="text" 
                                on:blur={() => updateStaal(staal.id)} 
                                bind:value={staal.patientAchternaam} 
                                class="bg-gray-100 rounded-lg h-14 text-lg pl-3 w-full" />
                        </div>
                        <div class="col-span-1">
                            <input type="text" 
                                on:blur={() => updateStaal(staal.id)} 
                                bind:value={staal.patientVoornaam} 
                                class="bg-gray-100 rounded-lg h-14 text-lg pl-3 w-full" />
                        </div>
                        <div class="col-span-1">
                            <select 
                                on:change={() => updateStaal(staal.id)} 
                                bind:value={staal.patientGeslacht} 
                                class="bg-gray-100 rounded-lg h-14 text-lg pl-3 w-full">
                                <option value="V">Man</option>
                                <option value="M">Vrouw</option>
                            </select>
                        </div>
                        <div class="col-span-1">
                            <input type="date" 
                                on:blur={() => updateStaal(staal.id)}
                                bind:value={staal.patientGeboorteDatum}
                                class="bg-gray-100 rounded-lg h-14 text-lg pl-3 w-full p-2" />
                        </div>
                        <div class="col-span-1 ">
                            <input type="text" 
                                on:blur={() => updateStaal(staal.id)} 
                                bind:value={staal.laborantNaam} 
                                class="bg-gray-100 rounded-lg h-14 text-lg pl-3 w-full" />
                        </div>
            
                        <!-- Acties -->
                        <div class="col-span-1 flex justify-end">
                            {#if staal.confirmDelete}
                                <button type="button" on:click={() => deleteStaal(staal?.id)} class="h-10 w-10 bg-red-500 p-2 rounded-lg text-white">
                                    <FaTrashAlt />
                                </button>
                            {:else}
                                <button type="button" on:click={() => {
                                    filteredStalen.forEach((c, i) => {
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