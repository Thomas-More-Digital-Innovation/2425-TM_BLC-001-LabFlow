<script lang="ts">
    import Nav from "../../components/nav.svelte";
    import { onMount } from 'svelte';
    import { getRol } from '$lib/globalFunctions';
    import { fetchStalen } from '$lib/fetchFunctions';
    import { getCookie } from '$lib/globalFunctions';
    // @ts-ignore
    import GoPlus from 'svelte-icons/go/GoPlus.svelte';
    // @ts-ignore
    import IoMdSettings from 'svelte-icons/io/IoMdSettings.svelte';
    // @ts-ignore
    import GoX from 'svelte-icons/go/GoX.svelte'
    // @ts-ignore
    import FaTrashAlt from 'svelte-icons/fa/FaTrashAlt.svelte'
    // @ts-ignore
    import FaRegEdit from 'svelte-icons/fa/FaRegEdit.svelte'
    // modal
    import Modal from '../../components/Modal/Modal.svelte';
    import Trigger from '../../components/Modal/Trigger.svelte';
    import Content from '../../components/Modal/Content.svelte';

    let openModalTestId: number | null = null;

    // achtergrond en klikbaar maken van instellingen gebaseerd op rol
    let bgColor = 'bg-blue-400';
    let pointerEvent = 'pointer-events-auto';
    const rol = getRol();
    if (rol !== 'admin') {
        bgColor = 'bg-gray-400';
        pointerEvent = 'pointer-events-none';
    }

    // fetchen van stalen
    let stalen: any[] = [];
    let filteredStalen: any[] = [];
    let searchCode = '';
    let searchDate = '';
    const token = getCookie('authToken') || '';


    onMount(async () => {
        const result = await fetchStalen();
        if (result) {
            stalen = result.stalen;
            filteredStalen = result.filteredStalen;
        }
    });

    // Function om te filteren op staalcode en datum
    function filterStalen() {
        filteredStalen = stalen.filter(staal => {
            const codeMatch = staal.staalCode.toString().toLowerCase().includes(searchCode.toLowerCase());

            // Converteren searchdate en aanmaakdatum naar een datumobject
            const searchDateObject = new Date(searchDate);
            const aanmaakDatumObject = new Date(staal.aanmaakDatum);

            const isValidDate = !isNaN(searchDateObject.getTime());

            // Date match: vergelijken van de datums, alleen als de searchDate een geldige datum is
            const dateMatch = isValidDate ? 
                (searchDateObject.toDateString() === aanmaakDatumObject.toDateString()) : true;

            return codeMatch && dateMatch;
        });
    }

    function deleteFilters() {
        searchCode = '';
        searchDate = '';
        filterStalen();
    }

    // delete staal
        async function deleteStaal(id: number) {
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
    }
</script>


<Nav />
<div class="px-8 flex flex-row space-x-5">
    <div class="flex flex-col space-y-5">
        <a href='/stalen/nieuw' class="bg-blue-400 flex flex-col items-center justify-center w-72 h-72 rounded-2xl">
            <div class="w-40 h-40 text-white flex items-center justify-center">
                <GoPlus />
            </div>
            <p class="text-white text-2xl text-center mt-2">Nieuwe staal</p>
        </a>

        <a href={rol === 'admin' ? '/instellingen' : '#'}
           class="{bgColor} flex flex-col items-center justify-center w-72 h-72 rounded-2xl {pointerEvent}">
            <div class="w-40 h-40 text-white flex items-center justify-center">
                <IoMdSettings />
            </div>
            <p class="text-white text-2xl text-center mt-2">Instellingen</p>
        </a>
    </div>
    <div class="bg-slate-200 w-full h-full rounded-2xl p-5">
        <!-- filteren op code en datum -->
        <div class="flex space-x-5 mb-5">
            <!-- https://learn.svelte.dev/tutorial/text-inputs -->
            <input 
                type="text" 
                id="searchCode" 
                name="searchCode" 
                placeholder="zoeken op code" 
                bind:value={searchCode} on:input={filterStalen} 
                class="w-2/5 h-12 rounded-lg text-black pl-3">
            <input 
                type="date" 
                id="searchDate" 
                name="searchDate" 
                bind:value={searchDate} 
                on:input={filterStalen} 
                class="w-2/5 h-12 rounded-lg text-black px-3" 
            />
            <!-- verwijder filters -->
            <button class="bg-blue-600 rounded-lg p-3 text-white h-12 w-1/5" type="button" on:click={deleteFilters}>Verwijder filters</button>
        </div>

        <div class="space-y-3">
            {#each filteredStalen as staal, index}
                <div class="grid grid-cols-8 gap-4 bg-white rounded-lg h-16 items-center px-3">
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
                    <div>

                        <!-- Admin-only CRUD buttons -->
                        {#if rol === 'admin'}
                            <div class="col-span-1 flex justify-end space-x-2">
                                <!-- Edit Button -->
                                <Modal>
                                    <Trigger>
                                        <button type="button" class="h-10 w-10 bg-blue-400 p-2 rounded-lg text-white" on:click={async () => { 
                                            openModalTestId = staal.id;
                                        }}>
                                            <FaRegEdit />
                                        </button>
                                    </Trigger>
                                    {#if openModalTestId === staal.id}
                                        <Content>
                                            
                                        </Content>
                                    {/if}
                                </Modal>
        
                                <!-- Delete button -->
                                {#if staal?.confirmDelete}
                                    <button type="button" on:click={() => deleteStaal(staal?.id)} class="h-10 w-10 bg-red-500 p-2 rounded-lg text-white">
                                        <FaTrashAlt />
                                    </button>
                                {:else}
                                    <button type="button" on:click={() => {
                                        filteredStalen.forEach((s, i) => {
                                            if (i !== index) s.confirmDelete = false;
                                        });
                                        staal.confirmDelete = true;
                                    }} class="h-10 w-10 bg-red-300 p-2 rounded-lg text-white">
                                        <GoX />
                                    </button>
                                {/if}
                            </div>
                        {/if}
                    </div>
                </div>
            {/each}
        </div>
    </div>
</div>